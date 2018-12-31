package com.kroraina.easyreader.modules.rank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.kroraina.easyreader.R
import com.kroraina.easyreader.utils.Constant

import java.util.ArrayList

import butterknife.ButterKnife


class RankListAdapter : BaseExpandableListAdapter() {
    private val mGroups = ArrayList<RankListBean>()
    private val mChildren = ArrayList<RankListBean>()

    override fun getGroupCount(): Int {
        return mGroups.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return if (groupPosition == mGroups.size - 1) {
            mChildren.size
        } else 0
    }

    override fun getGroup(groupPosition: Int): RankListBean {
        return mGroups[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): RankListBean? {
        //只有最后一个groups才有child
        return if (groupPosition == mGroups.size - 1) {
            mChildren[childPosition]
        } else null
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: GroupViewHolder?

        if (convertView == null) {
            holder = GroupViewHolder()
            convertView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_billboard_group, parent, false)
            holder.ivSymbol = ButterKnife.findById(convertView!!, R.id.billboard_group_iv_symbol)
            holder.tvName = ButterKnife.findById(convertView, R.id.billboard_group_tv_name)
            holder.ivArrow = ButterKnife.findById(convertView, R.id.billboard_group_iv_arrow)
            convertView.tag = holder
        } else {
            holder = convertView.tag as GroupViewHolder
        }

        val bean = getGroup(groupPosition)

        if (bean.cover != null) {
            Glide.with(parent.context)
                    .load(Constant.IMG_BASE_URL + bean.cover)
                    .placeholder(R.drawable.ic_loadding)
                    .error(R.drawable.ic_load_error)
                    .fitCenter()
                    .into(holder.ivSymbol!!)
        } else {
            holder.ivSymbol!!.setImageResource(R.drawable.ic_billboard_collapse)
        }

        holder.tvName!!.text = bean.title

        if (groupPosition == mGroups.size - 1) {
            holder.ivArrow!!.visibility = View.VISIBLE
            if (isExpanded) {
                holder.ivArrow!!.setImageResource(R.drawable.ic_billboard_arrow_up)
            } else {
                holder.ivArrow!!.setImageResource(R.drawable.ic_billboard_arrow_down)
            }
        } else {
            holder.ivArrow!!.visibility = View.GONE
        }
        return convertView
    }


    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ChildViewHolder
        if (convertView == null) {
            holder = ChildViewHolder()
            convertView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_billborad_child, parent, false)
            holder.tvName = ButterKnife.findById(convertView!!, R.id.billboard_child_tv_name)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ChildViewHolder
        }
        holder.tvName!!.text = mChildren[childPosition].title
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    fun addGroups(beans: List<RankListBean>) {
        mGroups.addAll(beans)
        notifyDataSetChanged()
    }

    fun addChildren(beans: List<RankListBean>) {
        mChildren.addAll(beans)
        notifyDataSetChanged()
    }


    private inner class GroupViewHolder {
        internal var ivSymbol: ImageView? = null
        internal var tvName: TextView? = null
        internal var ivArrow: ImageView? = null
    }

    private inner class ChildViewHolder {
        internal var tvName: TextView? = null
    }
}
