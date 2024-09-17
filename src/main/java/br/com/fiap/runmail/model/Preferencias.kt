package br.com.fiap.runmail.model

data class UserPreferences(
    val theme: Boolean,
    val name: String,
    val notificationsEnabled: Boolean,
    val vibrationEnabled: Boolean,
    val autoSignature: Boolean,
    val signatureText: String
)


