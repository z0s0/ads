package ads.service

import ads.core.Ad
import ads.service.Ads.Params.GetAdParams
import cats.effect.IO

trait GetAd {
  def apply(params: GetAdParams): IO[Option[Ad]]
}

object GetAd {
  def const(ad: Ad): GetAd = (params: GetAdParams) => IO.pure(Some(ad))

  def naive = new GetAd {
     def apply(params: GetAdParams): IO[Option[Ad]] = IO(None)
  }
}
