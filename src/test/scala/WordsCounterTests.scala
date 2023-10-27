import munit.FunSuite

class WordsCounterTests extends FunSuite {
  //can also be run with WordsCounter.make2
  val wordsCounter: WordsCounter = WordsCounter.make

  test("one empty line"){
    assertEquals(wordsCounter.getCountMap(List("").iterator), Map.empty[String, Int])
  }

  test("several empty lines") {
    assertEquals(wordsCounter.getCountMap(List("", "").iterator), Map.empty[String, Int])
  }

  test("empty after filter") {
    assertEquals(wordsCounter.getCountMap(List(",.", "  ").iterator), Map.empty[String, Int])
  }

  test("unique words one line") {
    assertEquals(wordsCounter.getCountMap(List("a b c").iterator), Map("a" -> 1, "b" -> 1, "c" -> 1))
  }

  test("unique words several lines") {
    assertEquals(wordsCounter.getCountMap(List("a b c", "d f g").iterator),
      Map("a" -> 1, "b" -> 1, "c" -> 1, "d" -> 1, "f" -> 1, "g" -> 1))
  }

  test("common words one line") {
    assertEquals(wordsCounter.getCountMap(List("ab ab c").iterator), Map("ab" -> 2, "c" -> 1))
  }

  test("common words several lines") {
    assertEquals(wordsCounter.getCountMap(List("ab ab c", "ab c g").iterator),
      Map("ab" -> 3, "c" -> 2, "g" -> 1))
  }

  test("words with special symbols") {
    assertEquals(wordsCounter.getCountMap(List("$ab ab@ c,.", "ab* (c g`").iterator),
      Map("ab" -> 3, "c" -> 2, "g" -> 1))
  }
}
