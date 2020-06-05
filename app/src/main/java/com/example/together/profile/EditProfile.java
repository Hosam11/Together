package com.example.together.profile;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.User;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UserViewModel;
import com.example.together.view_model.UsersViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.yalantis.ucrop.UCrop;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EditProfile extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private static final String apiKey="AIzaSyDzY_iKzUnC8sAocNoJPSupQrIOCCjpG7U";
TextView changeImgTv;
    EditText emailEt;
    EditText passEt;
    EditText nameEt;
    EditText addressEt;
    EditText dateEt;
    ImageView profileImg;
    Bitmap userImgBitmap;
    ImageView dateImg;
    RadioGroup genderRadioGroup;
    RadioButton maleRadioBtn,femaleRadioBtn;
    Button saveChangesBtn;
    Calendar cldr;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    int CAMERA_REQUEST_CODE = 2;
    int GALLERY_REQUEST_CODE = 3;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    User receivedUser;
    UsersViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();
        changeImgTv=findViewById(R.id.change_img_tv);
        emailEt=findViewById(R.id.email_et);
        passEt=findViewById(R.id.password_et);
        nameEt=findViewById(R.id.name_et);
        addressEt=findViewById(R.id.address_et);
        profileImg=findViewById(R.id.profile_image);
        dateEt=findViewById(R.id.date_et);
        dateImg=findViewById(R.id.date_img);
        genderRadioGroup=findViewById(R.id.gender_radio_group);
        maleRadioBtn=findViewById(R.id.male_radio_btn);
        femaleRadioBtn=findViewById(R.id.female_radio_btn);
        genderRadioGroup.setOnCheckedChangeListener(this);
        saveChangesBtn=findViewById(R.id.save_btn);
        Places.initialize(getApplicationContext(), apiKey);
        addressEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartAutoCompleteActivity();

            }
        });
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        dateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        Storage storage=new Storage();

       // receivedUser = (User)getIntent().getSerializableExtra("userData");
        receivedUser=storage.getPassUser(this);

emailEt.setText(receivedUser.getEmail());
addressEt.setText(receivedUser.getAddress());
passEt.setText(receivedUser.getPassword());
nameEt.setText(receivedUser.getName());
userImgBitmap=HelperClass.decodeBase64(receivedUser.image);
profileImg.setImageBitmap(userImgBitmap);
dateEt.setText(receivedUser.getBirthDate());
        if(receivedUser.getGender().equalsIgnoreCase("female")){
            femaleRadioBtn.setChecked(true);

        }
        else { maleRadioBtn.setChecked(true); }

        userViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        changeImgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String radiovalue = ((RadioButton)findViewById(genderRadioGroup.getCheckedRadioButtonId())).getText().toString();
if(validateForm()) {
    CustomProgressDialog.getInstance(EditProfile.this).show();

    List<String> interests= receivedUser.getInterests();
receivedUser =new User(nameEt.getText().toString(),emailEt.getText().toString(),passEt.getText().toString(),dateEt.getText().toString(),
        addressEt.getText().toString(),radiovalue
        );
receivedUser.setInterests(interests);

    receivedUser.setImage(HelperClass.encodeTobase64(userImgBitmap));



save(receivedUser);




}
            }
        });





    }

    public void save(User user){

        if(HelperClass.checkInternetState(this)) {
            Storage storage = new Storage(getApplicationContext());
            userViewModel.updateUserProfile(storage.getId(), storage.getToken(), user).observe(this, new Observer<GeneralResponse>() {
                @Override
                public void onChanged(GeneralResponse generalResponse) {
                    if(generalResponse!=null) {

                        Toast.makeText(getApplicationContext(), generalResponse.response, Toast.LENGTH_LONG).show();
                        CustomProgressDialog.getInstance(EditProfile.this).cancel();

                        EditProfile.this.finish();
                    }
                    else {          CustomProgressDialog.getInstance(EditProfile.this).cancel();

                        HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, EditProfile.this);}

                }
            });
        }
        else {
            CustomProgressDialog.getInstance(EditProfile.this).cancel();

            HelperClass.showAlert("Error", HelperClass.checkYourCon, this);


        }

    }

    public void StartAutoCompleteActivity() {
        Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.LAT_LNG))
                .setTypeFilter(TypeFilter.ADDRESS)
                .setCountries(Arrays.asList("EG"))
                .build(getApplicationContext());
        startActivityForResult(i,AUTOCOMPLETE_REQUEST_CODE);

    }

    public void   selectDate(){

        cldr= Calendar.getInstance();
        int  day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(EditProfile.this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        dateEt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        cldr = Calendar.getInstance();
        cldr.set(2007, 11, 31);
        datePicker.getDatePicker().setMaxDate(cldr.getTimeInMillis());



        datePicker.show();


    }




    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                    }
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,GALLERY_REQUEST_CODE);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
            else
            {
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
                userImgBitmap = (Bitmap) data.getExtras().get("data");

                profileImg.setImageBitmap(userImgBitmap);


            }

            if (requestCode == GALLERY_REQUEST_CODE) {
                UCrop.of(data.getData(), Uri.fromFile(new File(this.getCacheDir(), "IMG_" + System.currentTimeMillis())))
                        .start(EditProfile.this);
            }
            if (requestCode == UCrop.REQUEST_CROP) {
                Uri imgUri = UCrop.getOutput(data);
                if (imgUri != null) {


                    try {
                        userImgBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);

                        profileImg.setImageBitmap(userImgBitmap);
                        //TODO
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }


        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
    private boolean validateForm() {
        boolean valid = true;

        String name = nameEt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameEt.setError("Required.");
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
        String pass = passEt.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            passEt.setError("Required.");
            valid = false;
        } else {
            passEt.setError(null);
        }
        String address = addressEt.getText().toString();
        if (TextUtils.isEmpty(address)) {
            addressEt.setError("Required.");
            valid = false;
        } else {
            addressEt.setError(null);
        }


        String endPoint = dateEt.getText().toString();
        if (TextUtils.isEmpty(endPoint)) {
            dateEt.setError("Required.");
            valid = false;
        } else {
            dateEt.setError(null);
        }





        return valid;
    }

}
