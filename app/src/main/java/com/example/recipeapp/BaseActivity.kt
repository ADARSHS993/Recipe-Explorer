package com.example.recipeapp


import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.Dispatcher
import kotlin.coroutines.CoroutineContext


open class BaseActivity : AppCompatActivity(), CoroutineScope{

    private lateinit var job : Job
    override val coroutineContext : CoroutineContext
        get() = job + Dispatchers.Main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}