package com.hj.flutter_androiddemo;

import androidx.appcompat.app.AppCompatActivity;
import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.FlutterEngineGroup;
import io.flutter.embedding.engine.dart.DartExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity implements Serializable {

    /// 多个flutter入口，如本例子
    public FlutterEngineGroup group;

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

        ///基本使用
//        FlutterEngine flutterEngine = new FlutterEngine(this);
//        ///初始路由
////        flutterEngine.getNavigationChannel().setInitialRoute("/homePage");
//
//        flutterEngine.getDartExecutor().executeDartEntrypoint(
//                DartExecutor.DartEntrypoint.createDefault()
//        );
//
//        // Cache the FlutterEngine to be used by FlutterActivity.
//        FlutterEngineCache
//                .getInstance()
//                .put("hj_engine_id", flutterEngine);


        ///group使用
        group = new FlutterEngineGroup(this);

        DartExecutor.DartEntrypoint dartEntrypoint1 = new DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(),null
        );
        FlutterEngine firstEngine = group.createAndRunEngine(this, dartEntrypoint1);
        FlutterEngineCache.getInstance().put("hjfirst_engine_id", firstEngine);

        DartExecutor.DartEntrypoint dartEntrypoint2 = new DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(), "otherMain"
        );
        FlutterEngine secondEngine = group.createAndRunEngine(this, dartEntrypoint2);
        FlutterEngineCache.getInstance().put("hjsecond_engine_id", secondEngine);
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
        //Intent intent = FlutterCustomActivity.withNewEngine().build(this);
        //重新设置一下目标class到子类FlutterCustomActivity.class
        //不这么搞我一直交互失败 方式1  方式2：在FlutterCustomActivity重写方法
        //intent.setClass(this, FlutterCustomActivity.class);
        //startActivity(intent);

//        startActivity(
//                FlutterCustomActivity
//                 .withCachedEngine("hj_engine_id")
//                 .build(this)
//        );

        startActivity(
                FlutterCustomActivity
                        .withCachedEngine("hjfirst_engine_id")
                        .build(this)
        );

    }

    void pushHomePage() {
//        Intent intent = FlutterCustomActivity.withNewEngine().initialRoute("/homePage").build(this);
        //重新设置一下目标class到子类FlutterCustomActivity.class
        //不这么搞我一直交互失败  方式1 方式2：在FlutterCustomActivity重写方法
        //intent.setClass(this, FlutterCustomActivity.class);
//        startActivity(intent);

//        startActivity(
//                FlutterCustomActivity
//                        .withCachedEngine("hj_engine_id")
//                        .build(this)
//        );

        startActivity(
                FlutterCustomActivity
                        .withCachedEngine("hjsecond_engine_id")
                        .build(this)
        );
    }
}