package com.kroraina.easyreader.model.local;

import com.kroraina.easyreader.model.entity.DownloadTaskBean;
import com.kroraina.easyreader.modules.rank.RankListPackage;

import java.util.List;



public interface GetDbHelper {
    RankListPackage getBillboardPackage();


    /******************************/
    List<DownloadTaskBean> getDownloadTaskList();
}
