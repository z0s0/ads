package ads

import ads.api.{Docs, Routes}
import ads.config.AppConfig
import ads.repo.{AdsRepo, ClicksRepo}
import ads.service.{AdsService, GetAdService}
import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter

import scala.concurrent.ExecutionContext.global

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val swaggerEndpoints = SwaggerInterpreter().fromEndpoints[IO](Docs.allDocs, "Ads", "0.1")

    val program = for {
      config <- AppConfig.load()
      transactor = DbConnection.of(config.db)
      adsRepo = AdsRepo.pg(transactor)
      clicksRepo = ClicksRepo.pg(transactor)
      getAdAlgo = GetAdService.naive
      adsService = AdsService.of(adsRepo, getAdAlgo)
      routes = new Routes(adsService).list
      interpreter = Http4sServerInterpreter[IO]().toRoutes(routes ++ swaggerEndpoints)
      router = Router("/" -> interpreter).orNotFound

      _ <- BlazeServerBuilder[IO](global)
             // TODO read from conf
             .bindHttp(8080, "localhost")
             .withHttpApp(router)
             .serve
             .compile
             .drain
    } yield ExitCode.Success

    program
  }
}