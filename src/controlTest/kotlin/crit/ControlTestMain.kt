package crit

fun main(args: Array<String>) {
    val suite = args.singleOrNull() ?: "all"
    require(suite in setOf("all", "session", "leases", "input") || suite.startsWith("map")) { "Unknown test suite: $suite" }
    if (suite == "all" || suite.startsWith("map")) runMapContract(suite)
    if (suite == "all" || suite == "map-layout") runMapLayout()
    if (suite == "all" || suite == "session") runSessionControl()
    if (suite == "all" || suite == "leases") runResourceLeases()
    if (suite == "all" || suite == "input") runInputLifecycle()
    println("Passed $suite")
}
