package com.kroraina.easyreader.event;

import com.kroraina.easyreader.model.entity.CollBookBean;



public class DeleteTaskEvent {
    public CollBookBean collBook;

    public DeleteTaskEvent(CollBookBean collBook){
        this.collBook = collBook;
    }
}
