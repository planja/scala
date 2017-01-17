package controllers

import play.api.mvc._


object Application extends Controller{

  def index = Action {
    Ok(views.html.index())
  }

  def test() = Action {
    Ok(views.html.test("test"))
  }

  def param(name: String) = Action {
    Ok(views.html.hello(name))
  }

}