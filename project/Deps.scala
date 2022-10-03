import sbt._

object Deps {
  private object versions {
    val slf4j = "1.7.30"
    val http4s = "0.23.11"
    val doobie = "1.0.0-RC1"
    val tapir = "1.0.0-M7"
    val circe = "0.14.1"
    val catsEffect = "3.3.11"
    val cats = "2.8.0"
    val logback = "1.2.3"
    val pureConfigVersion = "0.17.1"
    val postgresql = "42.2.15"
  }

  val db = List("org.postgresql" % "postgresql" % versions.postgresql)

  val config = List("com.github.pureconfig" %% "pureconfig" % versions.pureConfigVersion)

  val circe = List(
    "io.circe" %% "circe-core" % versions.circe,
    "io.circe" %% "circe-generic" % versions.circe,
    "io.circe" %% "circe-parser" % versions.circe
  )

  val doobie = List(
    "org.tpolecat" %% "doobie-core" % versions.doobie,
    "org.tpolecat" %% "doobie-hikari" % versions.doobie,
    "org.tpolecat" %% "doobie-postgres" % versions.doobie
  )

  val http4s = List(
    "org.http4s" %% "http4s-blaze-server" % versions.http4s,
    "org.http4s" %% "http4s-circe" % versions.http4s,
    "org.http4s" %% "http4s-dsl" % versions.http4s
  )

  val tapir = List(
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-core" % versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % versions.tapir,
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % versions.tapir
  )

  val cats = List(
    "org.typelevel" %% "cats-core" % versions.cats,
    "org.typelevel" %% "cats-effect" % versions.catsEffect,
    "org.typelevel" %% "cats-effect-std" % versions.catsEffect
  )

  val log = Seq(
    "ch.qos.logback" % "logback-classic" % versions.logback,
    "org.slf4j" % "slf4j-api" % versions.slf4j
  )

  val all = cats ++ tapir ++ http4s ++ circe ++ db ++ config ++ doobie ++ log
}
