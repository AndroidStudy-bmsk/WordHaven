package org.bmsk.wordheaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.bmsk.wordheaven.adapter.WordAdapter
import org.bmsk.wordheaven.data.local.AppDatabase
import org.bmsk.wordheaven.data.model.Word
import org.bmsk.wordheaven.databinding.ActivityWordBookBinding

class WordBookActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var binding: ActivityWordBookBinding
    private lateinit var wordAdapter: WordAdapter
    private lateinit var dummyList: MutableList<Word>
    private var selectedWord: Word? = null
    private val updateAddedWordResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val isUpdated = result.data?.getBooleanExtra("isUpdated", false) ?: false

            if (result.resultCode == RESULT_OK && isUpdated) {
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
        initButtons()
    }

    private fun initButtons() {
        initAddButton()
        initDeleteButton()
    }

    private fun initAddButton() {
        binding.btnAdd.setOnClickListener {
            Intent(this, AddWordActivity::class.java).let {
                updateAddedWordResult.launch(it)
            }
        }
    }

    private fun initDeleteButton() {
        binding.ivDelete.setOnClickListener {
            delete()
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

    private fun delete() {
        if(selectedWord == null) return

        Thread {
            selectedWord?.let { word ->
                AppDatabase.getInstance(this)?.wordDao()?.delete(word)
                runOnUiThread {
                    wordAdapter.wordList.remove(word)
                    wordAdapter.notifyDataSetChanged()
                    binding.tvWord.text = ""
                    binding.tvWordMean.text = ""
                    Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    override fun onClick(word: Word) {
        selectedWord = word
        binding.tvWord.text = word.text
        binding.tvWordMean.text = word.mean
    }
}