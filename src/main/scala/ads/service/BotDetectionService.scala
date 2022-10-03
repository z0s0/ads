package ads.service

import cats.effect.IO

trait BotDetectionService {
  def isBot(userAgent: String): IO[Boolean]
}

object BotDetectionService {
  def of(): BotDetectionService = (userAgent: String) => IO.pure(false)
}
