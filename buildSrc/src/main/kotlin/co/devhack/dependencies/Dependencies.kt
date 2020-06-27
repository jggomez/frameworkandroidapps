package co.devhack.dependencies

object Modules {
    const val app = ":app"
    const val base = ":base"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Scripts {
    const val publish = "publish.gradle"
}

object Versions {
    const val compileSdk = 29
    const val buildToolsVersion = "29.0.2"
    const val minSdk = 21
    const val targetSdk = 29

    const val appcompat = "1.1.0"
    const val design = "1.0.0"

    const val kotlin = "1.3.41"
    const val junit = "4.12"
    const val runner = "1.2.0"
    const val testExt = "1.1.1"
    const val kotlinCoroutines = "1.3.2"
    const val compressorImage = "2.1.0"
    const val glide = "4.10.0"
    const val retrofit = "2.6.0"
    const val loggingInterceptor = "3.12.0"
}

object SupportLibraries {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val design = "com.google.android.material:material:${Versions.design}"

}

object Libraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlinCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object ExternalLibraries {
    const val compressorImage = "id.zelory:compressor:${Versions.compressorImage}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompile = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
}

object Testing {
    const val junit = "junit:junit:${Versions.junit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val testExt = "androidx.test.ext:junit:${Versions.testExt}"
}
