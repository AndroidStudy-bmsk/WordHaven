package org.bmsk.wordhaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.chip.Chip
import org.bmsk.wordhaven.data.local.AppDatabase
import org.bmsk.wordhaven.data.model.Word
import org.bmsk.wordhaven.databinding.ActivityAddWordBinding

class AddWordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        initChipGroup()
        initAddButton()
    }

    private fun initChipGroup() {
        val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")
        binding.cgTypes.apply {
            types.forEach { type ->
                addView(createChip(type))
            }
        }
    }

    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }

    private fun initAddButton() {
        binding.btnAdd.setOnClickListener {
            add()
        }
    }

    private fun add() {
        val text = binding.tietTextInputWord.text.toString()
        val mean = binding.tietTextInputMean.text.toString()
        val type = findViewById<Chip>(binding.cgTypes.checkedChipId)?.text?.toString() ?: "no type"
        val word = Word(text, mean, type)

        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.insert(word)
            runOnUiThread {
                Toast.makeText(this, "저장을 완료했습니다", Toast.LENGTH_SHORT).show()
            }
            finish()
        }.start()
    }
}