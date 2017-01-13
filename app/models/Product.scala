package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.Tag

import config.SlickDBDriver.driver.api._

import scala.concurrent.Future

import scala.concurrent._
import ExecutionContext.Implicits.global

/**
  * Created by ShchykalauM on 09.01.2017.
  */
case class Product(id: Long,ean: Long,name: String,description: String) {


  /*def this(id: Long, ean: Long, name: String, description: String) {
    this(ean, name, description)
    this.id = id
  }*/
}

class ProductTableDef(tag: Tag) extends Table[Product](tag, "product") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def ean = column[Long]("ean")

  def name = column[String]("name")

  def description = column[String]("description")

  def * = (id, ean, name, description) <> ((Product.apply _).tupled, Product.unapply)

}

object ProductDao {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val products = TableQuery[ProductTableDef]

  def listAll: Future[Seq[Product]] = {
    dbConfig.db.run(products.result)
  }

  def add(user: Product): Future[String] = {
    dbConfig.db.run(products += user).map(res => "Product successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(products.filter(_.id === id).delete)
  }

}

object ProductCompanion {
  private var products = Set(
    new Product(1, 5010255079763L, "Paperclips Large",
      "Large Plain Pack of 1000"),
    new Product(2, 5018206244666L, "Giant Paperclips",
      "Giant Plain 51mm 100 pack"),
    new Product(3, 5018306332812L, "Paperclip Giant Plain",
      "Giant Plain Pack of 10000"),
    new Product(4, 5018306312913L, "No Tear Paper Clip",
      "No Tear Extra Large Pack of 1000"),
    new Product(5, 5018206244611L, "Zebra Paperclips",
      "Zebra Length 28mm Assorted 150 Pack")
  )

  def getMax(products: Set[Product]): Long = {
  /*  var max: Long = 0
    for (product <- products) {
      if (max <= product.getId)
        max = product.getId
    }
    max + 1*/
    0
  }


  def add(product: Product): Unit = {
    //product.setId(getMax(products))
    products = products + product
  }

  def findById(id: Long): Option[Product] = {
   //products.find(_.getId == id)
    null
  }

  def update(id: Long, newProduct: Product): Unit = {
    var product = this.findById(id)
    if (product.isDefined) {
     /* product.get.setId(id)
      product.get.setEan(newProduct.getEan)
      product.get.setName(newProduct.getName)
      product.get.setDescription(newProduct.getDescription)*/
    }
  }

  def delete(product: Product): Unit = {
    products = products - product
  }

  def findAll: List[Product] = {
    //products.toList.sortBy(_.getName)
    null
  }

}