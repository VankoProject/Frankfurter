plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    //Javax annotation
    implementation(group = "javax.inject", name = "javax.inject", version = "1")
}