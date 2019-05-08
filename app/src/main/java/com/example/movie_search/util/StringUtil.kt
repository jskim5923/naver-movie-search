package com.example.movie_search.util

import android.os.Build
import android.text.Html

class StringUtil {
    companion object {
        fun subtractEndSymbol(str: String, symbol: String): String {
            if (str.isEmpty()) {
                return str
            }

            if (str.endsWith(symbol)) {
                return str.dropLast(1)
            }
            return str
        }

        fun stripHtmlTag(str: String): String {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                return Html.fromHtml(str).toString()
            }
            return Html.fromHtml(str,Html.FROM_HTML_MODE_LEGACY).toString()
        }
    }
}