package com.xincubate.lego.adapter.bridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBridge {

    private Map<String,List<BridgeEntity>> mItems = new HashMap<>();

    public void subscribe(String event,BridgeEntity bridgeEntity){
        List<BridgeEntity> items = mItems.get(event);
        if (items == null){
            items = new ArrayList<>();
            mItems.put(event,items);
        }
        if (!items.contains(bridgeEntity)){
            items.add(bridgeEntity);
        }
    }

    public void publish(BridgeEntity sender,String event,Object args){
        List<BridgeEntity> items = mItems.get(event);
        if (items != null){
            for (BridgeEntity item : items){
                item.onBridge(sender,event,args);
            }
        }
    }

}
