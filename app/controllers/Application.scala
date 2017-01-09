package controllers

import play.api.i18n.I18nSupport
import play.api.mvc._


object Application extends Controller{

  def index = Action {
    Redirect(routes.Products.list())
  }

  def test() = Action {
    Ok(views.html.test("test"))
  }

  def param(name: String) = Action {
    Ok(views.html.hello(name))
  }

}