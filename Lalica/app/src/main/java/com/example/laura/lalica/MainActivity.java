package com.example.laura.lalica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button load,save,share;
    String currentImage = "";
    ImageView view1;
    private static final int RESULT_LOAD_IMAGE = 0;
    ViewPager vp;
    RelativeLayout rel ;
    com.example.laura.lalica.adapter ada;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rel=(RelativeLayout)findViewById(R.id.lay);
        vp=(ViewPager)findViewById(R.id.vp);
        ada = new adapter(getSupportFragmentManager());
        vp.setAdapter(ada);
        view1=(ImageView)findViewById(R.id.imageView);
        load=(Button)findViewById(R.id.load);
        save=(Button)findViewById(R.id.save);
        share=(Button)findViewById(R.id.share);

        save.setEnabled(false);
        share.setEnabled(false);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
                load.startAnimation(myAnim);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
                save.setEnabled(true);
                share.setEnabled(true);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
                save.startAnimation(myAnim);
                View content = findViewById(R.id.lay);
                Bitmap bitmap = getScreenShot(content);
                currentImage = "image"+System.currentTimeMillis()+".jpg";
                store(bitmap,currentImage);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
                share.startAnimation(myAnim);
                shareImage(currentImage);
            }
        });

    }

    public void onClick(View view){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(view1.getWidth(), view1.getHeight());
        rel.setLayoutParams(params);
        view1.clearColorFilter();
        int k = view.getId();
        if(k==R.id.button){
            view1.setColorFilter(getResources().getColor(R.color.colorPrimary));
        }else if(k==R.id.button2){
            view1.setColorFilter(getResources().getColor(R.color.colorPrimary2));
        }else if(k==R.id.button3){
            view1.setColorFilter(getResources().getColor(R.color.colorPrimary3));
        }else if(k==R.id.button4){
            view1.setColorFilter(getResources().getColor(R.color.colorPrimary4));
        }else if(k==R.id.button5){
            view1.setColorFilter(getResources().getColor(R.color.colorPrimary5));
        }else if(k==R.id.button6){
            view1.setColorFilter(getResources().getColor(R.color.colorPrimary6));
        }else if(k==R.id.button7){
            view1.setColorFilter(getResources().getColor(R.color.colorPrimary7));
        }else if(k==R.id.button8) {
            view1.setColorFilter(getResources().getColor(R.color.colorPrimary8));
        }
    }

    private static Bitmap getScreenShot(View view){
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private void store(Bitmap bm,String fileName){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/FILTEREDIMAGES";
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dirPath,fileName);


        try{

            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
            fos.close();
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareImage(String fileName){

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/FILTEREDIMAGES";
        Uri uri = Uri.fromFile(new File(dirPath,fileName));
        Intent intent = new Intent();
        intent.setAction(intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT,"");
        intent.putExtra(Intent.EXTRA_TEXT,"");
        intent.putExtra(Intent.EXTRA_STREAM,uri);

        try{
            startActivity(Intent.createChooser(intent,"Share via"));
        }catch(Exception e){
            Toast.makeText(this," Please try again later ! ",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==RESULT_LOAD_IMAGE && resultCode ==RESULT_OK && null!=data){

            Uri selectedImage= data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null,null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex((filePathColumn[0]));
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            view1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

}
