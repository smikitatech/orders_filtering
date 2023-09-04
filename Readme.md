# Project description

## Introduction

The purpose of this exercise is to check if older products are still being sold. The program implements the logic of
filtering Orders depending on the creation date of the earliest product in it. The program is the base implementation.

## Parameters

- The program can accept an array of strings as parameters.
- For the program to work correctly, the number of array elements must be at least two.
- The first two parameters should be the dates (in the format "yyyy-MM-dd HH:mm:ss") of the start and end of the period
  for which we need to filter orders. If dates are passed in the wrong format, an exception will
  be thrown.
- If you need to print the results of the program execution with the following parameters, you need to pass the
  parameters for the printer (see section Printing)
  (example: ["2022-01-01 00:00:00", "2023-01-01 00:00:00"]
  or ["2022-01-01 00:00:00", "2023-01-01 00:00:00", "true", "false"])
- If you need to specify specific intervals, you can do this immediately after specifying the first two parameters
  (there must be at least 3 of these intervals) in the format ("number-number") and then you can specify parameters for
  the printer (see section Printing)
- (example: ["2022-01-01 00:00:00", "2023-01-01 00:00:00", "1-4", "5-8", "9-12"]
  or ["2022-01-01 00:00:00", "2023-01-01 00:00:00", "1-3", "4-6", "7-8", "9-12"]
  or ["2022-01-01 00:00:00", "2023-01-01 00:00:00", "1-4", "5-8", "9-12", "false", "true"])

## Printing

- The program can take parameters to select how the execution result is output to the console. These parameters are
  [needToPrint] and [isShortPrint]. It should have value [true] or [false].
- How these parameters should be specified is described in the section above.
- [needToPrint] - parameter specifies whether to output information to the console.
- [isShortPrint] - parameter specifies whether to print information to the console in short form or with detail.