package com.duoc.trazamar.viewmodel

import androidx.lifecycle.ViewModel
import com.duoc.trazamar.model.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    // Estado interno: solo el ViewModel lo puede modificar
    private val _uiState = MutableStateFlow(LoginUiState())

    // Estado público: la pantalla solo puede "mirarlo"
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onCorreoChange(nuevoCorreo: String) {
        _uiState.update { actual -> actual.copy(correo = nuevoCorreo) }
    }

    fun onContrasenaChange(nuevaContrasena: String) {
        _uiState.update { actual -> actual.copy(contrasena = nuevaContrasena) }
    }

    // onExito = función que se ejecuta SOLO si el login está bien
    fun ingresar(onExito: () -> Unit) {
        val estadoActual = _uiState.value

        if (estadoActual.correo.isEmpty() || estadoActual.contrasena.isEmpty()) {
            _uiState.update { actual ->
                actual.copy(mensajeError = "Debes ingresar correo y contraseña")
            }
            return
        }

        _uiState.update { actual -> actual.copy(mensajeError = null) }
        onExito()
    }
}