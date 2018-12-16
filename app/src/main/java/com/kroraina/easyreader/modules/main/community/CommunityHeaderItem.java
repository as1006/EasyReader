package com.kroraina.easyreader.modules.main.community;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kroraina.easyreader.R;
import com.xincubate.lego.adapter.core.BaseAdapter;
import com.xincubate.lego.adapter.core.BaseItem;
import com.xincubate.lego.adapter.core.BaseViewHolder;
import com.xincubate.lego.annotation.LegoItem;

@LegoItem
public class CommunityHeaderItem extends BaseItem {
    public CommunityHeaderItem(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_community_head;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder , int position) {
        RecyclerView recyclerView = viewHolder.findViewById(R.id.rv_content);
        GridLayoutManager manager = new GridLayoutManager(recyclerView.getContext(),4);
        recyclerView.setLayoutManager(manager);

        BaseAdapter baseAdapter = new BaseAdapter(context);

        baseAdapter.addItem(new CommunityHeaderEntryItem(context,R.drawable.community_head_history,"大话历史",CommunityType.HISTORY));
        baseAdapter.addItem(new CommunityHeaderEntryItem(context,R.drawable.community_head_girl,"女生密语",CommunityType.GIRL));
        baseAdapter.addItem(new CommunityHeaderEntryItem(context,R.drawable.community_head_erciyuan,"二次元",CommunityType.MANHUA));
        baseAdapter.addItem(new CommunityHeaderEntryItem(context,R.drawable.community_head_jinji,"电子竞技",CommunityType.ESPORT));
        baseAdapter.addItem(new CommunityHeaderEntryItem(context,R.drawable.community_head_wangwen,"网文江湖",CommunityType.JIANGHU));
        baseAdapter.addItem(new CommunityHeaderEntryItem(context,R.drawable.community_head_yuanchuang,"原创写作",CommunityType.COMPOSE));
        baseAdapter.addItem(new CommunityHeaderEntryItem(context,R.drawable.community_head_fuli,"活动福利",CommunityType.FULI));
        baseAdapter.addItem(new CommunityHeaderEntryItem(context,R.drawable.community_head_help,"书荒互助",CommunityType.HELP));

        recyclerView.setAdapter(baseAdapter);
    }
}
