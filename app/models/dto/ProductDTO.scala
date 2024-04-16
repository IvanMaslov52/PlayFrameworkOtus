package models.dto

import play.api.libs.json.{Json, Reads, Writes}

case class ProductDTO(id: String, title: String, description: String) {
}

object ProductDTO {
  implicit val reads: Reads[ProductDTO] = Json.reads[ProductDTO]
  implicit val writes: Writes[ProductDTO] = Json.writes[ProductDTO]
}
