package models.dto

import play.api.libs.json.{Json, Reads, Writes}

case class ProductItemCreateDTO(price: Int, quantity: Int, inStock: Boolean)
object ProductItemCreateDTO{

  implicit val reads: Reads[ProductItemCreateDTO] = Json.reads[ProductItemCreateDTO]
  implicit val writes: Writes[ProductItemCreateDTO] = Json.writes[ProductItemCreateDTO]
}
