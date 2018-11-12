package com.kroraina.easyreader.modules.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.activity.BaseTabActivity;
import com.kroraina.easyreader.base.annotations.ActivityUI;
import com.kroraina.easyreader.base.annotations.NavigationBar;
import com.kroraina.easyreader.modules.community.comment.BookCommentFragment;
import com.kroraina.easyreader.modules.community.discuss.DiscussFragment;
import com.kroraina.easyreader.modules.community.shortcomment.BookShortCommentFragment;

import java.util.Arrays;
import java.util.List;

@NavigationBar(title = "讨论区")
@ActivityUI(layoutId = R.layout.activity_tab_top)
public class DiscussActivity extends BaseTabActivity {

    public static void launch(Context context,String bookId){
        Intent intent = new Intent(context,DiscussActivity.class);
        intent.putExtra("book_id",bookId);
        context.startActivity(intent);
    }

    private String mBookId;

    @Override
    protected List<Fragment> createTabFragments() {
        Bundle bundle = new Bundle();
        bundle.putString("book_id",mBookId);
        Fragment discussFragment = new DiscussFragment();
        discussFragment.setArguments(bundle);

        bundle = new Bundle();
        bundle.putString("book_id",mBookId);
        Fragment bookCommentFragment = new BookCommentFragment();
        bookCommentFragment.setArguments(bundle);

        bundle = new Bundle();
        bundle.putString("book_id",mBookId);
        Fragment bookShortCommentFragment = new BookShortCommentFragment();
        bookShortCommentFragment.setArguments(bundle);
        return Arrays.asList(discussFragment,bookCommentFragment,bookShortCommentFragment);
    }

    @Override
    protected List<String> createTabTitles() {
        return Arrays.asList("讨论","书评","短评");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mBookId = getIntent().getStringExtra("book_id");
    }
}
