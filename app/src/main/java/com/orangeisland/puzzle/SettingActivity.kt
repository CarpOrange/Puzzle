package com.orangeisland.puzzle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SwitchCompat
import com.orangeisland.puzzle.util.Preference

class SettingActivity : AppCompatActivity() {

    private val btnSave: AppCompatButton by lazy { findViewById<AppCompatButton>(R.id.btnSave) }
    private val switchSpanCount: SwitchCompat by lazy { findViewById<SwitchCompat>(R.id.switchSpanCount) }
    private val layoutSpanCount: LinearLayoutCompat by lazy { findViewById<LinearLayoutCompat>(R.id.layoutSpanCount) }
    private val etSpanCount: EditText by lazy { findViewById<EditText>(R.id.etSpanCount) }

    private var customSpanCountEnable by Preference("customSpanCountEnable",false)
    private var customSpanCount by Preference("customSpanCount",3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initView()
    }

    private fun initView() {
        switchSpanCount.isChecked = customSpanCountEnable
        switchSpanCount.setOnCheckedChangeListener { compoundButton, b ->
            if(switchSpanCount.isChecked) {
                layoutSpanCount.visibility = View.VISIBLE
            } else {
                layoutSpanCount.visibility = View.GONE
            }
        }

        btnSave.setOnClickListener {
            save()
        }
    }

    private fun save() {
        customSpanCountEnable = switchSpanCount.isChecked
        customSpanCount = etSpanCount.text.toString().toIntOrNull() ?: 3
        finish()
    }
}