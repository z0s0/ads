package ads.service

import ads.repo.util.{DbError, MutationError}
import cats.effect.IO
import doobie.implicits.toSqlInterpolator
import doobie.util.transactor.Transactor
import doobie.implicits._
import ads.repo.util.DbError.ConnectionIOOps

trait ImpressionsService {
  def create(adId: Int, userId: Int): IO[Either[MutationError, Int]]
}

object ImpressionsService {
  // TODO must be abstracted to Repo
  def pg(tr: Transactor[IO]) = new ImpressionsService {
    def create(adId: Int, userId: Int): IO[Either[MutationError, Int]] = {
      sql"insert into impressions(ad_id, user_id) values(${adId}, ${userId})"
        .update
        .withUniqueGeneratedKeys[Int]("id")
        .withMutationErrorsHandling
        .transact(tr)
    }
  }
}
