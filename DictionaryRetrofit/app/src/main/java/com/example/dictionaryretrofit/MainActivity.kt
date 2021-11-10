package com.example.dictionaryretrofit

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.adapter.WordAdapter
import com.example.dictionaryretrofit.json.MyDictionary
import com.example.dictionaryretrofit.json.MyDictionaryItem
import com.example.dictionaryretrofit.model.DictionaryWord
import com.example.dictionaryretrofit.retrofit.APIClient
import com.example.dictionaryretrofit.retrofit.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var etSearch : EditText
    lateinit var btSearch : Button
    lateinit var rvMain : RecyclerView
    lateinit var wordAdapter: WordAdapter
    var list = ArrayList<DictionaryWord>()
    var search =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkInternet()

        etSearch = findViewById(R.id.etSearch)
        btSearch = findViewById(R.id.btSearch)
        rvMain = findViewById(R.id.rvMain)

        wordAdapter = WordAdapter(this , list)
        rvMain.adapter = wordAdapter
        rvMain.layoutManager = LinearLayoutManager(this)

        btSearch.setOnClickListener {
            search = etSearch.text.toString()

            if (search.isNotEmpty()){
               getWordData()
            }else {
                Toast.makeText(this , "Enter a Search name" , Toast.LENGTH_SHORT).show()
            }
            etSearch.text.clear()
        }
    }

    private fun getWordData(){
        val apiInterface = APIClient.getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        apiInterface?.getData("$search")?.enqueue(object : Callback<List<MyDictionaryItem>> {
            override fun onResponse(
                call: Call<List<MyDictionaryItem>>,
                response: Response<List<MyDictionaryItem>>
            ) {
              try {
                  val resource = response.body()!!

                progressDialog.dismiss()
                  for ( i in resource){
                      var word = i.word
                      var definition = i.meanings[0].definitions[0].definition
                      list.add(DictionaryWord( word,definition))


                  }
                  // rvMain.adapter?.notifyDataSetChanged()
                  wordAdapter.update(list)
                  rvMain.scrollToPosition(list.size - 1)
              }catch (e: Exception){

              Toast.makeText(applicationContext , "word is invalid" , Toast.LENGTH_SHORT).show()
              }

            }

            override fun onFailure(call: Call<List<MyDictionaryItem>>, t: Throwable) {
                progressDialog.dismiss()
                call.cancel()
            }
        })

    }

    private fun checkInternet(){
        if(!connectedToInternet()){
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Internet Connection Not Found")
                .setPositiveButton("RETRY"){_, _ -> checkInternet()}
                .show()
        }
    }

    private fun connectedToInternet(): Boolean{
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}