package controllers

import com.google.inject.Inject
import models.{ProductId, Title}
import models.dto.{ProductCreateDTO, ProductDTO}
import models.services.ProductService
import play.api.libs.json.Json
import play.api.mvc._


class ProductController @Inject()(val productService: ProductService) extends Controller {

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

}
