package com.example.together.group_screens;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.FixedDBValues;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.Interest;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.single_group.GroupViewPager;
import com.example.together.utils.CommonSpinner;
import com.example.together.utils.DownLoadImage;
import com.example.together.utils.HelperClass;
import com.example.together.utils.TestApis;
import com.example.together.utils.UploadImageToFireBase;
import com.example.together.view_model.GroupViewModel;
import com.example.together.view_model.UsersViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;

import static com.example.together.utils.HelperClass.ALERT;
import static com.example.together.utils.HelperClass.TAG;
import static com.example.together.utils.HelperClass.showAlert;

public class CreateGroup extends AppCompatActivity implements DownLoadImage {


    private static final int CAMERA_PERMISSION_CODE = 7;
    private final List<String> levels = new ArrayList<>();
    //
    CommonSpinner interestSpinner;
    CommonSpinner locationSpinner;
    CommonSpinner levelsSpinner;
    // Edit Texts
    EditText etGroupName;
    EditText etGroupDesc;
    EditText etMaxMembersNumber;
    EditText etGpDuration;
    EditText etErrorMember;
    EditText etErrorDuration;


    // TextView

    TextView tvAddImg;
    TextView tvDurationRight;
    TextView tvMemberNumberRight;
    // ImageView
    ImageView groupImg;
    GroupViewModel groupViewModel;
    //

    // Form Strings
    String gpName;
    String gpDesc;
    int maxMemberNumber;
    int duration;
    String gpInterest;
    String gpLevel;
    String gpLocation;
    // ToDo 1- uri
    Uri imageUri;
    Storage storage;

    List<Interest> interestList;

    UsersViewModel usersViewModel;
    // Spinners Objects
    private BetterSpinner spInterests;
    private BetterSpinner spLocations;
    private BetterSpinner spLevels;
    // List Objects
    private List<String> interests;
    private List<String> locations;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group2);

        getSupportActionBar().hide();


        spInterests = findViewById(R.id.sp_interest);
        spLocations = findViewById(R.id.sp_locations);
        spLevels = findViewById(R.id.sp_required_Level);

        // EditTexts
        etGroupName = findViewById(R.id.et_group_name);
        etGroupDesc = findViewById(R.id.ed_group_desc);
        etMaxMembersNumber = findViewById(R.id.et_member_number);
        etGpDuration = findViewById(R.id.et_duration_week);
        etErrorMember = findViewById(R.id.et_show_error_member);
        etErrorDuration = findViewById(R.id.et_show_error_duration);
        tvAddImg = findViewById(R.id.tv_add_image);
        groupImg = findViewById(R.id.iv_group_img);
        groupImg.setOnClickListener(v -> {
            // TODO reomve that when finishing
            Intent testApis = new Intent(this, TestApis.class);
            startActivity(testApis);
        });


        FixedDBValues dbValues = new FixedDBValues();

        interests = new ArrayList<>();
        locations = new ArrayList<>();


        locations.add("egypt");
        locations.add("german");
        locations.add("spain");

        levels.add("Beginner");
        levels.add("Intermediate");
        levels.add("Expert");

        storage = new Storage(this);

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        CustomProgressDialog.getInstance(this).show();
        usersViewModel.getAllInterests().observe(this,
                this::getInterestsObservable);


        locationSpinner = new CommonSpinner(spLocations, this, locations);
        levelsSpinner = new CommonSpinner(spLevels, this, levels);
        locationSpinner.setLocation(true);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);


        findViewById(R.id.btn_create_group).setOnClickListener(v -> {
            if (validGroupData()) {
                createGroup();
            }
        });

        tvDurationRight = findViewById(R.id.tv_duration_rigth_btn);

        tvMemberNumberRight = findViewById(R.id.tv_member_right_btn);
        //  increment decrement counter
        tvDurationRight.setOnClickListener(v -> increment(etGpDuration, true));

        findViewById(R.id.tv_duration_left_btn).setOnClickListener(v -> decrement(etGpDuration));

        tvMemberNumberRight.setOnClickListener(v -> increment(etMaxMembersNumber, false));

        findViewById(R.id.tv_member_left_btn).setOnClickListener(v -> decrement(etMaxMembersNumber));
        tvAddImg.setOnClickListener(v -> HelperClass.newSelectImage(this));

    }

    private void getInterestsObservable(ArrayList<Interest> interestsListReturn) {
        for (Interest i : interestsListReturn) {
            interests.add(i.getName());
        }
        interestSpinner = new CommonSpinner(spInterests, this, interests);
        CustomProgressDialog.getInstance(this).cancel();
    }


    public void createGroup() {
        // FirstShow >> canceled in observCreateGroup()
        CustomProgressDialog.getInstance(this).show();
        if (imageUri != null) {
            UploadImageToFireBase imgToFireBase = new UploadImageToFireBase(this);
            if (HelperClass.checkInternetState(this)) {

                imgToFireBase.uploadFile(imageUri);
            } else {
                HelperClass.showAlert("Error", HelperClass.checkYourCon, this);
                CustomProgressDialog.getInstance(this).cancel();
            }

        } else {
            // create group
            Group group = new Group(
                    storage.getId(), gpLocation, null,
                    maxMemberNumber, duration, gpName,
                    gpDesc, HelperClass.FREE, gpLevel, gpInterest);

            String token = storage.getToken();

            Log.i(TAG, getLocalClassName() + " -- createGroup: mGroup >> "
                    + group.toString());

            //CustomProgressDialog.getInstance(this).show();
            if (HelperClass.checkInternetState(this)) {
                // CustomProgressDialog.getInstance(this).show();
                groupViewModel.createGroup(group, token)
                        .observe(this, this::observCreateGroup);
            } else {
                HelperClass.showAlert(ALERT, HelperClass.checkYourCon, this);
                CustomProgressDialog.getInstance(this).cancel();
            }
        }

    }

    private boolean validGroupData() {
        Log.i(TAG, getLocalClassName() + " -- vaildGroupData: ");
        boolean vaild = true;
        String gpMembersValue = etMaxMembersNumber.getText().toString();
        String gpDurationValue = etGpDuration.getText().toString();

        gpName = etGroupName.getText().toString();
        gpDesc = etGroupDesc.getText().toString();

        gpInterest = interestSpinner.getSpItemSelected();
        gpLevel = levelsSpinner.getSpItemSelected();
        gpLocation = locationSpinner.getSpItemSelected();

        maxMemberNumber = Integer.parseInt(gpMembersValue);
        duration = Integer.parseInt(gpDurationValue);

        // Group Name
        if (TextUtils.isEmpty(gpName)) {
            etGroupName.setError("Required");
            vaild = false;
        } else {
            etGroupName.setError(null);
        }
        // Group Desc
        if (TextUtils.isEmpty(gpDesc)) {
            etGroupDesc.setError("Required");
            vaild = false;
        } else {
            etGroupDesc.setError(null);
        }
        // interests
        if (interestSpinner.getSpItemSelected() == null) {
            spInterests.setError("Required");
            vaild = false;
        } else {
            spInterests.setError(null);
        }

        // level
        if (levelsSpinner.getSpItemSelected() == null) {
            spLevels.setError("Required");
            vaild = false;
        } else {
            spLevels.setError(null);
        }
        // Group Member
        if (maxMemberNumber <= 1) {
            etErrorMember.setError("atleast 2 members");
            vaild = false;
        } else {
            etErrorMember.setError(null);

        }

        // Group Duration
        if (duration == 0) {
            etErrorDuration.setError("atleast 1 weak");
            vaild = false;
        } else if (duration >= 13) {
            etErrorDuration.setError("max numbers 12 weak");
            vaild = false;
        } else {
            etErrorDuration.setError(null);
        }

        return vaild;
    }

    private void observCreateGroup(GeneralResponse generalRes) {
        // Log.i(TAG, getLocalClassName() + " -- observCreateGroup: generalRes.res >> "
        // + generalRes.response);

        if (generalRes.response.equals(HelperClass.CREATE_GROUP_SUCCESS)) {
            Log.i(TAG, "AddGroup -- observCreateGroup: from if Statment");
            // 1- Go To Group Screens
//            userViewModel.clearCreateGroupRes();
            Toast.makeText(this, generalRes.response, Toast.LENGTH_SHORT).show();
            Intent goToSingleGroup = new Intent(this, GroupViewPager.class);
//            startActivity(goToSingleGroup);
            finish();
        } else {
            Log.i(TAG, "AddGroup -- observCreateGroup: from else statmet");
//            showAlert(generalRes.response, this);
            showAlert(ALERT,generalRes.response, this);
        }
        // canceled
        CustomProgressDialog.getInstance(this).cancel();

    }


    public void decrement(EditText tv) {
        int decrement = Integer.parseInt(tv.getText().toString());
//        Log.i(TAG, "AddGroup -- onCreate: decrement" + decrement);
        if (decrement != 0) {
            decrement--;
//            Log.i(TAG, "AddGroup -- onCreate: IF decrement" + decrement);
            tv.setText(String.valueOf(decrement));
        }
    }

    public void increment(EditText tv, boolean isDuration) {
        int increment = Integer.parseInt(tv.getText().toString());
//        Log.i(TAG, "AddGroup -- onCreate: increment" + increment);
        if (isDuration & increment == 12) {

        } else {
            increment++;
        }
        tv.setText(String.valueOf(increment));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PERMISSION_CODE);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {


                this.imageUri = result.getUri();
                File thumm_filepath = new File(imageUri.getPath());
                try {
                    Bitmap thumb_Bitmab = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(70)
                            .compressToBitmap(thumm_filepath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumb_Bitmab.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    groupImg.setImageBitmap(thumb_Bitmab);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    @Override
    public void onFinishedDownloadListener(String imgUrl) {
        Log.i(TAG, "onFinishedDownloadListner: ");
        Group group = new Group(
                storage.getId(), gpLocation, imgUrl,
                maxMemberNumber, duration, gpName,
                gpDesc, HelperClass.FREE, gpLevel, gpInterest);
        String token = storage.getToken();
        groupViewModel.createGroup(group, token).observe(this, this::observCreateGroup);

    }
}


