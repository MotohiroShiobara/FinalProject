package com.final.project.teechear.util

object EscapeStringConverter {

    /**
     * MySQLのLike検索で必要なエスケープ処理を行う
     */
    fun searchQuery(query: String): String {
        return query.
                replace("_", "~_").
                replace("%", "~%").
                replace("~", "~~")
    }
}
