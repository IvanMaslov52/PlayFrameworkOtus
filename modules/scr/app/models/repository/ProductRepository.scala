package models.repository

import models.dao.Product
import org.squeryl.Table


trait ProductRepository extends CrudRepository[String, Product] {
  def findWithTitle(title: String): Option[Product]
}

class ProductRepositoryImpl extends ProductRepository {
  override def defaultTable: Table[Product] = Product.products

  override def findWithTitle(title: String): Option[Product] =
    transaction(from(Product.products)(r => where(r.title === title) select(r) ).headOption)
}
