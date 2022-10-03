package ads.api

import io.circe.generic.JsonCodec


@JsonCodec final case class ClientError(reason: String)
