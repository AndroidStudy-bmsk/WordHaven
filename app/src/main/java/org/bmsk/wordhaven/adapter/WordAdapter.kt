package org.bmsk.wordhaven.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bmsk.wordhaven.data.model.Word
import org.bmsk.wordhaven.databinding.ItemWordBinding

class WordAdapter(private val list: MutableList<Word>) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.binding.apply {
            val word = list[position]
            tvWord.text = word.text
            tvMean.text = word.mean
            chipWordType.text = word.type
        }
    }

    class WordViewHolder(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}