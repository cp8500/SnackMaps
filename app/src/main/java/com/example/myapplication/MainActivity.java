package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    static List<PyObject> items;
    EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInput = findViewById(R.id.TextInput);

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject myModual = py.getModule("CvsWebScrape");
        PyObject scrapeCode = myModual.get("getItems");
        //System.out.println(scrapeCode.call());
        //TextView testThis =  (TextView)findViewById(R.id.textView1);
        int[] numList = {1, 3, 2, 5, 9};
        //System.out.println(items.get(4));
        //testThis.setText(items.get(4).toString());
        //https://www.selenium.dev/documentation/webdriver/troubleshooting/errors/driver_location/
        //https://stackoverflow.com/questions/42478591/python-selenium-chrome-webdriver
        //https://stackoverflow.com/questions/2378607/what-permission-do-i-need-to-access-internet-from-an-android-application

        Button btn = (Button)findViewById(R.id.searchButtonHome);
        btn.setOnClickListener(new View.OnClickListener() {
            // when the button is clicked, the listener opens the new activity
            @Override
            public void onClick(View v) {
                pageSet(scrapeCode);
            }
        });

        textInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    System.out.println("sodfou");
                    pageSet(scrapeCode);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * storeInput - stores the input in the "EditText" field upon pressing the search button
     * stores input into string variable to be passed into web-scraping tools
     */
    public String storeInput() {
        textInput = findViewById(R.id.TextInput);
        String input = textInput.getText().toString();
        return input;
    }

    public void pageSet(PyObject scrapeCode){
        MainActivity.items = scrapeCode.call(storeInput()).asList();
        System.out.println(MainActivity.items.size());
        startActivity(new Intent(MainActivity.this, ProductPage.class));
    }
}