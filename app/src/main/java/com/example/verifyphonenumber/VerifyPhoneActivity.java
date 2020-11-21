package com.example.verifyphonenumber;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.verifyphonenumber.presenter.VerifyCodePresenter;
import com.example.verifyphonenumber.views.VerifyView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.santalu.maskedittext.MaskEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class VerifyPhoneActivity extends AppCompatActivity implements VerifyView {

    private VerifyCodePresenter presenter;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_phone_number_error)
    TextView tvPhoneNumberError;
    @BindView(R.id.ed_phone)
    MaskEditText edPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_code);
        ButterKnife.bind(this);

        presenter = new VerifyCodePresenter(this, this);
        initObservable();
        initTouchListener();
    }

    @SuppressLint("CheckResult")
    private void initObservable() {
        RxTextView
                .textChanges(edPhone)
                .observeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .subscribe(input -> {
                    presenter.verifyPhoneNumber(edPhone.getRawText());
                });
    }

    @OnClick(R.id.tv_get_code)
    public void getCode() {
        //Intent intent = new Intent(this, VerifyCodeActivity.class);
        //intent.putExtra("phone", presenter.getPhoneNumber(edPhone.getRawText()));
        //startActivity(intent);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setMessage(String message) {
        tvGetCode.setText(message);
    }

    @Override
    public void showErrorView() {
        tvPhoneNumberError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorView() {
        tvPhoneNumberError.setVisibility(View.GONE);
    }

    @Override
    public void showErrorResponse(String message) {
        tvPhoneNumberError.setText(message);
    }

    @Override
    public void enableRegistration() {
        tvGetCode.setEnabled(true);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            tvGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_enable_round_corner));
        } else {
            tvGetCode.setBackground(getResources().getDrawable(R.drawable.bg_enable_round_corner));
        }
    }

    @Override
    public void disableRegistration() {
        tvGetCode.setEnabled(false);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            tvGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_disable_round_corner));
        } else {
            tvGetCode.setBackground(getResources().getDrawable(R.drawable.bg_disable_round_corner));
        }
    }

    private void initTouchListener() {
        edPhone.setOnTouchListener((v, event) -> {
            edPhone.onTouchEvent(event);
            MaskEditText editText = (MaskEditText) v;
            String text = editText.getText().toString();
            if (text != null) {
                editText.setSelection(text.length());
            }
            return true;
        });
    }
}