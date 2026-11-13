package crit

fun main(args: Array<String>) {
    val suite = args.singleOrNull() ?: "all"
    require(suite == "all" || suite.startsWith("map")) { "Unknown test suite: $suite" }
    runMapContract(suite)
    println("Passed $suite")
}
