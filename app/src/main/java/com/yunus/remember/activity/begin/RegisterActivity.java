package com.yunus.remember.activity.begin;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.LogUtil;
import com.example.yunus.utils.RWUtil;
import com.example.yunus.utils.ViewUtil;
import com.yunus.remember.R;
import com.yunus.remember.activity.chief.CropperActivity;
import com.yunus.remember.utils.HttpUtil;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    public static final int APPLY_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    Toolbar toolbar;
    TextView toolbarTitle;
    CircleImageView portrait;
    ImageButton addPortrait;
    EditText name;
    EditText email;
    EditText password;
    Button register;
    TextView login;
    byte[] imageByte;
    ProgressDialog myDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getView();
        ViewUtil.setToolbar(RegisterActivity.this, toolbar);

        //TODO 初始化图片
        imageByte = " ".getBytes();

        addPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RegisterActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty() || !RWUtil.isName(name.getText().toString
                        ())) {
                    Toast.makeText(RegisterActivity.this, "昵称输入错误", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().isEmpty() || !RWUtil.isEmail(email.getText
                        ().toString())) {
                    Toast.makeText(RegisterActivity.this, "邮箱输入错误", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty() || !RWUtil.isPwd(password
                        .getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                } else {
                    myDialog = ProgressDialog.show(RegisterActivity.this, "提示...", "注册中...", true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            registerToIntel();
                        }
                    }).start();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar.setTitle("");
        toolbarTitle.setText(getString(R.string.register_short));
    }

    private void getView() {
        toolbar = findViewById(R.id.register_toolbar);
        toolbarTitle = findViewById(R.id.begin_toolbar_title);
        portrait = findViewById(R.id.register_portrait);
        addPortrait = findViewById(R.id.register_add_portrait);
        name = findViewById(R.id.register_name);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.register_to_login);
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "无权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case APPLY_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将照片显示出来
                        imageByte = data.getByteArrayExtra("data");
                        Glide.with(this).load(imageByte).into(portrait);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        LogUtil.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse
                        ("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        applyImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        applyImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void applyImage(String imagePath) {
        if (imagePath != null) {
            Intent intent = new Intent(RegisterActivity.this, CropperActivity.class);
            intent.putExtra("source", imagePath);
            startActivityForResult(intent, APPLY_PHOTO);
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerToIntel() {
        final RequestBody requestBody = new FormBody.Builder()
                .add("image", new String(imageByte))
                .add("name", name.getText().toString())
                .add("email", email.getText().toString())
                .add("password", password.getText().toString())
                .add("action", "password")
                .build();
        HttpUtil.post(requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "联网失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                        switch (result) {
                            case "0":
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case "1":
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT)
                                        .show();
                                //todo 解析数据，将个人信息存至本地和数据库

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity
                                        .class);
                                finish();
                                startActivity(intent);
                                break;
                            default:
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT)
                                        .show();
                        }
                    }
                });
            }
        });
    }
}
