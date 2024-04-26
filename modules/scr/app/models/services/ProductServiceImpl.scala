package models.services

import models.{Product, ProductId, ProductItem, Title}
import models.dto.{ProductCreateDTO, ProductDTO, ProductItemCreateDTO, ProductItemDTO}

import scala.collection.mutable


class ProductServiceImpl extends ProductService {
  private val products = mutable.Map[String, Product](
    "45628dfc-a25e-4854-97f0-cee12a6a8968" -> Product("45628dfc-a25e-4854-97f0-cee12a6a8968", "product1", "product1"),
    "be2e977b-f4aa-4bc3-a0da-09fe5cefd00f" -> Product("be2e977b-f4aa-4bc3-a0da-09fe5cefd00f", "product1", "product2"),
    "d0902a23-43f8-424d-b20b-5a965e7c311f" -> Product("d0902a23-43f8-424d-b20b-5a965e7c311f", "product3", "product3"))
  private val productItems = mutable.Map[String, ProductItem](
    "1d1b314f-d701-46da-bb2b-dc5c0dbb56ac" -> ProductItem("1d1b314f-d701-46da-bb2b-dc5c0dbb56ac",
      "45628dfc-a25e-4854-97f0-cee12a6a8968", 39, 100, true),
    "49becb08-95e5-42fe-b014-e7df4400edfa" -> ProductItem("49becb08-95e5-42fe-b014-e7df4400edfa",
      "be2e977b-f4aa-4bc3-a0da-09fe5cefd00f", 50, 67, false),
    "eee18a94-3d1e-42c1-980c-8cbf5ed52b49" -> ProductItem("eee18a94-3d1e-42c1-980c-8cbf5ed52b49",
      "d0902a23-43f8-424d-b20b-5a965e7c311f", 46, 78, true),
    "cc84512f-10e8-4aca-99d3-6bafce327c14" -> ProductItem("cc84512f-10e8-4aca-99d3-6bafce327c14",
      "45628dfc-a25e-4854-97f0-cee12a6a8968", 56, 71, true))

  override def addProduct(productCreateDTO: ProductCreateDTO): ProductDTO = {
    if (products.exists { case (_, value) => value.title == productCreateDTO.title }) {
      val product = products.find { case (_, value) => value.title == productCreateDTO.title }.get._2
      addProductItems(product.id, productCreateDTO.productItems)
      productToDTO(product)
    }
    else {
      val id = java.util.UUID.randomUUID.toString
      products += (id -> Product(id, productCreateDTO.title, productCreateDTO.description))
      addProductItems(id, productCreateDTO.productItems)
      productToDTO(products(id))
    }
  }

  override def updateProducts(productUpdateDTO: ProductDTO): Option[ProductDTO] = {
    if (productItems.contains(productUpdateDTO.id) && hasItem(productUpdateDTO.productItems)) {
      updateProducts(products(productUpdateDTO.id), productUpdateDTO)
      updateItems(productUpdateDTO.productItems)
      Some(productToDTO(products(productUpdateDTO.id)))
    } else {
      None
    }
  }

  override def deleteProduct(productId: ProductId): Boolean = {
    if (products.contains(productId.raw)) {
      productItems --= productItems.values.toList.map(_.productId).filter(_ == productId.raw)
      products -= productId.raw
      true
    } else {
      false
    }
  }

  override def getProducts(title: Option[Title]): List[ProductDTO] = {
    title match {
      case Some(title) if title.raw.trim.nonEmpty => products.values.toList.filter(_.title == title.raw).map(productToDTO)
      case _ => products.values.toList.map(productToDTO)
    }
  }

  private def addProductItems(productId: String, list: List[ProductItemCreateDTO]): Unit = {
    list.foreach { item =>
      val id = java.util.UUID.randomUUID.toString
      productItems += (id -> ProductItem(id, productId, item.price, item.quantity, item.inStock))
    }
  }

  private def productItemToDTO(productItem: ProductItem): ProductItemDTO =
    ProductItemDTO(productItem.id, productItem.productId, productItem.price, productItem.quantity, productItem.inStock)

  private def productToDTO(product: Product): ProductDTO = {
    ProductDTO(product.id, product.title, product.description,
      productItems.values.toList
        .filter(_.productId == product.id)
        .map(productItemToDTO))
  }

  private def hasItem(list: List[ProductItemDTO]): Boolean = {
    list.map(product => productItems.contains(product.id)).forall(Boolean => Boolean)
  }

  private def updateItems(list: List[ProductItemDTO]): Unit = {
    list.foreach(item => productItems.update(item.id, updateItem(item, productItems(item.id))))

  }

  private def updateProducts(product: Product, productUpdateDTO: ProductDTO): Unit = {
    products.update(product.id, Product(product.id, product.title, productUpdateDTO.description))
  }

  private def updateItem(productItemDTO: ProductItemDTO, productItem: ProductItem): ProductItem = {
    ProductItem(productItem.id, productItem.productId, productItemDTO.price, productItemDTO.quantity, productItemDTO.inStock)
  }
}
