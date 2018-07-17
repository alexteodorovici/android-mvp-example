package com.ideeastudios.example.mvp.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ideeastudios.example.mvp.R;
import com.ideeastudios.example.mvp.interfaces.HomeActivityContract;
import com.ideeastudios.example.mvp.presenter.HomeActivityPresenter;

public class HomeActivity extends AppCompatActivity implements HomeActivityContract.ViewItf {

    TextView usernameTextView;
    ProgressBar progressBar;
    Button logoutButton;
    private HomeActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("MVP-HomeActivity", "onCreate");

        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        logoutButton = (Button) findViewById(R.id.logoutButton);

        //instantiate the Presenter for this activity.
        //the presenter helps separate the business logic with our user interface (view).
        //the data that we show in the view should be provided by the model via the presenter.
        //everything is decoupled - we can swap our logic in any of the three components of our
        // application (Model, View, Presenter) without impacting each other.
        presenter = new HomeActivityPresenter(this);

        usernameTextView.setText("Welcome " + presenter.getUsername());

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MVP-HomeActivity", "loginClicked");
                presenter.performLogout();
            }
        });
    }

    @Override
    public void showProgress() {
        Log.d("MVP-HomeActivity", "showProgress");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        Log.d("MVP-HomeActivity", "hideProgress");
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void logoutSuccessAction() {
        Log.d("MVP-HomeActivity", "logoutSuccessAction");
        Toast.makeText(this, "HomeActivity logoutSuccessAction OK!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        Log.d("MVP-HomeActivity", "onDestroy");
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
