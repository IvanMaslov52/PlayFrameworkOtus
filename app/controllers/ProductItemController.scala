package controllers

import models.services.ProductServiceImpl
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

object ProductItemController extends Controller{
  def getProductItems: Action[AnyContent] = Action {
    Ok(Json.toJson(ProductServiceImpl.getProductItems))
  }
}
