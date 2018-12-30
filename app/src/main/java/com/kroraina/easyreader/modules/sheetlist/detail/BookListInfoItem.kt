package com.kroraina.easyreader.modules.sheetlist.detail

import android.content.Context
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.kroraina.easyreader.R
import com.kroraina.easyreader.modules.book.detail.BookDetailActivity
import com.kroraina.easyreader.utils.Constant
import com.xincubate.lego.adapter.core.BaseItem
import com.xincubate.lego.adapter.core.BaseViewHolder
import com.xincubate.lego.annotation.LegoItem

import java.util.ArrayList

@LegoItem
class BookListInfoItem(context: Context, val bean: BookListDetailBean.BooksBean.BookBean) : BaseItem(context) {

    override fun getLayoutId(): Int {
        return R.layout.item_book_list_info
    }

    override fun onClick() {
        BookDetailActivity.startActivity(context, bean._id)
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        val mIvPortrait = viewHolder.findViewById<ImageView>(R.id.book_list_info_iv_cover)
        val mTvTitle = viewHolder.findViewById<TextView>(R.id.book_list_info_tv_title)
        val mTvAuthor = viewHolder.findViewById<TextView>(R.id.book_list_info_tv_author)
        val mTvContent = viewHolder.findViewById<TextView>(R.id.book_list_info_tv_content)
        val mTvMsg = viewHolder.findViewById<TextView>(R.id.book_list_info_tv_msg)
        val mTvWord = viewHolder.findViewById<TextView>(R.id.book_list_info_tv_word)

        Glide.with(context)
                .load(Constant.IMG_BASE_URL + bean.cover)
                .placeholder(R.drawable.ic_book_loading)
                .error(R.drawable.ic_load_error)
                .fitCenter()
                .into(mIvPortrait!!)
        //书单名
        mTvTitle!!.text = bean.title
        //作者
        mTvAuthor!!.text = bean.author
        //简介
        mTvContent!!.text = bean.longIntro
        //信息
        mTvMsg!!.text = context.resources.getString(R.string.nb_book_message,
                bean.latelyFollower, bean.retentionRatio)
        //书籍字数
        mTvWord!!.text = context.resources.getString(R.string.nb_book_word, bean.wordCount / 10000)
    }

    companion object {

        fun initFrom(context: Context, beans: List<BookListDetailBean.BooksBean.BookBean>): List<BookListInfoItem> {
            val results = ArrayList<BookListInfoItem>()
            for (bean in beans) {
                results.add(BookListInfoItem(context, bean))
            }
            return results
        }
    }
}
