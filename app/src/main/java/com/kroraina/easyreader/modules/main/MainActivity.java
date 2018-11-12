package com.kroraina.easyreader.modules.main;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.activity.BaseTabActivity;
import com.kroraina.easyreader.modules.main.community.CommunityFragment;
import com.kroraina.easyreader.modules.main.discover.DiscoverFragment;
import com.kroraina.easyreader.modules.main.shelf.BookShelfFragment;
import com.kroraina.easyreader.modules.main.store.BookStoreFragment;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.SharedPreUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseTabActivity{

    @Override
    protected List<Fragment> createTabFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new BookShelfFragment());
        fragments.add(new BookStoreFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new DiscoverFragment());
        return fragments;
    }

    @Override
    protected List<String> createTabTitles() {
        String [] titles = getResources().getStringArray(R.array.nb_fragment_title);
        return Arrays.asList(titles);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //性别选择框
        //showSexChooseDialog();
    }

    /**
     * 如果之前没有选择过性别，那么就弹框选择吧
     */
    private void showSexChooseDialog(){
        String sex = SharedPreUtils.getInstance().getString(Constant.SHARED_SEX);
        if (sex.equals("")){
            mVp.postDelayed(()-> {
                Dialog dialog = new SexChooseDialog(this);
                dialog.show();
            },500);
        }
    }


    /**
     * 两次返回键，退出app
     */
    private static final int EXIT_WAIT_INTERVAL = 2000;
    private boolean isPrepareFinish = false;
    @Override
    public void onBackPressed() {
        if(!isPrepareFinish){
            mVp.postDelayed(
                    () -> isPrepareFinish = false,EXIT_WAIT_INTERVAL
            );
            isPrepareFinish = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
        else {
            super.onBackPressed();
        }
    }
}
