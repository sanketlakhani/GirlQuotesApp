package com.example.girlquotesapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.girlquotesapp.Models.CatagoryNameModel
import com.example.girlquotesapp.R
import java.util.*

class CategoryNameAdapter(var list: ArrayList<CatagoryNameModel>,var onItemClick: (Int, String) -> Unit) :
    RecyclerView.Adapter<CategoryNameAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtCatagoryName = view.findViewById<TextView>(R.id.txtCatagoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.catagory_item_file, parent, false)
        return MyViewHolder(v)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtCatagoryName.text = list[position].catagoryName

        holder.txtCatagoryName.setOnClickListener {

            onItemClick.invoke(list[position].id, list[position].catagoryName)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}