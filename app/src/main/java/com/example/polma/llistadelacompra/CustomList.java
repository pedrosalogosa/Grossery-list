package com.example.polma.llistadelacompra;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.polma.llistadelacompra.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final String[] imageId;
    private final String[] webAmount;
    private boolean addAmount;
    public CustomList(Activity context, String[] web, String[] imageId, String[] webAmount, int currentLine, boolean addAmount) {
        super(context, R.layout.list_single, new String[currentLine]);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.webAmount = webAmount;
        this.addAmount = addAmount;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            TextView txtAmount = (TextView) rowView.findViewById(R.id.txt2);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText("  " + web[position]);
            if (addAmount == true) {
                txtAmount.setText("  x" + webAmount[position]);
            }
            URL url = null;
            new DownloadImageTask(imageView).execute(imageId[position]);

            return rowView;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}