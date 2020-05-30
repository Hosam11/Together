package com.example.together.Login_Signup;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.together.R;
import com.example.together.utils.HelperClass;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

//public class SignUpActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
//    private static final String apiKey="AIzaSyDzY_iKzUnC8sAocNoJPSupQrIOCCjpG7U";
//   ImageView profileImage;
//   ImageView dateImg;
//   TextView addImgTv;
//    EditText dateEt;
//    EditText addressEt;
//    EditText nameEt;
//    EditText emailEt;
//    EditText passEt;
//    RadioGroup genderRadioGroup;
//    RadioButton maleRadioBtn,femaleRadioBtn;
//    Button nextBtn;
//=======

public class SignUpActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener {

    private static final String apiKey = "AIzaSyDzY_iKzUnC8sAocNoJPSupQrIOCCjpG7U";
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    ImageView profileImage, dateImg;
    EditText dateEt; // 4
    EditText addressEt; //6
    EditText nameEt; // 1
    EditText emailEt; // 2
    EditText passEt; // 3
    TextView addImgTv;
    Button nextBtn;
    RadioGroup genderRadioGroup; // 5
    RadioButton maleRadioBtn, femaleRadioBtn;
    Calendar cldr;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    int CAMERA_REQUEST_CODE = 2;
    int GALLERY_REQUEST_CODE = 3;
    private String gender = HelperClass.MALE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();


        Places.initialize(getApplicationContext(), apiKey);
        addImgTv = findViewById(R.id.add_img_tv);
        dateImg = findViewById(R.id.date_img);
        nextBtn = findViewById(R.id.next_btn);
        nameEt = findViewById(R.id.name_et);
        emailEt = findViewById(R.id.email_et);
        passEt = findViewById(R.id.password_et);
        profileImage = findViewById(R.id.profile_image);
        dateEt = findViewById(R.id.date_et);
        addressEt = findViewById(R.id.address_et);
        genderRadioGroup = findViewById(R.id.gender_radio_group);
        maleRadioBtn = findViewById(R.id.male_radio_btn);
        femaleRadioBtn = findViewById(R.id.female_radio_btn);
        genderRadioGroup.setOnCheckedChangeListener(this);

        dateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        addressEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartAutoCompleteActivity();

            }
        });
        addImgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInterests = new Intent(getApplicationContext(), InterestsActivity.class);
                startActivity(toInterests);
            }
        });


    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.male_radio_btn) {
            gender = HelperClass.MALE;
            Toast.makeText(getApplicationContext(), "Male", Toast.LENGTH_SHORT).show();
        } else {
            gender = HelperClass.FEMALE;
            Toast.makeText(getApplicationContext(), "Female", Toast.LENGTH_SHORT).show();
        }

    }


    public void selectDate() {
        // TODO try todo comment
        cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(SignUpActivity.this, R.style.DialogTheme,
                (view, year1, monthOfYear, dayOfMonth) -> dateEt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
        cldr = Calendar.getInstance();
        cldr.set(2007, 11, 31);
        datePicker.getDatePicker().setMaxDate(cldr.getTimeInMillis());

        datePicker.show();
    }

    public void StartAutoCompleteActivity() {
        Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
                .setTypeFilter(TypeFilter.ADDRESS)
                .setCountries(Arrays.asList("EG"))
                .build(getApplicationContext());
        startActivityForResult(i, AUTOCOMPLETE_REQUEST_CODE);
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_REQUEST_CODE);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                addressEt.setText(place.getName());

            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // TODO: Handle the error.
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.i("TAG", status.getStatusMessage());
        } else if (resultCode == RESULT_CANCELED) {
        }

        /////

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profileImage.setImageBitmap(photo);
            }

            if (requestCode == GALLERY_REQUEST_CODE) {
                UCrop.of(data.getData(), Uri.fromFile(new File(this.getCacheDir(), "IMG_" + System.currentTimeMillis())))
                        .start(SignUpActivity.this);
            }
            if (requestCode == UCrop.REQUEST_CROP) {
                Uri imgUri = UCrop.getOutput(data);
                if (imgUri != null) {

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        profileImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }


        }

    }
}

