package com.example.verifyphonenumber.views;

public interface VerifyView {
    void showToastMessage(String message);
    void setMessage(String message);
    void showErrorResponse(String message);
    default void showSuccessResponse(String message) {}
    void enableRegistration();
    void disableRegistration();
    void showErrorView();
    void hideErrorView();
}
