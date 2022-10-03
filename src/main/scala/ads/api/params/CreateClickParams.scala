package ads.api.params

import io.circe.generic.JsonCodec

@JsonCodec
final case class CreateClickParams(adId: Option[Int], userId: Option[Int])
