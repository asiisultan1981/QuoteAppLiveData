package com.sultan.quoteappdatabinding.veiwmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.sultan.quoteappdatabinding.data.Quote
import org.json.JSONArray
import kotlin.properties.Delegates

class MainViewModel(val context: Context) : ViewModel() {
    val TAG = "MainViewTag"
    private val _quotes = MutableLiveData<Array<Quote>>()

    val quotes: LiveData<Array<Quote>>
        get() = _quotes

    private var index = 0

    init {

        _quotes.value = loadJsonFromAssets()

    }

    private fun loadJsonFromAssets(): Array<Quote>? {
        val inputStream = context.assets.open("quotes.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
//        val TAG = "JsonTAG"
        val json = String(buffer, Charsets.UTF_8)
//        val jsonArray = JSONArray(json)
        val gson = Gson()

//        for (i in 0 until jsonArray.length()){
//            val jsonObject = jsonArray.getJSONObject(i)
//            Log.d(TAG, "loadJsonFromAssets: json object = $jsonObject")
//            val quote: Quote = gson.fromJson(jsonObject.toString(), Quote::class.java)
//            Log.d(TAG, "loadJsonFromAssets: quote object quote = ${quote.text}")
//        }

        return gson.fromJson(json, Array<Quote>::class.java)
    }

    fun getQuote() = _quotes.value!![index]

    fun nextQuote() = _quotes.value!![++index % _quotes.value!!.size]

    fun previousQuote() = _quotes.value!![(--index + _quotes.value!!.size) % _quotes.value!!.size]




}







