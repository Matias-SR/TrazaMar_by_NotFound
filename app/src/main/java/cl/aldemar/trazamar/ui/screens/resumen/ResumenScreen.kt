package cl.aldemar.trazamar.ui.screens.resumen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.aldemar.trazamar.ui.components.EstadoBadge
import cl.aldemar.trazamar.ui.components.TrazaMarTopBar
import cl.aldemar.trazamar.ui.theme.*
import java.util.Locale

@Composable
fun ResumenScreen(
    onVolver: () -> Unit,
    viewModel: ResumenViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TrazaMarTopBar(
                titulo = "Resumen de indicadores",
                subtitulo = "Métricas y sincronización",
                alVolver = onVolver
            )
        },
        containerColor = Tide
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (estado.cargando) {
                Box(Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                // Tarjeta Principal: Totales e Indicadores
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    tonalElevation = 1.dp,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Métricas Generales", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Ink)

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total de muestras:", color = InkMuted)
                            Text("${estado.totalMuestras}", fontWeight = FontWeight.Bold, color = Ink)
                        }

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Individuos contados:", color = InkMuted)
                            Text("${estado.totalIndividuos}", fontWeight = FontWeight.Bold, color = Ink)
                        }

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Densidad promedio:", color = InkMuted)
                            Text("${"%.1f".format(Locale.US, estado.densidadPromedio)} ind/m", fontWeight = FontWeight.Bold, color = KelpSecondary)
                        }
                    }
                }

                // Tarjeta Desglose por Estado
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    tonalElevation = 1.dp,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Conteo por Estado", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Ink)

                        estado.conteoPorEstado.forEach { (est, cantidad) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                EstadoBadge(est)
                                Text("$cantidad muestras", fontWeight = FontWeight.Bold, color = Ink)
                            }
                            HorizontalDivider(color = LineSubtle)
                        }
                    }
                }

                // Tarjeta de Sincronización
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    tonalElevation = 1.dp,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Sincronización con Servidor", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Ink)

                        Text(
                            text = "Validadas pendientes de sincronizar: ${estado.validadasPorSincronizar}",
                            color = InkMuted,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        estado.mensajeSync?.let { msg ->
                            Text(msg, color = if (msg.contains("éxito")) KelpSecondary else ErrorRed, style = MaterialTheme.typography.bodySmall)
                        }

                        Button(
                            onClick = { viewModel.sincronizar() },
                            enabled = !estado.sincronizando,
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BayPrimary)
                        ) {
                            if (estado.sincronizando) {
                                CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                            } else {
                                Icon(Icons.Default.Sync, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Sincronizar ahora", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
