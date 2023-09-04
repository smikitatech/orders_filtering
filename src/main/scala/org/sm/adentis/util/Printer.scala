package org.sm.adentis.util

import org.sm.adentis.model.{Item, Order, Response}

import scala.language.postfixOps

object Printer {

  private final val WhiteSpace = " "

  def prettyPrintDataWithDetails(responses: List[Response]): Unit = {
    println(responses.sorted.foldLeft("")((acc, resp) => acc + responseToString(resp) + "\n"))
  }

  def prettyPrintResponseInShortFormat(responses: List[Response]): Unit = {
    responses.sorted.foreach(resp => println(s"${rangeToString(resp)} - ${resp.orderNumber}"))
  }

  private def rangeToString(response: Response): String = {
    s"${response.intervalInMonth.range._1} - ${response.intervalInMonth.range._2}"
  }

  private def indent(num: Int): String = WhiteSpace.repeat(num)

  private def responseToString(resp: Response): String = {
    s"Response: [\n${this indent 3}" +
      s"${resp.intervalInMonth}\n${this indent 3}" +
      s"Orders: ${
        if (resp.orders.isEmpty) "[]\n]" else s"[ ${
          resp.orders
            .foldLeft("")((acc, order) =>
              acc + s"${this indent 10}\n${this indent 10}Order [${orderToString(order)}\n${this indent 10}],")
        }\n${this indent 3}]\n]"
      }"
  }

  private def orderToString(order: Order): String = s"\n${this indent 15}items: [" +
    s"\n${
      order.items.foldLeft("")((acc, item) =>
        acc + s"${this indent 18}" + itemToString(item) + "\n")
    }${this indent 18}]" +
    s"\n${this indent 15}${order.customerInfo}" +
    s"\n${this indent 15}${order.shippingAddress}" +
    s"\n${this indent 15}Grand Total: ${order.grandTotal}" +
    s"\n${this indent 15}CreatedAt: ${order.createdAt}"

  private def itemToString(item: Item): String = s"Item: [${item.product} cost = ${item.cost} shippingFee = ${item.shippingFee} taxAmount = ${item.taxAmount}]"
}
