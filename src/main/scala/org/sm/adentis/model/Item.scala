package org.sm.adentis.model

case class Item(product: Product,
                cost: BigDecimal,
                shippingFee: BigDecimal,
                taxAmount: BigDecimal)
