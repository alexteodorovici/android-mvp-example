package com.ideeastudios.example.mvp.presenter;

import android.util.Log;

import com.ideeastudios.example.mvp.interfaces.HomeActivityContract;
import com.ideeastudios.example.mvp.model.ModelInteractor;
import com.ideeastudios.example.mvp.view.HomeActivity;

public class HomeActivityPresenter implements HomeActivityContract.LogoutListener, HomeActivityContract.PresenterItf {

    private HomeActivityContract.ViewItf activity;

    public HomeActivityPresenter(HomeActivity activity) {
        Log.d("MVP-HomeActPresenter", "constructor");
        this.activity = activity;
    }

    public void performLogout() {
        Log.d("MVP-HomeActPresenter", "performLogout");
        if (activity != null) {
            activity.showProgress();
        }
        ModelInteractor.getInstance().performLogout(activity.getContext(), this);
    }

    @Override
    public String getUsername() {
        return ModelInteractor.getInstance().getUsername();
    }

    public void onDestroy() {
        Log.d("MVP-HomeActPresenter", "onDestroy");
        activity = null;
    }

    public void onPause() {
        Log.d("MVP-HomeActPresenter", "onPause");
        ModelInteractor.getInstance().cancelLogout();
        if (activity != null) {
            activity.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        Log.d("MVP-HomeActPresenter", "onSuccess");
        if (activity != null) {
            activity.hideProgress();
            activity.logoutSuccessAction();
        }
    }

}
