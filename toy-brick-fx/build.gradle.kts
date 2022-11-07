import com.shinonometn.toybrick.build.publishToShinonomeTN

/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("org.openjfx.javafxplugin") version "0.0.13"
}

javafx {
    version = "11.0.2"
    modules = listOf(
        "javafx.controls",
        "javafx.graphics",
        "javafx.fxml",
        "javafx.web",
        "javafx.media"
    )
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.6.4")
}

publishToShinonomeTN(publishing)