package org.bmsk.wordhaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.bmsk.wordhaven.adapter.WordAdapter
import org.bmsk.wordhaven.data.model.Word
import org.bmsk.wordhaven.databinding.ActivityWordBookBinding

class WordBookActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var binding: ActivityWordBookBinding
    private lateinit var wordAdapter: WordAdapter
    private lateinit var dummyList: MutableList<Word>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initDummyData() // For Test: View를 구성하기 전 Data를 먼저 초기화합니다.
        initView()
    }

    private fun initDummyData() {
        dummyList = mutableListOf<Word>(
            Word("weather", "날씨", "명사"),
            Word("honey", "꿀", "명사"),
            Word("run", "실행하다", "동사"),
        )
    }

    private fun initView() {
        initRecyclerView()
        initButton()
    }

    private fun initButton() {
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddWordActivity::class.java))
        }
    }

    private fun initRecyclerView() {
        wordAdapter = WordAdapter(mutableListOf(), this)
        binding.rvWordList.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onClick(word: Word) {
        Toast.makeText(this, "${word.text} 가 클릭 됐습니다", Toast.LENGTH_SHORT).show()
    }
}