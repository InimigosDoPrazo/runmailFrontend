package br.com.fiap.runmail.telas.email

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.runmail.model.Email


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmailDetail(navController: NavController, emailId: Int, email: Email) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(text = "Para: ${email.sender}", fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "Assunto: ${email.subject}", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))

        Card(modifier = Modifier.padding(vertical = 8.dp).fillMaxSize()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = email.body, fontSize = 14.sp)
            }
        }
    }
}

