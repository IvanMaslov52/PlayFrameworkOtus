package models.services

import models.{ProductId, Title}
import models.dto.ProductDTO
import play.api.libs.json.JsValue
import play.api.mvc.{AnyContent, Request, Result}


trait ProductService {
  def addProduct(productDTO: ProductDTO): ProductDTO

  def updateProduct(productDTO: ProductDTO): Option[ProductDTO]

  def deleteProduct(productId: ProductId): Boolean

  def getProducts: List[ProductDTO]

  def getProductsByTitle(title: Option[Title]): List[ProductDTO]
}


