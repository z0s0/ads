package ads.repo

import ads.repo.util.MutationError
import ads.service.AdsService.Params.AdParams
import cats.effect.IO
import doobie.implicits.toSqlInterpolator
import doobie.util.transactor.Transactor
import doobie.implicits._

import ads.repo.util.DbError.ConnectionIOOps

trait AdsRepo {
  // TODO returning ints everywhere as the type conversions will take some time
  def create(params: AdParams): IO[Either[MutationError, Int]]
  // TODO introduce value types for ids
  def delete(id: Int): IO[Either[MutationError, Int]]
  def get(id: Int): IO[Option[Int]]
}

object AdsRepo {
  def pg(tr: Transactor[IO]) = new AdsRepo {
    def create(params: AdParams): IO[Either[MutationError, Int]] =
      sql.create(params).update.withUniqueGeneratedKeys[Int]("id").withMutationErrorsHandling.transact(tr)

    def delete(id: Int): IO[Either[MutationError, Int]] =
      sql.delete(id).update.run.withMutationErrorsHandling.transact(tr)

    def get(id: Int): IO[Option[Int]] =
      sql.get(id).query[Int].option.transact(tr)

    private object sql {
      def get(id: Int) =
        sql"select id from ads where id = ${id}"
      def create(params: AdParams) =
        sql"insert into ads(body) values (${params.title})"

      def delete(adId: Int) = sql"delete from ads where id = ${adId}"
    }
  }
}