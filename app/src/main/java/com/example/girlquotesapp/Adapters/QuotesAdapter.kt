package com.example.girlquotesapp.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.girlquotesapp.QuotesModelClass
import com.example.girlquotesapp.R
import java.util.*
import kotlin.collections.ArrayList

class QuotesAdapter(
    var quotesList: ArrayList<QuotesModelClass>,
    var context: Context,
    var onEditClick: ((String)) -> Unit,
    var onLike: (Int, Int) -> Unit
) :
    RecyclerView.Adapter<QuotesAdapter.MyViewholder>() {
    class MyViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtQuotes = itemView.findViewById<TextView>(R.id.txtQuotes)
        var imgLike = itemView.findViewById<ImageView>(R.id.imgLike)
        var relativeLayout = itemView.findViewById<RelativeLayout>(R.id.relativeLayout)
      //  var imgLikeEmpty = itemView.findViewById<ImageView>(R.id.imgLikeEmpty)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.quotes_item, parent, false)
        return MyViewholder(v)

    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.txtQuotes.text = quotesList[position].quotes

        if (quotesList[position].status == 1) {

            holder.imgLike.setImageResource(R.drawable.heartfill)
        } else {

            holder.imgLike.setImageResource(R.drawable.heart)

        }
        holder.imgLike.setOnClickListener {


            if (quotesList[position].status == 1) {

                onLike.invoke(quotesList[position].id, 0)

            } else {
                onLike.invoke(quotesList[position].id, 1)
            }
        }


        holder.relativeLayout.setOnClickListener {

            onEditClick.invoke(quotesList[position].quotes)
        }
    }

    override fun getItemCount(): Int {
        return quotesList.size
    }

    fun update(list: ArrayList<QuotesModelClass>) {

        this.quotesList = ArrayList()
        this.quotesList.addAll(list)
        notifyDataSetChanged()

    }
}