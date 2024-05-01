package models.services

import models.{ProductId, Title}
import models.dto.{ProductCreateDTO, ProductDTO}


trait ProductService {
  def addProduct(productCreateDTO: ProductCreateDTO): ProductDTO

  def updateProducts(productUpdateDTO: ProductDTO): Option[ProductDTO]

  def deleteProduct(productId: ProductId): Boolean

  def getProducts(title: Option[Title]): List[ProductDTO]

}


