import com.blamejared.modtemplate.Utils
import groovy.namespace.QName
import groovy.util.Node
import groovy.util.NodeList
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.darkhax.curseforgegradle.Constants as CFG_Contants

plugins {
    `maven-publish`
    id("net.minecraftforge.gradle") version ("5.1.+")
    id("org.parchmentmc.librarian.forgegradle") version ("1.+")
    id("org.spongepowered.mixin") version ("0.7-SNAPSHOT")
    id("com.blamejared.modtemplate")
    id("net.darkhax.curseforgegradle") version ("1.0.9")
}

val modVersion: String by project
val minecraftVersion: String by project
val forgeVersion: String by project
val forgeAtsEnabled: String by project
val modName: String by project
val modAuthor: String by project
val modId: String by project
val modAvatar: String by project
val curseProjectId: String by project
val curseHomepageLink: String by project
val gitFirstCommit: String by project
val gitRepo: String by project
val modJavaVersion: String by project

val baseArchiveName = "${modName}-forge-${minecraftVersion}"

version = Utils.updatingVersion(modVersion)
base {
    archivesName.set(baseArchiveName)
}

mixin {

    add(sourceSets.main.get(), "${modId}.refmap.json")

    config("${modId}.mixins.json")
    config("${modId}.forge.mixins.json")
}

dependencies {
    "minecraft"("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")
    compileOnly(project(":Common"))
    (project.ext["zenCodeDeps"] as Set<*>).forEach {
        implementation(project(it.toString()))
    }
    implementation(fg.deobf("mezz.jei:jei-1.18.2:9.7.0.229"))
    annotationProcessor("org.spongepowered:mixin:0.8.5-SNAPSHOT:processor")

    gametestCompileOnly(project(":Common"))
    gametestCompileOnly(files(project(":Common").dependencyProject.sourceSets.gametest.get().java.srcDirs))
    (project.ext["zenCodeTestDeps"] as Set<*>).forEach {
        gametestImplementation(project(it.toString()).dependencyProject.sourceSets.test.get().output)
    }
}

minecraft {
    mappings("parchment", "2022.03.13-1.18.2")

    if (forgeAtsEnabled.toBoolean()) {
        accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))
        project.logger.debug("Forge Access Transformers are enabled for this project.")
    }

    runs {
        all {
            lazyToken("minecraft_classpath") {
                configurations.library.get().copyRecursive().resolve()
                        .joinToString(File.pathSeparator) { it.absolutePath }

                configurations.gametestLibrary.get().copyRecursive().resolve()
                        .joinToString(File.pathSeparator) { it.absolutePath }
            }
        }
        create("client") {
            taskName("Client")
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args("-mixin.config=${modId}.mixins.json", "-mixin.config=${modId}.forge.mixins.json")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                    (project.ext["zenCodeDeps"] as Set<*>).forEach {
                        source(project(it.toString()).sourceSets.main.get())
                    }
                }
            }
        }
        create("server") {
            taskName("Server")
            workingDirectory(project.file("run_server"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args("-mixin.config=${modId}.mixins.json", "-mixin.config=${modId}.forge.mixins.json", "nogui")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                    (project.ext["zenCodeDeps"] as Set<*>).forEach {
                        source(project(it.toString()).sourceSets.main.get())
                    }
                }
            }
        }

        create("data") {
            taskName("Data")
            workingDirectory(project.file("run_game_test"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args(
                    "--mod",
                    modId,
                    "--all",
                    "--output",
                    file("src/generated/resources/"),
                    "--existing",
                    file("src/main/resources/")
            )
            args("-mixin.config=${modId}.mixins.json", "-mixin.config=${modId}.forge.mixins.json")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                    (project.ext["zenCodeDeps"] as Set<*>).forEach {
                        source(project(it.toString()).sourceSets.main.get())
                    }
                }
            }
        }

        create("gameTestServer") {
            taskName("GameTest")
            workingDirectory(project.file("run_game_test"))
            ideaModule("${rootProject.name}.${project.name}.main")
            property("forge.enabledGameTestNamespaces", modId)
            setForceExit(false)
            args("-mixin.config=${modId}.mixins.json", "-mixin.config=${modId}.forge.mixins.json")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(sourceSets.gametest.get())

                    source(project(":Common").sourceSets.main.get())
                    source(project(":Common").sourceSets.gametest.get())

                    (project.ext["zenCodeDeps"] as Set<*>).forEach {
                        source(project(it.toString()).sourceSets.main.get())
                    }
                    (project.ext["zenCodeTestDeps"] as Set<*>).forEach {
                        source(project(it.toString()).sourceSets.test.get())
                    }
                }
            }
        }
    }
}

modTemplate {
    mcVersion(minecraftVersion)
    curseHomepage(curseHomepageLink)
    displayName(modName)
    modLoader("Forge")
    changelog.apply {
        enabled(true)
        firstCommit(gitFirstCommit)
        repo(gitRepo)
    }
    versionTracker.apply {
        enabled(true)
        endpoint(System.getenv("versionTrackerAPI"))
        author(modAuthor)
        projectName("${modName}-Forge")
        homepage(curseHomepageLink)
        uid(System.getenv("versionTrackerKey"))
    }
}

sourceSets.main.get().resources.srcDir("src/generated/resources")

tasks.withType<JavaCompile> {
    source(project(":Common").sourceSets.main.get().allSource)

    (project.ext["zenCodeDeps"] as Set<*>).forEach {
        source(project(it.toString()).sourceSets.main.get().allSource)
    }
}

tasks.compileGametestJava {
    outputs.upToDateWhen { false }
    source(project(":Common").sourceSets.gametest.get().java)
}

tasks.processGametestResources {
    outputs.upToDateWhen { false }
    from(project(":Common").sourceSets.gametest.get().resources)
}

tasks.withType<Javadoc> {
    source(project(":Common").sourceSets.main.get().allJava)

    (project.ext["zenCodeDeps"] as Set<*>).forEach {
        source(project(it.toString()).sourceSets.main.get().allJava)
    }
}

tasks.named<Jar>("sourcesJar") {
    from(project(":Common").sourceSets.main.get().allSource)

    (project.ext["zenCodeDeps"] as Set<*>).forEach {
        from(project(it.toString()).sourceSets.main.get().allSource)
    }
}

tasks.processResources {
    outputs.upToDateWhen { false }
    from(project(":Common").sourceSets.main.get().resources)
    dependsOn(":StdLibs:zipItUp")
    from(files(evaluationDependsOn(":StdLibs").tasks.getByName("zipItUp").outputs))

    filesMatching("*.mixins.json") {
        expand("refmap_target" to "${modId}.")
    }
}

tasks {
    jar {
        finalizedBy("reobfJar")
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = baseArchiveName
            from(components["java"])

            pom.withXml {
                val depNodeList = asNode()["dependencies"] as NodeList
                depNodeList.map { it as Node }.forEach { depList ->
                    val deps = depList.getAt(QName("http://maven.apache.org/POM/4.0.0", "dependency"))
                    deps.map { it as Node }.forEach { dep ->
                        dep.parent().remove(dep)
                    }
                }
            }
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}

tasks.create<TaskPublishCurseForge>("publishCurseForge") {
    apiToken = Utils.locateProperty(project, "curseforgeApiToken") ?: 0

    val mainFile = upload(curseProjectId, file("${project.buildDir}/libs/$baseArchiveName-$version.jar"))
    mainFile.changelogType = "markdown"
    mainFile.changelog = Utils.getFullChangelog(project)
    mainFile.releaseType = CFG_Contants.RELEASE_TYPE_RELEASE
    mainFile.addJavaVersion("Java $modJavaVersion")
    mainFile.addRequirement("jeitweaker")

    doLast {
        project.ext.set("curse_file_url", "${curseHomepageLink}/files/${mainFile.curseFileId}")
    }
}