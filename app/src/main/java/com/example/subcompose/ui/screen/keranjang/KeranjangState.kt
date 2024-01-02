package com.example.subcompose.ui.screen.keranjang

import com.example.subcompose.model.OrderMenu

data class KeranjangState (
    val orderMenu: List<OrderMenu>,
    val totalHarga: Int
)