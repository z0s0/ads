package ads.api

import ads.api.params.{CreateAdParams, CreateClickParams}
import sttp.model.StatusCode
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.generic.auto._
import sttp.tapir._
import ads.core.Ad

object Docs {
  object Ads {
    val getAd = endpoint.get
      .in("/getAd")
      .out(jsonBody[Ad])
      .errorOut(jsonBody[ClientError])

    val createAd = endpoint.post
      .in("/ads")
      .in(jsonBody[CreateAdParams])
      .out(jsonBody[Ad])
      .errorOut(jsonBody[ClientError])

    val deleteAd = endpoint.delete
      .in("/ads" / path[Int])
      .errorOut(jsonBody[ClientError])
      .errorOut(statusCode(StatusCode.NotFound))
  }

  object Clicks {
    val create = endpoint
      .post
      .in("/clicks")
      .in(jsonBody[CreateClickParams])
  }

  val allDocs: List[AnyEndpoint] = List(Clicks.create, Ads.createAd, Ads.deleteAd, Ads.getAd)
}
