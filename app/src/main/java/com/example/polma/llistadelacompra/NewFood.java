package com.example.polma.llistadelacompra;

/**
 * Created by polma on 28/9/2017.
 */
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class NewFood extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ArrayAdapter adapterForTheList;
    // These are the Contacts rows that we will retrieve
    static final String[] values = new String[] {"Pa", "Vi", "Pizza"};
    static final String[] ids = new String[] {"http://padepagescatala.org/wp-content/uploads/reglament1.gif", "http://www.xicqueviuresiceller.com/admin/19793.jpg", "https://cdn.modpizza.com/wp-content/uploads/2016/11/mod-pizza-mad-dog-e1479167997381.png"};
    public int amount = 1;
    // This is the select criteria
    static final String SELECTION = "wtf";
    ListView LListaCompra;
    TextView amountT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final Intent myIntent = getIntent();
        amount = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_food);
        amountT = (TextView) findViewById(R.id.amount);
        LListaCompra = (ListView) findViewById(R.id.FoodList);
        addToArray();

        LListaCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                myIntent.putExtra("IdEntry", ids[position]);
                myIntent.putExtra("NameEntry", values[position]);
                myIntent.putExtra("AmountEntry", amount);
                setResult(0, myIntent);
                finish();
            }
        });
    }
    public void addToArray () {
        String[] amountsString = new String [90];
        for (int a = 0; a <= 89; a++) {
                amountsString[a] = "";
        }
        CustomList adapterForTheList = new CustomList(NewFood.this, values, ids, amountsString, values.length, false);
        LListaCompra.setAdapter(adapterForTheList);
    }

    public void plus (View v) {
        amount++;
        actualizeText();
    }
    public void minus (View v) {
        amount--;
        actualizeText();
    }
    public void actualizeText () {
        amountT.setText("Cuantitat: " + Integer.toString(amount));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}