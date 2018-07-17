package com.ideeastudios.example.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.ideeastudios.example.mvp.interfaces.LogoutListener;
import com.ideeastudios.example.mvp.model.ModelInteractor;
import com.ideeastudios.example.mvp.view.HomeActivity;

public class HomeActivityPresenter implements LogoutListener {

    private HomeActivity activity;

    public HomeActivityPresenter(HomeActivity activity) {
        Log.d("MVP-HomeActPresenter", "constructor");
        this.activity = activity;
    }

    public void performLogout(Context context) {
        Log.d("MVP-HomeActPresenter", "performLogout");
        if (activity != null) {
            activity.showProgress();
        }
        ModelInteractor.getInstance().performLogout(context, this);
    }

    public void onDestroy() {
        Log.d("MVP-HomeActPresenter", "onDestroy");
        activity = null;
    }

    public void onPause() {
        Log.d("MVP-HomeActPresenter", "onPause");
        ModelInteractor.getInstance().cancelLogout();
        activity.hideProgress();
    }

    @Override
    public void onSuccess() {
        Log.d("MVP-HomeActPresenter", "onSuccess");
        if (activity != null) {
            activity.hideProgress();
            activity.logoutSuccessAction();
        }
    }

    public String getUsername() {
        return ModelInteractor.getInstance().getUsername();
    }
}
