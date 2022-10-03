package ads.config

final case class MaskedString(value: String) extends AnyVal {
  override def toString: String = "*******"
}

final case class AppConfig(db: DbConfig)

final case class DbConfig(
  username: String,
  password: MaskedString,
  database: String,
  host: String,
  port: Int
)

