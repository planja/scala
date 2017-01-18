package models

import slick.lifted.Tag
import config.SlickDBDriver.driver.api._

import scala.concurrent._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.driver.JdbcProfile

/**
  * Created by ShchykalauM on 09.01.2017.
  */
case class Product(id: Option[Long], ean: Long, name: String, description: String) {
}

object Product {
  implicit val personFormat = Json.format[Product]
}

class ProductTableDef(tag: Tag) extends Table[Product](tag, "PRODUCT") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def ean = column[Long]("EAN")

  def name = column[String]("NAME")

  def description = column[String]("DESCRIPTION")

  def * = (id.?, ean, name, description) <> ((Product.apply _).tupled, Product.unapply)

}

object ProductDao {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val products = TableQuery[ProductTableDef]

  val insertQuery = (product: Product) => {
    (products += product).flatMap { x =>
      products.sortBy {
        _.id.desc.nullsFirst
      }.map(_.id).result.head
    }
  }

  def findById(id: Long): Future[Option[Product]] = {
    dbConfig.db.run(products.filter(_.id === id).result.headOption)
  }


  def listAll: Future[Seq[Product]] = {
    dbConfig.db.run(products.result)
  }

  def add(product: Product): Future[Long] = dbConfig.db.run {
    insertQuery(product)
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(products.filter(_.id === id).delete)
  }


  def update(updatedProduct: Product): Product = {
    val action = products.filter(_.id === updatedProduct.id).map(product => (product.name, product.ean, product.description))
      .update(updatedProduct.name, updatedProduct.ean, updatedProduct.description)
    val future = dbConfig.db.run(action)
    updatedProduct
  }

}

object ProductCompanion {
  private var products = Set(
  )

  def getMax(products: Set[Product]): Long = {
    0
  }


  def add(product: Product): Unit = {
  }

  def findById(id: Long): Option[Product] = {
    null
  }

  def update(id: Long, newProduct: Product): Unit = {
    var product = this.findById(id)
    if (product.isDefined) {
    }
  }

  def delete(product: Product): Unit = {
  }

  def findAll: List[Product] = {
    null
  }

}