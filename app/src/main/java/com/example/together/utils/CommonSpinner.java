package com.example.together.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.List;

import static com.example.together.utils.HelperClass.TAG;

public class CommonSpinner implements TextWatcher {

    private String spItemSelected;
    private BetterSpinner spinner;
    private EditText etOther;
    private boolean isLocation = false;

    /**
     * @param sp:       spinner object from the activity
     * @param context   context that spinner attach to
     * @param arrayList list that will fill in array adapter
     */
    public CommonSpinner(BetterSpinner sp, Context context, List<String> arrayList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_list_item_1, arrayList);
        spinner = sp;
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapter);

        spinner.addTextChangedListener(this);
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }

    public EditText getEtOther() {
        return etOther;
    }

    public void setEdOther(EditText edOther) {
        this.etOther = edOther;
    }

    public String getSpItemSelected() {
        if (spItemSelected == null != isLocation) {
            spinner.setError("Required");
        }

        return spItemSelected;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i(TAG, "beforeTextChanged: ");
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i(TAG, "onTextChanged: ");
        spinner.setError(null);
    }

    @Override
    public void afterTextChanged(Editable s) {
        spItemSelected = spinner.getText().toString();

        if (spItemSelected.equals("other") && etOther != null) {
            Log.i(TAG, "CommonSpinner -- afterTextChanged: spItemSelected make ot visibile");
            etOther.setVisibility(View.VISIBLE);
            // other
        } else if (!spItemSelected.equals("other") && etOther != null) {
            Log.i(TAG, "CommonSpinner -- afterTextChanged: spItemSelected make it disappear");

            etOther.setVisibility(View.GONE);
        }
        Log.i(TAG, "CommonSpinner -- afterTextChanged: sp_interest >> " + spItemSelected);
    }
}
