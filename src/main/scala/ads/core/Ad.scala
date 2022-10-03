package ads.core

import io.circe.generic.JsonCodec

@JsonCodec
final case class Ad(title: String)
