package com.example.together.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.List;

import static com.example.together.utils.HelperClass.TAG;

public class CommonSpinner implements TextWatcher {

    private String spItemSelected;
    private BetterSpinner spinner;

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

    public String getSpItemSelected() {
        return spItemSelected;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        spItemSelected = spinner.getText().toString();
        Log.i(TAG, "AddGroup -- afterTextChanged: sp_interest >> " + spItemSelected);
    }
}
