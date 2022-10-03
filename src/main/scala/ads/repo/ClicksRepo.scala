package ads.repo

import cats.effect.IO
import doobie.implicits.toSqlInterpolator
import doobie.util.transactor.Transactor
import doobie._
import doobie.implicits._

trait ClicksRepo {
  def create(adId: Int, userId: Int): IO[Unit]
}

object ClicksRepo {
  // not the best storage for clicks though
  def pg(tr: Transactor[IO]): ClicksRepo = new ClicksRepo {
    def create(adId: Int, userId: Int): IO[Unit] =
      sql.create(adId, userId).transact(tr).as(())

    private object sql {
      def create(adId: Int, userId: Int): doobie.ConnectionIO[Int] =
        sql"""
              insert into clicks(ad_id, user_id)
              values(${adId}, ${userId})
             """
          .update
          .withUniqueGeneratedKeys[Int]("id")
    }
  }
}