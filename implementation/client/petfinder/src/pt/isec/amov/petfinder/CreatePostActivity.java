package pt.isec.amov.petfinder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import pt.isec.amov.petfinder.core.*;
import pt.isec.amov.petfinder.rest.CreatePostTask;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static pt.isec.amov.petfinder.MatchingPostsActivity.*;
import static pt.isec.amov.petfinder.SelectPostLocationActivity.RESULT_LATITUDE;
import static pt.isec.amov.petfinder.SelectPostLocationActivity.RESULT_LONGITUDE;

/**
 * Created by mgois on 30-12-2014.
 */
public class CreatePostActivity extends Activity {

    private final int REQ_GET_LOCATION = 1;
    private final int REQ_GET_PHOTO = 2;
    private final int ERROR_COLOR = Color.RED;

    String errNoLocation, errNoPhoto, errSavingPhoto, errMissingRequiredFields, errCreatingPost,successCreatingPost;
    RadioGroup rgType, rgSpecie, rgSize;
    CheckBox cbWhite, cbBlack, cbBrown, cbGrey, cbYellow;
    Button btnLocation, btnPhoto, btnMatchPost, btnCreatePost;
    TextView txtType, txtSpecie, txtColor, txtSize, txtLocation;
    ImageView ivPhoto;

    int defaultTextColor;
    boolean locationDefined = false;
    double lat, lng;
    String photoPath;
    private byte[] photoBytes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_activity);

        final PetFinderApp app = (PetFinderApp)getApplication();

        txtType = (TextView) findViewById(R.id.create_post_txtPostType);
        rgType = (RadioGroup) findViewById(R.id.create_post_rgType);
        txtSpecie = (TextView) findViewById(R.id.create_post_txtSpecie);
        rgSpecie = (RadioGroup) findViewById(R.id.create_post_rgSpecie);
        txtSize = (TextView) findViewById(R.id.create_post_txtSize);
        rgSize = (RadioGroup) findViewById(R.id.create_post_rgSize);
        txtColor = (TextView) findViewById(R.id.create_post_txtColor);
        cbWhite = (CheckBox) findViewById(R.id.create_post_cbColorWhite);
        cbBlack = (CheckBox) findViewById(R.id.create_post_cbColorBlack);
        cbBrown = (CheckBox) findViewById(R.id.create_post_cbColorBrown);
        cbGrey = (CheckBox) findViewById(R.id.create_post_cbColorGrey);
        cbYellow = (CheckBox) findViewById(R.id.create_post_cbColorYellow);
        txtLocation = (TextView) findViewById(R.id.create_post_txtLocation);
        btnLocation = (Button) findViewById(R.id.create_post_btnLocation);
        btnPhoto = (Button) findViewById(R.id.create_post_btnPhoto);
        ivPhoto = (ImageView) findViewById(R.id.create_post_ivPhoto);
        btnMatchPost = (Button) findViewById(R.id.create_post_btnMatchPost);
        btnCreatePost = (Button) findViewById(R.id.create_post_btnCreatePost);
        errNoLocation = app.getString(R.string.create_post_errNoLocation);
        errNoPhoto = app.getString(R.string.create_post_errNoPhoto);
        errSavingPhoto = app.getString(R.string.create_post_errSavingPhoto);
        errMissingRequiredFields = app.getString(R.string.create_post_errMissingRequiredFields);
        errCreatingPost = app.getString(R.string.create_post_errCreatingPost);
        successCreatingPost = app.getString(R.string.create_post_successCreatingPost);

                defaultTextColor = txtType.getCurrentTextColor();

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

        btnMatchPost.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(validRequiredFields()) {
                            launchMatchingPostActivity();
                            btnCreatePost.setEnabled(true);
                        } else {
                            showMessage(errMissingRequiredFields);
                        }
                    }
                }
        );

        btnCreatePost.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(validRequiredFields()) {


                            PostType type = getSelectedType();
                            Location location = doubleToLocation(lat,lng);
                            AnimalSpecie specie = getSelectedSpecie();
                            AnimalSize size = getSelectedSize();
                            Set<AnimalColor> color = getSelectedColors();

                            CreatePostTask.Parameters params = new CreatePostTask.Parameters(type,location,specie,size,color);

//                            if(photoPath != null) {
//                                List<byte[]> image = new ArrayList<byte[]>();
//                                image.add(photoFileToByte());
//                                params.setImage(image);
//                            }
                            if(photoBytes != null) {
                                List<byte[]> image = new ArrayList<byte[]>();
                                image.add(photoBytes);
                                params.setImage(image);
                            }

                            new CreatePostTask(app.getApiParams(), app.getToken().getAccessToken(), params) {

                                @Override
                                public void onTaskSuccess(final Boolean valid) {
                                    if(valid) {
                                        showMessage(successCreatingPost);
                                        launchMainActivity();
                                    } else {
                                        showMessage(errCreatingPost);
                                    }
                                }
                            }.execute();
                        } else {
                            showMessage(errMissingRequiredFields);
                        }
                    }
                }
        );
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private boolean validRequiredFields() {
        if(isTypeValid() && isSpecieValid() && isSizeValid() && isColorValid() && isLocationValid())
            return true;
        else
            return false;
    }

    private boolean isTypeValid() {
        if(getSelectedType() == null) {
            txtType.setTextColor(ERROR_COLOR);
            return false;
        }
        return true;
    }

    private boolean isSpecieValid() {
        if(getSelectedSpecie() == null) {
            txtSpecie.setTextColor(ERROR_COLOR);
            return false;
        }
        txtSpecie.setTextColor(defaultTextColor);
        return true;
    }

    private boolean isSizeValid() {
        if(getSelectedSize() == null) {
            txtSize.setTextColor(ERROR_COLOR);
            return false;
        }
        txtSpecie.setTextColor(defaultTextColor);
        return true;
    }

    private boolean isColorValid() {
        if(getSelectedColors().size() > 0) {
            txtColor.setTextColor(defaultTextColor);
            return true;
        }
        txtColor.setTextColor(ERROR_COLOR);
        return false;
    }

    private boolean isLocationValid() {
        if(locationDefined) {
            txtSpecie.setTextColor(defaultTextColor);
            return true;
        }
        txtLocation.setTextColor(ERROR_COLOR);
        return false;
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

    private EnumSet<AnimalColor> getSelectedColors() {
        EnumSet<AnimalColor> color = EnumSet.noneOf(AnimalColor.class);
        if(cbWhite.isChecked())
            color.add(AnimalColor.WHITE);
        if(cbBlack.isChecked())
            color.add(AnimalColor.BLACK);
        if(cbBrown.isChecked())
            color.add(AnimalColor.BROWN);
        if(cbGrey.isChecked())
            color.add(AnimalColor.GREY);
        if(cbYellow.isChecked())
            color.add(AnimalColor.YELLOW);
        return color;
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void launchMatchingPostActivity() {
        final Intent intent = new Intent(this,MatchingPostsActivity.class);
        intent.putExtra(PARAM_TYPE, getSelectedType());
        intent.putExtra(PARAM_SPECIE, getSelectedSpecie());
        intent.putExtra(PARAM_SIZE, getSelectedSize());
        intent.putExtra(PARAM_COLORS, getSelectedColors());
        intent.putExtra(PARAM_LOCATION, doubleToLocation(lat,lng));
        startActivity(intent);
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
                showMessage(errSavingPhoto);
            }

        }
    }

    private Location doubleToLocation(double lat, double lng) {
            return new Location(lat,lng);
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
                    lat = data.getDoubleExtra(RESULT_LATITUDE, 0);
                    lng = data.getDoubleExtra(RESULT_LONGITUDE, 0);
                    locationDefined = true;
                    txtLocation.setText("[" + lat + "," + lng + "]");
                } else {
                    showMessage(errNoLocation);
                }
                break;
            }
            case (REQ_GET_PHOTO) : {
                if (resultCode == RESULT_OK) {
                    //final Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
                    try {
                        final Bitmap bitmap = fixImageOrientation(new File(photoPath));
                        ivPhoto.setVisibility(View.VISIBLE);
                        ivPhoto.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        // TODO error handling
                    }

                } else {
                    showMessage(errNoPhoto);
                }
                break;
            }
            default:
                break;
        }
    }

    private Bitmap fixImageOrientation(final File file) throws IOException {
        final ExifInterface exif = new ExifInterface(file.getAbsolutePath());
        final int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        int rotate = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
        }

        if (rotate != 0) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Matrix mtx = new Matrix();
            mtx.postRotate(rotate);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // Store the compressed image bytes for further reuse
        photoBytes = baos.toByteArray();

        return BitmapFactory.decodeStream(new ByteArrayInputStream(photoBytes));
    }
}