package cl.aldemar.trazamar.util

/**
 * Reglas de negocio puras para la gestión y trazabilidad de muestras de choritos.
 * Diseñado de forma desvinculada de Android para facilitar pruebas unitarias.
 */
object Reglas {

    /**
     * Calcula la densidad de individuos por metro de cuerda.
     * @param conteo Número de individuos contados.
     * @param tramoMetros Tramo muestreado en metros (ej. 0.2 m = 20 cm).
     */
    fun densidad(conteo: Int, tramoMetros: Double): Double {
        if (tramoMetros <= 0.0) return 0.0
        return conteo / tramoMetros
    }

    /**
     * Calcula la densidad a partir de un tramo expresado en centímetros.
     */
    fun densidadCm(conteo: Int, tramoCm: Int): Double {
        if (tramoCm <= 0) return 0.0
        return conteo * 100.0 / tramoCm
    }

    /**
     * Determina el estado al enviar a revisión: si estaba "observada" pasa a "corregida",
     * de lo contrario pasa a "pendiente".
     */
    fun estadoAlEnviar(actual: String): String {
        return if (actual.equals("observada", ignoreCase = true)) "corregida" else "pendiente"
    }

    /**
     * Un operador puede editar una muestra si es de su propiedad y está en estado borrador u observada.
     */
    fun puedeEditar(rol: String, operadorUsername: String?, miUsername: String, estado: String): Boolean {
        val esOperador = rol.equals("operador", ignoreCase = true)
        val esPropia = operadorUsername.isNullOrBlank() || operadorUsername.equals(miUsername, ignoreCase = true)
        val estadoEditable = estado.equals("borrador", ignoreCase = true) || estado.equals("observada", ignoreCase = true)
        return esOperador && esPropia && estadoEditable
    }

    /**
     * Un operador puede eliminar sólo sus muestras en borrador.
     */
    fun puedeEliminar(rol: String, operadorUsername: String?, miUsername: String, estado: String): Boolean {
        val esOperador = rol.equals("operador", ignoreCase = true)
        val esPropia = operadorUsername.isNullOrBlank() || operadorUsername.equals(miUsername, ignoreCase = true)
        return esOperador && esPropia && estado.equals("borrador", ignoreCase = true)
    }

    /**
     * El supervisor o admin puede revisar muestras que estén en estado pendiente o corregida.
     */
    fun puedeRevisar(rol: String, estado: String): Boolean {
        val esSupervisorOAdmin = rol.equals("supervisor", ignoreCase = true) || rol.equals("admin", ignoreCase = true)
        val enRevision = estado.equals("pendiente", ignoreCase = true) || estado.equals("corregida", ignoreCase = true)
        return esSupervisorOAdmin && enRevision
    }

    /**
     * Determina qué muestras son visibles para cada rol.
     */
    fun esVisible(rol: String, operadorUsername: String?, miUsername: String, estado: String): Boolean {
        return when (rol.lowercase()) {
            "operador" -> operadorUsername.isNullOrBlank() || operadorUsername.equals(miUsername, ignoreCase = true)
            "supervisor", "admin" -> !estado.equals("borrador", ignoreCase = true)
            "analista" -> estado.equals("validada", ignoreCase = true)
            else -> false
        }
    }
}
