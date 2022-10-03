package ads.service

import ads.repo.ClicksRepo
import ads.service.ClicksService.CreateClickParams
import cats.effect.IO
import cats.implicits.{catsSyntaxApplicativeId, catsSyntaxEitherId}

trait ClicksService {
  def create(params: CreateClickParams): IO[Either[ClicksService.Error, Unit]]
}

object ClicksService {
  def of(repo: ClicksRepo, botDetectionService: BotDetectionService): ClicksService = new ClicksService {
    def create(params: CreateClickParams): IO[Either[Error, Unit]] =
      botDetectionService.isBot(params.userAgent).flatMap {
        case true => BotDetected(params.userAgent).asLeft[Unit].pure[IO]
        case false => repo.create(params.adId, params.userId).map(_.asRight[Error])
      }
  }

  sealed trait Error
  final case class BotDetected(agent: String) extends Error
  final case class CreateClickParams(adId: Int, userId: Int, userAgent: String)
}
