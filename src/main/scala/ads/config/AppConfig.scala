package ads.config

import cats.effect.IO
import pureconfig.ConfigSource
import pureconfig.generic.auto._

final case class MaskedString(value: String) extends AnyVal {
  override def toString: String = "*******"
}

final case class AppConfig(db: DbConfig)

final case class DbConfig(
  username: String,
  password: MaskedString,
  url: String
)

object AppConfig {
  def load(): IO[AppConfig] = IO.blocking(ConfigSource.default.loadOrThrow[AppConfig])
}
