package com.sultan.quoteappdatabinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sultan.quoteappdatabinding.data.Quote
import com.sultan.quoteappdatabinding.databinding.ActivityMainBinding

import com.sultan.quoteappdatabinding.veiwmodel.MainViewModel
import com.sultan.quoteappdatabinding.veiwmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]

        binding.vm = mainViewModel

        mainViewModel.quotes.observe(this, Observer {
            mainViewModel.getQuote()
            Log.d("myview", "onCreate: ${mainViewModel.getQuote()}")
        })

    }

//    private fun setQuote(quote: Quote){
//        binding.tvText.text = quote.quoteText
//        binding.tvAuthor.text = quote.quoteAuthor
//        Log.e("myview", "setQuote: ${quote.quoteText}")
//    }


    fun onShare(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.getQuote()!!.quoteText)
    }


}