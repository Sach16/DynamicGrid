package com.skpissay.dynamicgrid;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by skpissay on 25/04/17.
 */
public class MainActivity extends AppCompatActivity {

    GridView mGridView;
    RelativeLayout mMainLayout;
    EditText mRowEt1;
    EditText mRowEt2;
    Button mSubmitBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpDynamicGrid();
    }

    private void setUpDynamicGrid() {
        mMainLayout = (RelativeLayout) findViewById(R.id.main);

        LinearLayout parent = new LinearLayout(this);

        parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.VERTICAL);


        LinearLayout innerLL = new LinearLayout(this);

        LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llparams.setMargins(0, 0, 0,15);
        innerLL.setLayoutParams(llparams);
        innerLL.setOrientation(LinearLayout.HORIZONTAL);

        mRowEt1 = createEditBox();
        mRowEt1.setHint(getResources().getString(R.string.rows_txt));

        mRowEt2 = createEditBox();
        mRowEt2.setHint(getResources().getString(R.string.columns_txt));

        mSubmitBt = createButton();
        mSubmitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int row = Integer.parseInt(mRowEt1.getText().toString());
                    int column = Integer.parseInt(mRowEt2.getText().toString());
                    int size = row * column;
                    List<Integer> list = new ArrayList<Integer>();
                    for (int i = 0; i < size; i++)
                        list.add(i);
                    if (list.size() > 0) {
                        mGridView.setNumColumns(column);
                        ArrayAdapter<Integer> lAdapter = new ArrayAdapter<Integer>(MainActivity.this, android.R.layout.simple_list_item_1, list) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView text = (TextView) view.findViewById(android.R.id.text1);
                                text.setGravity(Gravity.CENTER);
                                text.setBackgroundColor(getRandomColor());
                                text.setTextColor(Color.WHITE);
                                text.setTextSize(getResources().getDimension(R.dimen.text_size));
                                return view;
                            }
                        };
                        mGridView.setAdapter(lAdapter);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mGridView = new GridView(this);
        mGridView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        mGridView.setColumnWidth(mGridView.AUTO_FIT);
        mGridView.setStretchMode(mGridView.STRETCH_COLUMN_WIDTH);


        innerLL.addView(mRowEt1);
        innerLL.addView(mRowEt2);
        innerLL.addView(mSubmitBt);
        parent.addView(innerLL);
        parent.addView(mGridView);
        mMainLayout.addView(parent);
    }

    private EditText createEditBox() {
        EditText lEditText = new EditText(this);
        LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        llparams.weight = 1;
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(1);
        lEditText.setLayoutParams(llparams);
        lEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        lEditText.setSingleLine();
        lEditText.setMaxLines(1);
        lEditText.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.numeric)));
        lEditText.setHint(getResources().getString(R.string.rows_txt));
        lEditText.setHint(getResources().getString(R.string.rows_txt));
        lEditText.setFilters(FilterArray);
        return lEditText;
    }

    private Button createButton() {
        Button lButton = new Button(this);
        LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        llparams.weight = 1;
        lButton.setLayoutParams(llparams);
        lButton.setGravity(Gravity.CENTER);
        lButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        lButton.setTextColor(Color.WHITE);
        lButton.setText(getResources().getString(R.string.submit_txt));
        return lButton;
    }

    public static int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        return color;
    }
}
