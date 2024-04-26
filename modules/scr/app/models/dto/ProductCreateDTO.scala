package models.dto

import play.api.libs.json.{Json, Reads, Writes}

case class ProductCreateDTO(title: String, description: String, productItems: List[ProductItemCreateDTO])

object ProductCreateDTO {
  implicit val reads: Reads[ProductCreateDTO] = Json.reads[ProductCreateDTO]
  implicit val writes: Writes[ProductCreateDTO] = Json.writes[ProductCreateDTO]
}
