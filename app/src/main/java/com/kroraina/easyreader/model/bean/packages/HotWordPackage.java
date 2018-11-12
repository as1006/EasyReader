package com.kroraina.easyreader.model.bean.packages;

import java.util.List;

/**
 * Created on 17-6-2.
 */

public class HotWordPackage extends BasePackage {


    private List<String> hotWords;

    public List<String> getHotWords() {
        return hotWords;
    }

    public void setHotWords(List<String> hotWords) {
        this.hotWords = hotWords;
    }
}
