package config

import com.typesafe.slick.driver.db2.DB2Driver
import slick.driver.MySQLDriver
import slick.driver.PostgresDriver


object SlickDBDriver {
  val driver = play.api.Play.current.configuration.getString("mydb.driverjava").getOrElse("dev") match {
    case "freeslick.DB2Profile$" => DB2Driver
    case "slick.driver.PostgresDriver$" => PostgresDriver
    case _ => MySQLDriver
  }
}