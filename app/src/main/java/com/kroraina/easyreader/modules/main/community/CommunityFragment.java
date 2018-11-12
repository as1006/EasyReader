package com.kroraina.easyreader.modules.main.community;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.base.annotations.NavigationBar;
import com.kroraina.easyreader.base.fragment.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 社区 2018-11-7
 */
@NavigationBar(titleResId = R.string.nb_fragment_title_community)
public class CommunityFragment extends BaseListFragment {

    @Override
    protected void processLogic() {
        super.processLogic();
        List<CommunitySectionItem> sections = new ArrayList<>();
        CommunityType type = CommunityType.HOT;
        sections.add(new CommunitySectionItem(getContext(),type.getTypeName(),type.getIconId(),type));
        type = CommunityType.COMMENT;
        sections.add(new CommunitySectionItem(getContext(),type.getTypeName(),type.getIconId(),type));
        type = CommunityType.REVIEW;
        sections.add(new CommunitySectionItem(getContext(),type.getTypeName(),type.getIconId(),type));
        mAdapter.addItems(sections);
        mAdapter.addHeaderItem(new CommunityHeaderItem(getContext()));
        mAdapter.notifyDataSetChanged();
    }
}
