package com.hj.flutter_androiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;


public class FlutterCustomActivity extends FlutterActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    public void configureFlutterEngine(@NonNull @NotNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        MethodChannel nativeChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), "hj.flutter.dev/homePageTouch");
        nativeChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull @org.jetbrains.annotations.NotNull MethodCall call, @NonNull @org.jetbrains.annotations.NotNull MethodChannel.Result result) {

                switch (call.method) {
                    case "homePageTouch":

                        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(FlutterCustomActivity.this);
                        alertdialogbuilder.setMessage(call.argument("param"));
                        alertdialogbuilder.setPositiveButton("确定", null);
                        alertdialogbuilder.setNeutralButton("取消", null);
                        final AlertDialog alertdialog1 = alertdialogbuilder.create();
                        alertdialog1.show();
                        result.success("com.hj.flutter_androidDemo");
                        break;
                    default:
                        result.notImplemented();
                        break;
                }
            }

        });

        MethodChannel nativeChannel1 = new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(), "hj.flutter.dev/pushToNative");
        nativeChannel1.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull @org.jetbrains.annotations.NotNull MethodCall call, @NonNull @org.jetbrains.annotations.NotNull MethodChannel.Result result) {

                switch (call.method) {
                    case "pushToNative":
                        String a = call.argument("param").toString();
                        Intent intent = new Intent(FlutterCustomActivity.this,MainActivity.class);
                        intent.putExtra("fromFlutterText", a);
                        startActivity(intent);

                        break;
                    default:
                        result.notImplemented();
                        break;
                }
            }
        });
    }

    /// 方式2：重写下面的方法
    public static class MyNewEngineIntentBuilder extends NewEngineIntentBuilder {
        MyNewEngineIntentBuilder(@NonNull Class<? extends FlutterActivity> activityClass) {
            super(activityClass);
        }
    }
    //接着则可以按提示自行提供withNewEngine()返回用子类构建的`NewEngineIntentBuilder`
    public static FlutterActivity.NewEngineIntentBuilder withNewEngine() {
        return new MyNewEngineIntentBuilder(FlutterCustomActivity.class);
    }

    //类似的，如果有使用CachedEngine，也可能继承`CachedEngineIntentBuilder`调用被`protected`修饰的Constructor来返回用子类构建的`CachedEngineIntentBuilder`
    public static FlutterActivity.CachedEngineIntentBuilder withCachedEngine(@NonNull String cachedEngineId) {
        return new MyCachedEngineIntentBuilder(FlutterCustomActivity.class, cachedEngineId);
    }
    public static class MyCachedEngineIntentBuilder extends CachedEngineIntentBuilder{
        protected MyCachedEngineIntentBuilder(@NonNull Class<? extends FlutterActivity> activityClass, @NonNull String engineId) {
            super(activityClass, engineId);
        }
    }
}
