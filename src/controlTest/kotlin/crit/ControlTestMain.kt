package crit

fun main(args: Array<String>) {
    val suite = args.singleOrNull() ?: "all"
    require(suite in setOf("all", "session", "leases") || suite.startsWith("map")) { "Unknown test suite: $suite" }
    if (suite == "all" || suite.startsWith("map")) runMapContract(suite)
    if (suite == "all" || suite == "session") runSessionControl()
    if (suite == "all" || suite == "leases") runResourceLeases()
    println("Passed $suite")
}
