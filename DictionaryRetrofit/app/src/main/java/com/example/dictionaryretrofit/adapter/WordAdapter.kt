package com.example.dictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryretrofit.model.DictionaryWord
import com.example.dictionaryretrofit.MainActivity
import com.example.dictionaryretrofit.R
import kotlinx.android.synthetic.main.item_row.view.*

class WordAdapter (private val activity: MainActivity, private var list: ArrayList<DictionaryWord>):  RecyclerView.Adapter<WordAdapter.ItemViewHolder>(){


    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var lesson = list[position]

        holder.itemView.apply {
            tvTitle.text = lesson.title
            tvDefinition.text = lesson.definition

            }
    }


    override fun getItemCount() = list.size


    fun update(list: ArrayList<DictionaryWord>){
        this.list = list
        notifyDataSetChanged()
    }
}