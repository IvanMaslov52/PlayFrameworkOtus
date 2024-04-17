package models.dto

import jdk.jfr.Description
import models.{Email, User, UserId}
import play.api.libs.functional.syntax.{functionalCanBuildApplicative, toFunctionalBuilderOps}
import play.api.libs.json.{JsPath, Json, Reads, Writes}

case class ProductDTO(id: String, title: String, description: String) {
}


object ProductDTO {
  implicit val reads: Reads[ProductDTO] = Json.reads[ProductDTO]
  implicit val writes: Writes[ProductDTO] = Json.writes[ProductDTO]

  implicit val read: Reads[ProductDTO] = (
      (JsPath \ "title").read[String] and
      (JsPath \ "description").read[String]
    )(ProductDTO.create _)

  def create(title: String, description: String): ProductDTO = ProductDTO("", title, description)
}
