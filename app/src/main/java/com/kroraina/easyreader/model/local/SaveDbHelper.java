package com.kroraina.easyreader.model.local;

import com.kroraina.easyreader.model.entity.DownloadTaskBean;
import com.kroraina.easyreader.modules.rank.BillboardPackage;



public interface SaveDbHelper {

    void saveBillboardPackage(BillboardPackage bean);
    /*************DownloadTask*********************/
    void saveDownloadTask(DownloadTaskBean bean);
}
