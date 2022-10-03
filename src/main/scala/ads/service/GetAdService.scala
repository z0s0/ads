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
}
