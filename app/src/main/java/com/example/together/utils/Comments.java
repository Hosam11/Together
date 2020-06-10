package com.example.together.utils;

public class Comments {


    // TODO
    //  1-
    //

/*   FixMe -- freqqment code
// craete group finished

    if (HelperClass.checkInternetState(this)) {
        // CustomProgressDialog.getInstance(this).show();

    } else {
        //   CustomProgressDialog.getInstance(this).cancel();
        HelperClass.showAlert("Error", HelperClass.checkYourCon, this);

    }
    */



      /*  // method for bitmap to base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 10, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    // method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
*/

    // FIXME - when gone from login activity call api again why ??


    // FixME - Replace marginStart with marginleft
// tab layout 7oda
    //                Intent i = new Intent(context, ToDoListMain.class);
//                context.startActivity(i);
//                return null;
//                return new  ToDoListFragment();


     /*   <!-- <FrameLayout
    android:layout_marginBottom="16dp"
    android:layout_gravity="center_horizontal"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">

         <ImageView
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:padding="6dp"
    android:src="@drawable/group"/>

         <ImageView
    android:src="@drawable/frame"
    android:layout_width="200dp"
    android:layout_height="200dp" />
         </FrameLayout>-->*/
    /*

    private void loginObservable(UserLogin userLogin) {
        userViewModel.login(userLogin).observe(this, loginRes -> {
            if (loginRes.isConFailed()) {
                showErrorAlert("Failed connect to host!", this);
            } else {
                if (loginRes.isSuccess()) {
                    Log.i(TAG, "LoginActivity -- signUpObservable: go to home");
                    Storage storage = new Storage(this);
                    storage.saveUserData(loginRes.getToken(), loginRes.getId());
                    Intent intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);

                } else { // not valid user
                    Log.i(TAG, "LoginActivity -- signUpObservable: not valid ");
                    showErrorAlert(loginRes.getResponse(), this);
                }
            }

        });
    }
     */
    /*
        public static final String SING_UP_FAILED = "this email is exist";

    public static final String LOGIN_SUCCESS = "log in success";
    public static final String LOGIN_EMAIL_FAIILED = "this mail not registered";
    public static final String LOGIN_pass_FAIILED = "password not correct";
     */
    /**
     * beacause after each click on sign up i remove the observable so
     * when he listen for livedata found ot filled with old value
     * so i need to clear it to ensure that code will call signUp call again and
     * recevie fres response
     */
  /*  public void clearSignUpRes() {
        signUpRes = null;
    }

    */
    /*

      private MutableLiveData makeRequest(M reqBody) {
//        Call<LoginResponse> calLog = apiInterface.login(new UserLogin("", ""));
        MutableLiveData liveData = new MutableLiveData();

        if (reqBody instanceof User) {
            User user = (User) reqBody;
            Call<SignUpResponse> calSign = apiInterface.signUp(user);
            CustomCall customCallBack = new CustomCall(calSign);
            liveData = customCallBack.makeRequest();

        } else if (reqBody instanceof UserLogin) {
            UserLogin userLogin = (UserLogin) reqBody;
            Call<LoginResponse> loginCall = apiInterface.login(userLogin);
            CustomCall customCall = new CustomCall(loginCall);
            liveData = customCall.makeRequest();
        }
        return liveData;
    }

    class CustomCall<L> {

        private Call<L> call;

        public CustomCall(Call<L> call) {
            this.call = call;
        }


        public MutableLiveData makeRequest() {

            MutableLiveData liveData = new MutableLiveData();

            call.enqueue(new Callback<L>() {
                @Override
                public void onResponse(Call<L> call, Response<L> res) {
                    if (res.body() instanceof SignUpResponse) {
                        liveData.setValue(((SignUpResponse) res.body()).response);
                    }

                }

                @Override
                public void onFailure(Call<L> call, Throwable t) {

                }
            });

            return liveData;

        }

    }


     */

/*        log = userViewModel.signUp(user).hasObservers() ? "has" : "not has";
        Log.i(TAG, "userSignUpObservable: 2- " + log);*/

 /*   public void signUp(User user) {
        if (signUpRes == null) {
            signUpRes = userRepo.signUp(user);
        }
    }

    public MutableLiveData<String> getSignUpRes() {
        if (signUpRes == null) {
            signUpRes = new MutableLiveData<>();
        }
        return signUpRes;
    }*/

    //
//    public UserViewModel() {
////        signUpRes = userRepo.signUp();
//    }

}
