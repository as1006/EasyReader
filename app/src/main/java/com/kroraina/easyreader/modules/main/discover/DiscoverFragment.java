package com.kroraina.easyreader.modules.main.discover;

import android.content.Intent;
import android.view.View;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.annotations.FragmentUI;
import com.kroraina.easyreader.base.annotations.NavigationBar;
import com.kroraina.easyreader.base.fragment.BaseListFragment;
import com.kroraina.easyreader.modules.category.BookCategoryActivity;
import com.kroraina.easyreader.modules.rank.RankListActivity;
import com.kroraina.easyreader.modules.sheetlist.BookSheetListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@NavigationBar(titleResId = R.string.nb_fragment_title_find)
@FragmentUI(layoutId = R.layout.fragment_discover)
public class DiscoverFragment extends BaseListFragment {

    @BindView(R.id.ll_category_container)
    public View mCategoryEntryView;

    @BindView(R.id.ll_rank_container)
    public View mRankEntryView;

    @BindView(R.id.ll_booksheet_container)
    public View mBookSheetEntryView;

    @Override
    protected void initClick() {
        super.initClick();

        mCategoryEntryView.setOnClickListener(v -> startActivity(new Intent(getContext(),BookCategoryActivity.class)));
        mRankEntryView.setOnClickListener(v -> startActivity(new Intent(getContext(),RankListActivity.class)));
        mBookSheetEntryView.setOnClickListener(v-> startActivity(new Intent(getContext(),BookSheetListActivity.class)));
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        List<DiscoverSectionItem> sections = new ArrayList<>();
        for (DiscoverType type : DiscoverType.values()){
            sections.add(new DiscoverSectionItem(getContext(),type));
        }
        mAdapter.addItems(sections);
        mAdapter.notifyDataSetChanged();
    }
}
