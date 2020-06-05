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

import com.example.together.R;
import com.example.together.data.model.FixedDBValues;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.single_group.GroupViewPager;
import com.example.together.utils.CommonSpinner;
import com.example.together.utils.HelperClass;
import com.example.together.utils.TestApis;
import com.example.together.view_model.GroupViewModel;
import com.weiwangcn.betterspinner.library.BetterSpinner;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.together.utils.HelperClass.ERROR_MISSING_FILEDS;
import static com.example.together.utils.HelperClass.TAG;
import static com.example.together.utils.HelperClass.showAlert;

public class AddGroup extends AppCompatActivity {


    private static final int CAMERA_PERMISSION_CODE = 7;
    private final List<String> levels = new ArrayList<>();
    //
    CommonSpinner interestSpinner;
    CommonSpinner locationSpinner;
    CommonSpinner levelsSpinner;
    // Edit Texts
    EditText etGroupName;
    EditText etGroupDesc;
    EditText etHiddenOther;
    EditText etMaxMembersNumber;
    EditText etGpDuration;
    EditText etErrorMember;
    EditText etErrorDuration;


    // TextView
//    TextView tvGroupMaxMembers;G
//    TextView tvGroupDuration;
    TextView tvAddImg;
    TextView tvDurationRight;
    TextView tvMemberNumberRight;
    // ImageView
    ImageView groupImg;
    GroupViewModel groupViewModel;
    //
    Bitmap groupImgBitmap;

    boolean isEnterCrop;

    int CAMERA_REQUEST_CODE = 11;
    int GALLERY_REQUEST_CODE = 12;
    String gpName;
    String gpDesc;
    int maxMemberNumber;
    int duration;
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
        // etHiddenOther = findViewById(R.id.ed_other_interest);
        // TextsViews
        // tvGroupMaxMembers = findViewById(R.id.tv_member_number);
        //tvGroupDuration = findViewById(R.id.tv_duration_week);


        tvAddImg = findViewById(R.id.tv_add_image);
        //
        groupImg = findViewById(R.id.iv_group_img);

        groupImg.setOnClickListener(v -> {
            Intent testApis = new Intent(this, TestApis.class);
            startActivity(testApis);
        });

        FixedDBValues dbValues = new FixedDBValues();

        interests = new ArrayList<>(dbValues.getInterests().values());
        locations = new ArrayList<>();

        // TODO get interest form backend
        /*interests.add("android");
        interests.add("ios");
        interests.add("web desing");
        interests.add("php");
        interests.add("react native");
        interests.add("other");
        */

        locations.add("egypt");
        locations.add("german");
        locations.add("spain");

        levels.add("beginner");
        levels.add("intermediate");
        levels.add("expert");


        interestSpinner = new CommonSpinner(spInterests, this, interests);
        interestSpinner.setEdOther(etHiddenOther);
        locationSpinner = new CommonSpinner(spLocations, this, locations);
        levelsSpinner = new CommonSpinner(spLevels, this, levels);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        findViewById(R.id.btn_create_group).setOnClickListener(v -> createGroup());

        tvDurationRight = findViewById(R.id.tv_duration_rigth_btn);

        tvMemberNumberRight = findViewById(R.id.tv_member_right_btn);
        //  increment decrement counter
        tvDurationRight.setOnClickListener(v -> increment(etGpDuration, true));

        findViewById(R.id.tv_duration_left_btn).setOnClickListener(v -> decrement(etGpDuration));

        tvMemberNumberRight.setOnClickListener(v -> increment(etMaxMembersNumber, false));

        findViewById(R.id.tv_member_left_btn).setOnClickListener(v -> decrement(etMaxMembersNumber));


        tvAddImg.setOnClickListener(v -> HelperClass.selectImage(this,
                CAMERA_PERMISSION_CODE, CAMERA_REQUEST_CODE, GALLERY_REQUEST_CODE));

    }

  /*  public void chooseImage(View view) {
        Log.i(TAG, "chooseImage: ");
        Log.i(TAG, "onCreate: interest item selected >> " + interestSpinner.getSpItemSelected());
        Log.i(TAG, "onCreate: location item selected >> " + locationSpinner.getSpItemSelected());
        Log.i(TAG, "onCreate: level item selected >> " + levelsSpinner.getSpItemSelected());
    }*/

    public void createGroup() {
        /*String gpName = etGroupName.getText().toString();
        String gpDesc = etGroupDesc.getText().toString();
        // TODO will get it from maps that return it form db table contain interests
        int gpMembers = Integer.parseInt(etMaxMembersNumber.getText().toString());
        int gpDuration = Integer.parseInt(etGpDuration.getText().toString());
        */
        String gpInterest = interestSpinner.getSpItemSelected();
        String gpLevel = levelsSpinner.getSpItemSelected();
        String gpLocation = locationSpinner.getSpItemSelected();

        Storage storage = new Storage(this);
        // FixedDBValues dbValues = new FixedDBValues();
        // TODO interest id leave it for now
        if (gpLevel == null || gpInterest == null) {
            showAlert(ERROR_MISSING_FILEDS, this);
        } else {
            if (vaildGroupData()) {

                String img = null;
                if (groupImgBitmap != null) {
                    Log.i(TAG, getLocalClassName() + " -- createGroup: imgNotNull");
                    img = HelperClass.encodeTobase64(groupImgBitmap);
                    Log.i(TAG, getLocalClassName() + " -- createGroup: imgLength >>||>> " + img.length());
                }

                Storage s = new Storage(this);
                Log.i(TAG, getLocalClassName() + " -- createGroup: token" + s.getToken());
                Group group = new Group(
                        storage.getId(), gpLocation, img,
                        maxMemberNumber, duration, gpName,
                        gpDesc, HelperClass.FREE, gpLevel, gpInterest);
                String token = storage.getToken();
                groupViewModel.createGroup(group, token).observe(this, this::observCreateGroup);
            }

        }

    }

    private boolean vaildGroupData() {
        boolean vaild = true;

        gpName = etGroupName.getText().toString();
        gpDesc = etGroupDesc.getText().toString();

        String gpMembersValue = etMaxMembersNumber.getText().toString();
        String gpDurationValue = etGpDuration.getText().toString();

      /*  maxMemberNumber = 0;
        if (!gpMembersValue.isEmpty()) {*/
        maxMemberNumber = Integer.parseInt(gpMembersValue);
//        }


//        duration = 0;
//        if (!gpDurationValue.isEmpty()) {
        duration = Integer.parseInt(gpDurationValue);
//        }

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
        Log.i(TAG, getLocalClassName() + " -- observCreateGroup: generalRes.res >> "
                + generalRes.response);

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
            showAlert(generalRes.response, this);
        }
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
        if (resultCode == RESULT_OK) {

            groupImgBitmap = getImage(requestCode, data);
            if (isEnterCrop) {
                if (groupImgBitmap != null) {
                    Log.i(TAG, "onActivityResult: groupImgBitmap != null");
                }
            }
        }
    }

    private Bitmap getImage(int requestCode, @Nullable Intent data) {

        Bitmap imgBitmap = null;
        isEnterCrop = false;
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            groupImg.setImageBitmap(photo);
            imgBitmap = photo;
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            UCrop.of(data.getData(), Uri.fromFile(new File(this.getCacheDir(),
                    "IMG_" + System.currentTimeMillis())))
                    .start(this);
        }
        if (requestCode == UCrop.REQUEST_CROP) {
            Uri imgUri = UCrop.getOutput(data);
            if (imgUri != null) {
                try {
                    isEnterCrop = true;
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                    groupImg.setImageBitmap(bitmap);
                    imgBitmap = bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imgBitmap;
    }
}


