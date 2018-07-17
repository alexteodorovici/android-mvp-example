package com.ideeastudios.example.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.ideeastudios.example.mvp.interfaces.LoginListener;
import com.ideeastudios.example.mvp.model.ModelInteractor;
import com.ideeastudios.example.mvp.view.MainActivity;

public class MainActivityPresenter implements LoginListener {

    private MainActivity activity;

    public MainActivityPresenter(MainActivity activity) {
        Log.d("MVP-Presenter", "constructor");
        this.activity = activity;
    }

    public void performLogin(Context context, String username, String password) {
        Log.d("MVP-Presenter", "performLogin");
        if (activity != null) {
            activity.showProgress();
        }

        ModelInteractor.getInstance().performLogin(context, username, password, this);
    }

    public void onDestroy() {
        Log.d("MVP-Presenter", "onDestroy");
        activity = null;
    }

    public void onPause() {
        Log.d("MVP-Presenter", "onPause");
        ModelInteractor.getInstance().cancelLogin();
        activity.hideProgress();
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

    public boolean isLoggedIn(Context context) {
        return ModelInteractor.getInstance().isLoggedIn(context);
    }
}
