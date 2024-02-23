package store

class Route {
    companion object {
        const val HOME = "/home"
        const val SETTING = "/setting"
        const val DETAIL = "/detail/{id}"
        const val DETAIL_ACTION = "/detailAction/{timeId}/{id}?"
        fun detailPath(id: String) = "/detail/$id?"
        fun detailAction(timeId: String, id: String? = null) = if (id == null) "/detailAction/$timeId"
        else "/detailAction/$timeId/$id"
    }
}