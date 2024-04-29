package models.dao

import org.squeryl.{KeyedEntity, Schema}

case class Product(id: String, title: String, description: String) extends KeyedEntity[String] {
  lazy val productItems = Product.productsItems.left(this)
}

object Product extends Schema {

  import org.squeryl.PrimitiveTypeMode._

  val products = table[Product]
  val productItems = table[ProductItem]
  val productsItems = oneToManyRelation(products, productItems).via(_.id === _.productId)
}
