package ads.service

import ads.repo.StatsRepo
import ads.service.AdsService.Params.GetAdParams
import cats.effect.IO

trait GetAdService {
  def apply(params: GetAdParams): IO[Option[Int]]
}

object GetAdService {

  // the closer clicksByAdId/impressionsByAdId to 1.0 the higher the score
  def statsBased(statsRepo: StatsRepo) = new GetAdService {
    def apply(params: GetAdParams): IO[Option[Int]] = statsRepo.getBestAdId().map(_.map(_.adId))
  }
}
