package pt.isec.amov.petfinder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import pt.isec.amov.petfinder.core.*;
import pt.isec.amov.petfinder.entities.Post;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;

/**
 * Created by mgois on 30-12-2014.
 */
public class CreatePostActivity extends Activity {

    private final int REQ_GET_LOCATION = 1;
    private final int REQ_GET_PHOTO = 2;
    private final String errNoLocation = "No location defined!";
    private final String errNoPhoto = "Photo wasn't taken!";
    private final String errSavingPhoto = "An error occurred while saving the image";

    RadioGroup rgType, rgSpecie, rgSize;
    CheckBox cbWhite, cbBlack, cbBrown, cbGrey, cbYellow;
    Button btnLocation, btnPhoto, btnMatchOrCreate;
    TextView txtLocation;
    ImageView ivPhoto;

    double lat, lng;
    String photoPath;

    Post post = new Post();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_activity);

        rgType = (RadioGroup) findViewById(R.id.create_post_rgType);
        rgSpecie = (RadioGroup) findViewById(R.id.create_post_rgSpecie);
        rgSize = (RadioGroup) findViewById(R.id.create_post_rgSize);
        cbWhite = (CheckBox) findViewById(R.id.create_post_cbColorWhite);
        cbBlack = (CheckBox) findViewById(R.id.create_post_cbColorBlack);
        cbBrown = (CheckBox) findViewById(R.id.create_post_cbColorBrown);
        cbGrey = (CheckBox) findViewById(R.id.create_post_cbColorGrey);
        cbYellow = (CheckBox) findViewById(R.id.create_post_cbColorYellow);
        txtLocation = (TextView) findViewById(R.id.create_post_txtLocation);
        btnLocation = (Button) findViewById(R.id.create_post_btnLocation);
        btnPhoto = (Button) findViewById(R.id.create_post_btnPhoto);
        ivPhoto = (ImageView) findViewById(R.id.create_post_ivPhoto);
        btnMatchOrCreate = (Button) findViewById(R.id.create_post_btnMatchOrCreate);

        btnPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchTakePhotoActivityForResult();
                    }
                }
        );

        btnLocation.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchSetLocationActivityForResult();
                    }
                }
        );

        btnMatchOrCreate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnMatchOrCreate.setText("Create");

                    }
                }
        );
    }

    private void showErrorMessage(String errMessage) {
        Toast.makeText(getApplicationContext(), errMessage, Toast.LENGTH_LONG).show();
    }

    private void buildPostObject() {
        post.setType(getSelectedType());
        post.setLocation(doubleToLatLng(lat, lng));
        post.getMetadata().setSize(getSelectedSize());
        post.getMetadata().setSpecie(getSelectedSpecie());
        setSelectedColors(post.getMetadata().getColors());
        post.setImages(photoFileToByte());
    }

    private PostType getSelectedType() {
        switch(rgType.getCheckedRadioButtonId()) {
            case R.id.create_post_rbLost:
                return PostType.LOST;
            case R.id.create_post_rbFound:
                return PostType.FOUND;
            default:
                return null;
        }
    }

    private AnimalSpecie getSelectedSpecie() {
        switch (rgSpecie.getCheckedRadioButtonId()) {
            case R.id.create_post_rbDog:
                return AnimalSpecie.DOG;
            case R.id.create_post_rbCat:
                return AnimalSpecie.CAT;
            default:
                return null;
        }
    }

    private AnimalSize getSelectedSize() {
        switch(rgSize.getCheckedRadioButtonId()) {
            case R.id.create_post_rbSmallSize:
                return AnimalSize.SMALL;
            case R.id.create_post_rbMediumSize:
                return AnimalSize.MEDIUM;
            case R.id.create_post_rbLargeSize:
                return AnimalSize.LARGE;
            default:
                return null;
        }
    }

    private void setSelectedColors(EnumSet<AnimalColor> colors) {
        if(cbWhite.isChecked())
            colors.add(AnimalColor.WHITE);
        if(cbBlack.isChecked())
            colors.add(AnimalColor.BLACK);
        if(cbBrown.isChecked())
            colors.add(AnimalColor.BROWN);
        if(cbGrey.isChecked())
            colors.add(AnimalColor.GREY);
        if(cbYellow.isChecked())
            colors.add(AnimalColor.YELLOW);
    }

    private void launchMatchingPostActivity() {
        final Intent intent = new Intent(this,MatchingPostsActivity.class);
    }

    private void launchSetLocationActivityForResult() {
        final Intent intent = new Intent(this,SelectPostLocationActivity.class);
        startActivityForResult(intent, REQ_GET_LOCATION);
    }

    private void launchTakePhotoActivityForResult() {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                final File photo = createImageFile();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                startActivityForResult(intent, REQ_GET_PHOTO);
            } catch (final IOException ex) {
                showErrorMessage(errSavingPhoto);
            }

        }
    }

    private Location doubleToLatLng(Double lat, Double lng) {
        if(!lat.isNaN() && !lng.isNaN())
            return new Location(lat,lng);
        else
            return null;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);

        photoPath = image.getAbsolutePath();
        return image;
    }

    private byte[] photoFileToByte() {
        byte[] photo;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        photo = baos.toByteArray();
        return photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQ_GET_LOCATION) : {
                if(resultCode == Activity.RESULT_OK) {
                    lat = data.getDoubleExtra("lat", 0);
                    lng = data.getDoubleExtra("lng", 0);
                    txtLocation.setText("[" + lat + "," + lng + "]");
                } else {
                    showErrorMessage(errNoLocation);
                }
                break;
            }
            case (REQ_GET_PHOTO) : {
                if (requestCode == REQ_GET_PHOTO) {
                    if (resultCode == RESULT_OK) {
                        ivPhoto.setVisibility(View.VISIBLE);
                        ivPhoto.setImageBitmap(BitmapFactory.decodeFile(photoPath));
                    } else {
                        showErrorMessage(errNoPhoto);
                    }
                }
                break;
            }
            default:
                break;
        }
    }
}