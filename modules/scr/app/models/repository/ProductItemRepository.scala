package models.repository

import models.dao.ProductItem
import org.squeryl.Table

trait ProductItemRepository extends CrudRepository[String, ProductItem]{
}

class ProductItemRepositoryImpl extends ProductItemRepository {
  override def defaultTable: Table[ProductItem] = ProductItem.productItems
}


