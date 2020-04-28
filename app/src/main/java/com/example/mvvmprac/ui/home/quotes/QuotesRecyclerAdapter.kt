package com.example.mvvmprac.ui.home.quotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmprac.data.db.entities.Quote
import com.example.mvvmprac.databinding.ItemListQuotesBinding

class QuotesRecyclerAdapter : RecyclerView.Adapter<QuotesRecyclerAdapter.QuotesItemViewHolder>(){
    private var itemList = ArrayList<Quote>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesItemViewHolder {

        return QuotesItemViewHolder(
            ItemListQuotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount()=itemList.size

    override fun onBindViewHolder(holder: QuotesItemViewHolder, position: Int) {
       holder.bind(itemList[position])
    }

    fun submitData(list: ArrayList<Quote>) {
        itemList.clear()
        itemList = list
        notifyDataSetChanged()
    }


    inner class QuotesItemViewHolder(private val binding: ItemListQuotesBinding) : RecyclerView.ViewHolder
        (binding.root) {

        fun bind(item: Quote) {
            binding.quote.text = item.quote
            binding.author.text = item.author
        }
    }


}
