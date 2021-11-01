plugins {
    `java-library`
    `maven-publish`
}

dependencies {
    implementation(project(":common"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {

            from(components["java"])
        }
    }
}
