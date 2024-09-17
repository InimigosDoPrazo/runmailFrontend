package br.com.fiap.runmail.service

import br.com.fiap.runmail.model.EmailDto
import br.com.fiap.runmail.model.UserPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RunMailApiService {
    @GET("api/runmail/mock")
    fun getEmails(): Call<List<EmailDto>>

    @GET("api/runmail/spam")
    fun getSpamEmails(): Call<List<EmailDto>>

    @GET("api/runmail/sent")
    fun getSentEmails(): Call<List<EmailDto>>

    @POST("api/runmail/send")
    fun sendEmail(@Body email: EmailDto): Call<Void>

    @POST("/user/preferences")
    fun savePreferences(@Body preferences: UserPreferences): Call<Void>

    @GET("/user/preferences")
    fun getUserPreferences(): Call<List<UserPreferences>>
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.231:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: RunMailApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RunMailApiService::class.java)
    }
}

