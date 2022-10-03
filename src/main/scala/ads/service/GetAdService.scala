package ads.service

import ads.core.Ad
import ads.service.AdsService.Params.GetAdParams
import cats.effect.IO

trait GetAdService {
  def apply(params: GetAdParams): IO[Option[Ad]]
}

object GetAdService {
  def const(ad: Ad): GetAdService = (params: GetAdParams) => IO.pure(Some(ad))

  def naive = new GetAdService {
     def apply(params: GetAdParams): IO[Option[Ad]] = IO(None)
  }

  // the closer clicksByAdId/impressionsByAdId to 1.0 the higher the score
  // so the sql query is like this(CTE expression used)
  /*
      WITH groupedClicks as (select ad_id, count(*) as score from clicks group by ad_id),
           groupedImpressions as (select ad_id, count(*) as score from impressions group by ad_id)

      select ad_id, (gc.score / gi.score) from ads
      join groupedClicks gc on gc.ad_id = ad_id
      join groupedImpressions gi on gi.ad_id = ad_id
   */

  /*
   to simplify we can use clickRepo.all, impressionRepo.all and calculate in memory
   which is of course a very bad idea to do like this in real system
   */
  def statsBased() = new GetAdService {

  }
}
