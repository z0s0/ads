package ads.repo

import cats.effect.IO
import doobie.util.transactor.Transactor

trait ClicksRepo {
  def create(adId: Int, userId: Int): IO[Unit]
}

object ClicksRepo {
  // not the best storage for clicks though
  def pg(tr: Transactor[IO]) = new ClicksRepo {
    def create(adId: Int, userId: Int): IO[Unit] = ???
  }
}