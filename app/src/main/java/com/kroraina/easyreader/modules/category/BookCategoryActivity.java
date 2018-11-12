package com.kroraina.easyreader.modules.category;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.activity.BaseTabActivity;
import com.kroraina.easyreader.base.annotations.NavigationBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 发现->分类
 *
 */

@NavigationBar(titleResId = R.string.nb_fragment_find_sort)
public class BookCategoryActivity extends BaseTabActivity{

    @Override
    protected List<Fragment> createTabFragments() {
        List<Fragment> result = new ArrayList<>();
        Fragment fragment = new BookCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(BookCategoryFragment.KEY_IS_MALE,true);
        fragment.setArguments(bundle);
        result.add(fragment);

        fragment = new BookCategoryFragment();
        bundle = new Bundle();
        bundle.putBoolean(BookCategoryFragment.KEY_IS_MALE,false);
        fragment.setArguments(bundle);
        result.add(fragment);
        return result;
    }

    @Override
    protected List<String> createTabTitles() {
        return Arrays.asList("男生","女生");
    }

    /**********************init***********************************/
    @Override
    protected int getContentId() {
        return R.layout.activity_tab_top;
    }
}
