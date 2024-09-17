package br.com.fiap.runmail.telas.preferencias

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.runmail.model.UserPreferences
import br.com.fiap.runmail.service.RunMailApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SavedPreferencesScreen(
    navController: NavController,
    apiService: RunMailApiService,
    preferencesManager: PreferencesManager
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var savedPreferences by remember { mutableStateOf<List<UserPreferences>>(emptyList()) }

    LaunchedEffect(Unit) {
        apiService.getUserPreferences().enqueue(object : Callback<List<UserPreferences>> {
            override fun onResponse(call: Call<List<UserPreferences>>, response: Response<List<UserPreferences>>) {
                if (response.isSuccessful) {
                    savedPreferences = response.body() ?: emptyList()
                } else {
                    Toast.makeText(context, "Erro ao carregar preferências", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<UserPreferences>>, t: Throwable) {
                Toast.makeText(context, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Preferências Salvas", style = MaterialTheme.typography.headlineSmall)

        if (savedPreferences.isNotEmpty()) {
            savedPreferences.forEach { prefs ->
                Spacer(modifier = Modifier.height(40.dp))

                Text(text = "Nome: ${prefs.name ?: "Sem nome"}")
                Text(text = "Tema: ${if (prefs.theme) "Escuro" else "Claro"}")
                Text(text = "Notificações: ${if (prefs.notificationsEnabled) "Ativadas" else "Desativadas"}")
                Text(text = "Vibração: ${if (prefs.vibrationEnabled) "Ativada" else "Desativada"}")
                Text(text = "Assinatura Automática: ${if (prefs.autoSignature) "Ativada" else "Desativada"}")
                Text(text = "Texto da Assinatura: ${prefs.signatureText ?: "Sem assinatura"}")

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {
                        scope.launch {
                            preferencesManager.savePreferences(
                                prefs.theme,
                                prefs.name ?: "",
                                prefs.notificationsEnabled,
                                prefs.vibrationEnabled,
                                prefs.autoSignature,
                                prefs.signatureText
                            )

                            // Aplica o tema imediatamente
                            if (prefs.theme) {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            } else {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            }

                            (context as Activity).recreate() // Recriar a Activity para aplicar o tema

                            Toast.makeText(context, "Preferências aplicadas!", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Aplicar esta Preferência")
                }
            }
        } else {
            Text(text = "Carregando preferências...")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Voltar")
        }
    }
}

