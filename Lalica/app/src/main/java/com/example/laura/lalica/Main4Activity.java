package com.example.laura.lalica;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.widget.Button;
public class Main4Activity extends Activity {
    ImageView im;
    RelativeLayout rel4;
    Button save;
    private static final int RESULT_LOAD_IMAGE = 0;
    int plus=0;
    String currentImage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        im = (ImageView) findViewById(R.id.im);
        save=(Button)findViewById(R.id.button5);
        save.setEnabled(false);
        rel4=(RelativeLayout)findViewById(R.id.lay4);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View content = findViewById(R.id.lay4);
                Bitmap bitmap = getScreenShot(content);
                currentImage = "image"+System.currentTimeMillis()+".jpg";
                store(bitmap,currentImage);
            }
        });
    }
    public void load(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,RESULT_LOAD_IMAGE);
        save.setEnabled(true);
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
            im.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    public void rl(View view) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(im.getHeight(), im.getWidth());
        rel4.setLayoutParams(params);
        plus+=90;
        im.setRotation(plus);
    }

    public void rr(View view) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(im.getHeight(), im.getWidth());
        rel4.setLayoutParams(params);
            plus-=90;
        im.setRotation(plus);
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
    private static Bitmap getScreenShot(View view){
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }
}
