package models.dao

import org.squeryl.{KeyedEntity, Schema}

case class ProductItem(id: String, productId: String, price: Int, quantity: Int, inStock: Boolean) extends KeyedEntity[String]


object ProductItem extends Schema {
  val productItems = table[ProductItem]
}
