package br.com.fiap.runmail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.runmail.model.Email
import br.com.fiap.runmail.model.EmailDto
import br.com.fiap.runmail.service.RetrofitClient
import br.com.fiap.runmail.service.RunMailApiService
import br.com.fiap.runmail.telas.email.EmailDetail
import br.com.fiap.runmail.telas.email.Enviados
import br.com.fiap.runmail.telas.email.EscreverEmail
import br.com.fiap.runmail.telas.email.Favoritos
import br.com.fiap.runmail.telas.email.Importantes
import br.com.fiap.runmail.telas.email.ListaDeEmails
import br.com.fiap.runmail.telas.email.Lixeira
import br.com.fiap.runmail.telas.preferencias.PreferencesScreen
import br.com.fiap.runmail.telas.email.RunmailTheme
import br.com.fiap.runmail.telas.preferencias.SavedPreferencesScreen
import br.com.fiap.runmail.telas.email.SpamEmails
import br.com.fiap.runmail.telas.preferencias.PreferencesManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var apiService: RunMailApiService

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesManager = PreferencesManager(applicationContext)
        apiService = RetrofitClient.apiService
        setContent {
            RunmailTheme(preferencesManager = preferencesManager) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    var emailList by remember { mutableStateOf<List<Email>>(emptyList()) }
                    var trashEmails by remember { mutableStateOf<List<Email>>(emptyList()) }
                    var importantEmails by remember { mutableStateOf<List<Email>>(emptyList()) }
                    var favoriteEmails by remember { mutableStateOf<List<Email>>(emptyList()) }
                    var spamEmails by remember { mutableStateOf<List<Email>>(emptyList()) }
                    var enviadosEmails by remember { mutableStateOf<List<Email>>(emptyList()) }

                    lifecycleScope.launch {
                        fetchEmails { emails ->
                            emailList = emails
                        }
                        fetchSpamEmails { emails -> spamEmails = emails }
                        fetchEnviadosEmails { emails -> enviadosEmails = emails }
                    }

                    NavHost(navController = navController, startDestination = "emailList") {
                        composable("emailList") {
                            ListaDeEmails(navController, emailList, { email ->
                                trashEmails = trashEmails + email
                                emailList = emailList - email
                            }, { email ->
                                if (email.isImportant) {
                                    importantEmails = importantEmails + email
                                } else {
                                    importantEmails = importantEmails - email
                                }
                            }, { email ->
                                if (email.isFavorite) {
                                    favoriteEmails = favoriteEmails + email
                                } else {
                                    favoriteEmails = favoriteEmails - email
                                }
                            })
                        }
                        composable("emailDetail/{emailId}") { backStackEntry ->
                            val emailId =
                                backStackEntry.arguments?.getString("emailId")?.toIntOrNull()
                            if (emailId != null && emailId < emailList.size) {
                                EmailDetail(navController, emailId, emailList[emailId])
                            }
                        }
                        composable("emailDetailFromSpam/{emailId}") { backStackEntry ->
                            val emailId =
                                backStackEntry.arguments?.getString("emailId")?.toIntOrNull()
                            if (emailId != null && emailId < spamEmails.size) {
                                EmailDetail(navController, emailId, spamEmails[emailId])
                            }
                        }
                        composable("emailDetailFromEnviados/{emailId}") { backStackEntry ->
                            val emailId =
                                backStackEntry.arguments?.getString("emailId")?.toIntOrNull()
                            if (emailId != null && emailId < enviadosEmails.size) {
                                EmailDetail(navController, emailId, enviadosEmails[emailId])
                            }
                        }
                        composable("lixeira") {
                            Lixeira(navController, trashEmails)
                        }
                        composable("importantes") {
                            Importantes(navController, importantEmails)
                        }
                        composable("favoritos") {
                            Favoritos(navController, favoriteEmails)
                        }
                        composable("spamEmails") {
                            SpamEmails(navController, spamEmails)
                        }
                        composable("escreveremail") {
                            EscreverEmail(navController)
                        }
                        composable("enviados") {
                            Enviados(navController, enviadosEmails)
                        }
                        composable("preferences") {
                            PreferencesScreen(
                                preferencesManager = preferencesManager,
                                navController = navController,
                                apiService = apiService
                            )
                        }
                        composable("savedPreferences") {
                            SavedPreferencesScreen(
                                preferencesManager = preferencesManager,
                                navController = navController,
                                apiService = apiService
                            )
                        }
                    }

                }
            }
        }
    }

    private fun fetchEmails(onResult: (List<Email>) -> Unit) {
        RetrofitClient.apiService.getEmails().enqueue(object : Callback<List<EmailDto>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<List<EmailDto>>,
                response: Response<List<EmailDto>>
            ) {
                if (response.isSuccessful) {
                    val emails = response.body()?.map { it.toEmail() } ?: emptyList()
                    Log.d("API_SUCCESS", "Received ${emails.size} emails")
                    onResult(emails)
                } else {
                    Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
                    onResult(emptyList())
                }
            }

            override fun onFailure(call: Call<List<EmailDto>>, t: Throwable) {
                Log.e("API_FAILURE", "Network Error: ${t.message}")
                onResult(emptyList())
            }
        })
    }

    private fun fetchSpamEmails(onResult: (List<Email>) -> Unit) {
        RetrofitClient.apiService.getSpamEmails().enqueue(object : Callback<List<EmailDto>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<List<EmailDto>>,
                response: Response<List<EmailDto>>
            ) {
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

    private fun fetchEnviadosEmails(onResult: (List<Email>) -> Unit) {
        RetrofitClient.apiService.getSentEmails().enqueue(object : Callback<List<EmailDto>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<List<EmailDto>>,
                response: Response<List<EmailDto>>
            ) {
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
}
