package ads.api.params

import io.circe.generic.JsonCodec

@JsonCodec
final case class CreateAdParams(title: Option[String])
