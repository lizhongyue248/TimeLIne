package expect

import StoreKeys
import kotlinx.serialization.KSerializer

interface Store {
    fun <T> get(key: StoreKeys, defaultValue: T, serializer: KSerializer<T>): T

    fun <T> set(key: StoreKeys, content: T, serializer: KSerializer<T>)

}

expect fun getStore(): Store
