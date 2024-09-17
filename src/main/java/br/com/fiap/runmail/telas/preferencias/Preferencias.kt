package br.com.fiap.runmail.telas.preferencias

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.runmail.telas.preferencias.PreferencesManager
import br.com.fiap.runmail.model.UserPreferences
import br.com.fiap.runmail.service.RunMailApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun PreferencesScreen(
    preferencesManager: PreferencesManager,
    navController: NavController,
    apiService: RunMailApiService
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val isDarkTheme by preferencesManager.themePreference.collectAsState(initial = false)
    val userName by preferencesManager.userName.collectAsState(initial = "")
    val notificationsEnabled by preferencesManager.notificationsEnabled.collectAsState(initial = true)
    val vibrationEnabled by preferencesManager.vibrationEnabled.collectAsState(initial = true)
    val autoSignature by preferencesManager.autoSignature.collectAsState(initial = false)
    val signatureText by preferencesManager.signatureText.collectAsState(initial = "")

    var name by remember { mutableStateOf(userName ?: "") }
    var isDarkThemeChecked by remember { mutableStateOf(isDarkTheme) }
    var isNotificationsEnabled by remember { mutableStateOf(notificationsEnabled) }
    var isVibrationEnabled by remember { mutableStateOf(vibrationEnabled) }
    var isAutoSignatureEnabled by remember { mutableStateOf(autoSignature) }
    var signatureTextState by remember { mutableStateOf(signatureText) }

    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Configurações de Preferências", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Nome da preferência")
        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Tema Escuro")
            Switch(
                modifier = Modifier.padding(10.dp),
                checked = isDarkThemeChecked,
                onCheckedChange = {
                    isDarkThemeChecked = it // Mantém o estado do tema localmente
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Notificações Ativadas")
            Switch(
                modifier = Modifier.padding(10.dp),
                checked = isNotificationsEnabled,
                onCheckedChange = { isNotificationsEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Vibração")
            Switch(
                modifier = Modifier.padding(10.dp),
                checked = isVibrationEnabled,
                onCheckedChange = { isVibrationEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Assinatura Automática")
            Switch(
                modifier = Modifier.padding(10.dp),
                checked = isAutoSignatureEnabled,
                onCheckedChange = { isAutoSignatureEnabled = it }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Texto da Assinatura")
        TextField(
            value = signatureTextState,
            onValueChange = { signatureTextState = it },
            modifier = Modifier.fillMaxWidth(),
            enabled = isAutoSignatureEnabled
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    preferencesManager.savePreferences(
                        isDarkThemeChecked,
                        name,
                        isNotificationsEnabled,
                        isVibrationEnabled,
                        isAutoSignatureEnabled,
                        signatureTextState
                    )

                    val userPreferences = UserPreferences(
                        theme = isDarkThemeChecked,
                        name = name,
                        notificationsEnabled = isNotificationsEnabled,
                        vibrationEnabled = isVibrationEnabled,
                        autoSignature = isAutoSignatureEnabled,
                        signatureText = signatureTextState
                    )

                    apiService.savePreferences(userPreferences).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Preferências salvas!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Erro ao salvar preferências", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        ) {
            Text("Salvar Preferências")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navegar para a tela de preferências salvas
        Button(
            onClick = {
                navController.navigate("savedPreferences")
            }
        ) {
            Text("Minhas Preferências")
        }
    }
}








