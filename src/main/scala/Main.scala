import org.sm.adentis.service.OrderService
import org.sm.adentis.util.DateTimeConvertor.convertToLocalDateTimes
import org.sm.adentis.util.InMemoryStorage
import org.sm.adentis.util.Printer.{prettyPrintDataWithDetails, prettyPrintResponseInShortFormat}

object Main {

  def main(args: Array[String]): Unit = {
    if (args.length == 2 || args.length == 4)
      taskWithoutIntervals(args)
    else if (args.length > 4)
      taskWithIntervals(args)
    else throw new IllegalArgumentException("Invalid number of arguments.")
  }

  private def taskWithoutIntervals(args: Array[String]): Unit = {
    val (dateTimeFrom, dateTimeTo) = convertToLocalDateTimes(args(0), args(1))
    val storage = InMemoryStorage.createInMemoryStorageWithData(dateTimeFrom, dateTimeTo)
    val responses = OrderService.filterOrdersWithoutIntervals(dateTimeFrom, dateTimeTo, storage.orders)
    if (args.length == 4) {
      val needToPrint = Option(args(2)).exists(_.toBoolean)
      val isShortPrint = Option(args(3)).exists(_.toBoolean)

      if (needToPrint) {
        if (isShortPrint) {
          prettyPrintResponseInShortFormat(responses)
        }
        prettyPrintDataWithDetails(responses)
      }
    }
  }

  private def taskWithIntervals(args: Array[String]): Unit = {
    val (dateTimeFrom, dateTimeTo) = convertToLocalDateTimes(args(0), args(1))
    val storage = InMemoryStorage.createInMemoryStorageWithData(dateTimeFrom, dateTimeTo)
    val intervals = args.drop(2).filterNot(arg => arg == "true" || arg == "false")
    val responses = OrderService.filterOrdersWithIntervals(dateTimeFrom, dateTimeTo, intervals, storage.orders)
    val printParams = args.drop(2).filter(arg => arg == "true" || arg == "false")
    if (printParams.length == 2) {
      val needToPrint = Option(printParams(0)).exists(_.toBoolean)
      val isShortPrint = Option(printParams(1)).exists(_.toBoolean)

      if (needToPrint) {
        if (isShortPrint) {
          prettyPrintResponseInShortFormat(responses)
          return
        }
        prettyPrintDataWithDetails(responses)
      }
    }
  }
}