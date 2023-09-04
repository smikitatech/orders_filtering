package org.sm.adentis.service

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.sm.adentis.model._
import org.sm.adentis.util.{DateTimeConvertor, InMemoryStorage, Printer}

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OrderServiceSpec extends AnyFlatSpec with Matchers {

  final val Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  val orders: Set[Order] = Set(
    Order(items = Set(
      Item(
        Product(name = "Product1",
          category = "Category1",
          weight = 13.3,
          price = BigDecimal.valueOf(10.0),
          createdAt = LocalDateTime.parse("2022-02-02 00:00:00", Formatter)),
        cost = BigDecimal.valueOf(14.0), shippingFee = BigDecimal.valueOf(1), taxAmount = BigDecimal.valueOf(3)),
      Item(
        Product(name = "Product2",
          category = "Category2",
          weight = 13.3,
          price = BigDecimal.valueOf(10.0),
          createdAt = LocalDateTime.parse("2022-05-02 00:00:00", Formatter)),
        cost = BigDecimal.valueOf(14.0), shippingFee = BigDecimal.valueOf(1), taxAmount = BigDecimal.valueOf(3))),
      customerInfo = CustomerInfo(name = "Customer1", phoneNumber = "XXXXXXXXXX", email = "XXXXXXXXXXXXXXXXXXX"),
      shippingAddress = Address(country = "Country1", street = "Street1", city = "City1", houseNumber = 1),
      grandTotal = BigDecimal.valueOf(100), createdAt = LocalDateTime.parse("2022-05-02 00:00:00", Formatter)
    ),
    Order(items = Set(
      Item(
        Product(name = "Product2",
          category = "Category2",
          weight = 13.3,
          price = BigDecimal.valueOf(10.0),
          createdAt = LocalDateTime.parse("2022-05-02 00:00:00", Formatter)),
        cost = BigDecimal.valueOf(14.0), shippingFee = BigDecimal.valueOf(1), taxAmount = BigDecimal.valueOf(3)),
      Item(
        Product(name = "Product3",
          category = "Category1",
          weight = 13.3,
          price = BigDecimal.valueOf(10.0),
          createdAt = LocalDateTime.parse("2022-07-02 00:00:00", Formatter)),
        cost = BigDecimal.valueOf(14.0), shippingFee = BigDecimal.valueOf(1), taxAmount = BigDecimal.valueOf(3))),
      customerInfo = CustomerInfo(name = "Customer1", phoneNumber = "XXXXXXXXXX", email = "XXXXXXXXXXXXXXXXXXX"),
      shippingAddress = Address(country = "Country1", street = "Street1", city = "City1", houseNumber = 1),
      grandTotal = BigDecimal.valueOf(100), createdAt = LocalDateTime.parse("2022-07-02 00:00:00", Formatter)
    ))

  "Order service" should "filter orders without interval params with generated data." in {

    val (dateTimeFrom, dateTimeTo): (LocalDateTime, LocalDateTime) =
      DateTimeConvertor.convertToLocalDateTimes("2022-01-01 00:00:00", "2023-01-01 00:00:00")
    val storage = InMemoryStorage.createInMemoryStorageWithData(dateTimeFrom, dateTimeTo)
    val actual = OrderService.filterOrdersWithoutIntervals(dateTimeFrom, dateTimeTo, storage.orders)
    actual.size shouldEqual 12
  }

  "Order service" should "filter orders without interval params with stub data." in {

    val (dateTimeFrom, dateTimeTo): (LocalDateTime, LocalDateTime) =
      DateTimeConvertor.convertToLocalDateTimes("2022-01-01 00:00:00", "2023-01-01 00:00:00")
    val actual = OrderService.filterOrdersWithoutIntervals(dateTimeFrom, dateTimeTo, orders)
    Printer.prettyPrintDataWithDetails(actual)
    actual.sorted.map(_.orders.size) shouldEqual List(0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2)
  }
}

