package ads.repo

import ads.repo.StatsRepo.Stat
import cats.effect.IO
import doobie.implicits.toSqlInterpolator
import doobie.util.transactor.Transactor
import doobie.implicits._

trait StatsRepo {
  def getBestAdId(): IO[Option[Stat]]
}

object StatsRepo {
  final case class Stat(adId: Int, impressionsCount: Int, clicksCount: Int)

  def pg(tr: Transactor[IO]) = new StatsRepo {
    def getBestAdId(): IO[Option[Stat]] = {
      val sql =
        sql"""
             |      WITH groupedClicks as (select ad_id, count(*) as score from clicks group by ad_id),
             |           groupedImpressions as (select ad_id, count(*) as score from impressions group by ad_id)
             |
             |      SELECT ads.id, gc.score::float / gi.score::float as rate from ads
             |      JOIN groupedClicks gc on gc.ad_id = ads.id
             |      JOIN groupedImpressions gi on gi.ad_id = ads.id
             |      ORDER BY rate DESC
             |      LIMIT 1;
             |""".stripMargin

      sql.query[Stat].option.transact(tr)
    }
  }
}
