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
import android.text.TextUtils;
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

import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.User;
import com.example.together.data.storage.Storage;
import com.example.together.utils.DownLoadImage;
import com.example.together.utils.HelperClass;
import com.example.together.utils.UploadImageToFireBase;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.theartofdev.edmodo.cropper.CropImage;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

import id.zelory.compressor.Compressor;


public class SignUpActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener, DownLoadImage {

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
    Bitmap userImgBitmap;
    Uri imageUri;
    // values
    String uName;
    String uEmail;
    String uPass;
    String birthDate;
    String address;
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
        nextBtn.setOnClickListener(v -> nextScreen());


    }

    private void nextScreen() {
        if (validateForm()) {
            uName = nameEt.getText().toString();
            uEmail = emailEt.getText().toString().trim();
            uPass = passEt.getText().toString();
            birthDate = dateEt.getText().toString();
            address = addressEt.getText().toString();

            if (imageUri != null) {
                Log.i(HelperClass.TAG, "nextScreen: Img not Null");
                CustomProgressDialog.getInstance(this).show();
                UploadImageToFireBase imgToFireBase = new UploadImageToFireBase(this);
                imgToFireBase.uploadFile(imageUri);

            } else {
                Log.i(HelperClass.TAG, "nextScreen: Img  Null");

                User user = new User(uName, uEmail, uPass, birthDate, address, gender);

                Storage storage = new Storage();
                storage.savePassedUser(user, this);
                Log.i(HelperClass.TAG, "SignUpActivity -- createAccount: #click#" +
                        " user  >> " + user);
                Intent toInterests = new Intent(getApplicationContext(),
                        InterestsActivity.class);
                startActivity(toInterests);
            }
        }

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
       /*final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
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
        builder.show();*/
        HelperClass.newSelectImage(this);
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
      /*  if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                userImgBitmap = photo;
                this.imageUri = data.getData();
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
                        this.imageUri = imgUri;
                        userImgBitmap = bitmap;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }*/

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                File thumm_filepath = new File(imageUri.getPath());
                try {
                    Bitmap thumb_Bitmab = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(70)
                            .compressToBitmap(thumm_filepath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumb_Bitmab.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    profileImage.setImageBitmap(thumb_Bitmab);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private boolean validateForm() {

        boolean valid = true;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        String name = nameEt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameEt.setError("Required.");
            valid = false;
        } else if (name.length() <= 2) {
            nameEt.setError("The name must be at least 3 characters.");
            valid = false;
        } else {
            nameEt.setError(null);
        }

        String email = emailEt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Required.");
            valid = false;
        } else {
            emailEt.setError(null);
        }


        if (!email.matches(emailPattern)) {
            emailEt.setError("Enter a valid e-mail!");
            valid = false;

        } else {
            emailEt.setError(null);

        }
        String pass = passEt.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            passEt.setError("Required.");
            valid = false;
        } else {
            passEt.setError(null);
        }
        String address = addressEt.getText().toString();
      /*  if (TextUtils.isEmpty(address)) {
            addressEt.setError("Required.");
            valid = false;
        } else {
            addressEt.setError(null);
        }
*/

        String endPoint = dateEt.getText().toString();
        if (TextUtils.isEmpty(endPoint)) {
            dateEt.setError("Required.");
            valid = false;
        } else {
            dateEt.setError(null);
        }


        return valid;
    }

    @Override
    public void onFinishedDownloadListener(String imgUrl) {
        User user = new User(uName, uEmail, uPass, birthDate, address, gender);
        user.setImage(imgUrl);
        Storage storage = new Storage();
        storage.savePassedUser(user, this);
        Log.i(HelperClass.TAG, "SignUpActivity -- createAccount: #click#" +
                " user  >> " + user);
        Log.i(HelperClass.TAG, "SignUpActivity -- onFinishedDownloadListener: " + user);
        Intent toInterests = new Intent(getApplicationContext(),
                InterestsActivity.class);
        CustomProgressDialog.getInstance(this).cancel();

        startActivity(toInterests);
    }
}

