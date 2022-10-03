package ads.service

import ads.core.Ad
import ads.service.Ads.Params.{AdParams, GetAdParams}
import cats.effect.IO

trait Ads {
  def create(params: AdParams): IO[Either[Ads.MutationError, Ad]]
  def delete(id: Int): IO[Either[Ads.Error, Ad]]
  def get(params: GetAdParams): IO[Option[Ad]]
}

object Ads {
  def of(adsRepo: AdsRepo, algo: GetAdAlgo): Ads = new Ads {
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