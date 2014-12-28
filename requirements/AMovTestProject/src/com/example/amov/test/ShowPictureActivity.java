package com.example.amov.test;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by mgois on 26-12-2014.
 */
public class ShowPictureActivity extends Activity {

    public static final String EXT_FILE = "file";

    ImageView imgPicture;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_picture_activity);

        imgPicture = (ImageView) findViewById(R.id.showPicture_imgPicture);

        final Bundle bundle = getIntent().getExtras();
        final String file = bundle.getString(EXT_FILE);
        imgPicture.setImageBitmap(BitmapFactory.decodeFile(file));
    }
}