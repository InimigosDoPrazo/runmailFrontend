package br.com.fiap.runmail.telas.email

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.runmail.model.Email
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun showCalendario(navController: NavController, emails: List<Email>) {
    val context = LocalContext.current
    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()


    val date = remember {
        mutableStateOf("")
    }


    val selectedDate = remember {
        mutableStateOf<LocalDate?>(null)
    }


    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/${month + 1}/$year"
            selectedDate.value = LocalDate.of(year, month + 1, dayOfMonth)
        }, year, month, day
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Selecione a data do e-mail: ${date.value}")
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = "Abrir o calendário")
        }

        selectedDate.value?.let { selectedDate ->
            Spacer(modifier = Modifier.size(16.dp))
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {

                val filteredEmails = emails.filter { email ->
                    email.date == selectedDate
                }
                if (filteredEmails.isEmpty()) {
                    item {
                        Text(
                            "Nenhum e-mail encontrado para a data selecionada.",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    itemsIndexed(filteredEmails) { index, email ->
                        EmailCard(
                            email,
                            email.isFavorite,
                            email.isImportant,
                            onFavoriteClick = {},
                            onImportantClick = {},
                            onEmailClick = { navController.navigate("emailDetail/$index") },
                            onDeleteClick = {}
                        )
                    }
                }
            }
        }
    }
}
