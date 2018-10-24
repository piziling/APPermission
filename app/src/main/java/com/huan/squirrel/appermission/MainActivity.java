package com.huan.squirrel.appermission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huan.squirrel.appermission.util.PermissionManager;

import static com.huan.squirrel.appermission.util.PermissionManager.SYSTEM_ALERT_WINDOW_CODE;

public class MainActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*********************     需要权限的功能要再次检查权限是否可用    start     ******************************************/
        context = this;
        final Button btn_permission = findViewById(R.id.btn_permission);

        final String[] PERMISSIONS_ALL = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        btn_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionManager.chekPermission(context, PERMISSIONS_ALL, new PermissionManager.OnCheckPermissionListener() {
                    @Override
                    public void onPassed() {
                        Toast.makeText(context,"权限已经全部开启",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNoPassed() {
                        Toast.makeText(context,"部分权限未开启",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        /*************************   需要权限的功能要再次检查权限是否可用  end      **************************************/
    }

    /************************  app第一次打开页面设置app运行必要的权限    start     ***************************************/
    @Override
    protected void onResume() {
        super.onResume();
        PermissionManager.getInstance(this).initPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(SYSTEM_ALERT_WINDOW_CODE==requestCode){
            PermissionManager.getInstance(this).showProcess();
        }else{
            PermissionManager.getInstance(this).initPermission();
        }
    }

    //处理权限申请回调(写在Activity中)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.getInstance(this).initPermission();
    }
    /**********************     app第一次打开页面设置app运行必要的权限    start   ***************************************/

}
