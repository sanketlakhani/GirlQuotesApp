package com.example.girlquotesapp

import DBHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.girlquotesapp.Adapters.QuotesAdapter
import java.util.*

class QuotesActivity : AppCompatActivity() {
    var list = ArrayList<QuotesModelClass>()
    lateinit var txtCatagoryQuotes: TextView
    lateinit var imgBack: ImageView
    lateinit var rcvQuotes: RecyclerView
    lateinit var adapter: QuotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)

        initView()
    }

    private fun initView() {
        var dbHelper = DBHelper(this)
        txtCatagoryQuotes = findViewById(R.id.txtcatagoryQuotes)
        rcvQuotes = findViewById(R.id.rcvQuotes)
        imgBack = findViewById(R.id.imgBack)

        val categoryName: String? = intent.getStringExtra("cNameList")
        val idCatagory: Int? = intent.getIntExtra("catagoryId", 0)

        txtCatagoryQuotes.text = categoryName.toString()

        list = dbHelper.displayQuotes(idCatagory)

        adapter = QuotesAdapter(list, this, onEditClick = { quotes ->

            var quotesE = quotes
            var intent = Intent(this, EditActivity::class.java)
            intent.putExtra("quotes", quotesE)
            intent.putExtra("catagoryE", categoryName)
            startActivity(intent)

        }, onLike = { id, like ->
            dbHelper.likeUpdate(like, id)
            list = dbHelper.displayQuotes(idCatagory)
            adapter.update(list)
        })

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcvQuotes.layoutManager = manager
        rcvQuotes.adapter = adapter

        imgBack.setOnClickListener {
            finish()
        }
    }
}