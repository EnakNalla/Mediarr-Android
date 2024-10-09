package com.github.enaknalla.mediarrtv.di

import android.content.Context
import android.provider.Settings
import com.github.enaknalla.mediarrtv.data.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

const val NETWORK_REQUEST_TIMEOUT = 60_000L

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesPreferences(@ApplicationContext context: Context) = Preferences(context)

    @Provides
    fun providesHttpClient(@ApplicationContext context: Context) = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    useAlternativeNames = true
                    encodeDefaults = false
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = NETWORK_REQUEST_TIMEOUT
            connectTimeoutMillis = NETWORK_REQUEST_TIMEOUT
            socketTimeoutMillis = NETWORK_REQUEST_TIMEOUT
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d(message)
                }
            }
            level = LogLevel.INFO
        }

        install(ResponseObserver) {
            onResponse { response ->
                Timber.d("HTTP status: ${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(
                HttpHeaders.UserAgent,
                Settings.Global.getString(context.contentResolver, "device_name")
            )
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}