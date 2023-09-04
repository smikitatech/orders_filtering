package org.sm.adentis.service

import java.time.format.DateTimeFormatter

object Validator {

  def isValidDateTimeFormat(dateTimeString: String, format: String): Boolean = {
    val formatter = DateTimeFormatter.ofPattern(format)
    try {
      formatter.parse(dateTimeString)
      true
    } catch {
      case _: Exception => false
    }
  }
}
