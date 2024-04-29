package models.repository

import models.dao.ProductItem
import org.squeryl.Table

trait ProductItemRepository extends CrudRepository[String, ProductItem] {
  def findByList(list: List[String]): Boolean
}

class ProductItemRepositoryImpl extends ProductItemRepository {
  override def defaultTable: Table[ProductItem] = ProductItem.productItems

  override def findByList(list: List[String]): Boolean = transaction {
    from(defaultTable)(item =>
      where(item.id in list)
        select (item)
    ).size == list.size
  }
}


