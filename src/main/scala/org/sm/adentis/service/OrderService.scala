package org.sm.adentis.service

import org.sm.adentis.model.{MonthInterval, Order, Response}

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import scala.collection.mutable

object OrderService {

  /**
   * Method to filter orders by date without specified month intervals.
   *
   * @param dateTimeFrom - start date
   * @param dateTimeTo   - end date
   * @param orders       - set of orders
   * @return - list of orders grouped by month intervals
   */
  def filterOrdersWithoutIntervals(dateTimeFrom: LocalDateTime, dateTimeTo: LocalDateTime, orders: Set[Order]): List[Response] = {
    var fromDate = dateTimeFrom
    var timeIntervalsMap = mutable.Map.empty[MonthInterval, Set[Order]]
    while (dateTimeTo.isAfter(fromDate)) {
      timeIntervalsMap = timeIntervalsMap +
        (MonthInterval(fromDate,
          fromDate.plusMonths(1),
          calculateRange(fromDate, fromDate.plusMonths(1), dateTimeTo)
        ) -> Set.empty[Order])
      fromDate = fromDate.plusMonths(1)
    }
    filterOrders(orders, timeIntervalsMap, dateTimeTo)
  }

  def filterOrdersWithIntervals(dateTimeFrom: LocalDateTime,
                                dateTimeTo: LocalDateTime,
                                timeIntervals: Array[String],
                                orders: Set[Order]): List[Response] = {
    val timeIntervalsMap = collection.mutable.Map(convertTimeIntervals(dateTimeTo, timeIntervals)
      .map(interval => (interval, Set.empty[Order])): _*)
    filterOrders(orders, timeIntervalsMap, dateTimeTo)

  }

  private def filterOrders(orders: Set[Order],
                           timeIntervalsMap: mutable.Map[MonthInterval, Set[Order]],
                           dateTimeTo: LocalDateTime): List[Response] = {
    orders.foreach(order => {
      val ordersProductsCreationDates = order.items.map(_.product.createdAt)
      ordersProductsCreationDates.foreach(createdAt => {
        timeIntervalsMap
          .filter(interval => createdAt.isBefore(dateTimeTo) &&
            !createdAt.isAfter(interval._1.toDate))
          .foreach(interval => timeIntervalsMap += (interval._1 -> (interval._2 + order)))
      })
    })
    timeIntervalsMap.map(interval => Response(interval._1, interval._2, interval._2.size)).toList
  }

  private def convertTimeIntervals(date: LocalDateTime, timeIntervals: Array[String]): List[MonthInterval] = {
    timeIntervals.map(interval => {
      val monthIntervalString = interval.split("-")
      val monthIntervals = monthIntervalString.map(interval => interval.trim.toIntOption match {
        case Some(value) if value > 0 && value < 13 => value
        case _ => throw new IllegalArgumentException("Interval is incorrect.")
      })
      val to = date.minusMonths(monthIntervals.head)
      val from = date.minusMonths(monthIntervals.last)
      MonthInterval(from, to, calculateRange(from, to, date))
    }).toList
  }

  private def calculateRange(startInterval: LocalDateTime, endOfInterval: LocalDateTime, date: LocalDateTime): (Long, Long) = {
    (ChronoUnit.MONTHS.between(endOfInterval, date), ChronoUnit.MONTHS.between(startInterval, date))
  }
}
