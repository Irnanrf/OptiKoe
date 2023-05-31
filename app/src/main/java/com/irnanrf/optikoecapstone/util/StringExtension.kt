package com.irnanrf.optikoecapstone.util

import java.text.NumberFormat
import java.util.*

fun Int.toRupiah(hideCurrency: Boolean = false): String {
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    var value = format.format(this).replace(",00", "")
    if (hideCurrency) value = value.replace("Rp", "")
    return value
}


fun Int?.toRupiah(hideCurrency: Boolean = false): String {
    return (this ?: 0).toRupiah(hideCurrency)
}

fun Double.toRupiah(hideCurrency: Boolean = false): String {
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    var value = format.format(this).replace(",00", "")
    if (hideCurrency) value = value.replace("Rp", "")
    return value
}

fun String?.toRupiah(hideCurrency: Boolean = false): String {
    if (this == null || this.isEmpty()) return ""
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    var value = format.format(this.toDouble()).replace(",00", "")
    if (hideCurrency) value = value.replace("Rp", "")
    return value
}