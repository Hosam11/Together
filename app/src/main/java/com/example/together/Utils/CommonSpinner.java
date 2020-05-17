package com.example.together.Utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import static com.example.together.Utils.HelperClass.TAG;

public class CommonSpinner implements AdapterView.OnItemSelectedListener {

    private String spItemSelected;


    /**
     * @param spinner:  spinner object from the activity
     * @param context   context that spinner attach to
     * @param arrayList list that will fill in array adapter
     */

    public CommonSpinner(Spinner spinner, Context context, List<String> arrayList) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, arrayList);

        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(this);
    }

    public String getSpItemSelected() {
        return spItemSelected;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        spItemSelected = (String) parent.getItemAtPosition(pos);
        Log.i(TAG, "AddGroup -- onItemSelected: sp_interest >> " + parent.getItemAtPosition(pos));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
