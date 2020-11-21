package com.example.verifyphonenumber.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.verifyphonenumber.R;
import com.example.verifyphonenumber.views.VerifyView;

public class VerifyCodePresenter {

    private Activity activity;
    private VerifyView verifyView;
    private boolean firstTime = true;

    public VerifyCodePresenter(Activity activity, VerifyView verifyView) {
        this.activity = activity;
        this.verifyView = verifyView;
    }

    public void verifyPhoneNumber(String number) {
        if (firstTime) {
            firstTime = false;
            verifyView.disableRegistration();
        } else {
            checkPhoneNumber(getPhoneNumber(number));
        }
    }

    private void checkPhoneNumber(String number) {
        Log.v("numberWithCode", "numberWithCode: " + number);
        if (!number.isEmpty()) {
            if (number.length() > 3 && !(number.startsWith("+79"))) {
                verifyView.showErrorView();
                verifyView.showErrorResponse(activity.getString(R.string.should_start_with_seven_nine));
                verifyView.disableRegistration();
            } else if (!number.matches("[0-9+]+")) {
                verifyView.showErrorView();
                verifyView.showErrorResponse(activity.getString(R.string.should_contain_number));
                verifyView.disableRegistration();
            } else if (number.length() < 12) {
                verifyView.disableRegistration();
                verifyView.showErrorResponse("");
                verifyView.hideErrorView();
            } else {
                verifyView.showErrorResponse("");
                verifyView.hideErrorView();
                verifyView.enableRegistration();
            }
        } else {
            verifyView.disableRegistration();
            verifyView.showErrorResponse("");
            verifyView.hideErrorView();
        }
    }

    public String getPhoneNumber(String number) {
        return "+7" + number;
    }
}
