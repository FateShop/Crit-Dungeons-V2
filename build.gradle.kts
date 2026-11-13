import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.zip.ZipFile

plugins {
    kotlin("jvm")
    id("net.fabricmc.fabric-loom")
    id("maven-publish")
    idea
}

val minecraftVersion = stonecutter.current.version
val targetJavaVersion = 25
val noammAddonsVersion = project.property("noammaddons_version") as String
val noammAddonsType = project.property("noammaddons_type") as String
val modName = project.property("mod_name") as String
val modId = project.property("mod_id") as String

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
    archivesName.set(modName)
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

if (System.getProperty("idea.sync.active").toBoolean() && !stonecutter.current.isActive) {
    sourceSets.main {
        java.setSrcDirs(emptyList<File>())
        kotlin.setSrcDirs(emptyList<File>())
    }
}

tasks.named("ideaSyncTask") {
    dependsOn("stonecutterGenerate", "stonecutterGenerateTest")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
}

repositories {
    maven { url = uri("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") }
    exclusiveContent {
        forRepository {
            ivy("https://github.com/Noamm9/NoammAddons/releases/download/") {
                name = "NoammAddonsReleases"
                patternLayout {
                    artifact("[revision]/[artifact]-[revision]-[classifier].[ext]")
                }
                metadataSources { artifact() }
            }
        }
        filter { includeModule("com.github.Noamm9", "NoammAddons") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    implementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    implementation("net.fabricmc:fabric-language-kotlin:${project.property("fabric_kotlin_version")}")

    implementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")

    runtimeOnly("me.djtheredstoner:DevAuth-fabric:${project.property("devauth_version")}")

    implementation("com.github.Noamm9:NoammAddons:$noammAddonsVersion:$minecraftVersion-$noammAddonsType") {
        isTransitive = false
    }
}

val resourceProperties = mapOf(
    "version" to project.version,
    "mod_name" to modName,
    "mod_id" to modId,
    "minecraft_version" to minecraftVersion,
    "java_version" to targetJavaVersion,
    "loader_version" to project.property("loader_version"),
    "fabric_kotlin_version" to project.property("fabric_kotlin_version"),
    "noammaddons_version" to noammAddonsVersion
)

tasks.processResources {
    inputs.properties(resourceProperties)
    filteringCharset = "UTF-8"

    filesMatching(listOf("fabric.mod.json", "$modId.mixins.json")) {
        expand(resourceProperties)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
    options.compilerArgs.addAll(listOf("-Xlint:deprecation", "-Xlint:unchecked"))
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}

val controlTestSources = sourceSets.create("controlTest") {
    kotlin.setSrcDirs(listOf(rootProject.file("src/controlTest/kotlin"), rootProject.file("src/main/kotlin"), rootProject.file("src/devControl/kotlin")))
    kotlin.include("crit/**", "**/critaddons/MapContract*.kt", "**/crit/control/Session*.kt", "**/crit/control/TaskContract.kt", "**/crit/control/ResourceLeases.kt", "**/crit/control/CancellationToken.kt", "**/crit/control/ManualInputState.kt", "**/crit/control/LifecycleAdapter.kt", "**/dev/FixtureSession.kt")
}

dependencies {
    add(controlTestSources.implementationConfigurationName, kotlin("stdlib"))
}

tasks.register<JavaExec>("controlTest") {
    group = "verification"
    description = "Runs the isolated JVM control contracts."
    dependsOn(controlTestSources.classesTaskName)
    classpath = controlTestSources.runtimeClasspath
    mainClass.set("crit.ControlTestMainKt")
}

val devControlSources = sourceSets.create("devControl") {
    kotlin.setSrcDirs(listOf(rootProject.file("src/devControl/kotlin")))
    resources.setSrcDirs(listOf(rootProject.file("src/devControl/resources")))
    compileClasspath += sourceSets.main.get().output + sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().output + sourceSets.main.get().runtimeClasspath
}

val fixtureArchive = layout.buildDirectory.file("dev-control/fixture.jar")
val devControlJar = tasks.register<Jar>("devControlJar") {
    dependsOn(devControlSources.classesTaskName)
    archiveClassifier.set("dev-control-fixture")
    destinationDirectory.set(layout.buildDirectory.dir("dev-control"))
    archiveFileName.set("fixture.jar")
    from(devControlSources.output)
}

loom {
    runs.register("controlFixture") {
        client()
        sourceSet.set(devControlSources.name)
        systemProperties.put("crit.controlFixture", "true")
        systemProperties.put("fabric.addMods", fixtureArchive.map { it.asFile.absolutePath })
    }
}
tasks.named("runControlFixture") { dependsOn(devControlJar) }

tasks.register("verifyControlFixtureIsolation") {
    group = "verification"
    dependsOn(devControlSources.classesTaskName, tasks.jar)
    doLast {
        val archive = tasks.jar.get().archiveFile.get().asFile
        ZipFile(archive).use { zip ->
            check(zip.entries().asSequence().none { it.name.contains("/dev/") || it.name.contains("Fixture") })
            val metadata = zip.getInputStream(zip.getEntry("fabric.mod.json")).bufferedReader().readText()
            check(!metadata.contains("crit_control_fixture") && !metadata.contains("CritControlFixture"))
        }
        loom.runs.filter { it.name != "controlFixture" }.forEach { run ->
            check(run.sourceSet.get() != devControlSources.name)
            check(!run.systemProperties.get().containsKey("crit.controlFixture"))
        }
        check(sourceSets.main.get().runtimeClasspath.files.none { it in devControlSources.output.files })
        println("Fixture isolation passed: ${archive.name}")
    }
}

tasks.withType<Jar>().configureEach {
    archiveVersion.set("${project.version}-$minecraftVersion")
}

tasks.jar {
    archiveClassifier.set(noammAddonsType)
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = modName
            version = "${project.version}-$minecraftVersion"
            from(components["java"])
        }
    }
}

loom {
    runs.configureEach {
        runDirectory.set(layout.projectDirectory.dir("run"))
        generateRunConfig.set(false)
        jvmArguments.addAll(
            "-XX:+AllowEnhancedClassRedefinition", // This is only available in JBR
            "-XX:+IgnoreUnrecognizedVMOptions",
        )
        jvmArguments.add(provider {
            "-javaagent:${configurations.compileClasspath.get().single { "sponge-mixin" in it.name }}"
        })
    }
}
