package com.kroraina.easyreader.modules.rank.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.activity.BaseTabActivity;
import com.kroraina.easyreader.base.annotations.ActivityUI;
import com.kroraina.easyreader.base.annotations.NavigationBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@NavigationBar(title = "追书最热榜")
@ActivityUI(layoutId = R.layout.activity_tab_top)
public class BillBookActivity extends BaseTabActivity {
    private static final String EXTRA_WEEK_ID = "extra_week_id";
    private static final String EXTRA_MONTH_ID = "extra_month_id";
    private static final String EXTRA_TOTAL_ID = "extra_total_id";


    private String mWeekId;
    private String mMonthId;
    private String mTotalId;
    public static void startActivity(Context context,String weekId, String monthId, String totalId){
        Intent intent = new Intent(context,BillBookActivity.class);
        intent.putExtra(EXTRA_WEEK_ID,weekId);
        intent.putExtra(EXTRA_MONTH_ID,monthId);
        intent.putExtra(EXTRA_TOTAL_ID,totalId);

        context.startActivity(intent);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null){
            mWeekId = savedInstanceState.getString(EXTRA_WEEK_ID);
            mMonthId = savedInstanceState.getString(EXTRA_MONTH_ID);
            mTotalId = savedInstanceState.getString(EXTRA_TOTAL_ID);
        }
        else {
            mWeekId = getIntent().getStringExtra(EXTRA_WEEK_ID);
            mMonthId = getIntent().getStringExtra(EXTRA_MONTH_ID);
            mTotalId = getIntent().getStringExtra(EXTRA_TOTAL_ID);
        }
    }

    @Override
    protected List<Fragment> createTabFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(BillBookFragment.newInstance(mWeekId));
        fragments.add(BillBookFragment.newInstance(mMonthId));
        fragments.add(BillBookFragment.newInstance(mTotalId));
        return fragments;
    }

    @Override
    protected List<String> createTabTitles() {
        String [] title = getResources().getStringArray(R.array.nb_fragment_bill_book);
        return Arrays.asList(title);
    }

    /****************************************************/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_WEEK_ID,mWeekId);
        outState.putString(EXTRA_MONTH_ID,mMonthId);
        outState.putString(EXTRA_TOTAL_ID,mTotalId);
    }
}
