package com.yapp.maskru_mobile_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    //각 버튼 이벤트 걸어주기 위한 객체 생성
    private Button dustDetailBtn;
    private Button maskMapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dustDetailBtn = (Button) findViewById(R.id.dust_detail); //먼지정보 상세페이지 버튼 객체
        maskMapBtn = (Button) findViewById(R.id.mask_map); //마스크맵 페이지 버튼 객체

        //버튼 onclick 이벤트 연결 start
        dustDetailBtn.setOnClickListener(this);
        maskMapBtn.setOnClickListener(this);
        //버튼 onclick 이벤트 연결 end
    }

    /**
     * 해당 버튼을 클릭하면 그 버튼의 id를 비교하여 맞는 id의 문장 실행
     * onClick리스너의 메소드는 다양한 구현 방법이 있지만 switch문을 많이 사용
     * startActivity()에 intent 객체를 넣음으로서 해당 페이지로 이동
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dust_detail:
                Log.e("dust_detail", "dust_detail_page");
                Intent dustDetail = new Intent(this, DustDetailActivity.class);
                startActivity(dustDetail);
                break;
            case R.id.mask_map:
                Log.e("mask_map","mask_map_page");
                Intent maskMap = new Intent(this, TestActivity.class);
                startActivity(maskMap);
                break;
        }
    }
}
