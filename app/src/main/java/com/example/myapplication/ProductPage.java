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
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class ProductPage extends AppCompatActivity {

    static int listStart = 0;
    static final int listSectionSize = 4;  // how many items are needed per card

    final int itemsPerPage = 10; // the amount of items that are on each page

    EditText textInput;

    /**
     * sets the the list that contain the ui that can be edited as well as calls the function that populates the first page
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(MainActivity.items.size());

        listStart = 0; // resets the the begining of the list

        System.out.println("created");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        //MainActivity.Send.textInput = findViewById(R.id.Searchbar);

        // all the cards that are used for the page
        ConstraintLayout[] cards = {findViewById(R.id.cardView), findViewById(R.id.cardView2), findViewById(R.id.cardView3),
                findViewById(R.id.cardView4), findViewById(R.id.cardView5), findViewById(R.id.cardView6),
                findViewById(R.id.cardView7), findViewById(R.id.cardView8), findViewById(R.id.cardView9),
                findViewById(R.id.cardView10)};

        // the text boxes for the products
        TextView[] allProduct = {findViewById(R.id.ProductNameCard1), findViewById(R.id.ProductNameCard2), findViewById(R.id.ProductNameCard3),
                findViewById(R.id.ProductNameCard4), findViewById(R.id.ProductNameCard5), findViewById(R.id.ProductNameCard6),
                findViewById(R.id.ProductNameCard7), findViewById(R.id.ProductNameCard8), findViewById(R.id.ProductNameCard9),
                findViewById(R.id.ProductNameCard10)};

        // the text boxes for the prices
        TextView[] allPrice = {findViewById(R.id.EditPriceCard1), findViewById(R.id.EditPriceCard2), findViewById(R.id.EditPriceCard3),
                findViewById(R.id.EditPriceCard4), findViewById(R.id.EditPriceCard5), findViewById(R.id.EditPriceCard6),
                findViewById(R.id.EditPriceCard7), findViewById(R.id.EditPriceCard8), findViewById(R.id.EditPriceCard9),
                findViewById(R.id.EditPriceCard10) };

        // the image spots for the product images
        ImageView[] allImageView = {findViewById(R.id.imageViewCard1), findViewById(R.id.imageViewCard2),
                findViewById(R.id.imageView3), findViewById(R.id.imageView4), findViewById(R.id.imageView5), findViewById(R.id.imageView6),
                findViewById(R.id.imageView7), findViewById(R.id.imageView8), findViewById(R.id.imageView9), findViewById(R.id.imageView10)};

        // the text boxes for which store it is
        TextView[] allStores = {findViewById(R.id.editLocationCard1), findViewById(R.id.editLocationCard2), findViewById(R.id.editLocationCard3),
                findViewById(R.id.editLocationCard4), findViewById(R.id.editLocationCard5), findViewById(R.id.editLocationCard6),
                findViewById(R.id.editLocationCard7), findViewById(R.id.editLocationCard8), findViewById(R.id.editLocationCard9),
                findViewById(R.id.editLocationCard10) };

        ImageView imageView = findViewById(R.id.imageViewCard1);
        TextView textView = findViewById(R.id.ProductNameCard1);
        textView.setText("hudishfowe");



        String url= "https://th.bing.com/th/id/OIP.cRu1DrTQqTXb0KkM4M3iVgHaJ4?rs=1&pid=ImgDetMain";
//        Picasso.with(ProductPage.this).load("https://www.bing.com/images/search?view=detailV2&ccid=TX5cvuJg&id=E96D4A2CFA7AC6F47E9F8FED94D3A587022EB3A0&thid=OIP.TX5cvuJgfnEdPcvRUE0TSAHaFU&mediaurl=https%3a%2f%2fcbsnews1.cbsistatic.com%2fhub%2fi%2f2012%2f03%2f30%2fcc26a85a-d26f-11e2-a43e-02911869d855%2fwhopper.jpg&cdnurl=https%3a%2f%2fth.bing.com%2fth%2fid%2fR.4d7e5cbee2607e711d3dcbd1504d1348%3frik%3doLMuAoel05Ttjw%26pid%3dImgRaw%26r%3d0&exph=920&expw=1280&q=burger+king&simid=608043923811289183&FORM=IRPRST&ck=A54BB3F4D077E0436049BDF6A831DCF0&selectedIndex=10&itb=0").into(imageView);
        Glide.with(this).load(url).into(imageView);


        // assings the back and next button
        Button btnNext = findViewById(R.id.NextButton);
        Button btnBack = findViewById(R.id.BackButton);

        populateCards(allProduct, allPrice, allImageView, allStores, cards, this, btnNext, btnBack);


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
        //Button btnNext = findViewById(R.id.NextButton);
        final Activity thisreference = this;
        /**
         * changes the page when clicked
         */
        btnNext.setOnClickListener(new View.OnClickListener() {
            // when the button is clicked, the listener opens the new activity
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ProductPage.this, ProductPage2.class));

                System.out.println("Dani's code ran");
                listStart += itemsPerPage * listSectionSize;

                populateCards(allProduct, allPrice, allImageView, allStores, cards, thisreference, btnNext, btnBack);

            }
        });
        //endregion
        //Button btnBack = findViewById(R.id.BackButton);
        /**
         * goes to the previous page when clicked
         */
        btnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                listStart -= itemsPerPage * listSectionSize;

                populateCards(allProduct, allPrice, allImageView, allStores, cards, thisreference, btnNext, btnBack);
            }
        });

        //endregion
    }

    /**
     * gets the user input from the text field
     * @return the input that can be used to search for new inputs
     */
    public String storeInput() {
        textInput = findViewById(R.id.TextInput);
        String input = textInput.getText().toString();
//        System.out.println(input);
        return input;
    }

    /**
     * manages the cards that display the products, adding the data or hiding them if needed
     * @param allProduct the list of text boxes that have the products name added
     * @param allPrice the list of text boxes that have the products prices added
     * @param allImageView the list of image holders
     * @param allStores the list of textboxes that say which store the product is sold from
     * @param cards the cards that hold all the data, and are used for making the cards visible or invisible
     * @param activity this activity
     * @param btnNext the next button so that it can be made visible or disable it depending on the page
     * @param btnBack the back button so that it can be made visible or disable it depending on the page
     */
    public void populateCards(TextView[] allProduct, TextView[] allPrice, ImageView[] allImageView, TextView[] allStores,
                              ConstraintLayout[] cards, Activity activity, Button btnNext, Button btnBack){

        TextView tempTextVeiw;
        ImageView tempImageVeiw;

        /**
        if(MainActivity.items.size() == 0){
            tempImageVeiw =
            Glide.with(activity).load();//.into(tempImageVeiw);
        }
         **/

        btnBack.setVisibility(listStart == 0 ? Button.GONE : Button.VISIBLE);
        btnNext.setVisibility(Button.VISIBLE); // the next button is managed later to see if it needs to be disabled or not

        for (ConstraintLayout card : cards){ // makes all the cards visible so that products can be added, and then any others can be disabled as needed
            card.setVisibility(ConstraintLayout.VISIBLE);
        }


        int textBoxIndex = 0; // resets the index

        //region the for loop that manages the cards and data put into the cards
        for(int i = listStart; i < listStart + itemsPerPage * listSectionSize; i++){

            //System.out.println(i + ", " + MainActivity.items.size());

            // if we've gotten everything, hide the remaining cards and the next button
            if (MainActivity.items.size() <= i) {
                while (textBoxIndex < itemsPerPage){
                    cards[textBoxIndex].setVisibility(ConstraintLayout.GONE);
                    textBoxIndex++;
                }
                btnNext.setVisibility(Button.GONE);

                break;
            }

            tempTextVeiw = allProduct[textBoxIndex];
            tempTextVeiw.setText(MainActivity.items.get(i).toString());
            i++;

            tempTextVeiw = allPrice[textBoxIndex];
            tempTextVeiw.setText(MainActivity.items.get(i).toString());
            i++;

            tempImageVeiw = allImageView[textBoxIndex];
            Glide.with(activity).load(MainActivity.items.get(i).toString()).into(tempImageVeiw);
            i++;

            tempTextVeiw = allStores[textBoxIndex];
            tempTextVeiw.setText(MainActivity.items.get(i).toString());

            textBoxIndex++;
        }
        //endregion
    }


}