package com.kroraina.easyreader.modules.main.community;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.kroraina.easyreader.App;
import com.kroraina.easyreader.R;

public enum CommunityType {
//http://api.zhuishushenqi.com/user/twitter/hottweets这个是动态
    HOT(R.string.nb_fragment_community_hot,"",R.drawable.community_item_hot),
    COMMENT(R.string.nb_fragment_community_comment, "ramble",R.drawable.ic_section_comment),
    REVIEW(R.string.nb_fragment_community_discussion,"",R.drawable.ic_section_discuss),
    HELP(R.string.nb_fragment_community_help,"",R.drawable.ic_section_help),
    GIRL(R.string.nb_fragment_community_girl,"girl",R.drawable.ic_section_girl),
    COMPOSE(R.string.nb_fragment_community_compose,"original",R.drawable.ic_section_compose),
    ESPORT(R.string.nb_fragment_community_esport,"yingxiong",R.drawable.ic_section_compose),
    MANHUA(R.string.nb_fragment_community_manhua,"erciyuan",R.drawable.ic_section_compose),
    JIANGHU(R.string.nb_fragment_community_jianghu,"wangwen",R.drawable.ic_section_compose),
    HISTORY(R.string.nb_fragment_community_history,"dahua",R.drawable.ic_section_compose),
    FULI(R.string.nb_fragment_community_fuli,"fuli",R.drawable.ic_section_compose);

    private String typeName;
    private String netName;
    private int iconId;
    CommunityType(@StringRes int typeId,String netName,@DrawableRes int iconId){
        this.typeName = App.getContext().getResources().getString(typeId);
        this.netName = netName;
        this.iconId = iconId;
    }

    public String getTypeName(){
        return typeName;
    }

    public String getNetName(){
        return netName;
    }

    public int getIconId(){
        return iconId;
    }
}
