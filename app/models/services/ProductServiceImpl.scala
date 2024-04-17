package models.services

import models.{Product, ProductId, ProductItem, Title}
import models.dto.{ProductDTO, ProductItemDTO}

import scala.collection.mutable.ListBuffer


object ProductServiceImpl extends ProductService {
  private val products = ListBuffer(
    Product("45628dfc-a25e-4854-97f0-cee12a6a8968", "product1", "product1"),
    Product("be2e977b-f4aa-4bc3-a0da-09fe5cefd00f", "product1", "product2"),
    Product("d0902a23-43f8-424d-b20b-5a965e7c311f", "product3", "product3"))
  private var productItems = ListBuffer(
    ProductItem(java.util.UUID.randomUUID.toString, "45628dfc-a25e-4854-97f0-cee12a6a8968", 39, 100, true),
    ProductItem(java.util.UUID.randomUUID.toString, "be2e977b-f4aa-4bc3-a0da-09fe5cefd00f", 50, 67, false),
    ProductItem(java.util.UUID.randomUUID.toString, "d0902a23-43f8-424d-b20b-5a965e7c311f", 46, 78, true),
    ProductItem(java.util.UUID.randomUUID.toString, "45628dfc-a25e-4854-97f0-cee12a6a8968", 56, 71, true))


  override def addProduct(productDTO: ProductDTO): ProductDTO = {
    val id = java.util.UUID.randomUUID.toString
    val product = Product(id, productDTO.title, productDTO.description)
    products += product
    productToDTO(product)
  }

  override def updateProduct(productDTO: ProductDTO): Option[ProductDTO] = {
    val index = products.indexWhere(_.id == productDTO.id)
    if (index != -1) {
      val product = dTOToProduct(productDTO)
      products.update(index, product)
      Some(productToDTO(product))
    } else {
      None
    }
  }

  override def deleteProduct(productId: ProductId): Boolean = {
    val index = products.indexWhere(_.id == productId.raw)
    if (index != -1) {
      productItems = productItems.filter(item => item.productId != productId.raw)
      products.remove(index)
      true
    } else {
      false
    }
  }

  override def getProducts(title: Option[Title]): List[ProductDTO] = {
    title match {
      case Some(title) if title.raw.nonEmpty => products.toList.filter(_.title == title.raw).map(productToDTO)
      case _ => products.toList.map(productToDTO)
    }
  }

  def getProductItems: List[ProductItemDTO] = {
    productItems.toList.map(x => ProductItemDTO(x.id, x.productId, x.price, x.quantity, x.inStock))
  }

  private def dTOToProduct(productDTO: ProductDTO): Product = Product(productDTO.id, productDTO.title, productDTO.description)

  private def productToDTO(product: Product): ProductDTO = ProductDTO(product.id, product.title, product.description)
}
