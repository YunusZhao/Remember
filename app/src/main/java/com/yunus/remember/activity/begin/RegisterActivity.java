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
import android.util.Base64;
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
import com.google.gson.Gson;
import com.yunus.remember.R;
import com.yunus.remember.activity.chief.CropperActivity;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.entity.User;
import com.yunus.remember.utils.HttpUtil;
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    public static final int APPLY_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private static final String TAG = "RegisterActivity";

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
        Glide.with(RegisterActivity.this).load(R.drawable.timg).into(portrait);



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
        Gson gson = new Gson();
        User me = new User();
        me.setName(name.getText().toString());
        me.setEmail(email.getText().toString());
        me.setPassword(password.getText().toString());
        me.setPortrait(Base64.encodeToString(imageByte,Base64.DEFAULT));

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, gson.toJson(me));

        HttpUtil.register(requestBody, new Callback() {
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
                Gson gson = new Gson();
                final User user = gson.fromJson(response.body().string(), User.class);
                if (user != null) {
                    saveUser(user);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                        if (user == null) {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity
                                    .class);
                            finish();
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private void saveUser(User user) {
        StorageUtil.updateInt(RegisterActivity.this, StorageUtil.USER_ID, user.getId());
        StorageUtil.updateString(RegisterActivity.this, StorageUtil.EMAIL, user.getEmail());
        StorageUtil.updateString(RegisterActivity.this, StorageUtil.USER_NAME, user.getName());
        StorageUtil.updateString(RegisterActivity.this, StorageUtil.PASSWORD, user.getPassword());
        DataSupport.deleteAll(Friend.class);
        Friend friend = new Friend();
        friend.setId(user.getId());
        friend.setName(user.getName());
        friend.setEmail(user.getEmail());
        friend.save();
    }
}