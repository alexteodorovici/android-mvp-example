package com.ideeastudios.example.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ideeastudios.example.mvp.R;
import com.ideeastudios.example.mvp.interfaces.MainActivityInterface;
import com.ideeastudios.example.mvp.presenter.MainActivityPresenter;


public class MainActivity extends AppCompatActivity implements MainActivityInterface {

    EditText usernameEditText;
    EditText passwordEditText;
    ProgressBar progressBar;
    Button loginButton;
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MVP-View", "onCreate");

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        loginButton = (Button) findViewById(R.id.loginButton);

        //instantiate the Presenter for this activity.
        //the presenter helps separate the business logic with our user interface (view).
        //the data that we show in the view should be provided by the model via the presenter.
        //everything is decoupled - we can swap our logic in any of the three components of our
        // application (Model, View, Presenter) without impacting each other.
        presenter = new MainActivityPresenter(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MVP-View", "loginClicked");
                presenter.performLogin(MainActivity.this, usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //here we check if there is already some saved user credentials on the persistent storage.
        //if we find a user saved, we log it in.
        if(presenter.isLoggedIn(this)){
            Log.d("MVP-View", "onStart isLoggedIn");
            Toast.makeText(this, "onStart isLoggedIn!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    @Override
    public void showProgress() {
        //while performing the login procedure, the presenter will trigger the display of the progress bar.
        Log.d("MVP-View", "showProgress");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        //after the login procedure completes, we hide the progress bar.
        Log.d("MVP-View", "hideProgress");
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setUsernameError() {
        //if there is some validation error for the username, the presenter will call this function.
        //the view will only know it has to show the user an error as instructed by the presenter.
        Log.d("MVP-View", "setUsernameError");
        usernameEditText.setError(getString(R.string.username_error));
        Toast.makeText(this, getString(R.string.username_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPasswordError() {
        //if there is some validation error for the password, the presenter will call this function.
        //the view will only know it has to show the user an error as instructed by the presenter.
        Log.d("MVP-View", "setPasswordError");
        passwordEditText.setError(getString(R.string.password_error));
        Toast.makeText(this, getString(R.string.password_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessAction() {
        //after the presenter performs the login action, it calls this function so that our view can start the HomeActivity.
        Log.d("MVP-View", "loginSuccessAction");
        Toast.makeText(this, "MainActivityInterface Success!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        //what if we click login and then our application suddenly closes (we get a call maybe?)
        //the presenter must be aware that our activity is destroyed so that it won't call any functions on a null activity.
        Log.d("MVP-View", "onDestroy");
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        //what if we click login and then our application suddenly closes (we get a call maybe?)
        //the presenter must be aware that our activity is not visible so that it won't call any functions.
        Log.d("MVP-View", "onPause");
        presenter.onPause();
        super.onPause();
    }
}
