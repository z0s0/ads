package ads.service

import ads.core.Ad
import ads.repo.{AdsRepo, util}
import ads.repo.util.{DbError, ForeignKeyViolation, Impossible, UniquenessViolation}
import ads.service.AdsService.Params.{AdParams, GetAdParams}
import cats.data.OptionT
import cats.effect.IO
import cats.implicits.toBifunctorOps

trait AdsService{
  def create(params: AdParams): IO[Either[AdsService.MutationError, Int]]
  def delete(id: Int): IO[Either[AdsService.Error, Int]]
//  def get(params: GetAdParams): IO[Option[Ad]]

  // show = get existing adId and create an impression
  def show(adId: Int, userId: Int): IO[Unit]
}

object AdsService {
  def of(adsRepo: AdsRepo, algo: GetAdService, impressionsService: ImpressionsService): AdsService = new AdsService {
    def create(params: AdParams): IO[Either[MutationError, Int]] =
      adsRepo.create(params).map(_.leftMap(MutationError.fromDbError))

    def delete(id: Int): IO[Either[Error, Int]] =
      adsRepo.delete(id).map(_.leftMap(MutationError.fromDbError))

    def show(adId: Int, userId: Int): IO[Unit] =
      for {
        ad <- algo(GetAdParams(adId, userId)) // handle properly
        _ <- impressionsService.create(adId, userId)
      } yield ()
//    def get(params: GetAdParams): IO[Option[Int]] = adsRepo.get(params.adId)
  }

  sealed trait Error
  sealed trait MutationError extends Error
  sealed trait ReadError extends Error

  case object AlreadyExists extends MutationError
  case object Other extends MutationError
  final case class NotFound(id: Int) extends ReadError

  object Params {
    final case class AdParams(title: String)
    final case class GetAdParams(adId: Int, userId: Int)
  }

  object MutationError {
    def fromDbError(dbError: util.MutationError): MutationError = dbError match {
      case UniquenessViolation(msg) => AlreadyExists
      case ForeignKeyViolation(msg) => Other
      case Impossible => Other
    }
  }
}