package com.cagataymuhammet.kotlinapisample.networking

import com.cagataymuhammet.kotlinapisample.di.Constants
import com.cagataymuhammet.kotlinapisample.model.UserModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ServiceApiClient {


    @GET("posts")
    fun getUsers(): Observable<List<UserModel>>


    @GET("posts/{id}")
    fun getUser(@Path("id") id: Int): Observable<UserModel>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("posts")
    fun addUser(@Body article: UserModel): Observable<UserModel>


    companion object {

        fun create(): ServiceApiClient {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build()

            return retrofit.create(ServiceApiClient::class.java)

        }
    }
}