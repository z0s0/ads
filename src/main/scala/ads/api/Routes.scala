package ads.api

import ads.core.Ad
import ads.service.AdsService
import ads.service.AdsService.Params.{AdParams, GetAdParams}
import cats.implicits.{catsSyntaxApplicativeId, catsSyntaxEitherId}

final class Routes(adsService: AdsService) {
  // TODO add params validations with cats.data.Validated

  private val getLogic = Docs.Ads.getAd.serverLogic{ _ =>
    val adId = 1
    val userId = 2
    adsService.show(adId, userId).map(_ => Ad("title").asRight[ClientError])
  }

  private val createLogic = Docs.Ads.createAd.serverLogic {params =>
    // add validation
    adsService.create(AdParams(params.title.getOrElse("empty"))).map {
      case Left(err) => ClientError(err.toString).asLeft[Ad]
      case Right(value) => Ad("placeholder").asRight[ClientError]
    }
  }

  val list = List(getLogic, createLogic)
}
