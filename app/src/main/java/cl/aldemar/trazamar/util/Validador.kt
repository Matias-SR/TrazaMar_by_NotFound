package cl.aldemar.trazamar.util

/**
 * Utilidades puras de validaciones de formularios y campos.
 */
object Validador {

    /**
     * Valida si un comentario de revisión del supervisor cumple con la longitud mínima requerida (mínimo 5 caracteres).
     */
    fun comentarioSupervisorValido(comentario: String?): Boolean {
        return (comentario?.trim()?.length ?: 0) >= 5
    }

    /**
     * Valida el formulario de registro de muestra.
     * Devuelve un mapa campo -> mensaje de error. Si el mapa está vacío, el formulario es válido.
     */
    fun validarFormulario(
        centroId: Int?,
        trenId: Int?,
        lineaId: Int?,
        tramoMetros: Double?,
        conteo: Int?,
        fotoPath: String?,
        enviarARevision: Boolean
    ): Map<String, String> = buildMap {
        if (centroId == null) put("centro", "Selecciona un centro de cultivo")
        if (trenId == null) put("tren", "Selecciona un tren")
        if (lineaId == null) put("linea", "Selecciona una línea")

        if (tramoMetros == null || tramoMetros <= 0.0 || tramoMetros > 50.0) {
            put("tramo", "El tramo debe ser un valor válido entre 0.1 y 50 metros")
        }

        if (enviarARevision) {
            if (conteo == null || conteo <= 0) {
                put("conteo", "El conteo debe ser mayor a 0 para enviar a revisión")
            }
            if (fotoPath.isNullOrBlank()) {
                put("foto", "Debes adjuntar una fotografía de evidencia")
            }
        } else {
            if (conteo != null && conteo < 0) {
                put("conteo", "El conteo no puede ser negativo")
            }
        }
    }
}
