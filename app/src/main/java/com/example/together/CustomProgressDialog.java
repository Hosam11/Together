package com.example.together;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

public class CustomProgressDialog extends AlertDialog {
    private static CustomProgressDialog single_instance = null;

    public static CustomProgressDialog getInstance(Context con){
        if (single_instance == null)
        {
            single_instance = new CustomProgressDialog(con);}

        return single_instance;


    }

    public CustomProgressDialog(Context context) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.custom_progress);
    }

    @Override
    public void cancel() {
        super.cancel();
        single_instance=null;
    }
}