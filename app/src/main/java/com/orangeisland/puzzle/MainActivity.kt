package com.orangeisland.puzzle

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orangeisland.puzzle.ui.adapter.ImageRvAdapter
import com.orangeisland.puzzle.ui.bean.ImageData
import com.orangeisland.puzzle.util.ImageSplitter
import com.orangeisland.puzzle.util.Preference
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        const val NOT_INIT = -1
    }

    private val btnRefreshPicture: AppCompatButton by lazy { findViewById<AppCompatButton>(R.id.btnRefreshPicture) }
    private val btnSelectPictureFromPic: AppCompatButton by lazy { findViewById<AppCompatButton>(R.id.btnSelectPictureFromPic) }
    private val btnUpdateSet: AppCompatButton by lazy { findViewById<AppCompatButton>(R.id.btnUpdateSet) }
    private val rv: RecyclerView by lazy { findViewById<RecyclerView>(R.id.rv) }
    val adapter = ImageRvAdapter()
    var firstClickPos = NOT_INIT
    val layoutManager = GridLayoutManager(this, 3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setupData()
    }

    private fun initView() {
        rv.layoutManager = layoutManager
        rv.adapter = adapter
//        rv.isLongPressDragEnabled = true
        adapter.setOnItemClickListener { adapter1, view, position ->
            if(firstClickPos == NOT_INIT) {
                firstClickPos = position
            } else {
                Collections.swap(adapter.data, firstClickPos, position)
                adapter.notifyDataSetChanged()
//                adapter.notifyItemMoved(firstClickPos, position)
//                adapter.notifyItemRangeChanged(Math.min(firstClickPos, position), Math.abs(firstClickPos - position) +1);//受影响的item都刷新position
                firstClickPos = NOT_INIT
                checkSort()
            }
        }
//        rv.setOnItemMoveListener(object : OnItemMoveListener {
//            override fun onItemMove(
//                srcHolder: RecyclerView.ViewHolder,
//                targetHolder: RecyclerView.ViewHolder
//            ): Boolean {
//                val fromPosition = srcHolder.adapterPosition
//                val toPosition = targetHolder.adapterPosition
//                Collections.swap(adapter.data, fromPosition, toPosition)
//                adapter.notifyDataSetChanged()
//                checkSort()
//                return true
//            }
//
//            override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder) {
//            }
//
//        })

        btnRefreshPicture.setOnClickListener {
            setupData()
        }

        btnSelectPictureFromPic.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivity.launch(intent)

        }

        btnUpdateSet.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

    }

    val startActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        //此处是跳转的result回调方法
        if (it.data != null && it.resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri = it.data!!.data!!
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            //查询我们需要的数据
            val cursor: Cursor = contentResolver.query(
                selectedImage,
                filePathColumn, null, null, null
            )!!
            cursor.moveToFirst()

            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
            val picturePath: String = cursor.getString(columnIndex)
            cursor.close()
            setupBitmap(BitmapFactory.decodeFile(picturePath))
        } else {
            Toast.makeText(
                applicationContext,
                "未选择图片",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    var currentDrawableId = -1

    private fun setupData() {
        var drawableId = DataConstant.DRAWABLE_IDS.filter {
            it != currentDrawableId
        }.random()
        currentDrawableId = drawableId
        val bitmap = resources.getDrawable(drawableId).toBitmap()
        setupBitmap(bitmap)
    }

    private var customSpanCount by Preference("customSpanCount",3)

    private fun setupBitmap(bitmap: Bitmap) {
        layoutManager.spanCount = customSpanCount
        val imageDatas = ImageSplitter.split(bitmap, customSpanCount, customSpanCount).mapIndexed { index, bitmap ->
            ImageData(bitmap, index)
        }.shuffled()
        adapter.setNewInstance(ArrayList<ImageData>().also {
            it.addAll(imageDatas)
        })
    }

    fun checkSort() {
        val datas = adapter.data
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