package com.kroraina.easyreader.modules.community.discussion;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.RxBus;
import com.kroraina.easyreader.base.activity.BaseActivity;
import com.kroraina.easyreader.event.SelectorEvent;
import com.kroraina.easyreader.model.flag.BookDistillate;
import com.kroraina.easyreader.model.flag.BookSort;
import com.kroraina.easyreader.model.flag.BookType;
import com.kroraina.easyreader.ui.widget.SelectorView;
import com.kroraina.easyreader.utils.Constant;

import butterknife.BindView;

import static com.kroraina.easyreader.model.flag.BookSelection.BOOK_TYPE;
import static com.kroraina.easyreader.model.flag.BookSelection.DISTILLATE;
import static com.kroraina.easyreader.model.flag.BookSelection.SORT_TYPE;

public abstract class BaseDiscussionActivity extends BaseActivity implements SelectorView.OnItemSelectedListener {

    public static final int TYPE_FIRST = 0;
    public static final int TYPE_SECOND= 1;


    @BindView(R.id.book_discussion_sv_selector)
    public SelectorView mSvSelector;


    private BookSort mBookSort = BookSort.DEFAULT;
    private BookDistillate mDistillate = BookDistillate.ALL;
    private BookType mBookType = BookType.ALL;

    @Override
    protected void initClick() {
        super.initClick();
        mSvSelector.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(int type, int pos) {
        //转换器
        switch (type) {
            case 0:
                mDistillate = BookDistillate.values()[pos];
                break;
            case 1:
                if (mSvSelector.getChildCount() == 2) {
                    //当size = 2的时候，就会到Sort这里。
                    mBookSort = BookSort.values()[pos];
                } else if (mSvSelector.getChildCount() == 3) {
                    mBookType = BookType.values()[pos];
                }
                break;
            case 2:
                mBookSort = BookSort.values()[pos];
                break;
            default:
                break;
        }

        RxBus.getInstance().post(Constant.MSG_SELECTOR, new SelectorEvent(mDistillate, mBookType, mBookSort));
    }

    protected void setUpSelectorView(int type){
        if (type == TYPE_FIRST){
            mSvSelector.setSelectData(DISTILLATE.getTypeParams(),SORT_TYPE.getTypeParams());
        }
        else {
            mSvSelector.setSelectData(DISTILLATE.getTypeParams(),BOOK_TYPE.getTypeParams(), SORT_TYPE.getTypeParams());
        }
    }
}
