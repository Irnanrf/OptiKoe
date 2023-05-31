package com.irnanrf.optikoecapstone.data.model

data class TransactionHistory(
    val idUser: String? = null,
    val productName: String?,
    val productPrice: String?,
    val quantity: String?,
    val shippingAddress: String?,
    val shippingCourier: String?,
    val shippingCost: String?,
    val shippingName: String?,
    val shippingPhone: String?,
    val totalPrice: String?,
    val paymentMethod: String?,
    val status: String?,
    val date: String?,
    val image: Int
)