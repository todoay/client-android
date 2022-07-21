package com.todoay.api.domain.auth.email

import com.todoay.api.domain.auth.email.dto.EmailRequest
import com.todoay.api.domain.auth.email.dto.EmailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 이메일 관련 API 호출 인터페이스
 */
interface EmailService {

    @GET("/auth/email_duplicate_check/{email}")
    fun getCheckEmailDuplicate(@Path("email") email: EmailRequest) : Call<EmailResponse>

    @GET("/auth/email/{email}")
    fun getSendCertMail(@Path("email") email: EmailRequest) : Call<EmailResponse>

}