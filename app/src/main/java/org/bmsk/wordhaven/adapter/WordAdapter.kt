package org.bmsk.wordhaven.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.bmsk.wordhaven.data.model.Word
import org.bmsk.wordhaven.databinding.ItemWordBinding

class WordAdapter(
    val wordList: MutableList<Word>,
    private val itemClickListener: ItemClickListener? = null,
) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = wordList[position]
        holder.bind(word)
        holder.itemView.setOnClickListener { itemClickListener?.onClick(word) }
    }

    class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.apply {
                tvWord.text = word.text
                tvMean.text = word.mean
                chipWordType.text = word.type
            }
        }
    }

    interface ItemClickListener {
        fun onClick(word: Word) {}
    }
}