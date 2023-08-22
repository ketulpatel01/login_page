package com.ketul.loginpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    int n;
    String message ="";

    //function created for random number.
    public void randomNumberGenerator()  {

        Random rand = new Random();
        n = rand.nextInt(20) + 1;
    }

    //function created for checking user value.
    public void guess(View view)    {

        EditText guess = (EditText) findViewById(R.id.guess);
        int guessInt = Integer.parseInt(guess.getText().toString());

        if(n > guessInt) {
            message = "Higher!!";
        } else if (n < guessInt) {
            message = "Lower!!";
        } else if (n == guessInt) {
            message = "That's Right!! Play Again!!";
            randomNumberGenerator();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        randomNumberGenerator();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(HomeActivity.this,LogoutActivity.class));
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

}