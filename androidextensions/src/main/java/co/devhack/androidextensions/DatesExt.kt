package co.devhack.androidextensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.getDateWithFormat(format: String = "dd-MM-yyyy HH:mm:ss"): String {
    return SimpleDateFormat(format).format(this)
}