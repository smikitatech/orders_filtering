package org.sm.adentis.util

import org.sm.adentis.service.Validator.isValidDateTimeFormat

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeConvertor {

  def convertToLocalDateTimes(from: String, to: String): (LocalDateTime, LocalDateTime) = {
    val stringDateFormat = "yyyy-MM-dd HH:mm:ss"
    require(isValidDateTimeFormat(from, stringDateFormat), "Date 'from' doesn't match to DateTime format.")
    require(isValidDateTimeFormat(to, stringDateFormat), "Date 'to' doesn't match to DateTime format.")
    val formatter = DateTimeFormatter.ofPattern(stringDateFormat)
    (LocalDateTime.parse(from, formatter), LocalDateTime.parse(to, formatter))
  }

}
