package ads.api

import ads.core.Ad
import ads.service.AdsService
import ads.service.AdsService.Params.GetAdParams
import cats.implicits.{catsSyntaxApplicativeId, catsSyntaxEitherId}

final class Routes(adsService: AdsService) {
  private val getLogic = Docs.Ads.getAd.serverLogic{ _ =>
    adsService.get(GetAdParams("myinitial string")).map {
      case Some(ad) => ad.asRight[ClientError]
      case None => ClientError("invalid params").asLeft[Ad]
    }
  }

  val list = List(getLogic)
}
