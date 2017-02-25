package com.yapp.maskru_mobile_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.app.ProgressDialog;
import android.media.Image;
import android.os.AsyncTask;

import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    //각 버튼 이벤트 걸어주기 위한 객체 생성
    private Button mMapButton;
    private  Button mIntroButton;
    private Button mAirDetailButton;
    private TextView mDustDataTextVIew1;
    private TextView mDustDataTextVIew2;
    ArrayList<String> resultList1;
    ArrayList<String> resultList2;
    String addres1;
    String addres2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SimpleDateFormat dateEN = new SimpleDateFormat("EEE MMM dd", new Locale("en", "US"));
        Date date = new Date();
        String dateStr = dateEN.format(date);
        TextView dateNow;
        dateNow = (TextView) findViewById(R.id.dateNow);
        dateNow.setText(dateStr);

        addres1 = "http://openapi.seoul.go.kr:8088/6643434c5879616539397572784569/xml/ForecastWarningMinuteParticleOfDustService/1/1/";
        mDustDataTextVIew1 = (TextView) findViewById(R.id.dust_data1); //초미세먼지

        addres2 = "http://openapi.seoul.go.kr:8088/626644517379616535365472796474/xml/ForecastWarningUltrafineParticleOfDustService/1/5/";
        mDustDataTextVIew2 = (TextView) findViewById(R.id.dust_data2); //미세먼지
        new NetworkAsyncTask().execute(addres1, addres2);

        mAirDetailButton = (Button)findViewById(R.id.detail_button);
        mAirDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(HomeActivity.this, DustDetailActivity.class);
                startActivity(k);
            }
        });

        mMapButton = (Button)findViewById(R.id.map_button);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, MaskMapActivity.class);
                startActivity(i);
            }
        });

        mIntroButton = (Button)findViewById(R.id.guide_button);
//        mIntroButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent j = new Intent(HomeActivity.this, GuideActivity.class);
//                startActivity(j);
//            }
//        });
    }

    public class Wrapper {
        public String result1;
        public String result2;
    }

    class NetworkAsyncTask extends AsyncTask<String, Integer, Wrapper> {
        public final static String TAG = "NetworkAsyncTask";
        public final static int TIME_OUT = 10000;

        ProgressDialog progressDlg;
        String address1, address2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(HomeActivity.this, "Wait", "Downloading Mask Index Level...");
        }

        @Override
        protected Wrapper doInBackground(String... strings) {
            address1 = strings[0];
            address2 = strings[1];
            StringBuilder resultBuilder = new StringBuilder();
            Wrapper wrapper = new Wrapper();

            try {
                URL url = new URL(address1);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        for (String line = br.readLine(); line != null; line = br.readLine()) {
                            resultBuilder.append(line + '\n');
                        }

                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                Toast.makeText(HomeActivity.this, "Malformed URL", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            wrapper.result1 = resultBuilder.toString();

            try {
                URL url = new URL(address2);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        resultBuilder.delete(0, resultBuilder.length());
                        for (String line = br.readLine(); line != null; line = br.readLine()) {
                            resultBuilder.append(line + '\n');
                        }

                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                Toast.makeText(HomeActivity.this, "Malformed URL", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            wrapper.result2 = resultBuilder.toString();

            return wrapper;
        }

        @Override
        protected void onPostExecute(Wrapper wrapper) {
            Dust1Parser parser1 = new Dust1Parser();
            Dust2Parser parser2 = new Dust2Parser();
//          어댑터에 이전에 보여준 데이터가 있을 경우 클리어

            resultList1 = parser1.parse(wrapper.result1);
            resultList2 = parser2.parse(wrapper.result2);

            Log.i("result1", wrapper.result1);
            Log.i("result2", wrapper.result2);

            for (String st1 : resultList1)
                mDustDataTextVIew1.setText(st1);


            for (String st2 : resultList2)
                mDustDataTextVIew2.setText(st2);

//          리스트뷰에 연결되어 있는 어댑터에 parsing 결과 ArrayList 를 추가
            progressDlg.dismiss();
        }

    }
}