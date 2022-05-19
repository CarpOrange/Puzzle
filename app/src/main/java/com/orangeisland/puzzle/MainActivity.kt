package com.orangeisland.puzzle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orangeisland.puzzle.ui.adapter.ImageRvAdapter
import com.orangeisland.puzzle.ui.bean.ImageData
import com.orangeisland.puzzle.util.ImageSplitter

class MainActivity : AppCompatActivity() {

    private val rv: RecyclerView by lazy { findViewById<RecyclerView>(R.id.rv) }
    val adapter = ImageRvAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        rv.layoutManager = GridLayoutManager(this, 3)
        rv.adapter = adapter
        val bitmap = resources.getDrawable(R.drawable.gintama).toBitmap()
        val imageDatas = ImageSplitter.split(bitmap, 3, 3).map{}.shuffled()
        adapter.setNewInstance(imageDatas)


    }

    fun checkSort() {
        val datas = arrayListOf<ImageData>()

        var isRightSort = true
        datas.forEachIndexed { index, imageData ->
            if(index != imageData.index) {
                isRightSort = false
            }
        }

    }
}