package com.example.amov.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartActivity extends Activity {

    static final int REQUEST_TAKE_PHOTO = 1;

    Context ctx;

    Button btnShowPhoneNumber;
    Button btnTakePicture;
    Button btnSelectMapPosition;

    String photoPath;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ctx = this;

        btnShowPhoneNumber = (Button) findViewById(R.id.start_btnShowPhoneNumber);
        btnShowPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                TelephonyManager tMgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
                String number = tMgr.getLine1Number();

                Toast.makeText(ctx, "Telephone number: " + number, Toast.LENGTH_LONG).show();
            }
        });

        btnTakePicture = (Button) findViewById(R.id.start_btnTakePicture);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null) { // make sure there is at least one handler for the intent
                    // Save the picture taken
                    try {
                        final File photo = createImageFile();
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);

                    } catch (final IOException ex) {
                        Toast.makeText(ctx, "An error occurred while saving the image: " + ex.toString(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        btnSelectMapPosition = (Button) findViewById(R.id.start_btnSelectMapPosition);
        btnSelectMapPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Intent intent = new Intent(ctx, MapPositionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                final Intent intent = new Intent(ctx, ShowPictureActivity.class);
                intent.putExtra(ShowPictureActivity.EXT_FILE, photoPath);
                startActivity(intent);

            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        photoPath = image.getAbsolutePath();
        return image;
    }
}
