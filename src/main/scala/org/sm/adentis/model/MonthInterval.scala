package org.sm.adentis.model

import java.time.LocalDateTime

case class MonthInterval(fromDate:LocalDateTime, toDate: LocalDateTime, range:(Long, Long))