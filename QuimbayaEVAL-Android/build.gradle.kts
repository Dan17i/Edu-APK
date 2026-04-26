plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
    id 'com.google.dagger.hilt.android'
}

android {
    namespace "com.sena.quimbayaeval"
    compileSdk 34

    defaultConfig {
        applicationId "com.sena.quimbayaeval"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField "String", "BASE_URL", '"http://10.0.2.2:8080/api/"'
        buildConfigField "String", "APP_VERSION", '"1.0.0"'
    }

    buildTypes {
        debug {
            debuggable true
            minifyEnabled false
        }
        release {
            debuggable false
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.release
        }
    }

    signingConfigs {
        release {
            storeFile file("../keystore/release.keystore")
            storePassword System.getenv("KEYSTORE_PASSWORD") ?: "password"
            keyAlias System.getenv("KEY_ALIAS") ?: "release"
            keyPassword System.getenv("KEY_PASSWORD") ?: "password"
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding true
        compose false
    }
}

dependencies {
    // Core Android
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.7.0'

    // UI
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.fragment:fragment-ktx:1.6.2'
    implementation 'androidx.recyclerview:recyclerview:1.3.2'

    // Navigation
    implementation 'androidx.navigation:navigation-fragment-ktx:2.7.6'
    implementation 'androidx.navigation:navigation-ui-ktx:2.7.6'

    // Reactive
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.7.0'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3'

    // Hilt DI
    implementation 'com.google.dagger:hilt-android:2.50'
    kapt 'com.google.dagger:hilt-compiler:2.50'

    // Networking
    implementation 'com.squareup.retrofit2:retrofit:2.10.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.10.0'
    implementation 'com.squareup.okhttp3:okhttp:4.11.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'

    // Local Database
    implementation 'androidx.room:room-runtime:2.6.1'
    implementation 'androidx.room:room-ktx:2.6.1'
    kapt 'androidx.room:room-compiler:2.6.1'

    // Data Store
    implementation 'androidx.datastore:datastore-preferences:1.0.0'

    // Security
    implementation 'androidx.security:security-crypto:1.1.0-alpha06'

    // Logging
    implementation 'com.jakewharton.timber:timber:5.0.1'

    // JSON
    implementation 'com.google.code.gson:gson:2.10.1'

    // Testing
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.mockito:mockito-core:5.5.1'
    testImplementation 'org.mockito.kotlin:mockito-kotlin:5.1.0'

    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    androidTestImplementation 'com.google.dagger:hilt-android-testing:2.50'
    kaptAndroidTest 'com.google.dagger:hilt-compiler:2.50'
}

kapt {
    correctErrorTypes true
}
