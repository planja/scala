package controllers

import play.api.mvc.{Action, Controller}
import models.{Product, ProductCompanion, ProductDao}
import play.api.data.Form
import play.api.data.Forms.{longNumber, mapping, nonEmptyText}
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import play.api.mvc.Flash


/**
  * Created by ShchykalauM on 09.01.2017.
  */
object Products extends Controller {

  def list() = Action { implicit request =>
    ProductDao.delete(1)
    ProductDao.add(Product(2, 123, "0", "0"))
    val products = ProductCompanion.findAll
    val test = ProductDao.listAll
    Ok(views.html.products.list(products))
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
        Ok(views.html.products.edit(form, product.id))
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
        Redirect(routes.Products.show(newProduct.id)).
          flashing("success" -> message)
      }
    )
  }

  val productForm: Form[Product] = Form(
    mapping(
      "id" -> longNumber,
      "ean" -> longNumber,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(Product.apply)(Product.unapply)
  )


}
