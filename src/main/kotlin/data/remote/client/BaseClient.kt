package data.remote.client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

abstract class BaseClient {
    abstract fun getBaseUrl(): String

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}

object OrdinaryClient : BaseClient() {
    override fun getBaseUrl(): String {
        return File("server_url.txt").bufferedReader().use {
            it.readText()
        }
    }
}

object AdminClient : BaseClient() {
    override fun getBaseUrl(): String {
        return File("server_url.txt").bufferedReader().use {
            it.readText() + "admin/"
        }
    }
}
