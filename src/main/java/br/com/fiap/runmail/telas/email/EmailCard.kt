package br.com.fiap.runmail.telas.email

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.runmail.R
import br.com.fiap.runmail.model.Email

@Composable
fun EmailCard(
    email: Email,
    isFavorite: Boolean,
    isImportant: Boolean,
    onFavoriteClick: () -> Unit,
    onImportantClick: () -> Unit,
    onEmailClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = onEmailClick)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = email.subject,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = email.sender,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = email.body,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = email.date.toString(), fontSize = 10.sp, color = Color.Gray)
            }
            Row {
                IconButton(onClick = onFavoriteClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        painter = painterResource(id = if (isFavorite) R.drawable.ic_favorite_border else R.drawable.ic_favorite),
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color(0xFFFF9800) else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onImportantClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        painter = painterResource(id = if (isImportant) R.drawable.ic_importante else R.drawable.ic_importante_outline),
                        contentDescription = "Important",
                        tint = if (isImportant) Color(0xFFFF9800) else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onDeleteClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}