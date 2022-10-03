package ads.repo

import ads.service.Ads.Params.AdParams
import cats.effect.IO
import doobie.util.transactor.Transactor

trait AdsRepo {
  def create(params: AdParams): IO[Either[String, Int]]
  def delete(id: Int): IO[Either[String, Int]]
}

object AdsRepo {
  def pg(tr: Transactor[IO]) = new AdsRepo {
    def create(params: AdParams): IO[Either[String, Int]] = ???

    def delete(id: Int): IO[Either[String, Int]] = ???
  }
}