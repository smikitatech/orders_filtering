package org.sm.adentis.model

import java.time.LocalDateTime

case class Product(name: String, category: String, weight: Double, price: BigDecimal, createdAt: LocalDateTime)
