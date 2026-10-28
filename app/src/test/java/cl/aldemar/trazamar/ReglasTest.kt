package cl.aldemar.trazamar

import cl.aldemar.trazamar.util.Reglas
import cl.aldemar.trazamar.util.Validador
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas unitarias de JUnit 4 para verificar las reglas de negocio y validaciones
 * especificadas en la pauta (DSY1105 / ALDEMAR SpA).
 */
class ReglasTest {

    @Test
    fun testCalculoDensidadEnMetros() {
        // 100 individuos en 0.2 metros (20 cm) = 500 ind/m
        val resultado = Reglas.densidad(100, 0.2)
        assertEquals(500.0, resultado, 0.001)

        // Tramo 0 o negativo retorna 0.0
        assertEquals(0.0, Reglas.densidad(50, 0.0), 0.001)
    }

    @Test
    fun testCalculoDensidadEnCentimetros() {
        // 10 individuos en 20 cm = 50 ind/m
        val resultado = Reglas.densidadCm(10, 20)
        assertEquals(50.0, resultado, 0.001)
    }

    @Test
    fun testEstadoAlEnviar() {
        // Si está observada y el operador la reenvía, pasa a corregida
        assertEquals("corregida", Reglas.estadoAlEnviar("observada"))
        assertEquals("corregida", Reglas.estadoAlEnviar("OBSERVADA"))

        // Cualquier otro estado (borrador, etc.) pasa a pendiente
        assertEquals("pendiente", Reglas.estadoAlEnviar("borrador"))
    }

    @Test
    fun testPermisosPorRolYEstado() {
        val op = "OP-01"
        val sup = "SUP-01"

        // Operador puede editar sus propios borradores u observadas
        assertTrue(Reglas.puedeEditar("operador", op, op, "borrador"))
        assertTrue(Reglas.puedeEditar("operador", op, op, "observada"))
        assertFalse(Reglas.puedeEditar("operador", op, op, "validada"))

        // Supervisor puede revisar pendientes y corregidas
        assertTrue(Reglas.puedeRevisar("supervisor", "pendiente"))
        assertTrue(Reglas.puedeRevisar("supervisor", "corregida"))
        assertFalse(Reglas.puedeRevisar("operador", "pendiente"))
    }

    @Test
    fun testVisibilidadSegunRol() {
        val op = "OP-01"

        // Operador ve sólo sus muestras
        assertTrue(Reglas.esVisible("operador", op, op, "borrador"))
        assertFalse(Reglas.esVisible("operador", "OP-02", op, "borrador"))

        // Supervisor no ve borradores de operadores
        assertFalse(Reglas.esVisible("supervisor", "OP-02", op, "borrador"))
        assertTrue(Reglas.esVisible("supervisor", "OP-02", op, "pendiente"))

        // Analista sólo ve validadas
        assertFalse(Reglas.esVisible("analista", op, op, "pendiente"))
        assertTrue(Reglas.esVisible("analista", op, op, "validada"))
    }

    @Test
    fun testValidacionFormulario() {
        // Formulario incompleto al enviar a revisión exige foto y conteo > 0
        val erroresEnviar = Validador.validarFormulario(
            centroId = 1, trenId = 1, lineaId = 1,
            tramoMetros = 0.2, conteo = 0, fotoPath = null, enviarARevision = true
        )
        assertTrue(erroresEnviar.containsKey("conteo"))
        assertTrue(erroresEnviar.containsKey("foto"))

        // Guardar borrador sin conteo ni foto es válido
        val erroresBorrador = Validador.validarFormulario(
            centroId = 1, trenId = 1, lineaId = 1,
            tramoMetros = 0.2, conteo = 0, fotoPath = null, enviarARevision = false
        )
        assertTrue(erroresBorrador.isEmpty())
    }

    @Test
    fun testValidacionComentarioSupervisor() {
        assertFalse(Validador.comentarioSupervisorValido("hola")) // < 5 caracteres
        assertTrue(Validador.comentarioSupervisorValido("Revisar conteo de la línea")) // >= 5
    }
}
