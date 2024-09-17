package br.com.fiap.runmail.telas.email


import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.runmail.model.EmailDto
import br.com.fiap.runmail.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EscreverEmail(navController: NavController) {
    val recipient = remember { mutableStateOf("") }
    val subject = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDAE2FF))
            .padding(16.dp)
    ) {
        Text(
            fontSize = 24.sp,
            text = "Enviando e-mail",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = recipient.value,
            onValueChange = { recipient.value = it },
            label = { Text("E-mail do Destinatário") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = subject.value,
            onValueChange = { subject.value = it },
            label = { Text("Assunto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = message.value,
            onValueChange = { message.value = it },
            label = { Text("Mensagem") },
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val emailDto = EmailDto(
                    subject = subject.value,
                    sender = recipient.value,
                    body = message.value,
                    date = LocalDate.now().toString(),
                    isFavorite = false,
                    isImportant = false
                )
                sendEmailToBackend(emailDto) { success, errorMessage ->
                    if (success) {
                        Toast.makeText(context, "Email enviado com sucesso!", Toast.LENGTH_SHORT)
                            .show()
                        navController.navigate("enviados")
                    } else {
                        Toast.makeText(
                            context,
                            errorMessage ?: "Falha ao enviar email",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}

fun sendEmailToBackend(emailDto: EmailDto, onResult: (Boolean, String?) -> Unit) {
    try {
        RetrofitClient.apiService.sendEmail(emailDto).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
                    Log.e("API_ERROR", errorMessage)
                    onResult(false, errorMessage)
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API_FAILURE", "Erro de rede: ${t.message}")
                onResult(false, t.message ?: "Erro de rede")
            }
        })
    } catch (e: Exception) {
        Log.e("EXCEPTION", "Erro inesperado: ${e.message}")
        onResult(false, e.message ?: "Erro inesperado")
    }
}



