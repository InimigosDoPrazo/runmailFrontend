package br.com.fiap.runmail.telas.email

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.runmail.R

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    showFavorites: Boolean,
    onFavoritesClick: () -> Unit,
    showImportant: Boolean,
    onImportantClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                fontSize = 16.sp
            ),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = "Pesquisar e-mails...",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                    )
                }
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = onFavoritesClick) {
            Icon(
                painter = painterResource(id = if (showFavorites) R.drawable.ic_favorite_border else R.drawable.ic_favorite),
                contentDescription = "Filter Favorites",
                tint = if (showFavorites) Color(0xFFFF9800) else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = onImportantClick) {
            Icon(
                painter = painterResource(id = if (showImportant) R.drawable.ic_importante else R.drawable.ic_importante_outline),
                contentDescription = "Filter Important",
                tint = if (showImportant) Color(0xFFFF9800) else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun DrawerMenu(navController: NavController, onCloseDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        IconButton(onClick = onCloseDrawer) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Drawer",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        val menuItems = listOf(
            "Escrever e-mail" to Icons.Default.Edit,
            "Calendario" to Icons.Default.DateRange,
            "Importantes" to Icons.Default.Notifications,
            "Favoritos" to Icons.Default.Star,
            "Enviados" to Icons.Default.Email,
            "Lixeira" to Icons.Default.Delete,
            "Spam" to Icons.Default.Warning,
            "Preferências" to Icons.Default.Settings
        )

        menuItems.forEach { item ->
            MenuItem(item.first, item.second) {
                val route = when (item.first) {
                    "Escrever e-mail" -> "escreveremail"
                    "Calendario" -> "calendario"
                    "Importantes" -> "importantes"
                    "Favoritos" -> "favoritos"
                    "Lixeira" -> "lixeira"
                    "Spam" -> "spamEmails"
                    "Enviados" -> "enviados"
                    "Preferências" -> "preferences"
                    else -> "escreveremail"
                }
                navController.navigate(route)
                onCloseDrawer()
            }
        }
    }
}


@Composable
fun MenuItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}