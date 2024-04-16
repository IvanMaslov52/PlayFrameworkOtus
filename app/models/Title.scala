package models

import play.api.mvc.QueryStringBindable

case class Title(raw: String)

object Title {
  implicit def queryBinder(implicit binder: QueryStringBindable[String]): QueryStringBindable[Option[Title]] =
    new QueryStringBindable[Option[Title]] {
      override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, Option[Title]]] =
        for{
          id <- binder.bind("title", params)
        } yield id match {
          case Right(raw) => Right(Some(Title(raw)))
          case _ => Left("Unable to bind Title")
        }

      override def unbind(key: String, value: Option[Title]): String =
        binder.unbind("title", value.map(_.raw).getOrElse("product"))
    }
}

