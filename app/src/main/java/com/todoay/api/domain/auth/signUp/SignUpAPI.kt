package com.todoay.api.domain.auth.signUp

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.todoay.api.domain.auth.signUp.dto.SignUpRequest
import com.todoay.api.domain.auth.signUp.dto.SignUpResponse
import com.todoay.api.util.ErrorResponse
import com.todoay.api.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 유저 회원가입 API 호출 및 응답을 처리하는 클래스.
 * API Interface: SignUpService.kt
 */
class SignUpAPI {

    private val service = RetrofitService.getService().create(SignUpService::class.java)

    /**
     * 유저 회원가입 수행
     */
    fun signUp(_email: String, _password: String, _nickName: String, onResponse: (SignUpResponse) -> Unit, onFailure: (ErrorResponse) -> Unit) {
        val request = SignUpRequest(
            email = _email,
            password = _password,
            nickName = _nickName
        )
        service.postSignUp(request)
            .enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    if(response.isSuccessful) {
                        val signUpResponse : SignUpResponse = response.body()!!
                        onResponse(signUpResponse)
                        Log.d("sign-up", "success {$signUpResponse}")
                    }
                    else {
                        val errorResponse = RetrofitService.getErrorResponse(response)
                        onFailure(errorResponse)
                        Log.d("sign-up", "failed {$errorResponse}")
                    }
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    val errorFailure = RetrofitService.getErrorFailure(
                        t, "회원가입"
                    )
                    onFailure(errorFailure)
                    Log.d("sign-up", "system - failed {${errorFailure}}")
                }

            })
    }

}