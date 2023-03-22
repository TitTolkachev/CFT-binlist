package com.example.cft.ui.screen.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cft.databinding.ItemBinBinding

class BinHistoryAdapter(private val binItemActionListener: BinItemActionListener) : RecyclerView.Adapter<BinHistoryAdapter.BinHistoryViewHolder>(), View.OnClickListener {

    var data: List<String> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class BinHistoryViewHolder(val binding: ItemBinBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinBinding.inflate(inflater, parent, false)

        binding.textView.setOnClickListener(this)

        return BinHistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BinHistoryViewHolder, position: Int) {

        val bin = data[position]

        holder.binding.textView.tag = bin

        with(holder.binding) {
            textView.text = bin
        }
    }

    override fun onClick(view: View) {
        val bin: String = view.tag as String
        binItemActionListener.onItemClicked(bin)
    }
}

interface BinItemActionListener {
    fun onItemClicked(bin: String)
}