package br.com.fiap.runmail.telas.email

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

@Composable
fun Lixeira(navController: NavController, trashEmails: List<Email>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDAE2FF))
    ) {
        Text(
            text = "Itens Excluídos",
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
                items(trashEmails) { email ->
                    EmailCard(
                        email,
                        email.isFavorite,
                        email.isImportant,
                        {},
                        {},
                        {},
                        {}
                    )
                }
            }
        }
    }
}

