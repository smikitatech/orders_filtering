package org.sm.adentis.service

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.sm.adentis.util.DateTimeConvertor

import java.time.LocalDateTime

class DateTimeConvertorSpec extends AnyFlatSpec with Matchers {

  "DateTimeConvertor" should "convert correct dateTime string" in {
    val firstCorrectDateTimeString = "2020-01-01 00:00:00"
    val secondCorrectDateTimeString = "2021-01-01 00:00:00"

    val (from, to): (LocalDateTime, LocalDateTime) = DateTimeConvertor.convertToLocalDateTimes(firstCorrectDateTimeString, secondCorrectDateTimeString)

    from shouldBe LocalDateTime.of(2020, 1, 1, 0, 0)
    to shouldBe LocalDateTime.of(2021, 1, 1, 0, 0)

  }

  "DateTimeConvertor" should "throw IllegalArgumentException with message 'Date 'from' doesn't match to DateTime format.'" in {
    val firstCorrectDateTimeString = "2020-01-01 00:00"
    val secondCorrectDateTimeString = "2021-01-01 00:00:00"

    val caught =
      intercept[IllegalArgumentException] {
        DateTimeConvertor.convertToLocalDateTimes(firstCorrectDateTimeString, secondCorrectDateTimeString)
      }
    assert(caught.getMessage.contains("Date 'from' doesn't match to DateTime format"))
  }

  "DateTimeConvertor" should "throw IllegalArgumentException with message 'Date 'to' doesn't match to DateTime format.'" in {
    val firstCorrectDateTimeString = "2020-01-01 00:00:00"
    val secondCorrectDateTimeString = "2021-01-01 00:00:oo"

    val caught =
      intercept[IllegalArgumentException] {
        DateTimeConvertor.convertToLocalDateTimes(firstCorrectDateTimeString, secondCorrectDateTimeString)
      }
    assert(caught.getMessage.contains("Date 'to' doesn't match to DateTime format"))
  }

}

