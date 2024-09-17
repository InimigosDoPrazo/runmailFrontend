package br.com.fiap.runmail.telas.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalDrawer
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.runmail.model.Email
import kotlinx.coroutines.launch

@Composable
fun ListaDeEmails(
    navController: NavController,
    emails: List<Email>,
    onDeleteEmail: (Email) -> Unit,
    onMarkImportant: (Email) -> Unit,
    onMarkFavorite: (Email) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val searchQuery = remember { mutableStateOf("") }
    val showFavorites = remember { mutableStateOf(false) }
    val showImportant = remember { mutableStateOf(false) }

    ModalDrawer(
        drawerContent = {
            DrawerMenu(navController) {
                scope.launch { drawerState.close() }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFDAE2FF))
        ) {
            TopAppBar(
                title = { Text("RunMail", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
            SearchBar(
                query = searchQuery.value,
                onQueryChange = { searchQuery.value = it },
                showFavorites = showFavorites.value,
                onFavoritesClick = { showFavorites.value = !showFavorites.value },
                showImportant = showImportant.value,
                onImportantClick = { showImportant.value = !showImportant.value }
            )
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                if (emails.isEmpty()) {
                    item {
                        Text("Nenhum e-mail disponível", modifier = Modifier.fillMaxSize())
                    }
                } else {
                    val filteredEmails = emails.filter { email ->
                        val matchesQuery =
                            email.subject.contains(searchQuery.value, ignoreCase = true) ||
                                    email.sender.contains(searchQuery.value, ignoreCase = true) ||
                                    email.body.contains(searchQuery.value, ignoreCase = true)
                        val matchesFavorites = !showFavorites.value || email.isFavorite
                        val matchesImportant = !showImportant.value || email.isImportant
                        matchesQuery && matchesFavorites && matchesImportant
                    }
                    itemsIndexed(filteredEmails) { index, email ->
                        val isFavorite = remember { mutableStateOf(email.isFavorite) }
                        val isImportant = remember { mutableStateOf(email.isImportant) }
                        EmailCard(
                            email,
                            isFavorite.value,
                            isImportant.value,
                            {
                                isFavorite.value = !isFavorite.value
                                email.isFavorite = isFavorite.value
                                onMarkFavorite(email)
                            },
                            {
                                isImportant.value = !isImportant.value
                                email.isImportant = isImportant.value
                                onMarkImportant(email)
                            },
                            {
                                navController.navigate("emailDetail/$index")
                            },
                            {
                                onDeleteEmail(email)
                            }
                        )
                    }
                }
            }
        }
    }
}



