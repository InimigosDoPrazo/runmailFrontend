package br.com.fiap.runmail.telas.email

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.runmail.model.Email
import br.com.fiap.runmail.telas.email.EmailCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Importantes(navController: NavController, importantEmails: List<Email>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDAE2FF))
    ) {

        Text(
            text = "Importantes",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 25.dp, horizontal = 25.dp),
            style = MaterialTheme.typography.h6,
            color = Color.Black
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            LazyColumn {
                items(importantEmails) { email ->
                    EmailCard(
                        email,
                        email.isFavorite,
                        email.isImportant,
                        onFavoriteClick = {},
                        onImportantClick = {},
                        onEmailClick = {
                            navController.navigate("emailDetail/${importantEmails.indexOf(email)}")
                        },
                        onDeleteClick = {}
                    )
                }
            }
        }
    }
}
