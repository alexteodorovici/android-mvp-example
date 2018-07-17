package com.ideeastudios.example.mvp.presenter;

import android.util.Log;

import com.ideeastudios.example.mvp.interfaces.MainActivityContract;
import com.ideeastudios.example.mvp.model.ModelInteractor;

public class MainActivityPresenter implements MainActivityContract.LoginListener, MainActivityContract.PresenterItf {

    private MainActivityContract.ViewItf activity;

    public MainActivityPresenter(MainActivityContract.ViewItf activity) {
        Log.d("MVP-Presenter", "constructor");
        this.activity = activity;
    }

    @Override
    public void performLogin(String username, String password) {
        Log.d("MVP-Presenter", "performLogin");
        if (activity != null) {
            activity.showProgress();
            ModelInteractor.getInstance().performLogin(activity.getContext(), username, password, this);
        }
    }

    @Override
    public boolean isLoggedIn() {
        if (activity != null) {
            return ModelInteractor.getInstance().isLoggedIn(activity.getContext());
        }
        return false;
    }

    @Override
    public void onDestroy() {
        Log.d("MVP-Presenter", "onDestroy");
        activity = null;
    }

    @Override
    public void onPause() {
        Log.d("MVP-Presenter", "onPause");
        ModelInteractor.getInstance().cancelLogin();
        if (activity != null) {
            activity.hideProgress();
        }
    }

    @Override
    public void onUsernameError() {
        Log.d("MVP-Presenter", "onUsernameError");
        if (activity != null) {
            activity.setUsernameError();
            activity.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        Log.d("MVP-Presenter", "onPasswordError");
        if (activity != null) {
            activity.setPasswordError();
            activity.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        Log.d("MVP-Presenter", "onSuccess");
        if (activity != null) {
            activity.hideProgress();
            activity.loginSuccessAction();
        }
    }


}
