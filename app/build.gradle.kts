plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.alwaysspring"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.alwaysspring"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    implementation(libs.legacy.support.v4)
    implementation("de.hdodenhof:circleimageview:3.1.0") //동그라미
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit 라이브러리 추가
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // JSON <-> Java 객체 변환을 위해 필요
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // (선택) 네트워크 로깅 인터셉터
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Retrofit 라이브러리 추가
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // JSON 변환을 위한 Gson 컨버터 추가
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // (선택사항) Retrofit 로그를 위한 HttpLoggingInterceptor 추가
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
}