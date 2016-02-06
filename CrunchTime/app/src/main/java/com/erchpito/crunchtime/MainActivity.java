package com.erchpito.crunchtime;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MyActivity";

    private boolean toCalorie;
    private boolean updatingCalorie;
    private boolean updatingAmount;
    private int selectedExercise;
    private int amountRep;
    private int amountCal;
    private int[] output;
    private int[] exerciseImages;
    private String selectedUnit;
    private String[] exercises;
    private String[] units;

    private ExerciseConversion converter;
    private ExerciseArrayAdapter listAdapter;

    private EditText editAmount;
    private EditText editCalorie;
    private ListView exerciseList;
    private Spinner exerciseSpinner;
    private ImageView link;
    private ImageView selectedImage;
    private TextView unitAmount;
    private TextView unitCalorie;

    private class ExerciseArrayAdapter extends ArrayAdapter<String> {

        private Context mContext;

        public ExerciseArrayAdapter(Context context) {
            super(context, R.layout.exercise_list_view, exercises);
            mContext = context;
        }

        @Override
        public int getCount() {
            return exercises.length - 1;
        }

        @Override
        public String getItem(int position) {
            if (selectedExercise <= position) {
                position++;
            }
            return Integer.toString(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.exercise_list_view, parent, false);
            }

            TextView otherExercise = (TextView) convertView.findViewById(R.id.list_view_exercise);
            TextView otherAmount = (TextView) convertView.findViewById(R.id.list_view_amount);
            ImageView otherImage = (ImageView) convertView.findViewById(R.id.list_view_image);

            position = Integer.parseInt(getItem(position));

            String render = Integer.toString(output[position]) + " ";
            if (output[position] == 1) {
                render += units[position].substring(0, units[position].length() - 1);
            } else {
                render += units[position];
            }
            otherAmount.setText(render);
            otherExercise.setText(exercises[position]);
//            otherImage.setImageResource(exerciseImages[position]);

//            ColorMatrix matrix = new ColorMatrix();
//            matrix.setSaturation(0);
//
//            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//            otherImage.setColorFilter(filter);

            return convertView;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toCalorie = true;
        updatingAmount = false;
        updatingCalorie = false;
        selectedExercise = 0;
        amountRep = 100;
        exercises = getResources().getStringArray(R.array.exercises_array);
        units = getResources().getStringArray(R.array.units_array);
        selectedUnit = units[selectedExercise];
        exerciseImages = new int[]{
                R.drawable.pushup,
                R.drawable.situp,
                R.drawable.squats,
                R.drawable.leglift,
                R.drawable.plank,
                R.drawable.jumpingjacks,
                R.drawable.pullup,
                R.drawable.cycling,
                R.drawable.walking,
                R.drawable.jogging,
                R.drawable.swimming,
                R.drawable.stairclimbing
        };

        converter = new ExerciseConversion();

        exerciseSpinner = (Spinner) findViewById(R.id.exercise);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.exercises_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(spinnerAdapter);
        exerciseSpinner.setOnItemSelectedListener(this);

        exerciseList = (ListView) findViewById(R.id.other_exercises);
        listAdapter = new ExerciseArrayAdapter(this);
        exerciseList.setAdapter(listAdapter);

        link = (ImageView) findViewById(R.id.link);
        selectedImage = (ImageView) findViewById(R.id.selected_image);

        editAmount = (EditText) findViewById(R.id.edit_amount);
        editCalorie = (EditText) findViewById(R.id.edit_calorie);
        unitAmount = (TextView) findViewById(R.id.unit_amount);
        unitCalorie = (TextView) findViewById(R.id.unit_calorie);

        output = converter.output(selectedExercise, amountRep, true, true);
        updateAmounts();

        editAmount.setText(Integer.toString(amountRep), TextView.BufferType.EDITABLE);
        editAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0 && Integer.parseInt(s.toString()) == 1) {
                    unitAmount.setText(selectedUnit.substring(0, selectedUnit.length() - 1));
                } else {
                    unitAmount.setText(selectedUnit);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!updatingAmount && s.length() != 0) {
                    toCalorie = true;
                    amountRep = Integer.parseInt(editAmount.getText().toString());
                    convert();
                }
            }
        });
        editCalorie.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!updatingCalorie && s.length() != 0) {
                    toCalorie = false;
                    amountCal = Integer.parseInt(editCalorie.getText().toString());
                    convert();
                }
            }
        });
        listAdapter.notifyDataSetChanged();
    }

    public void convert() {
        output = converter.output(selectedExercise, (toCalorie) ? amountRep : amountCal, toCalorie, true);
        Log.d(TAG, Arrays.toString(output));
        updateAmounts();
    }

    public void updateAmounts() {
        if (toCalorie) {
            updatingCalorie = true;
            editCalorie.setText(Integer.toString(output[selectedExercise]), TextView.BufferType.EDITABLE);
            updatingCalorie = false;
        } else {
            updatingAmount = true;
            editAmount.setText(Integer.toString(output[selectedExercise]), TextView.BufferType.EDITABLE);
            updatingAmount = false;
        }
        listAdapter.notifyDataSetChanged();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        selectedExercise = pos;
        selectedUnit = units[selectedExercise];
        unitAmount.setText(selectedUnit);
        convert();

//        selectedImage.setImageResource(exerciseImages[selectedExercise]);
    }

    public void onNothingSelected(AdapterView<?> parent) { }
}
