plugins {
    `maven-publish`
//    kotlin("kapt")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":ioc"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
    implementation("com.squareup:kotlinpoet:1.10.1")
    implementation("com.squareup:kotlinpoet-ksp:1.10.1")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.5.31-1.0.0")

//    implementation("com.squareup:kotlinpoet-metadata:1.10.1")
//    implementation("com.google.auto.service:auto-service:1.0")
//    kapt("com.google.auto.service:auto-service:1.0")
}

publishing {
    publications {
        create<MavenPublication>("maven") {

            from(components["java"])
        }
    }
}
