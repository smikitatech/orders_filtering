package org.sm.adentis.util

import faker._
import org.sm.adentis.model.{Address, _}

import java.time.ZoneOffset.UTC
import java.time.{LocalDateTime, ZoneId}
import scala.collection.mutable
import scala.util.Random

object DataGenerator {

  def generateRandomOrders(from: LocalDateTime, to: LocalDateTime): Set[Order] = {
    val ordersNumber = Random.between(10, 20)
    var allItems = generateRandomItems(from, to.plusMonths(2))
    val itemsInOrders = generateSequenceWithSum(ordersNumber, allItems.size)
    var orders = Set.empty[Order]
    for (itemNumber <- itemsInOrders) {
      val thisOrderItems = Random.shuffle(allItems.toSeq).take(itemNumber).toSet
      allItems --= thisOrderItems
      val order = generateOrder(thisOrderItems)
      orders += order
    }
    orders
  }

  private def generateOrder(items: Set[Item]): Order = {
    val lastProductAddedAt = items.map(item => item.product.createdAt).max
    Order(items = items,
      customerInfo = generateCustomerInfo,
      shippingAddress = generateRandomAddress,
      grandTotal = items.foldLeft(BigDecimal.valueOf(0))((acc, item) => acc + item.cost),
      createdAt = generateRandomDateTime(lastProductAddedAt, LocalDateTime.now()))
  }

  private def generateRandomDateTime(start: LocalDateTime, end: LocalDateTime): LocalDateTime = {
    val startEpoch = start.toEpochSecond(UTC)
    val endEpoch = end.toEpochSecond(UTC)
    val randomEpoch = startEpoch + Random.nextLong.abs % (endEpoch - startEpoch)
    val randomDateTime = LocalDateTime.ofInstant(java.time.Instant.ofEpochSecond(randomEpoch), ZoneId.systemDefault())
    randomDateTime
  }

  private def generateSequenceWithSum(targetNumber: Int, sum: Int): Seq[Int] = {
    var generatedSum = 0
    var sequence: Seq[Int] = Seq.empty
    while (generatedSum != sum) {
      sequence = Seq.fill(targetNumber)(Random.between(1, sum / targetNumber + 3))
      generatedSum = sequence.sum
    }
    sequence
  }

  private def generateCustomerInfo: CustomerInfo = {
    CustomerInfo(
      name = Internet.user_name,
      phoneNumber = PhoneNumber.phone_number,
      email = Internet.email)
  }

  private def generateRandomAddress: Address = {
    Address(
      country = faker.Address.country,
      city = faker.Address.city,
      street = faker.Address.street_name,
      houseNumber = Random.nextInt(20) + 1)
  }

  private def generateRandomItems(from: LocalDateTime, to: LocalDateTime): Set[Item] = {
    val products = generateRandomProducts(from, to)
    products.foldLeft(mutable.Set.empty[Item])((acc, product) => {
      acc + generateItem(product)
    }).toSet
  }

  private def generateItem(product: Product): Item = {
    val shippingFee = BigDecimal.valueOf(Random.nextDouble())
    val taxAmount = BigDecimal.valueOf(Random.nextDouble())
    Item(
      product = product,
      cost = product.price + shippingFee + taxAmount,
      shippingFee = shippingFee,
      taxAmount = taxAmount
    )
  }

  private def generateRandomProducts(from: LocalDateTime, to: LocalDateTime): Set[Product] = {
    val productsNumber = Random.between(50, 100)
    var products = Set.empty[Product]
    for (_ <- 10 to productsNumber) {
      products += generateProduct(from, to)
    }
    products
  }

  private def generateProduct(fromDate: LocalDateTime, toDate: LocalDateTime): Product = {
    Product(
      name = s"Product ${Random.nextInt()}",
      category = s"Category ${Random.between(1, 5)}",
      weight = Random.nextDouble(),
      price = BigDecimal(Random.nextDouble()),
      createdAt = generateRandomDateTime(fromDate, toDate))
  }
}
