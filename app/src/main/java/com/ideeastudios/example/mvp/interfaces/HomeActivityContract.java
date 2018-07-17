package com.ideeastudios.example.mvp.interfaces;

import android.content.Context;

public interface HomeActivityContract {

    interface ViewItf {
        void showProgress();

        void hideProgress();

        void logoutSuccessAction();

        Context getContext();
    }

    //The presenter must depend on the View interface and not directly on the Activity: in this way, you decouple the presenter from the view implementation
    interface PresenterItf {
        void performLogout();

        String getUsername();

        void onDestroy();

        void onPause();
    }

    interface LogoutListener {
        void onSuccess();
    }
}
