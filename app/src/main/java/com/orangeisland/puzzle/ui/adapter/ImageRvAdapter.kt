package com.orangeisland.puzzle.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.orangeisland.puzzle.R
import com.orangeisland.puzzle.ui.bean.ImageData

/**
 *
 *
 * @author 李昱辰 987424501@qq.com
 * @date 2022/5/19
 */
class ImageRvAdapter: BaseQuickAdapter<ImageData, BaseViewHolder>(R.layout.item_rv_bitmap) {
    override fun convert(holder: BaseViewHolder, item: ImageData) {
        holder.setImageBitmap(R.id.imgView, item.bitmap)
    }

}