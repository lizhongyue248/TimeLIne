package store

class Route {
    companion object {
        const val HOME = "/home"
        const val NAV_LAYOUT = "/navLayout"
        const val PERIOD = "/period"
        const val ACCOUNT = "/account"
        const val EVENT = "/event/{id}"
        const val SETTING = "/setting"
        const val DETAIL = "/detail/{id}/{name}"
        const val DETAIL_ACTION = "/detailAction/{timeId}/{id}?"
        fun detailPath(id: String, name: String) = "/detail/$id/$name"
        fun detailAction(timeId: String, id: String? = null) = if (id == null) "/detailAction/$timeId"
        else "/detailAction/$timeId/$id"
        fun eventPath(id: String) = "/event/$id"
    }
}