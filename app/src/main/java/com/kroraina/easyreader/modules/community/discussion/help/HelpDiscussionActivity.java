package com.kroraina.easyreader.modules.community.discussion.help;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.annotations.ActivityUI;
import com.kroraina.easyreader.base.annotations.NavigationBar;
import com.kroraina.easyreader.modules.community.discussion.BaseDiscussionActivity;

@NavigationBar(titleResId = R.string.nb_fragment_community_help)
@ActivityUI(layoutId = R.layout.activity_topic_discussion)
public class HelpDiscussionActivity extends BaseDiscussionActivity {

    @Override
    protected void processLogic() {
        setUpSelectorView(TYPE_FIRST);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.book_discussion_fl,new DiscHelpsFragment())
                .commit();
    }
}
