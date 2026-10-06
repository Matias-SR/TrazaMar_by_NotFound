package com.duoc.trazamar.model

data class LoginUiState(
    val correo: String = "",
    val contrasena: String = "",
    // '?' = puede tener un mensaje de error o no (null)
    val mensajeError: String? = null
)