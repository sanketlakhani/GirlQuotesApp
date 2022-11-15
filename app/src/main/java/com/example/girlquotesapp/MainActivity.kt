package com.example.girlquotesapp

import DBHelper
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.girlquotesapp.Adapters.CategoryNameAdapter
import com.example.girlquotesapp.Models.CatagoryNameModel
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    var list = ArrayList<CatagoryNameModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        internetCheck()
    }

    private fun internetCheck() {

        var connected = false

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            )?.state == NetworkInfo.State.CONNECTED) {
            true
        }else{
            val dialog=Dialog(this)
            dialog.setContentView(R.layout.interneet_check)
            dialog.setCancelable(false)
            val dialogButton=dialog.findViewById<Button>(R.id.btnOk)
            dialogButton.setOnClickListener {
                dialog.dismiss()
                finish()
            }
            dialog.show()
            false
        }

    }

    private fun initView() {

        var dbHelper = DBHelper(this)

        recyclerView = findViewById(R.id.recyclerView)

        list = dbHelper.display()

        var adapter = CategoryNameAdapter(list, onItemClick = { id, name ->

            var cName = name
            var intent = Intent(this, QuotesActivity::class.java)
            intent.putExtra("cNameList", cName)
            intent.putExtra("catagoryId", id)
            startActivity(intent)
        })

        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager

    }
}