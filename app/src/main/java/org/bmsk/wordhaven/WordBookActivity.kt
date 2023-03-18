package org.bmsk.wordhaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import org.bmsk.wordhaven.adapter.WordAdapter
import org.bmsk.wordhaven.data.model.Word
import org.bmsk.wordhaven.databinding.ActivityWordBookBinding

class WordBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWordBookBinding
    private lateinit var wordAdapter: WordAdapter
    private lateinit var dummyList: MutableList<Word>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWordBookBinding.inflate(layoutInflater)

        initDummyData()
        initView()

        setContentView(binding.root)
    }

    private fun initDummyData() {
        dummyList = mutableListOf<Word>(
            Word("weather", "날씨", "명사"),
            Word("honey", "꿀", "명사"),
            Word("run", "실행하다", "동사"),
        )
    }

    private fun initView() {
        wordAdapter = WordAdapter(dummyList)
        binding.rvWordList.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }
}