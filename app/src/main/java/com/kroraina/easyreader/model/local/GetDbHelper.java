package com.kroraina.easyreader.model.local;

import com.kroraina.easyreader.model.entity.DownloadTaskBean;
import com.kroraina.easyreader.modules.rank.BillboardPackage;

import java.util.List;



public interface GetDbHelper {
    BillboardPackage getBillboardPackage();


    /******************************/
    List<DownloadTaskBean> getDownloadTaskList();
}
