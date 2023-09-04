package org.sm.adentis.model

import java.time.LocalDateTime
import scala.language.postfixOps

case class Order(items: Set[Item], // we assume that items cannot be empty
                 customerInfo: CustomerInfo,
                 shippingAddress: Address,
                 grandTotal: BigDecimal,
                 createdAt: LocalDateTime)

case class CustomerInfo(name: String, phoneNumber: String, email: String)

case class Address(country: String, city: String, street: String, houseNumber: Int)