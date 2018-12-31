package com.kroraina.easyreader.model.local;

import com.kroraina.easyreader.model.entity.DownloadTaskBean;
import com.kroraina.easyreader.modules.rank.RankListPackage;



public interface SaveDbHelper {

    void saveBillboardPackage(RankListPackage bean);
    /*************DownloadTask*********************/
    void saveDownloadTask(DownloadTaskBean bean);
}
