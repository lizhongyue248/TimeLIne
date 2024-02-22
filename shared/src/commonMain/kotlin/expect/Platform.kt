package expect

enum class Platform {
    ANDROID,
    NATIVE,
    JVM,
    WASM;

    fun isDesktop() = this == JVM

    fun isIOS() = this == NATIVE

    fun isBrowser() = this == WASM

    fun isAndroid() = this === ANDROID
}

expect fun getPlatform(): Platform