package com.example.smartshop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivityPatternLockBinding
import com.itsxtt.patternlock.PatternLockView
import java.util.ArrayList

class PatternLockActivity : AppCompatActivity() {
    lateinit var binding: ActivityPatternLockBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatternLockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.patternLockView.setOnPatternListener(object : PatternLockView.OnPatternListener{
            override fun onComplete(ids: ArrayList<Int>): Boolean {
                val password = ids.joinToString(separator = "") { it.toString() }
                Toast.makeText(this@PatternLockActivity, "$password", Toast.LENGTH_SHORT).show()
                return true
            }
        })
    }
}