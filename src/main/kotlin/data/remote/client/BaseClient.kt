package data.remote.client

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseClient {

    abstract val BASE_URL: String

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            //.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object OrdinaryClient: BaseClient() {
    override val BASE_URL = "https://projfair.istu.edu/api/"
}

object AdminClient: BaseClient() {
    override val BASE_URL = "https://projfair.istu.edu/api/admin/"
}