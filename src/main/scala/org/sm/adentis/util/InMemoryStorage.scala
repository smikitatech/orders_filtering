package org.sm.adentis.util

import org.sm.adentis.model.Order

import java.time.LocalDateTime

case class InMemoryStorage(orders: Set[Order])

object InMemoryStorage {

  def createInMemoryStorageWithData(from: LocalDateTime, to: LocalDateTime): InMemoryStorage = {
    InMemoryStorage(orders = DataGenerator.generateRandomOrders(from, to))
  }
}
