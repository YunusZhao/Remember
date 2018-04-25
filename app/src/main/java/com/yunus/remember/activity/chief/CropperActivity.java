package com.yunus.remember.activity.chief;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.edmodo.cropper.CropImageView;
import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.LogUtil;
import com.yunus.remember.R;
import com.yunus.remember.utils.StorageUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class CropperActivity extends BaseActivity {

    Button ok;
    CropImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropper);
        String path = getIntent().getStringExtra("source");

        ok = findViewById(R.id.cropper_ok);
        image = findViewById(R.id.cropper_image);
        image.setFixedAspectRatio(true);//设置允许按比例截图，如果不设置就是默认的任意大小截图
        image.setAspectRatio(1, 1);//设置比例为一比一
        Bitmap imageMap = BitmapFactory.decodeFile(path);
        image.setImageBitmap(imageMap);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = image.getCroppedImage();//得到裁剪好的图片
                Bitmap newbitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                LogUtil.d("wechat", "压缩后图片的大小" + (newbitmap.getByteCount() / 1024) + "KB宽度为"
                        + newbitmap.getWidth() + "高度为" + newbitmap.getHeight());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                newbitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                Intent intent = new Intent();
                intent.putExtra("data", data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
