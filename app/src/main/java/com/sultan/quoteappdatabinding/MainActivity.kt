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
    private  val TAG = "MainActivityTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]

        binding.viewModel = mainViewModel
//        binding.lifecycleOwner = this

        mainViewModel.quotes.observe(this, Observer {
           setQuote( mainViewModel.getQuote())
            Log.e(TAG, "onCreate: ${mainViewModel.getQuote().text}" )
        })
    }

    private fun setQuote(quote: Quote) {
        binding.text.text = quote.text
        binding.author.text = quote.author
        Log.e(TAG, "setQuote: ${quote.text}")
    }

    fun onShare(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.getQuote().text)
        startActivity(intent)
        Log.d(TAG, "onShare: called")
    }

    fun OnPrvious(view: View) {
        setQuote(mainViewModel.previousQuote())
    }

    fun OnNext(view: View) {
        setQuote(mainViewModel.nextQuote())
    }


}