package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class ProductPage extends AppCompatActivity {

    static int listStart = 0;
    static final int listSectionSize = 4;

    final int itemsPerPage = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        listStart = 0;

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject myModual = py.getModule("CvsWebScrape");
        PyObject scrapeCode = myModual.get("getItems");

        System.out.println("created");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        MainActivity.Send.textInput = findViewById(R.id.Searchbar);

        ConstraintLayout[] cards = {findViewById(R.id.cardView), findViewById(R.id.cardView2), findViewById(R.id.cardView3),
                findViewById(R.id.cardView4), findViewById(R.id.cardView5), findViewById(R.id.cardView6),
                findViewById(R.id.cardView7), findViewById(R.id.cardView8), findViewById(R.id.cardView9),
                findViewById(R.id.cardView10)};

        TextView[] allProduct = {findViewById(R.id.ProductNameCard1), findViewById(R.id.ProductNameCard2), findViewById(R.id.ProductNameCard3),
                findViewById(R.id.ProductNameCard4), findViewById(R.id.ProductNameCard5), findViewById(R.id.ProductNameCard6),
                findViewById(R.id.ProductNameCard7), findViewById(R.id.ProductNameCard8), findViewById(R.id.ProductNameCard9),
                findViewById(R.id.ProductNameCard10)};

        TextView[] allPrice = {findViewById(R.id.EditPriceCard1), findViewById(R.id.EditPriceCard2), findViewById(R.id.EditPriceCard3),
                findViewById(R.id.EditPriceCard4), findViewById(R.id.EditPriceCard5), findViewById(R.id.EditPriceCard6),
                findViewById(R.id.EditPriceCard7), findViewById(R.id.EditPriceCard8), findViewById(R.id.EditPriceCard9),
                findViewById(R.id.EditPriceCard10) };

        ImageView[] allImageView = {findViewById(R.id.imageViewCard1), findViewById(R.id.imageViewCard2),
                findViewById(R.id.imageView3), findViewById(R.id.imageView4), findViewById(R.id.imageView5), findViewById(R.id.imageView6),
                findViewById(R.id.imageView7), findViewById(R.id.imageView8), findViewById(R.id.imageView9), findViewById(R.id.imageView10)};

        TextView[] allStores = {findViewById(R.id.editLocationCard1), findViewById(R.id.editLocationCard2), findViewById(R.id.editLocationCard3),
                findViewById(R.id.editLocationCard4), findViewById(R.id.editLocationCard5), findViewById(R.id.editLocationCard6),
                findViewById(R.id.editLocationCard7), findViewById(R.id.editLocationCard8), findViewById(R.id.editLocationCard9),
                findViewById(R.id.editLocationCard10) };

        ImageView imageView = findViewById(R.id.imageViewCard1);
        TextView textView = findViewById(R.id.ProductNameCard1);
        textView.setText("hudishfowe");

        int maxItemsShow;
        if(MainActivity.items.size() > itemsPerPage){
            maxItemsShow = itemsPerPage;
        }
        else {
            maxItemsShow = MainActivity.items.size();
        }

        String url= "https://th.bing.com/th/id/OIP.cRu1DrTQqTXb0KkM4M3iVgHaJ4?rs=1&pid=ImgDetMain";
//        Picasso.with(ProductPage.this).load("https://www.bing.com/images/search?view=detailV2&ccid=TX5cvuJg&id=E96D4A2CFA7AC6F47E9F8FED94D3A587022EB3A0&thid=OIP.TX5cvuJgfnEdPcvRUE0TSAHaFU&mediaurl=https%3a%2f%2fcbsnews1.cbsistatic.com%2fhub%2fi%2f2012%2f03%2f30%2fcc26a85a-d26f-11e2-a43e-02911869d855%2fwhopper.jpg&cdnurl=https%3a%2f%2fth.bing.com%2fth%2fid%2fR.4d7e5cbee2607e711d3dcbd1504d1348%3frik%3doLMuAoel05Ttjw%26pid%3dImgRaw%26r%3d0&exph=920&expw=1280&q=burger+king&simid=608043923811289183&FORM=IRPRST&ck=A54BB3F4D077E0436049BDF6A831DCF0&selectedIndex=10&itb=0").into(imageView);
        Glide.with(this).load(url).into(imageView);






        for(int i = listStart; i < maxItemsShow * listSectionSize; i++){
            int begining = i / listSectionSize;
            textView = allProduct[begining];
            textView.setText(MainActivity.items.get(i).toString());
            i++;

            textView = allPrice[begining];
            textView.setText(MainActivity.items.get(i).toString());
            i++;

            imageView = allImageView[begining];
            Glide.with(this).load(MainActivity.items.get(i).toString()).into(imageView);
            i++;

            textView = allStores[begining];
            textView.setText(MainActivity.items.get(i).toString());
        }



        //region - brings the user back to the main page
        //gets the corresponding XML object and assigns it to a variable
        Button btn = (Button)findViewById(R.id.ReturnButton);
        btn.setOnClickListener(new View.OnClickListener() {
            // when the button is clicked, the listener opens the new activity
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductPage.this, MainActivity.class));
            }
        });
        //endregion

        //region - brings the current page to the second product page
        //gets the corresponding XML object and assigns it to a variable
        Button btnNext = findViewById(R.id.NextButton);
        final Activity thisreference = this;
        btnNext.setOnClickListener(new View.OnClickListener() {
            // when the button is clicked, the listener opens the new activity
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ProductPage.this, ProductPage2.class));

                System.out.println("Dani's code ran");

                listStart += itemsPerPage * listSectionSize;

                TextView tempTextVeiw;
                ImageView tempImageVeiw;
                int textBoxIndex = 0;

                for(int i = listStart; i < listStart + maxItemsShow * listSectionSize; i++){

                    System.out.println(i + ", " + MainActivity.items.size());

                    //if we've gotten everything, hide the remaining cards and the next button
                    if (MainActivity.items.size() <= i) {
                        while (textBoxIndex < 10){
                            cards[textBoxIndex].setVisibility(ConstraintLayout.INVISIBLE);
                            textBoxIndex++;
                        }
                        btnNext.setVisibility(Button.INVISIBLE);

                        break;
                    }

                    //System.out.println(i);


                    tempTextVeiw = allProduct[textBoxIndex];
                    tempTextVeiw.setText(MainActivity.items.get(i).toString());
                    i++;

                    tempTextVeiw = allPrice[textBoxIndex];
                    tempTextVeiw.setText(MainActivity.items.get(i).toString());
                    i++;

                    tempImageVeiw = allImageView[textBoxIndex];
                    Glide.with(thisreference).load(MainActivity.items.get(i).toString()).into(tempImageVeiw);
                    i++;

                    tempTextVeiw = allStores[textBoxIndex];
                    tempTextVeiw.setText(MainActivity.items.get(i).toString());

                    textBoxIndex++;
                }

            }
        });
        //endregion

        //region - brings the current page to the first product page with new product searches
        //gets the corresponding XML object and assigns it to a variable
        Button searchButton = findViewById(R.id.SearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            // when the button is clicked, the listener opens the new activity
            @Override
            public void onClick(View v) {
                //nonfunctioning
//              storeInput();
                MainActivity.items = scrapeCode.call(storeInput()).asList();
                startActivity(new Intent(ProductPage.this, ProductPage.class));
            }
        });
        //endregion
    }

    public String storeInput() {
        EditText textInput = findViewById(R.id.TextInput);
        String input = textInput.getText().toString();
//        System.out.println(input);
        return input;
    }
}