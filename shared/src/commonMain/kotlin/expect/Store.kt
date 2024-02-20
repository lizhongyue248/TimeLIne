package expect

import kotlinx.serialization.KSerializer
import state.StoreKeys

interface Store {
    fun <T> get(key: StoreKeys, defaultValue: T, serializer: KSerializer<T>): T

    fun <T> set(key: StoreKeys, content: T, serializer: KSerializer<T>)

}

expect fun getStore(): Store
