package com.kroraina.easyreader.modules.sheetlist.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kroraina.easyreader.R
import com.kroraina.easyreader.base.activity.BaseMVPActivity
import com.kroraina.easyreader.base.annotations.ActivityUI
import com.kroraina.easyreader.ui.widget.itemdecoration.DividerItemDecoration
import com.kroraina.easyreader.ui.widget.transform.CircleTransform
import com.kroraina.easyreader.utils.Constant
import com.xincubate.lego.adapter.core.BaseItem
import com.xincubate.lego.adapter.core.BaseViewHolder
import com.xincubate.lego.adapter.load.LoadMoreAdapter
import com.xincubate.lego.annotation.LegoItem
import kotlinx.android.synthetic.main.activity_refresh_list.*
import java.util.*

@ActivityUI(layoutId = R.layout.activity_refresh_list)
class BookListDetailActivity : BaseMVPActivity<BookListDetailContract.Presenter>(), BookListDetailContract.View {

    private lateinit var mDetailAdapter: LoadMoreAdapter
    private lateinit var mDetailHeader: DetailHeaderItem
    private var mBooksList: List<BookListDetailBean.BooksBean>? = null

    private var mDetailId: String? = null
    private var start = 0

    private val bookList: List<BookListDetailBean.BooksBean.BookBean>
        get() {
            var end = start + LIMIT
            if (end > mBooksList!!.size) {
                end = mBooksList!!.size
            }
            val books = ArrayList<BookListDetailBean.BooksBean.BookBean>(LIMIT)
            for (i in start until end) {
                books.add(mBooksList!![i].book!!)
            }
            return books
        }

    override fun bindPresenter(): BookListDetailContract.Presenter {
        return BookListDetailPresenter()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mDetailId = if (savedInstanceState != null) {
            savedInstanceState.getString(EXTRA_DETAIL_ID)
        } else {
            intent.getStringExtra(EXTRA_DETAIL_ID)
        }
    }

    override fun setUpToolbar(toolbar: Toolbar) {
        super.setUpToolbar(toolbar)
        supportActionBar!!.title = "书单详情"
    }

    override fun initWidget() {
        super.initWidget()
        setUpAdapter()
    }

    private fun setUpAdapter() {
        mDetailAdapter = LoadMoreAdapter(this)
        mDetailHeader = DetailHeaderItem(this)
        mDetailAdapter.addHeaderItem(mDetailHeader)

        refresh_rv_content!!.layoutManager = LinearLayoutManager(this)
        refresh_rv_content!!.addItemDecoration(DividerItemDecoration(this))
        refresh_rv_content!!.adapter = mDetailAdapter
    }

    override fun initClick() {
        super.initClick()
        mDetailAdapter.setOnLoadMoreListener { Handler().postDelayed({ loadBook() }, 500) }
    }

    override fun processLogic() {
        super.processLogic()
        refresh_layout.showLoading()
        mPresenter.refreshBookListDetail(mDetailId!!)
    }

    override fun finishRefresh(bean: BookListDetailBean) {
        mDetailHeader.setBookListDetail(bean)
        mBooksList = bean.books
        refreshBook()
    }

    private fun refreshBook() {
        start = 0
        val books = bookList
        mDetailAdapter.refreshItems(BookListInfoItem.initFrom(this, books))
        start = books.size
    }

    private fun loadBook() {
        val books = bookList
        mDetailAdapter.addItems(BookListInfoItem.initFrom(this, books))
        start += books.size
        mDetailAdapter.notifyDataSetChanged()
    }

    override fun showError() {
        refresh_layout.showError()
    }

    override fun complete() {
        refresh_layout.showFinish()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_DETAIL_ID, mDetailId)
    }


    @LegoItem
    class DetailHeaderItem(context: Context) : BaseItem(context) {

        private var detailBean: BookListDetailBean? = null

        override fun getLayoutId(): Int {
            return R.layout.header_book_list_detail
        }

        override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
            val tvTitle = viewHolder.findViewById<TextView>(R.id.book_list_info_tv_title)
            val tvDesc = viewHolder.findViewById<TextView>(R.id.book_list_detail_tv_desc)
            val ivPortrait = viewHolder.findViewById<ImageView>(R.id.book_list_info_iv_cover)
            //val tvCreate = viewHolder.findViewById<TextView>(R.id.book_list_detail_tv_create)
            val tvAuthor = viewHolder.findViewById<TextView>(R.id.book_list_info_tv_author)
            //val tvShare = viewHolder.findViewById<TextView>(R.id.book_list_detail_tv_share)

            if (detailBean == null) {
                return
            }
            //标题
            tvTitle!!.text = detailBean!!.title
            //描述
            tvDesc!!.text = detailBean!!.desc
            //头像
            Glide.with(context)
                    .load(Constant.IMG_BASE_URL + detailBean!!.author!!.avatar!!)
                    .placeholder(R.drawable.ic_loadding)
                    .error(R.drawable.ic_load_error)
                    .transform(CircleTransform(context))
                    .into(ivPortrait!!)
            //作者
            tvAuthor!!.text = detailBean!!.author!!.nickname

        }

        fun setBookListDetail(bean: BookListDetailBean) {
            detailBean = bean
        }
    }

    companion object {

        private val EXTRA_DETAIL_ID = "extra_detail_id"
        val LIMIT = 20

        fun startActivity(context: Context, detailId: String) {
            val intent = Intent(context, BookListDetailActivity::class.java)
            intent.putExtra(EXTRA_DETAIL_ID, detailId)
            context.startActivity(intent)
        }
    }
}
