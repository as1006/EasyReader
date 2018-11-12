package com.kroraina.easyreader.model.bean.packages;

import java.util.List;

/**
 * Created on 17-6-2.
 */

public class KeyWordPackage extends BasePackage {

    private List<String> keywords;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
