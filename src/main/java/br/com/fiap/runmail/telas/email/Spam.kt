package br.com.fiap.runmail.telas.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.runmail.model.Email

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpamEmails(navController: NavController, spamEmails: List<Email>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDAE2FF))
    ) {
        TopAppBar(
            title = { Text("Spam", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
            }
        )
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            if (spamEmails.isEmpty()) {
                item {
                    Text("Nenhum e-mail de spam disponível", modifier = Modifier.fillMaxSize())
                }
            } else {
                itemsIndexed(spamEmails) { index, email ->
                    EmailCard(
                        email,
                        email.isFavorite,
                        email.isImportant,
                        onFavoriteClick = {},
                        onImportantClick = {},
                        onEmailClick = {
                            navController.navigate("emailDetailFromSpam/$index")
                        },
                        onDeleteClick = {}
                    )
                }
            }
        }
    }
}

