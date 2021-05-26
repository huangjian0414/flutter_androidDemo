package com.hj.flutter_androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) this.findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Auto-generated method stub
                btnAction();
            }
        });

        TextView textView = (TextView) this.findViewById(R.id.textView2);
        Intent intent = getIntent();
        String param = intent.getStringExtra("fromFlutterText");
        if (param != null){
            textView.setText("flutter传了参数：你按了"+param+"下");
        }
    }

    void btnAction() {
        Intent intent = getIntent();
        String param = intent.getStringExtra("fromFlutterText");
        if (param == null){
            defaultPush();
        }else {
            pushHomePage();
        }
    }

    void defaultPush() {
        Intent intent = FlutterCustomActivity.withNewEngine().build(this);
        //重新设置一下目标class到子类FlutterCustomActivity.class
        //不这么搞我一直交互失败 方式1  方式2：在FlutterCustomActivity重写方法
        //intent.setClass(this, FlutterCustomActivity.class);
        startActivity(intent);
    }

    void pushHomePage() {
        Intent intent = FlutterCustomActivity.withNewEngine().initialRoute("/homePage").build(this);
        //重新设置一下目标class到子类FlutterCustomActivity.class
        //不这么搞我一直交互失败  方式1 方式2：在FlutterCustomActivity重写方法
        //intent.setClass(this, FlutterCustomActivity.class);
        startActivity(intent);

    }
}