plugins {
    `java-library`
    id("com.vanmo.plugin") version "1.0.0"
}

dependencies {
    import(implementation(project(":common")))
    import(implementation(project(":ioc")))
}
