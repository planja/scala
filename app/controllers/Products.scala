package controllers

import play.api.mvc.{Action, Controller}
import models.Product

/**
  * Created by ShchykalauM on 09.01.2017.
  */
object Products extends Controller {

  def list() = Action { implicit request =>
    val products = Product.findAll
    Ok(views.html.products.list(products))
  }


}
