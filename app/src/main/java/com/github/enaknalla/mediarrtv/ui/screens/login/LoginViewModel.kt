package com.github.enaknalla.mediarrtv.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.enaknalla.mediarrtv.data.Preferences
import com.github.enaknalla.mediarrtv.domain.AuthFlow
import com.github.enaknalla.mediarrtv.utils.Constants.BASE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val client: HttpClient, private val preferences: Preferences) : ViewModel() {
    val _code: MutableStateFlow<String> = MutableStateFlow("")
    val code = _code.asStateFlow()

    fun start() = viewModelScope.launch(Dispatchers.IO) {
       val response = client.get("$BASE_URL/auth")
        if (response.status != HttpStatusCode.OK) {
            return@launch
        }

        val body = response.body<AuthFlow>()
        _code.value = body.code

        val now = System.currentTimeMillis()

        while (System.currentTimeMillis() - now < body.expiresIn) {
            delay(body.interval)

            val check = client.get("$BASE_URL/auth/${_code.value}")

            if (check.status == HttpStatusCode.OK) {
                preferences.saveToken(check.body())
                break
            }
        }
    }
}