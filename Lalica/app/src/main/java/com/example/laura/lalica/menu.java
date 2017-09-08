package com.example.laura.lalica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class menu extends AppCompatActivity {

    ImageButton edit,filter,share,rotate;
    private static final int MY_PERMISSION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        filter = (ImageButton)findViewById(R.id.filter);
        edit=(ImageButton)findViewById(R.id.edit);
        share=(ImageButton)findViewById(R.id.share);
        rotate=(ImageButton)findViewById(R.id.rotate);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(menu.this, R.anim.bounce);
                rotate.startAnimation(myAnim);
                Intent intent3 = new Intent(menu.this,Main4Activity.class);
                startActivity(intent3);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(menu.this, R.anim.bounce);
                share.startAnimation(myAnim);
                Toast.makeText(menu.this," This project doesn't share yet ! ",Toast.LENGTH_LONG).show()
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(menu.this, R.anim.bounce);
                filter.startAnimation(myAnim);
                Intent intent1 = new Intent(menu.this,MainActivity.class);
                startActivity(intent1);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(menu.this, R.anim.bounce);
                edit.startAnimation(myAnim);
                Intent intent2 = new Intent(menu.this,Edit.class);
                startActivity(intent2);
            }
        });
        if(ContextCompat.checkSelfPermission(menu.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if( ActivityCompat.shouldShowRequestPermissionRationale(menu.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(menu.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);

            }else{
                ActivityCompat.requestPermissions(menu.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MY_PERMISSION_REQUEST:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(menu.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){

                    }else{
                        Toast.makeText(this,"My permission granted ",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
        }
    }

}
