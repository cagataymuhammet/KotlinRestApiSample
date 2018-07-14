package com.cagataymuhammet.kotlinapisample.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.cagataymuhammet.kotlinapisample.R
import com.cagataymuhammet.kotlinapisample.adapter.UserAdapter
import com.cagataymuhammet.kotlinapisample.model.UserModel
import com.cagataymuhammet.kotlinapisample.networking.ServiceApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {


    val serviceClient by lazy {
        ServiceApiClient.create()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllUsers();
    }


    fun getAllUsers() {

        disposable = serviceClient.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            result -> Log.v("USERS", "" + result)
                            bindToRecycleview(result)

                        },
                        { error -> Log.e("ERROR", error.message) }
                )
    }


    fun bindToRecycleview(userList:List<UserModel>)
    {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_users_list)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        var recyclerViewAdapter = UserAdapter(userList)
        recyclerView.adapter = recyclerViewAdapter
    }



    fun getOneUser(id: Int) {

        disposable = serviceClient.getUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.v("USER ID ${id}: ", "" + result) },
                        { error -> Log.e("ERROR", error.message) }
                )

    }


    fun addUser(user: UserModel) {

        disposable = serviceClient.addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.v("POSTED USER", "" + user) },
                        { error -> Log.e("ERROR", error.message) }
                )
    }



    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }





}