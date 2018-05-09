package com.yunus.remember.activity.begin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunus.activity.BaseActivity;
import com.example.yunus.utils.LogUtil;
import com.example.yunus.utils.RWUtil;
import com.example.yunus.utils.ViewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunus.remember.R;
import com.yunus.remember.activity.chief.MainActivity;
import com.yunus.remember.entity.Book;
import com.yunus.remember.entity.Friend;
import com.yunus.remember.entity.RegisterCount;
import com.yunus.remember.entity.Word;
import com.yunus.remember.utils.HttpUtil;
import com.yunus.remember.utils.StorageUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    Toolbar toolbar;
    TextView toolbarTitle;
    CircleImageView portrait;
    EditText email;
    EditText password;
    Button login;
    TextView forgetPassword;
    TextView register;
    ProgressDialog myDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getView();
        ViewUtil.setToolbar(LoginActivity.this, toolbar);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty() ||
                        !RWUtil.isEmail(email.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "邮箱输入错误", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty() ||
                        !RWUtil.isPwd(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                } else {
                    myDialog = ProgressDialog.show(LoginActivity.this, "提示...", "登陆中...", true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            login();
                        }
                    }).start();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        toolbar.setTitle("");
        toolbarTitle.setText(R.string.sign_in_short);
    }

    private void getView() {
        toolbar = findViewById(R.id.login_toolbar);
        toolbarTitle = findViewById(R.id.begin_toolbar_title);
        portrait = findViewById(R.id.login_portrait);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.btn_login);
        forgetPassword = findViewById(R.id.login_forget_password);
        register = findViewById(R.id.login_to_register);
    }

    private void login() {
        final RequestBody requestBody = new FormBody.Builder()
                .add("email", email.getText().toString())
                .add("password", password.getText().toString())
                .build();
        HttpUtil.login(requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "联网失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                LogUtil.d("login", result);
                Gson gson = new Gson();
                final Friend friend = gson.fromJson(result, Friend.class);
                if (friend != null) {
                    saveUser(friend);
                    getUserBook();
//                    String session = response.header("set-cookie");
//                    StorageUtil.updateString(LoginActivity.this, StorageUtil.SESSION, session);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (friend == null) {
                            myDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
            }
        });
    }

    private void getUserBook() {
        HttpUtil.userBook(StorageUtil.getInt(LoginActivity.this, StorageUtil.USER_ID, 0) + "",
                new Callback() {


                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        LogUtil.d("book", result);
                        Gson gson = new Gson();
                        List<Book> books = gson.fromJson(result, new
                                TypeToken<List<Book>>() {
                                }.getType());
                        DataSupport.deleteAll(Book.class);
                        DataSupport.saveAll(books);
                        getRegisterCount();
                    }
                });
    }

    private void getRegisterCount() {
        HttpUtil.registerCount(StorageUtil.getInt(LoginActivity.this, StorageUtil.USER_ID, 0) + "",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        LogUtil.d("register", result);
                        Gson gson = new Gson();
                        List<RegisterCount> registerCounts = gson.fromJson(result, new
                                TypeToken<List<RegisterCount>>() {
                                }.getType());
                        DataSupport.deleteAll(RegisterCount.class);
                        DataSupport.saveAll(registerCounts);
                        getFriend();
                    }
                });
    }

    private void getFriend() {
        HttpUtil.friend(StorageUtil.getInt(LoginActivity.this, StorageUtil.USER_ID, 0) + "",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        LogUtil.d("friend", result);
                        Gson gson = new Gson();
                        List<Friend> friends = gson.fromJson(result, new
                                TypeToken<List<Friend>>() {
                                }.getType());
                        DataSupport.saveAll(friends);
                        getWords();
                    }
                });
    }

    private void getWords() {
        HttpUtil.word(StorageUtil.getInt(LoginActivity.this, StorageUtil.USER_ID, 0) + "",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        LogUtil.d("login word", result);
                        Gson gson = new Gson();
                        List<Word> words = gson.fromJson(result, new
                                TypeToken<List<Word>>() {
                                }.getType());
                        DataSupport.deleteAll(Word.class);
                        DataSupport.saveAll(words);
                        complete();
                    }
                });
    }

    private void complete() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveUser(Friend friend) {
        StorageUtil.updateInt(LoginActivity.this, StorageUtil.USER_ID, friend.getId());
        StorageUtil.updateString(LoginActivity.this, StorageUtil.EMAIL, friend.getEmail());
        StorageUtil.updateString(LoginActivity.this, StorageUtil.USER_NAME, friend.getName());
        StorageUtil.updateString(LoginActivity.this, StorageUtil.PASSWORD, password.getText()
                .toString());
        StorageUtil.updateInt(LoginActivity.this, StorageUtil.WORDS_NUM, friend.getWordNum());
        StorageUtil.updateInt(LoginActivity.this, StorageUtil.STUDY_TIME, friend.getAllTime());
        StorageUtil.updateInt(LoginActivity.this, StorageUtil.WORDS_STUDIED_NUM, friend
                .getWordNum());
        StorageUtil.updateString(LoginActivity.this, StorageUtil.PORTRAIT, friend.getPortrait());
        DataSupport.deleteAll(Friend.class);
        friend.save();
    }


}
