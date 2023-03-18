package org.bmsk.wordhaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.bmsk.wordhaven.adapter.WordAdapter
import org.bmsk.wordhaven.data.local.AppDatabase
import org.bmsk.wordhaven.data.model.Word
import org.bmsk.wordhaven.databinding.ActivityWordBookBinding

class WordBookActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var binding: ActivityWordBookBinding
    private lateinit var wordAdapter: WordAdapter
    private lateinit var dummyList: MutableList<Word>
    private val updateAddedWordResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val isUpdated = result.data?.getBooleanExtra("isUpdated", false) ?: false

            if(result.resultCode == RESULT_OK && isUpdated) {
                updateAddWord()
            }
        }

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
            Intent(this, AddWordActivity::class.java).let {
                updateAddedWordResult.launch(it)
            }
        }
    }

    private fun initRecyclerView() {
        wordAdapter = WordAdapter(mutableListOf(), this)
        binding.rvWordList.apply {
            adapter = wordAdapter
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration =
                DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        Thread {
            val wordList = AppDatabase.getInstance(this)?.wordDao()?.getAll() ?: emptyList()
            wordAdapter.wordList.addAll(wordList)
            runOnUiThread {
                wordAdapter.notifyDataSetChanged()  // 이 부분을 좀 더 정교하게 다듬게 된다면...
            }
        }.start()
    }

    private fun updateAddWord() {
        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.getLatestWord()?.let {
                wordAdapter.wordList.add(0, it)
                runOnUiThread { wordAdapter.notifyDataSetChanged() }
            }
        }.start()
    }

    override fun onClick(word: Word) {
        Toast.makeText(this, "${word.text} 가 클릭 됐습니다", Toast.LENGTH_SHORT).show()
    }
}