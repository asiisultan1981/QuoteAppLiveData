package com.sultan.quoteappdatabinding.veiwmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.sultan.quoteappdatabinding.data.Quote

class MainViewModel(val context: Context) : ViewModel() {
    private val _quotes = MutableLiveData<Array<Quote>>()
    val quotes : LiveData<Array<Quote>>
    get() = _quotes

    private var index = 0

    init {
        _quotes.postValue(loadJsonFromAssets())
        Log.d("postedValue", "${_quotes.postValue(loadJsonFromAssets())}: ")
    }

    private fun loadJsonFromAssets(): Array<Quote> {
        val inputStream = context.assets.open("quotes.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<Quote>::class.java)
    }

    fun getQuote() = _quotes.value!!.get(index)


    fun nextQuote() =  _quotes.value!!.get(++index % _quotes.value!!.size)

    fun previousQuote() = _quotes.value!!.get((--index + _quotes.value!!.size) % _quotes.value!!.size)












}







