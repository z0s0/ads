package ads

import ads.config.DbConfig
import cats.effect.IO
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux

object DbConnection {
  def of(dbConfig: DbConfig): Aux[IO, Unit] =
    Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",
      dbConfig.url,
      dbConfig.username,
      dbConfig.password.value
    )
}
