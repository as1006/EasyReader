package com.kroraina.easyreader.modules.main.discover;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.kroraina.easyreader.App;
import com.kroraina.easyreader.R;

public enum DiscoverType {
    HELP(R.string.nb_fragment_community_help,R.drawable.discover_icon_help);
    //LISTEN(R.string.nb_fragment_find_listen,R.drawable.ic_section_listen);

    private String typeName;
    private int iconId;

    DiscoverType(@StringRes int typeNameId, @DrawableRes int iconId){
        this.typeName = App.getContext().getResources().getString(typeNameId);
        this.iconId = iconId;
    }

    public String getTypeName(){
        return typeName;
    }

    public int getIconId(){
        return iconId;
    }
}
