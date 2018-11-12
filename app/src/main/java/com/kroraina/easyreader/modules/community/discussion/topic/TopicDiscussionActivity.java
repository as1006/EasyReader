package com.kroraina.easyreader.modules.community.discussion.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.annotations.ActivityUI;

import com.kroraina.easyreader.modules.community.discussion.BaseDiscussionActivity;
import com.kroraina.easyreader.modules.main.community.CommunityType;

@ActivityUI(layoutId = R.layout.activity_topic_discussion)
public class TopicDiscussionActivity extends BaseDiscussionActivity {

    private static final String EXTRA_COMMUNITY = "extra_community";
    public static void startActivity(Context context, CommunityType type){
        Intent intent = new Intent(context,TopicDiscussionActivity.class);
        intent.putExtra(EXTRA_COMMUNITY,type);
        context.startActivity(intent);
    }

    //当前的讨论组
    private CommunityType mType;

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            mType = (CommunityType) savedInstanceState.getSerializable(EXTRA_COMMUNITY);
        } else {
            mType = (CommunityType) getIntent().getSerializableExtra(EXTRA_COMMUNITY);
        }
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        getSupportActionBar().setTitle(mType.getTypeName());
    }

    @Override
    protected void processLogic() {
        setUpSelectorView(TYPE_FIRST);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.book_discussion_fl,DiscCommentFragment.newInstance(mType))
                .commit();
    }



    /**********************************************************************/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_COMMUNITY,mType);
    }
}
