plugins {
    alias(libs.plugins.androidApplication)
}


android {
    namespace = "step.learning.managerassistant"
    compileSdk = 34

    defaultConfig {
        applicationId = "step.learning.managerassistant"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0") // графіка

    // Room components
    implementation ("androidx.room:room-runtime:2.5.0") // Замість 2.5.0 використовуйте найновішу версію
    annotationProcessor ("androidx.room:room-compiler:2.5.0") // Для Java


    // Опціонально, для підтримки RxJava2
    implementation ("androidx.room:room-rxjava2:2.5.0")

    // Опціонально, для підтримки RxJava3
    implementation ("androidx.room:room-rxjava3:2.5.0")

    // Опціонально, для підтримки Guava
    implementation ("androidx.room:room-guava:2.5.0")

    // Опціонально, для тестування
    testImplementation ("androidx.room:room-testing:2.5.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0") // для логування запитів
    implementation ("com.google.code.gson:gson:2.8.6")
    implementation ("com.google.firebase:firebase-messaging:23.0.0")
}