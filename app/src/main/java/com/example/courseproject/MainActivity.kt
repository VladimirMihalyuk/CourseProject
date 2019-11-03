package com.example.courseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.courseproject.database.AppDatabase
import com.example.courseproject.database.Costs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var database = AppDatabase.getInstance(this.application).databaseDao

        var job = GlobalScope.launch {
            database.insertAll(Costs("", "", 1F,Calendar.getInstance().getTime(), 1))
        }

        Thread.sleep(200)

    }
}
