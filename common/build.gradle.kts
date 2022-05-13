plugins {
    `java-library`
    `maven-publish`
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
}

allOpen {
    annotation("com.vanmo.common.annotations.DataClass")
}

publishing {
    publications {
        create<MavenPublication>("maven") {

            from(components["java"])
        }
    }
}
