package com.example.polma.llistadelacompra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;
import android.view.LayoutInflater;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static com.example.polma.llistadelacompra.R.string.action_exit;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // These are the Contacts rows that we will retrieve
    String[] names = new String[90];
    String[] ids = new String[90];
    int[] amounts = new int[90];
    int currentLine = -1;
    // This is the select criteria
    static final String SELECTION = "wtf";
    ListView LListaCompra;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LListaCompra = (ListView) findViewById(R.id.List);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }
    public void addToArray () {
        String[] amountsString = new String [90];
        for (int a = 0; a <= 89; a++) {
            if (currentLine >= a) {
                amountsString[a] = Integer.toString(amounts[a]);
            } else {
                amountsString[a] = "";
            }
        }
        CustomList adapterForTheList = new CustomList(MainActivity.this, names, ids, amountsString, currentLine + 1, true);
        LListaCompra.setAdapter(adapterForTheList);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Exit) {
            System.exit(0);
        }
        if (id == R.id.action_Whatsapp) {
            String WhatsappText = WhatsappMessageGenerator();
            WhatsApp(item.getActionView(), WhatsappText);
        }
        return super.onOptionsItemSelected(item);
    }

    public void OnClickNewFood (View v) {
        //Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        Intent myIntent = new Intent(v.getContext(), NewFood.class);
        //Afageix la informació (Li posem un nom i li donem un valor)
        //executem l'Intent
        startActivityForResult(myIntent, 0);

    }
    public String WhatsappMessageGenerator () {
        String E = " \r\n";
        String Head = getString(R.string.Whatsapp_Head);
        String Content = "";
            for (int a = 0; a <= currentLine; a++) {
                Content += names[a];
                if (!Character.isWhitespace(Content.charAt(Content.length() - 1))) {
                    Content += " ";
                }
                Content += "x" + amounts[a];
                Content += E;
            }
        String Result = Head + E + Content;
        return Result;
    }
    public void WhatsApp(View view, String TextToUse) {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, TextToUse);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            if (data.hasExtra("IdEntry")) {
                String Id = data.getStringExtra("IdEntry");
                String Name =  data.getStringExtra("NameEntry");
                int Amount = data.getIntExtra("AmountEntry", 0);
                //Snackbar.make(this.getCurrentFocus(), Id + " Name: " + Name + " " + Integer.toString(Amount) , Snackbar.LENGTH_LONG).setAction("Action", null).show();
                currentLine++;
                names[currentLine] = Name;
                ids[currentLine] = Id;
                amounts[currentLine] = Amount;
                addToArray();
            }
        }
    }
}
