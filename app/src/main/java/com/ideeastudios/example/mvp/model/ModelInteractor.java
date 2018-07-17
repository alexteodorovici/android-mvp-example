package com.ideeastudios.example.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.ideeastudios.example.mvp.interfaces.LoginListener;
import com.ideeastudios.example.mvp.interfaces.LogoutListener;

public class ModelInteractor {

    private String username = "";
    private String password = "";
    private Handler loginHandler = null;
    private Runnable loginRunnable = null;
    private Handler logoutHandler = null;
    private Runnable logoutRunnable = null;

    private static ModelInteractor instance = null;

    public static ModelInteractor getInstance() {
        //there will be one single instance of the model, available to all presenters.
        if (instance == null) {
            instance = new ModelInteractor();
        }
        return instance;
    }

    public void performLogin(final Context context, final String username, final String password, final LoginListener listener) {
        Log.d("MVP-Model", "performLogin");
        //what if the user spams the login button? we can't let threads running around, can we?
        //we make sure we cancel any other running handlers.
        cancelLogin();

        //create a new runnable and a new handler and perform the long running login task.
        loginRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("MVP-Model", "performLogin-postDelayed-run");
                if (TextUtils.isEmpty(username)) {
                    listener.onUsernameError();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    listener.onPasswordError();
                    return;
                }

                setUsername(context, username);
                setPassword(context, password);
                saveUserPersistentData(context, username, password);

                listener.onSuccess();
            }
        };
        loginHandler = new Handler();
        loginHandler.postDelayed(loginRunnable, 5000);
    }

    public void cancelLogin() {
        //this function is called when the activity that triggered the login process is now destroyed or not visible.
        //we don't want any loose operations running around in foreground.
        Log.d("MVP-Model", "cancelLogin");
        if (loginHandler != null && loginRunnable != null) {
            loginHandler.removeCallbacks(loginRunnable);
            loginHandler = null;
            loginRunnable = null;
            Log.d("MVP-Model", "LOGIN CANCELLED");
        }
    }


    public void performLogout(final Context context, final LogoutListener listener) {
        Log.d("MVP-Model", "performLogout");
        //what if the user spams the logout button? we can't let threads running around, can we?
        //we make sure we cancel any other running handlers.
        cancelLogout();
        logoutRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("MVP-Model", "performLogout-postDelayed-run");

                setUsername(context, "");
                setPassword(context, "");
                saveUserPersistentData(context, username, password);

                listener.onSuccess();
            }
        };
        logoutHandler = new Handler();
        logoutHandler.postDelayed(logoutRunnable, 5000);
    }

    public void cancelLogout() {
        //this function is called when the activity that triggered the logout process is now destroyed or not visible.
        //we don't want any loose operations running around in foreground.
        Log.d("MVP-Model", "cancelLogout");
        if (logoutHandler != null && logoutRunnable != null) {
            logoutHandler.removeCallbacks(logoutRunnable);
            logoutHandler = null;
            logoutRunnable = null;
            Log.d("MVP-Model", "LOGOUT CANCELLED");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(Context context, String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(Context context, String password) {
        this.password = password;
    }

    public boolean isLoggedIn(Context context) {
        if (getUsername().isEmpty()) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("SHAREDPREFSMVP", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("USERNAME", "");
            String password = sharedPreferences.getString("PASSWORD", "");
            if (!username.isEmpty() && !password.isEmpty()) {
                setUsername(context, username);
                setPassword(context, password);
            }
        }

        return !getUsername().isEmpty();
    }

    private void saveUserPersistentData(Context context, String username, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SHAREDPREFSMVP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USERNAME", username);
        editor.putString("PASSWORD", password);
        editor.apply();
    }

}
