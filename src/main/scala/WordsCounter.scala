import cats.Monoid
import cats.implicits._

trait WordsCounter {
  def getCountMap(lines: Iterator[String]): Map[String, Int]
}

object WordsCounter{
  def make: WordsCounter = (lines: Iterator[String]) =>
    Monoid[Map[String, Int]].combineAll(lines.map(
    line => line
      .replaceAll("""\p{Punct}""", "")
      .split("""\p{Space}""")
      .foldLeft(Map.empty[String, Int])((map, s) => if (s.isEmpty) map else map.get(s) match {
        case Some(value) => map.updated(s, value + 1)
        case None => map.updated(s, 1)
      })
  ))

  def make2: WordsCounter = (lines: Iterator[String]) =>
    lines.mkString(" ")
        .replaceAll("""\p{Punct}""", "")
        .split("""\p{Space}""")
        .foldLeft(Map.empty[String, Int])((map, s) => if (s.isEmpty) map else map.get(s) match {
          case Some(value) => map.updated(s, value + 1)
          case None => map.updated(s, 1)
        })
}