package com.kroraina.easyreader.modules.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.kroraina.easyreader.R;
import com.kroraina.easyreader.RxBus;
import com.kroraina.easyreader.event.RecommendBookEvent;
import com.kroraina.easyreader.utils.Constant;
import com.kroraina.easyreader.utils.SharedPreUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SexChooseDialog extends Dialog {
    @BindView(R.id.choose_iv_close)
    ImageView mIvClose;
    @BindView(R.id.choose_btn_boy)
    Button mBtnBoy;
    @BindView(R.id.choose_btn_girl)
    Button mBtnGirl;

    private Unbinder unbinder;
    public SexChooseDialog(Context context) {
        super(context,R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sex_choose);
        unbinder = ButterKnife.bind(this);
        setUpWindow();
    }

    private void setUpWindow(){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.horizontalMargin = 0;
    }


    @OnClick({R.id.choose_iv_close,R.id.choose_btn_boy,R.id.choose_btn_girl})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.choose_btn_boy:
                //保存到SharePreference中
                SharedPreUtils.getInstance()
                        .putString(Constant.SHARED_SEX,Constant.SEX_BOY);
                RxBus.getInstance().post(new RecommendBookEvent("male"));
                break;
            case R.id.choose_btn_girl:
                //保存到SharePreference中
                SharedPreUtils.getInstance()
                        .putString(Constant.SHARED_SEX,Constant.SEX_GIRL);
                RxBus.getInstance().post(new RecommendBookEvent("female"));
                break;
            default:
                break;
        }
        dismiss();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }
}
