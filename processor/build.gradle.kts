plugins {
    `maven-publish`
}
val kotlin_version: String by project
val ksp_version: String by project

dependencies {
    implementation(project(":common"))
    implementation(project(":ioc"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    implementation("com.squareup:kotlinpoet:1.12.0")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
    implementation("com.google.devtools.ksp:symbol-processing-api:$kotlin_version-$ksp_version")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
