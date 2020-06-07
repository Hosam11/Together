package com.example.together.group_screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.together.BottomNavigationView;
import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.utils.DownLoadImage;
import com.example.together.utils.HelperClass;
import com.example.together.utils.UploadImageToFireBase;
import com.example.together.view_model.GroupViewModel;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

import static com.example.together.utils.HelperClass.TAG;

public class EditGroupInfo extends AppCompatActivity implements DownLoadImage {

    Group group;
    EditText etGpName;
    EditText etGpDesc;
    Button submitBtn;
    ImageView ivGroupImg;
    TextView tvEditImage;

    GroupViewModel groupViewModel;
    String groupName;
    String groupDesc;
    Storage groupStorage;
    Storage userStorage;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        getSupportActionBar().hide();
        etGpName = findViewById(R.id.et_edit_group_name);
        etGpDesc = findViewById(R.id.et_edit_group_desc);
        submitBtn = findViewById(R.id.btn_edit_group);
        ivGroupImg = findViewById(R.id.iv_edit_group);
        tvEditImage = findViewById(R.id.tv_edit_image);
    /*    group = new Group(2, "loc", 5,
                "android", "fro begginer",
                3, "free", "begginer", "java");

*/
        groupStorage = new Storage();
        userStorage = new Storage(this);

        group = groupStorage.getGroup(this);
        etGpName.setText(group.getGroupName());
        etGpDesc.setText(group.getGroupDesc());

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        submitBtn.setOnClickListener(v -> {
            if (vaildGroupData()) {
                editGroupInfo();
            }
        });
        if (group.getImage() != null) {
            Glide.with(this).load(group.getImage()).into(ivGroupImg);
        }

        tvEditImage.setOnClickListener(v -> HelperClass.newSelectImage(this));

    }

    private void editGroupInfo() {

        group.setGroupName(groupName);
        group.setGroupDesc(groupDesc);

        if (imageUri != null) {
            CustomProgressDialog.getInstance(this).show();
            UploadImageToFireBase imgToFireBase = new UploadImageToFireBase(this);
            imgToFireBase.uploadFile(imageUri);

        } else {
            CustomProgressDialog.getInstance(this).show();

            groupViewModel.updateGroupInfo(group.getAdminID(), userStorage.getId(),
                    group, userStorage.getToken()).observe(this, this::editGroupObserv);
        }
//        group.setImage();
        // group id
    }

    private void editGroupObserv(GeneralResponse generalResponse) {
        Log.i(TAG, "EditGroupInfo --  editGroupObserv: res >>  " + generalResponse);
        CustomProgressDialog.getInstance(this).cancel();
        Toast.makeText(this, generalResponse.response, Toast.LENGTH_SHORT).show();
        Intent goHome = new Intent(this, BottomNavigationView.class);
        goHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goHome);

    }

    private boolean vaildGroupData() {
        Log.i(TAG, getLocalClassName() + " -- vaildGroupData: ");
        boolean vaild = true;

        groupName = etGpName.getText().toString();
        groupDesc = etGpDesc.getText().toString();

        // Group Name
        if (TextUtils.isEmpty(groupName)) {
            etGpName.setError("Required");
            vaild = false;
        } else {
            etGpName.setError(null);
        }
        // Group Desc
        if (TextUtils.isEmpty(groupDesc)) {
            etGpDesc.setError("Required");
            vaild = false;
        } else {
            etGpDesc.setError(null);
        }

        return vaild;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Log.i(TAG, "EditGroupInfo -- onActivityResult: ");
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
                    ivGroupImg.setImageBitmap(thumb_Bitmab);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onFinishedDownloadListener(String imgUrl) {
        group.setImage(imgUrl);
        groupViewModel.updateGroupInfo(group.getAdminID(), userStorage.getId(),
                group, userStorage.getToken()).observe(this, this::editGroupObserv);

    }
}
