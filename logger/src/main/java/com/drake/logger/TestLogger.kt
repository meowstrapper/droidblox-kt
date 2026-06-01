package com.drake.logger

object TestLogger: Logger {
    private fun printPerLine(combineWith: String, str: String) {
        str.split("\n").forEach {
            println("$combineWith $it")
        }
    }
    override fun d(tag: String, message: String) {
        printPerLine("D: [$tag]", message)
    }

    override fun i(tag: String, message: String) {
        printPerLine("I: [$tag]", message)
    }

    override fun w(tag: String, message: String) {
        printPerLine("W: [$tag]", message)
    }

    override fun e(tag: String, message: String) {
        printPerLine("E: [$tag]", message)
    }
}