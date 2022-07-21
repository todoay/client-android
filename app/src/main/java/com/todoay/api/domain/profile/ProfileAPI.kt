package com.todoay.api.domain.profile

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.todoay.api.domain.profile.dto.ModifyProfileRequest
import com.todoay.api.domain.profile.dto.ModifyProfileResponse
import com.todoay.api.domain.profile.dto.ProfileResponse
import com.todoay.api.util.ErrorResponse
import com.todoay.api.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 유저 정보(Profile) 관련 API 호출 및 응답을 처리하는 클래스.
 * API Interface: ProfileService.kt
 */
class ProfileAPI {

    private val service = RetrofitService.getService().create(ProfileService::class.java)

    /**
     * 유저 정보(Profile) 조회 수행
     */
    fun getMyProfile(onResponse: (ProfileResponse) -> Unit, onFailure: (ErrorResponse) -> Unit) {
        service.getProfile()
            .enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if(response.isSuccessful) {
                        val profileResponse : ProfileResponse = response.body()!!
                        onResponse(profileResponse)
                        Log.d("profile", "get profile - success {$profileResponse}")
                    }
                    else {
                        val errorResponse = RetrofitService.getErrorResponse(response)
                        onFailure(errorResponse)
                        Log.d("profile", "get profile - failed {$errorResponse}")
                    }
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    val errorFailure = RetrofitService.getErrorFailure(
                        t, "내정보 조회"
                    )
                    onFailure(errorFailure)
                    Log.d("profile", "system - failed {${errorFailure}}")
                }

            })
    }

    /**
     * 유저 정보(Profile) 변경 수행
     */
    fun modifyMyProfile(_nickName: String, _message: String, _imageUrl: String, onResponse: (ModifyProfileResponse) -> Unit, onFailure: (ErrorResponse) -> Unit) {
        val request = ModifyProfileRequest(
            nickName = _nickName,
            message = _message,
            imageUrl = _imageUrl
        )

        service.putModifyProfile(request)
            .enqueue(object : Callback<ModifyProfileResponse> {
                override fun onResponse(
                    call: Call<ModifyProfileResponse>,
                    response: Response<ModifyProfileResponse>
                ) {
                    if(response.isSuccessful) {
                        val modifyProfileResponse: ModifyProfileResponse = response.body()!!
                        onResponse(modifyProfileResponse)
                        Log.d("profile", "modify profile - success {$modifyProfileResponse}")
                    }
                    else {
                        val errorResponse = RetrofitService.getErrorResponse(response)
                        onFailure(errorResponse)
                        Log.d("profile", "modify profile - failed {$errorResponse}")
                    }
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onFailure(call: Call<ModifyProfileResponse>, t: Throwable) {
                    val errorFailure = RetrofitService.getErrorFailure(
                        t, "내정보 변경"
                    )
                    onFailure(errorFailure)
                    Log.d("profile", "system - failed {${errorFailure}}")
                }

            })
    }
}