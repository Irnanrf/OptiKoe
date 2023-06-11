package com.irnanrf.optikoecapstone.data

import com.irnanrf.optikoecapstone.R
import com.irnanrf.optikoecapstone.data.model.Product
import com.irnanrf.optikoecapstone.data.model.TransactionHistory

object DummyData {
    val listProduct = listOf(
        Product(
            id = "1",
            name = "Dummy",
            price = 88888,
            desc = "Dummy Data Desc",
            faceshape = "Dummy Faceshape",
            location = "Dummy Location",
            image = R.drawable.frame1.toString()),

    )

    val listHistory = listOf(
        TransactionHistory(
            idUser = "Dummt",
            productName = "Dummy Product",
            productPrice = "888888",
            quantity = "888888",
            shippingAddress = "Dummy Address",
            shippingCourier = "Dummy Courier",
            shippingCost = "888888",
            shippingName = "Dummy Name",
            shippingPhone = "088888888888",
            totalPrice = "18888888",
            paymentMethod = "Dummy Method",
            status = "Dummy Status",
            date = "Dummy Date",
            image = R.drawable.frame1
        )

    )
}