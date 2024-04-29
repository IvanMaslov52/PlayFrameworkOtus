package controllers

import com.google.inject.Inject
import models.{ProductId, Title}
import models.dto.{ProductCreateDTO, ProductDTO}
import models.repository.{ProductItemRepository, ProductRepository}
import models.services.ProductService
import play.api.libs.json.Json
import play.api.mvc._

import java.util.UUID


class ProductController @Inject()(val productService: ProductService, productRepository: ProductRepository, productItemRepository: ProductItemRepository) extends Controller {

  def addProduct = Action(parse.json[ProductCreateDTO]) { rc =>
    Created(Json.toJson(productService.addProduct(rc.body)))
  }

  def deleteProduct(productId: ProductId): Action[AnyContent] = Action {
    if (productService.deleteProduct(productId))
      NoContent else NotFound("Product not found")
  }

  def updateProduct: Action[ProductDTO] = Action(parse.json[ProductDTO]) { request =>
    productService.updateProducts(request.body) match {
      case Some(productDTO: ProductDTO) => Ok(Json.toJson(productDTO))
      case None => NotFound("Product not found")
    }
  }

  def getProducts(title: Option[Title]): Action[AnyContent] = Action {
    Ok(Json.toJson(productService.getProducts(title)))
  }

  def test = Action {
    import org.squeryl.PrimitiveTypeMode._
    //productRepository.insert(Product("45628dfc-a25e-4854-97f0-cee12a6a8968", "product1", "product1"))
    //Ok(Json.toJson(productRepository.findWithTitle("product1").map(x=>ProductDTO(x.id,x.title,x.description,List()))))
    //productRepository.update(Product("45628dfc-a25e-4854-97f0-cee12a6a8968", "product1", "some products"))
    /*val list = List(
      ProductItem(java.util.UUID.randomUUID.toString,
      "45628dfc-a25e-4854-97f0-cee12a6a8968", 1321, 100, false),
      ProductItem(java.util.UUID.randomUUID.toString,
      "45628dfc-a25e-4854-97f0-cee12a6a8968", 231321, 100, true))
    productItemRepository.insertList(list)*/
    Ok
  }

}
