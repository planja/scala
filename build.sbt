name := "untitled"

version := "1.0"

lazy val `untitled` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

val sparkSQL       = "org.apache.spark"  %% "spark-sql" % "1.4.1" % "compile"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  filters,
  specs2 % Test,
  "com.github.nscala-time" %% "nscala-time" % "2.0.0",
  "mysql" % "mysql-connector-java" % "5.1.20",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "org.suecarter" % "freeslick_2.11" % "3.1.1.1",
  "org.flywaydb" %% "flyway-play" % "3.0.1",
  "javax.ws.rs" % "jsr311-api" % "1.0",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.0.0",
  "commons-io" % "commons-io" % "2.3",
  "org.apache.commons" % "commons-csv" % "1.3",
  "org.asynchttpclient" % "async-http-client" % "2.0.4",
  "com.box" % "box-java-sdk" % "2.1.1",
  "org.scala-lang.modules" % "scala-java8-compat_2.11" % "0.3.0",
  sparkSQL,
  cache
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"