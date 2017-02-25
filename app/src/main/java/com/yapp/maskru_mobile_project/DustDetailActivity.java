package com.yapp.maskru_mobile_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.yapp.maskru_mobile_project.SharedData;

/**
 * Created by cheoljin2 on 2017-02-25.
 */

public class DustDetailActivity extends AppCompatActivity {

    private TextView mDateTextView; // 날짜 받을 텍스트 뷰
    private TextView mPm25TextView;
    private TextView mPm10TextView;
    private TextView mO3TextView;

    private ImageView mPm25ImageView;
    private ImageView mPm10ImageView;
    private ImageView mO3ImageView;

    private ImageView mTomorrowImageView;
    private TextView mLevelTextView;
    private TextView mDescriptionTextView;

    SharedData mSharedData;

    SimpleDateFormat dateEN = new SimpleDateFormat("EEE MMM dd", new Locale("en", "US"));
    Date date = new Date();
    String dateStr = dateEN.format(date);

    String pm25_data;
    String pm10_data;
    String o3_data;
    String level_text;
    String description_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dustdetail);

        mDateTextView = (TextView) findViewById(R.id.date_text_view); // 날짜 연결
        mPm25TextView = (TextView) findViewById(R.id.pm25_text_view); // 초미세 먼지 텍스트뷰 연결
        mPm10TextView = (TextView) findViewById(R.id.pm10_text_view); // 미세먼지 텍스트뷰 연결
        mO3TextView = (TextView) findViewById(R.id.o3_text_view); // 오존 텍스트뷰 연결

        mPm25ImageView = (ImageView) findViewById(R.id.pm25_image_view); // 초미세 먼지 이미지뷰 연결
        mPm10ImageView = (ImageView) findViewById(R.id.pm10_image_view); // 미세먼지 이미지뷰 연결
        mO3ImageView = (ImageView) findViewById(R.id.o3_image_view); // 오존 이미지뷰 연결

        mTomorrowImageView = (ImageView) findViewById(R.id.tomorrow_image_view); // 내일 예보 이미지뷰 연결
        mLevelTextView = (TextView) findViewById(R.id.level_text_view); // 예보 레벨 텍스트뷰 연결
        mDescriptionTextView = (TextView) findViewById(R.id.description_text_view); // 상세설명 텍스트뷰 연결

        mDateTextView.setText(dateStr); // 날짜를 텍스트뷰에 셋

        mPm25TextView.setText(pm25_data); // 초미세먼지를 텍스트뷰에 셋
        mPm10TextView.setText(pm10_data); // 미세먼지를 텍스트뷰에 셋
        mO3TextView.setText(o3_data); // 오존을 텍스트뷰에 셋

        mLevelTextView.setText(level_text); // 예보레벨을 텍스트뷰에 셋
        mDescriptionTextView.setText(description_text); // 상세설명을 텍스트뷰에 셋

    }
}

