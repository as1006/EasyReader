package com.kroraina.easyreader.modules.rank.detail

import android.content.Context
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.kroraina.easyreader.R
import com.kroraina.easyreader.modules.book.detail.BookDetailActivity
import com.kroraina.easyreader.utils.Constant
import com.xincubate.lego.adapter.bean.BaseBeanItem
import com.xincubate.lego.adapter.core.BaseViewHolder
import com.xincubate.lego.annotation.LegoItem

@LegoItem
class RankDetailItem(context: Context, bean: RankDetailBean) : BaseBeanItem<RankDetailBean>(context, bean) {

    override fun getLayoutId(): Int {
        return R.layout.item_book_brief
    }

    override fun onClick() {
        BookDetailActivity.startActivity(context, bean._id)
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val mIvPortrait = viewHolder.findViewById<ImageView>(R.id.book_brief_iv_portrait)
        val mTvTitle = viewHolder.findViewById<TextView>(R.id.book_brief_tv_title)
        val mTvAuthor = viewHolder.findViewById<TextView>(R.id.book_brief_tv_author)
        val mTvBrief = viewHolder.findViewById<TextView>(R.id.book_brief_tv_brief)
        val mTvMsg = viewHolder.findViewById<TextView>(R.id.book_brief_tv_msg)

        //头像
        Glide.with(context)
                .load(Constant.IMG_BASE_URL + bean.cover)
                .placeholder(R.drawable.ic_default_portrait)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(mIvPortrait!!)
        //书单名
        mTvTitle!!.text = bean.title
        //作者
        mTvAuthor!!.text = bean.author
        //简介
        mTvBrief!!.text = bean.shortIntro
        //信息
        mTvMsg!!.text = context.getString(R.string.nb_book_message,
                bean.latelyFollower, bean.retentionRatio)
    }
}
