package store

class Route {
    companion object {
        const val HOME = "/home"
        const val SETTING = "/setting"
        const val DETAIL = "/detail/{id}"
        fun detailPath(id: String) = "/detail/$id"
    }
}