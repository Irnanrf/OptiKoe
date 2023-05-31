package com.irnanrf.optikoecapstone.data

import com.irnanrf.optikoecapstone.R
import com.irnanrf.optikoecapstone.data.model.Product
import com.irnanrf.optikoecapstone.data.model.TransactionHistory

object DummyData {
    val listProduct = listOf(
        Product(
            id = "1",
            name = "Frame Kacamata Optical - TRM 555 C2",
            price = 70000,
            desc = "Spesifikasi:\n" +
                    "- Model: TRM 555\n" +
                    "- Bahan Frame: TR90 (Plastic Lentur) + Besi\n" +
                    "- Bahan Lensa Bawaan: Acrylic tanpa minus/plus/cylinder (bisa custom lensa minus/plus/cylinder)\n" +
                    "- Frame ringan dan kuat.\n" +
                    "\n" +
                    "Ukuran:\n" +
                    "- Lens Width: 4.9 cm\n" +
                    "- Lens Height: 4.4 cm\n" +
                    "- Frame Width: 13.7 cm\n" +
                    "- Bridge Length: 2.2 cm\n" +
                    "- Temple Length: 14.2 cm\n" +
                    "\n" +
                    "Kelengkapan:\n" +
                    "- Frame\n" +
                    "- Hard Case & Kain Pembersih (Khusus pembelian dengan custom lensa)",
            faceshape = "Round, Rectangle",
            location = "Jakarta",
            image = R.drawable.frame1),
        Product(
            id = "2",
            name = "Frame Kacamata Optical - TR90 7007 C2",
            price = 65000,
            desc = "Spesifikasi:\n" +
                    "- Model: TR90 7007\n" +
                    "- Bahan Frame: TR90 (Plastic Lentur)\n" +
                    "- Bahan Lensa Bawaan: Acrylic tanpa minus/plus/cylinder (bisa custom lensa minus/plus/cylinder)\n" +
                    "- Frame ringan dan kuat.\n" +
                    "\n" +
                    "Ukuran:\n" +
                    "- Lens Width: 5.4 cm\n" +
                    "- Lens Height: 4.4 cm\n" +
                    "- Frame Width: 14.8 cm\n" +
                    "- Bridge Length: 1.9 cm\n" +
                    "- Temple Length: 14.5 cm\n" +
                    "\n" +
                    "Kelengkapan:\n" +
                    "- Frame\n" +
                    "- Hard Case & Kain Pembersih (Khusus pembelian dengan custom lensa)",
            faceshape = "Oval, Round",
            location = "Denpasar",
            image = R.drawable.frame2),
        Product(
            id = "3",
            name = "Frame Kacamata Optical - TR90 7002 C1",
            price = 65000,
            desc = "Spesifikasi:\n" +
                    "- Model: TR90 7002\n" +
                    "- Bahan Frame: TR90 (Plastic Lentur)\n" +
                    "- Bahan Lensa Bawaan: Acrylic tanpa minus/plus/cylinder (bisa custom lensa minus/plus/cylinder)\n" +
                    "- Frame ringan dan kuat.\n" +
                    "\n" +
                    "Ukuran:\n" +
                    "- Lens Width: 5.4 cm\n" +
                    "- Lens Height: 5.1 cm\n" +
                    "- Frame Width: 14.4 cm\n" +
                    "- Bridge Length: 1.7 cm\n" +
                    "- Temple Length: 14.4 cm\n" +
                    "\n" +
                    "Kelengkapan:\n" +
                    "- Frame\n" +
                    "- Hard Case & Kain Pembersih (Khusus pembelian dengan custom lensa)",
            faceshape = "Square",
            location = "Denpasar",
            image = R.drawable.frame1),
    )

    val listHistory = listOf(
        TransactionHistory(
            idUser = "LgOFkPoXQaMMMCem4MeewCemsha2",
            productName = "Dummy Frame Kacamata Optical - TRM 555 C2",
            productPrice = "70000",
            quantity = "2",
            shippingAddress = "Jl. Kemang Selatan., RT.11/RW.4, Bangka, South Jakarta City, Jakarta",
            shippingCourier = "SiNinja - Reguler",
            shippingCost = "10000",
            shippingName = "Irnan",
            shippingPhone = "085155401774",
            totalPrice = "150000",
            paymentMethod = "Bank Transfer",
            status = "On Process",
            date = "May 27, 2023",
            image = R.drawable.frame1
        )

    )
}