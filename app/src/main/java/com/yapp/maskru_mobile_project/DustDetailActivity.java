package com.yapp.maskru_mobile_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cheoljin2 on 2017-02-25.
 */

public class DustDetailActivity extends AppCompatActivity {

    private TextView mDateTextView; // 날짜 받을 텍스트 뷰
    private TextView mPm25TextView;
    private TextView mPm10TextView;
    private TextView mO3TextView;

    SimpleDateFormat dateEN = new SimpleDateFormat("EEE MMM dd", new Locale("en", "US"));
    Date date = new Date();
    String dateStr = dateEN.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dustdetail);

        mDateTextView = (TextView) findViewById(R.id.date_text_view); // 날짜 연결
        mPm25TextView = (TextView) findViewById(R.id.pm25_text_view); // 초미세 먼지 수치 연결
        mPm10TextView = (TextView) findViewById(R.id.pm10_text_view); // 미세먼지 수치 연결
        mO3TextView = (TextView) findViewById(R.id.o3_text_view); // 오존 수치 연결

        mDateTextView.setText(dateStr); // 날짜를 텍스트뷰에 셋
    }
}
