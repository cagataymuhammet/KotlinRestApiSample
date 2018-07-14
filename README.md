# KotlinRestApiSample

Retrofit2, RxJava2 ve Observer kullanılarak geliştirilmiş bir Kotlin örneğidir.

Android Stuido 3.1.3 ve Google Play Services 28.0.0 kullanılmıştır.

Api olarak  [JSONPlaceholder](https://jsonplaceholder.typicode.com/) kullanılmıştır.


<img src="https://github.com/cagataymuhammet/KotlinRestApiSample/blob/master/screenshot.png"/>


# dependencies

```gradle

dependencies {

    implementation fileTree(dir: 'libs', include: ['*.jar'])

    def supportLibraryVersion="28+"
    def retrofitLibraryVersion = "2.3.0"

    //SUPPORT LİBRARY
    implementation "com.android.support:appcompat-v7:$supportLibraryVersion"
    implementation "com.android.support:design:$supportLibraryVersion"
    implementation "com.android.support:recyclerview-v7:$supportLibraryVersion"
    implementation "com.android.support:cardview-v7:$supportLibraryVersion"
    implementation 'com.android.support.constraint:constraint-layout:1.1.2'

    //Retrofit Library
    implementation "com.squareup.retrofit2:retrofit:$retrofitLibraryVersion"
    implementation "com.squareup.retrofit2:converter-gson:$retrofitLibraryVersion"
    implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofitLibraryVersion"
    implementation 'com.squareup.okhttp:okhttp:2.7.2'

    //RxJava Library
    implementation 'io.reactivex.rxjava2:rxjava:2.1.7'
    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'

}

```

# ServiceApiClient.kt
```kotlin

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

```


# UserModel.kt

```kotlin
data class UserModel(

        val id: Int,
        val title: String,
        val body: String
)
```


# Servise bağlanıp veri çekmek
```kotlin
   val serviceClient by lazy {
        ServiceApiClient.create()
    }

    var disposable: Disposable? = null


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

```




# UserAdapter.kt
```kotlin


class UserAdapter(val userList: List<UserModel>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.txtID?.text = userList[position].id.toString()
        holder?.txtTitle?.text = userList[position].title
        holder?.txtBody?.text = userList[position].body
    }

    override fun onCreateViewHolder(parentView: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parentView?.context).inflate(R.layout.carview_user, parentView, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtTitle = itemView.findViewById<TextView>(R.id.txt_title)
        val txtBody = itemView.findViewById<TextView>(R.id.txt_body)
        val txtID = itemView.findViewById<TextView>(R.id.txt_user_id)
    }
}

```


License
--------


    Copyright 2018 Muhammet ÇAĞATAY.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
