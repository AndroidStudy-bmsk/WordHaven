package org.bmsk.wordhaven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.chip.Chip
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
}