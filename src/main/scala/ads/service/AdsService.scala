package ads.service

import ads.core.Ad
import ads.repo.AdsRepo
import ads.service.AdsService.Params.{AdParams, GetAdParams}
import cats.effect.IO

trait AdsService{
  def create(params: AdParams): IO[Either[AdsService.MutationError, Ad]]
  def delete(id: Int): IO[Either[AdsService.Error, Ad]]
  def get(params: GetAdParams): IO[Option[Ad]]
}

object AdsService {
  def of(adsRepo: AdsRepo, algo: GetAdService): AdsService = new AdsService {
    def create(params: AdParams): IO[Either[MutationError, Ad]] = ???

    def delete(id: Int): IO[Either[Error, Ad]] = ???

    def get(params: GetAdParams): IO[Option[Ad]] = ???
  }

  sealed trait Error
  sealed trait MutationError extends Error
  sealed trait ReadError extends Error

  case object AlreadyExists extends MutationError
  final case class NotFound(id: Int) extends ReadError

  object Params {
    final case class AdParams(title: String)
    final case class GetAdParams(search: String)
  }
}