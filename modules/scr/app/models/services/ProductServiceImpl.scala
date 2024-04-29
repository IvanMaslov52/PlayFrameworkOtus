package models.services

import com.google.inject.Inject
import models.dao.{Product, ProductItem}
import models.{ProductId, Title}
import models.dto.{ProductCreateDTO, ProductDTO, ProductItemCreateDTO, ProductItemDTO}
import models.repository.{ProductItemRepository, ProductRepository}
import org.squeryl.PrimitiveTypeMode.transaction


class ProductServiceImpl @Inject()(productRepository: ProductRepository, productItemRepository: ProductItemRepository) extends ProductService {

  override def addProduct(productCreateDTO: ProductCreateDTO): ProductDTO = {
    val foundProduct = productRepository.findWithTitle(productCreateDTO.title)
    if (foundProduct.isDefined) {
      addProductItems(foundProduct.get.id, productCreateDTO.productItems)
      productToDTO(foundProduct.get)
    } else {
      val id = java.util.UUID.randomUUID.toString
      val product = productRepository.insert(Product(id, productCreateDTO.title, productCreateDTO.description))
      addProductItems(id, productCreateDTO.productItems)
      productToDTO(product)
    }
  }

  override def updateProducts(productUpdateDTO: ProductDTO): Option[ProductDTO] = {
    val foundedProduct = productRepository.find(productUpdateDTO.id)
    if (foundedProduct.isDefined && hasItem(productUpdateDTO.productItems)) {

      updateProducts(foundedProduct.get, productUpdateDTO)
      updateItems(productUpdateDTO.productItems)

      Some(productToDTO(foundedProduct.get))
    } else {
      None
    }
  }

  override def deleteProduct(productId: ProductId): Boolean = {
    import org.squeryl.PrimitiveTypeMode._

    val foundedProduct = productRepository.find(productId.raw)
    if (foundedProduct.isDefined) {
      productItemRepository.deleteWhere(_.productId === foundedProduct.get.id)
      productRepository.delete(foundedProduct.get)
    } else {
      false
    }
  }

  override def getProducts(title: Option[Title]): List[ProductDTO] = {
    title match {
      case Some(title) if title.raw.trim.nonEmpty => productRepository
        .list()
        .filter(_.title == title)
        .map(productToDTO)
      case _ => productRepository
        .list()
        .map(productToDTO)
    }
  }

  private def addProductItems(productId: String, list: List[ProductItemCreateDTO]): Unit = {
    list.foreach { item =>
      val id = java.util.UUID.randomUUID.toString
      productItemRepository.insert(ProductItem(id, productId, item.price, item.quantity, item.inStock))
    }
  }

  private def productItemToDTO(productItem: ProductItem): ProductItemDTO =
    ProductItemDTO(productItem.id, productItem.productId, productItem.price, productItem.quantity, productItem.inStock)

  private def productToDTO(product: Product): ProductDTO = {
    transaction {
      ProductDTO(product.id, product.title, product.description, product.productItems.toList.map(productItemToDTO))
    }
  }

  private def hasItem(list: List[ProductItemDTO]): Boolean = {
    list.map(item => productItemRepository.find(item.id).isDefined).forall(Boolean => Boolean)
  }

  private def updateItems(list: List[ProductItemDTO]): Unit = {
    list.foreach(item => productItemRepository.update(updateItem(item, productItemRepository.find(item.id).get)))

  }

  private def updateProducts(product: Product, productUpdateDTO: ProductDTO): Unit = {
    productRepository.update(Product(product.id, product.title, productUpdateDTO.description))
  }

  private def updateItem(productItemDTO: ProductItemDTO, productItem: ProductItem): ProductItem = {
    ProductItem(productItem.id, productItem.productId, productItemDTO.price, productItemDTO.quantity, productItemDTO.inStock)
  }
}
