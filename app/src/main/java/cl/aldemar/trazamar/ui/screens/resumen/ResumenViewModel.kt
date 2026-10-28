package cl.aldemar.trazamar.ui.screens.resumen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.aldemar.trazamar.data.local.entities.MuestraEntity
import cl.aldemar.trazamar.data.repository.AuthRepository
import cl.aldemar.trazamar.data.repository.MuestraRepository
import cl.aldemar.trazamar.data.repository.ResultadoApi
import cl.aldemar.trazamar.util.Reglas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ResumenUiState(
    val muestras: List<MuestraEntity> = emptyList(),
    val rol: String = "",
    val cargando: Boolean = true,
    val mensajeSync: String? = null,
    val sincronizando: Boolean = false,
) {
    val totalMuestras get() = muestras.size
    val totalIndividuos get() = muestras.sumOf { it.conteoIndividuos ?: 0 }

    val densidadPromedio: Double get() {
        if (muestras.isEmpty()) return 0.0
        val densidades = muestras.map { Reglas.densidad(it.conteoIndividuos ?: 0, it.tramoMetros ?: 1.0) }
        return if (densidades.isNotEmpty()) densidades.average() else 0.0
    }

    val conteoPorEstado: Map<String, Int> get() {
        val mapa = mutableMapOf(
            "borrador" to 0,
            "pendiente" to 0,
            "observada" to 0,
            "corregida" to 0,
            "validada" to 0
        )
        muestras.forEach { m ->
            val est = m.estado.lowercase()
            mapa[est] = (mapa[est] ?: 0) + 1
        }
        return mapa
    }

    val validadasPorSincronizar get() = muestras.count { it.estado.equals("validada", ignoreCase = true) && !it.sincronizada }
}

class ResumenViewModel(application: Application) : AndroidViewModel(application) {
    private val muestraRepo = MuestraRepository(application)
    private val authRepo = AuthRepository(application)

    private val _uiState = MutableStateFlow(ResumenUiState())
    val uiState: StateFlow<ResumenUiState> = _uiState

    init {
        cargarResumen()
    }

    private fun cargarResumen() {
        viewModelScope.launch {
            val sesion = authRepo.sesion.first()
            _uiState.update { it.copy(rol = sesion.rol ?: "") }

            // Intentar descargar historial actualizado de la API
            muestraRepo.sincronizarHistorial()

            // Escuchar la base de datos local
            muestraRepo.observarCache().collect { lista ->
                _uiState.update { state ->
                    val vis = lista.filter { Reglas.esVisible(state.rol, it.operadorNombre, sesion.username ?: "", it.estado) }
                    state.copy(muestras = vis, cargando = false)
                }
            }
        }
    }

    fun sincronizar() {
        _uiState.update { it.copy(sincronizando = true, mensajeSync = null) }
        viewModelScope.launch {
            val res = muestraRepo.sincronizarHistorial()
            when (res) {
                is ResultadoApi.Exito -> _uiState.update { it.copy(sincronizando = false, mensajeSync = "Sincronización completada con éxito.") }
                is ResultadoApi.Error -> _uiState.update { it.copy(sincronizando = false, mensajeSync = res.mensaje) }
            }
        }
    }
}
