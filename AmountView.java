package com.example.hasee.changeicondemo;

/**
 * Created by hasee on 2017/11/10.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.inputmethodservice.Keyboard;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 自定义组件：购买数量，带减少增加按钮
 */
public class AmountView extends LinearLayout implements View.OnClickListener,TextWatcher{

    private int amount = 1; //购买数量
    private int goods_storage = 1; //商品库存

    private OnAmountChangeListener mListener;

    private EditText tvAmount;
    private Button btnDecrease;
    private Button btnIncrease;

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.item_view, this);
        tvAmount = (EditText) findViewById(R.id.tvAmount);
        btnDecrease = (Button) findViewById(R.id.btnDecrease);
        btnIncrease = (Button) findViewById(R.id.btnIncrease);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        tvAmount.addTextChangedListener(this);
        tvAmount.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
               // Toast.makeText(getContext(),keyCode+"",Toast.LENGTH_LONG).show();
                if ((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER)||keyCode==KeyEvent.KEYCODE_BACK|| keyCode==KeyEvent.KEYCODE_SEARCH) {
                    loseFocus();
                    tvAmount.setText(amount+"");
                    return true;
                }
                return false;
            }
        });

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, 160);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 80);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);
        obtainStyledAttributes.recycle();

        LayoutParams btnParams = new LayoutParams(btnWidth, LayoutParams.MATCH_PARENT);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
        if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }

        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);
        tvAmount.setLayoutParams(textParams);
        if (tvTextSize != 0) {
            tvAmount.setTextSize(tvTextSize);
        }
    }

    public void setListener(Object object){
        this.mListener = (OnAmountChangeListener) object;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            loseFocus();
            if (amount > 1) {
                amount--;
                tvAmount.setText(amount + "");
            }
        } else if (i == R.id.btnIncrease) {
               loseFocus();
                amount++;
                tvAmount.setText(amount + "");
        }

        if(mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(!TextUtils.isEmpty(s.toString().trim())){
            amount=Integer.valueOf(s.toString().trim());
        }else {
            amount=1;
        }

    }




    public interface OnAmountChangeListener{
        void onAmountChange(View view, int amount);
    }
    public void loseFocus(){
        if(!isFocused()){
            setFocusable(true);
            setFocusableInTouchMode(true);
            requestFocus();
            InputMethodManager imm = (InputMethodManager)getContext(). getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
        }


    }
    public void setAmount(int amount){
        this.amount=amount;
        tvAmount.setText(amount+"");
    }
    public int  getAmount(){
        return amount;
    }

}