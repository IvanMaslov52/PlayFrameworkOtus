package models

import play.api.mvc.PathBindable

case class ProductId(raw: String)

object ProductId {
  implicit val userId: PathBindable[ProductId] = new PathBindable[ProductId] {
    override def bind(key: String, value: String): Either[String, ProductId] =
      implicitly[PathBindable[String]].bind(key, value).right.map(ProductId(_))

    override def unbind(key: String, value: ProductId): String =
      implicitly[PathBindable[String]].unbind(key, value.raw)
  }
}
