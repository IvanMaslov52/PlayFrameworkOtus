package controllers

import models.{ProductId, Title}
import models.dto.ProductDTO
import models.services.ProductServiceImpl
import play.api.libs.json.Json
import play.api.mvc._

object ProductController extends Controller{

  def getProducts: Action[AnyContent] = Action {
    Ok(Json.toJson(ProductServiceImpl.getProducts))
  }

  def addProduct: Action[ProductDTO] = Action(parse.json[ProductDTO]) { request =>
    Created(Json.toJson(ProductServiceImpl.addProduct(request.body)))
  }

  def deleteProduct(productId: ProductId): Action[AnyContent] = Action {
    if (ProductServiceImpl.deleteProduct(productId))
      NoContent else NotFound("Product not found")
  }

  def updateProduct: Action[ProductDTO] = Action(parse.json[ProductDTO]) { request =>
    ProductServiceImpl.updateProduct(request.body) match {
      case Some(productDTO: ProductDTO) => Ok(Json.toJson(productDTO))
      case None => NotFound("Product not found")
    }
  }

  def getProductsByTitle(title: Option[Title]): Action[AnyContent] = Action {
    Ok(Json.toJson(ProductServiceImpl.getProductsByTitle(title)))
  }
}
