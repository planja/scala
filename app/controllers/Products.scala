package controllers

import models.{Product, ProductCompanion, ProductDao}
import play.api.data.Form
import play.api.data.Forms.{longNumber, mapping, nonEmptyText}
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import play.api.mvc.Flash
import play.api.mvc.{Action, Controller}
import play.api.data.Forms._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}


/**
  * Created by ShchykalauM on 09.01.2017.
  */
object Products extends Controller {

  def list() = Action { implicit request =>

    //val test = ProductDao.listAll

    //val test = ProductDao.listAll
    val newId = Await.ready(ProductDao.add(Product(null, 321, "0", "0")), Duration.Inf).value.get
    val test = Await.ready(ProductDao.listAll, Duration.Inf).value.get
    ProductDao.delete(2)

    /*ProductDao.delete(1).andThen {
      case Success(_) =>
        null
      case Failure(e) =>
        e.printStackTrace()
    }*/
    println("qwerty")

    //.delete(1)
    //
    //val test = ProductDao.listAll

    // val products = ProductCompanion.findAll

    Ok(views.html.products.list(null))
  }

  def show(id: Long) = Action { implicit request =>
    ProductCompanion.findById(id).map { product =>
      Ok(views.html.products.details(product))
    }.getOrElse(NotFound)
  }

  def delete(id: Long) = Action { implicit request =>
    ProductCompanion.findById(id).map { product =>
      ProductCompanion.delete(product)
      Redirect(routes.Products.list())
    }.getOrElse(NotFound)
  }

  def newProduct = Action { implicit request =>
    val form = if (request.flash.get("error").isDefined)
      productForm.bind(request.flash.data)
    else
      productForm
    Ok(views.html.products.save(form))
  }

  def edit(ean: Long) = Action { implicit request =>
    ProductCompanion.findById(ean) match {
      case Some(product) =>
        val form = productForm.fill(product)
        Ok(views.html.products.edit(form, product.id.get))
      case None => NotFound
    }
  }

  def update(id: Long) = Action { implicit request =>
    val updatedProductForm = productForm.bindFromRequest()
    updatedProductForm.fold(
      hasErrors = { form =>
        Redirect(routes.Products.edit(id)).
          flashing(Flash(form.data) +
            ("error" -> "validation errors"))
      },
      success = { newProduct =>
        ProductCompanion.update(id, newProduct)
        val message = "success"
        Redirect(routes.Products.show(id)).
          flashing("success" -> message)
      }
    )

  }

  def save = Action { implicit request =>
    val newProductForm = productForm.bindFromRequest()
    newProductForm.fold(
      hasErrors = { form =>
        Redirect(routes.Products.newProduct()).
          flashing(Flash(form.data) +
            ("error" -> "validation errors"))
      },
      success = { newProduct =>
        ProductCompanion.add(newProduct)
        val message = "success"
        Redirect(routes.Products.show(newProduct.id.get)).
          flashing("success" -> message)
      }
    )
  }

  val productForm: Form[Product] = Form(
    mapping(
      "id" -> optional(longNumber),
      "ean" -> longNumber,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(Product.apply)(Product.unapply)
  )


}
