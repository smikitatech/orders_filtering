package org.sm.adentis.model

case class Response(intervalInMonth: MonthInterval, orders: Set[Order], orderNumber: Int)
  extends Ordered[Response] {

  override def compare(that: Response): Int =
    this.intervalInMonth.fromDate.compareTo(that.intervalInMonth.fromDate)
}



