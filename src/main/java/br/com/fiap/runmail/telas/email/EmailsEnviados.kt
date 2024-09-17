package br.com.fiap.runmail.telas.email

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.runmail.model.Email
import br.com.fiap.runmail.model.EmailDto
import br.com.fiap.runmail.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Enviados(navController: NavController, enviadosEmails: List<Email>) {
    var sentEmails by remember { mutableStateOf<List<Email>>(emptyList()) }

    // Carrega os emails enviados assim que a tela é acessada
    LaunchedEffect(Unit) {
        fetchSentEmails { emails ->
            sentEmails = emails
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDAE2FF))
            .padding(16.dp)
    ) {
        Text(
            fontSize = 24.sp,
            text = "Emails Enviados",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (sentEmails.isEmpty()) {
                item {
                    Text("Nenhum e-mail enviado disponível", modifier = Modifier.fillMaxSize())
                }
            } else {
                itemsIndexed(sentEmails) { index, email ->
                    EmailCard(
                        email,
                        email.isFavorite,
                        email.isImportant,
                        onFavoriteClick = {},
                        onImportantClick = {},
                        onEmailClick = { navController.navigate("emailDetailFromEnviados/$index") },
                        onDeleteClick = {}
                    )
                }
            }
        }
    }
}

fun fetchSentEmails(onResult: (List<Email>) -> Unit) {
    RetrofitClient.apiService.getSentEmails().enqueue(object : Callback<List<EmailDto>> {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onResponse(call: Call<List<EmailDto>>, response: Response<List<EmailDto>>) {
            if (response.isSuccessful) {
                val emails = response.body()?.map { it.toEmail() } ?: emptyList()
                onResult(emails)
            } else {
                onResult(emptyList())
            }
        }

        override fun onFailure(call: Call<List<EmailDto>>, t: Throwable) {
            onResult(emptyList())
        }
    })
}

