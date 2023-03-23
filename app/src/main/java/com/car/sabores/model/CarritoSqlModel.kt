package com.car.sabores.model

data class CarritoSqlModel (
    val id: Int = 0,
    val nombre: String = "",
    val cantidad: Int = 1,
    val precio: String = "",
    val costo: Double = 0.0,
    val imagen: Int = 0
):java.io.Serializable
