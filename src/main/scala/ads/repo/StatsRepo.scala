package ads.repo

import ads.repo.StatsRepo.Stat
import cats.effect.IO
import doobie.util.transactor.Transactor

trait StatsRepo {
  def getStats(): IO[List[Stat]]
}

object StatsRepo {
  final case class Stat(adId: Int, impressionsCount: Int, clicksCount: Int)

  def pg(tr: Transactor[IO]) = new StatsRepo {
    def getStats(): IO[List[Stat]] = {
      ???
    }
  }
}
