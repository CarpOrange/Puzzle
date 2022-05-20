package com.orangeisland.puzzle

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orangeisland.puzzle.ui.adapter.ImageRvAdapter
import com.orangeisland.puzzle.ui.bean.ImageData
import com.orangeisland.puzzle.util.ImageSplitter
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener
import java.util.*

class MainActivity : AppCompatActivity() {

    private val rv: SwipeRecyclerView by lazy { findViewById<SwipeRecyclerView>(R.id.rv) }
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
        rv.isLongPressDragEnabled = true
        rv.setOnItemMoveListener(object : OnItemMoveListener {
            override fun onItemMove(
                srcHolder: RecyclerView.ViewHolder,
                targetHolder: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = srcHolder.adapterPosition
                val toPosition = targetHolder.adapterPosition
                Collections.swap(adapter.data, fromPosition, toPosition)
                adapter.notifyItemMoved(fromPosition, toPosition)
                checkSort()
                return true
            }

            override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder) {
            }

        })
    }

    fun checkSort() {
        val datas = arrayListOf<ImageData>()

        var isRightSort = true
        datas.forEachIndexed { index, imageData ->
            if(index != imageData.index) {
                isRightSort = false
            }
        }
        if(isRightSort) {
            Toast.makeText(this, "bingo", Toast.LENGTH_LONG).show()
        }

    }
}