package data.remote.client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseClient {

    abstract val BASE_URL: String

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}

object OrdinaryClient: BaseClient() {
//    override val BASE_URL = "https://projfair.istu.edu/api/"
    override val BASE_URL = "http://62.109.5.123/api/"
}

object AdminClient: BaseClient() {
    //override val BASE_URL = "https://projfair.istu.edu/api/admin/"
    override val BASE_URL = "http://62.109.5.123/api/admin/"
}