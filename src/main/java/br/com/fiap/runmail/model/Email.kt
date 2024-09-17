package br.com.fiap.runmail.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class Email(
    val subject: String,
    val sender: String,
    val body: String,
    var date: LocalDate,
    var isFavorite: Boolean = false,
    var isImportant: Boolean = false
)

data class EmailDto(
    @SerializedName("subject") val subject: String,
    @SerializedName("sender") val sender: String,
    @SerializedName("body") val body: String,
    @SerializedName("date") val date: String,
    @SerializedName("favorite") val isFavorite: Boolean,
    @SerializedName("important") val isImportant: Boolean
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun toEmail(): Email {
        val parsedDate = LocalDate.parse(date.substring(0, 10))
        return Email(subject, sender, body, parsedDate, isFavorite, isImportant)
    }
}