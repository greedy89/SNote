package com.seno.snote.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seno.snote.R;
import com.seno.snote.service.ApiClient;
import com.seno.snote.service.ApiInterfaces;
import com.seno.snote.serviceModel.noteModel.TblNote;
import com.seno.snote.serviceModel.noteModel.Update;
import com.seno.snote.utility.ImageUtil;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahData extends AppCompatActivity {
    private TblNote data ;
    private EditText etTItle,etIsi;
    private ImageView imgData;
    private TextView txtdateCreate,txtdateChange;
    private String key,categories,dateChange;
    private Button btnKamera,btnSubmit;
    Bitmap bitmap;
    static  byte[] bitmapx;

    public Uri fileUri;
    private int CAMERA_REQUEST = 100;
//    private int Gallary_REQUEST = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        data = (TblNote)getIntent().getExtras().getSerializable("data");
        key = getIntent().getExtras().getString("type");
        categories =getIntent().getExtras().getString("categories");
        etTItle = findViewById(R.id.etTitle);
        etIsi = findViewById(R.id.etisi);
        txtdateCreate = findViewById(R.id.dateCreate);
        txtdateChange= findViewById(R.id.dateChanged);
        btnSubmit = findViewById(R.id.btnSubmit);
        imgData = findViewById(R.id.imgAdd);
        btnKamera = findViewById(R.id.btnCamera);

        btnKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        SimpleDateFormat sdfPic = new SimpleDateFormat("yyyy-MM-dd");
        dateChange = sdfPic.format(new Date()).replace(" ", "");
        if(data!=null&&key.equalsIgnoreCase("update")){
            etIsi.setText(data.getCotents());
            etTItle.setText(data.getTitle());
            txtdateCreate.setText(data.getDateCreated());
            txtdateChange.setText(data.getDateChanged());
            ImageUtil.displayImage(imgData,data.getImg(),null);
        }else if(key.equalsIgnoreCase("save")){
            txtdateCreate.setText(dateChange);
            txtdateChange.setText(dateChange);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            imgData.setImageBitmap(bitmap);
            bitmapx = baos.toByteArray();
        }
    }

    private void Camerapermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(TambahData.this
                ,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(TambahData.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(TambahData.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    void openCamera() {
        File directory = new File(
                Environment.getExternalStorageDirectory(), "iCollector" + "/" + getPackageName());
        try {
            SimpleDateFormat sdfPic = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String currentDateandTime = sdfPic.format(new Date()).replace(" ", "");
            File imagesFolder = new File(directory.getAbsolutePath());
            imagesFolder.mkdirs();

            String fname = "IMAGE_" + currentDateandTime + ".jpg";
            File file = new File(imagesFolder, fname);
            fileUri = Uri.fromFile(file);
            Intent cameraIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public RequestBody toRequestBody(String value) {
        if (value == null) {
            value = "";
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public MultipartBody.Part toBodyImage (byte[] byteArray, String filename) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("img", filename + ".jpg", requestFile);

        return body;

    }

    ProgressDialog progressDialog;

    public void save() {
        if(bitmapx == null && key.equalsIgnoreCase("update")){
            bitmap =((BitmapDrawable) imgData.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            bitmapx = baos.toByteArray();
        }else if( bitmapx == null && key.equalsIgnoreCase("save")){
            imgData.setImageResource(R.drawable.default_image);
            bitmap =((BitmapDrawable) imgData.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            bitmapx = baos.toByteArray();
        }

        ApiInterfaces apiInterface = ApiClient.getClient().create(ApiInterfaces.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bitmapx);
        progressDialog = new ProgressDialog(TambahData.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();

        if(key.equalsIgnoreCase("update")) {
            Call<Update> updateService = apiInterface.update(
                    toRequestBody(data.getId()),
                    toRequestBody(etTItle.getText().toString()),
                    toRequestBody(etIsi.getText().toString()),
                    toRequestBody(txtdateCreate.getText().toString()),
                    toRequestBody(dateChange),
                    toRequestBody(categories),
                    toBodyImage(bitmapx, data.getTitle() + data.getId()));

            updateService.enqueue(new Callback<Update>() {
                @Override
                public void onResponse(Call<Update> call, Response<Update> response) {
                    progressDialog.dismiss();
                    if (response != null) {
                        Log.d("Test", response.message());
//                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                        bitmapx = null;
                        setResult(40);
                        finish();

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    //   Toast.makeText(getApplicationContext(), "Nothing happen", Toast.LENGTH_LONG).show();
                    // resultNya = false;
                }

                @Override
                public void onFailure(Call<Update> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Koneksi bermasalah", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();


                }
            });

        }else if(key.equalsIgnoreCase("save")){
            Call<Update> updateService = apiInterface.addData(
                    toRequestBody(etTItle.getText().toString()),
                    toRequestBody(etIsi.getText().toString()),
                    toRequestBody(dateChange),
                    toRequestBody(dateChange),
                    toRequestBody(categories),
                    toBodyImage(bitmapx,etTItle.getText().toString()));
            updateService.enqueue(new Callback<Update>() {
                @Override
                public void onResponse(Call<Update> call, Response<Update> response) {
                    progressDialog.dismiss();
                    if (response != null) {
//                        Log.d("Test", response.message());
//                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                        bitmapx = null;
                        setResult(40);
                        finish();

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Update> call, Throwable t) {

                }
            });
        }



    }
}
