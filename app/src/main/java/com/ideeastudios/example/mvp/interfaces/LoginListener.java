package com.ideeastudios.example.mvp.interfaces;

public interface LoginListener {
    void onUsernameError();

    void onPasswordError();

    void onSuccess();
}
