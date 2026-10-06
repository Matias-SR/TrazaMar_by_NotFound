package com.duoc.trazamar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.trazamar.viewmodel.LoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.duoc.trazamar.R

// Colores oficiales de TrazaMar
private val AzulPrincipal = Color(0xFF0D6E8C)
private val FondoApp = Color(0xFFF2F7F7)
private val ColorError = Color(0xFFC1443C)

@Composable
fun LoginScreen(
    vm: LoginViewModel = viewModel(),
    onLoginExitoso: () -> Unit
) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = FondoApp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_trazamar3),
                contentDescription = "Logo de TrazaMar",
                modifier = Modifier.size(300.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Trazabilidad de muestreo de choritos",
                fontSize = 20.sp,
                color = Color(0xFF1B2A2E)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = state.correo,
                onValueChange = { nuevoTexto -> vm.onCorreoChange(nuevoTexto) },
                label = { Text("Correo electrónico") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.contrasena,
                onValueChange = { nuevoTexto -> vm.onContrasenaChange(nuevoTexto) },
                label = { Text("Contraseña") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = { vm.ingresar(onExito = onLoginExitoso) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal)
            ) {
                Text("INGRESAR")
            }

            state.mensajeError?.let { error ->
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = error, color = ColorError)
            }
        }
    }
}