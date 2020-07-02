package com.example.whowroteitLoader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookInput=findViewById(R.id.bookInput);
        mTitleText= findViewById(R.id.tilteText);
        mAuthorText= findViewById(R.id.authotText);
    }

    public void searchBooks(View view) {
        String bookName= mBookInput.getText().toString();
        InputMethodManager inputManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputManager!=null){
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= null;
        if(connectivityManager!=null){
            networkInfo= connectivityManager.getActiveNetworkInfo();
        }
        if(networkInfo!=null && networkInfo.isConnected() && bookName.length()!=0){


        new FetchBook(mTitleText,mAuthorText).execute(bookName);
        mAuthorText.setText("");
        mTitleText.setText("Loading..");
        }else {
            if(bookName.length()==0){
                mAuthorText.setText("");
                mTitleText.setText("No Book to Search");
            }else{
                mAuthorText.setText("");
                mTitleText.setText("No Connection");
            }
        }
    }
}
