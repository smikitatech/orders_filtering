package org.sm.adentis.service

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ValidatorSpec extends AnyFlatSpec with Matchers {

  "Validator" should "validate a valid input" in {
    val dateTimeString = "2019-01-01 00:00:00"
    val format = "yyyy-MM-dd HH:mm:ss"

    val isValid = Validator.isValidDateTimeFormat(dateTimeString, format)
    isValid shouldBe true
  }

  "Validator" should "validate a invalid input" in {
    val dateTimeString = "2019-01-01 00:00"
    val format = "yyyy-MM-dd HH:mm:ss"

    val isValid = Validator.isValidDateTimeFormat(dateTimeString, format)
    isValid shouldBe false
  }
}
