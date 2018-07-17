package com.ideeastudios.example.mvp.interfaces;

import android.content.Context;

public interface MainActivityContract {

    interface ViewItf {
        void showProgress();

        void hideProgress();

        void setUsernameError();

        void setPasswordError();

        void loginSuccessAction();

        Context getContext();
    }

    // decouple the presenter from the view implementation
    interface PresenterItf {
        void performLogin(String username, String password);

        boolean isLoggedIn();

        void onDestroy();

        void onPause();
    }

    interface LoginListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }

}
