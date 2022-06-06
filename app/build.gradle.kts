plugins {
    id(BuildPlugins.androidApplication)
    kotlin(BuildPlugins.android)
    kotlin(BuildPlugins.kapt)
    id(BuildPlugins.hilt)
}

android {
    compileSdk = SdkVersions.compileSdk

    defaultConfig {
        applicationId = "com.patronas.flickr"
        minSdk = SdkVersions.minSdk
        targetSdk = SdkVersions.targetSdk
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "com.patronas.flickr.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions.add("environment")
    productFlavors {
        create("flavorTest") {
            buildConfigField("boolean", "SERVICE_CONFIG_LIVE", "false")
            dimension = "environment"
        }
        create("flavorLive") {
            buildConfigField("boolean", "SERVICE_CONFIG_LIVE", "true")
            dimension = "environment"
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/*"
        }
    }

}

dependencies {

    //modules
    implementation(project(":network"))
    implementation(project(":data"))
    implementation(project(":domain"))

    //core & ui
    implementation(Libraries.coreKtx)
    implementation(Libraries.appCompat)
    implementation(Libraries.material)
    implementation(Libraries.composeUi)
    implementation(Libraries.composeMaterial)
    implementation(Libraries.composeUiPreview)
    implementation(Libraries.runtimeLifecycleKtx)
    implementation(Libraries.composeActivity)

    //navigation
    implementation(Libraries.navigation)

    //image loading - We use this library instead of Coil, because coil had issues on loading some images from flickr
    implementation(Libraries.landscapistGlide)

    //logger
    implementation(Libraries.timber)

    //dependency injection
    implementation(Libraries.hilt)
    kapt(Libraries.hiltCompiler)

    //network
    implementation(Libraries.retrofit)
    implementation(Libraries.okhttpLogging)
    implementation(Libraries.moshi)
    kapt(Libraries.moshiCodegen)

    //google play services
    implementation(Libraries.googlePlayServices)

    //test libraries
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.mockitoKotlin)
    testImplementation(TestLibraries.coroutines)
    testImplementation(TestLibraries.archCore)

}