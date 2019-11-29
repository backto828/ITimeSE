package com.chen.practice.itimese;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.practice.itimese.model.MyTicker;
import com.chen.practice.itimese.others.Tools;
import com.chen.practice.itimese.ui.home.HomeFragment;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static com.chen.practice.itimese.MainActivity.setStatusBarTransparent;

public class TickerDetailsActivity extends AppCompatActivity {
    //定义一个startActivityForResult（）方法用到的整型值
    private static final int REQUEST_CODE = 1600;

    private int position;
    private MyTicker myTicker;
    private boolean isModified = false;

    private Timer timer = null;
    private long mDeltaTime;
    private TextView countDownTextView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                isModified = true;
                Bundle bundle = data.getExtras();
                myTicker = (MyTicker) bundle.getSerializable("time");
                showDetails();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker_details);

        // 状态栏透明
        setStatusBarTransparent(this, R.id.app_bar_layout_time_detail);

        final Toolbar toolbar = this.findViewById(R.id.tool_bar_time_detail);
        setSupportActionBar(toolbar);
        // 工具栏内容设置
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        Intent intent = getIntent();

        position = intent.getIntExtra("position", -1);

        // 工具栏按钮
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 返回
                if (isModified) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("mode", HomeFragment.MODIFY_MODE);
                    bundle.putInt("position", position);
                    bundle.putSerializable("time", myTicker);
                    intent.putExtras(bundle);
                    TickerDetailsActivity.this.setResult(RESULT_OK, intent);
                }
                TickerDetailsActivity.this.finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_time: {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("mode", HomeFragment.DELETE_MODE);
                        bundle.putInt("position", position);
                        intent.putExtras(bundle);
                        TickerDetailsActivity.this.setResult(RESULT_OK, intent);

                        TickerDetailsActivity.this.finish();
                        break;
                    }
                    case R.id.share_time:
                        break;
                    case R.id.edit_time: {
                        Intent intent = new Intent(TickerDetailsActivity.this, NewAndEditTickerActivity.class);
                        intent.putExtra("time", myTicker);
                        intent.putExtra("mode", HomeFragment.MODIFY_MODE);
                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                    }
                }
                return true;
            }
        });

        myTicker = (MyTicker) intent.getSerializableExtra("time");
        countDownTextView = this.findViewById(R.id.text_view_time_detail_countdown);

        showDetails();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void showDetails() {
        ((TextView) this.findViewById(R.id.text_view_time_detail_title)).setText(myTicker.title);
        if (myTicker.title.isEmpty()) {
            ((TextView) this.findViewById(R.id.text_view_time_detail_remark)).setHeight(0);
        } else {
            ((TextView) this.findViewById(R.id.text_view_time_detail_remark)).setText(myTicker.remark);
        }

        if (!myTicker.imageUriPath.isEmpty()) {
            Bitmap bitmap = Tools.getBitmapFromUriString(this.getContentResolver(), myTicker.imageUriPath);
            if (bitmap != null) {
                ((ImageView) this.findViewById(R.id.image_view_ticker_detail)).setImageBitmap(bitmap);
            } else {
                ((ImageView) this.findViewById(R.id.image_view_ticker_detail)).setColorFilter(0xFF000000);
            }
        } else {
            ((ImageView) this.findViewById(R.id.image_view_ticker_detail)).setColorFilter(0xFF000000);
        }

        // 计算时间差（s）
        Calendar now = Calendar.getInstance();
        Calendar timeDate = Calendar.getInstance();
        timeDate.set(myTicker.date.year, myTicker.date.month - 1, myTicker.date.day, 0, 0, 0);
        mDeltaTime = (now.getTime().getTime() - timeDate.getTime().getTime()) / 1000;

        // 计时
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int days = (int) Math.abs(mDeltaTime / (3600 * 24));
                        int clock = (int) Math.abs(mDeltaTime % (3600 * 24));
                        int hours = clock / 3600;
                        int minutes = (clock % 3600) / 60;
                        int seconds = clock % 3600 % 60;

                        String text = "";
                        if (days > 0) {
                            text += days + "天";
                        }
                        if (!(days == 0 && hours == 0)) {
                            text += hours + "小时";
                        }
                        if (!(days == 0 && hours == 0 && minutes == 0)) {
                            text += minutes + "分钟";
                        }
                        text += seconds + "秒";

                        countDownTextView.setText(text);

                        mDeltaTime++;
                    }
                });
            }
        }, 0, 1000);

        ((TextView) this.findViewById(R.id.text_view_time_detail_countdown)).setText(myTicker.date.year + "年" + myTicker.date.month + "月" + myTicker.date.day + "日");
        ((TextView) this.findViewById(R.id.text_view_time_detail_date)).setText(myTicker.date.year + "年" + myTicker.date.month + "月" + myTicker.date.day + "日");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 设置菜单栏按钮图片
        getMenuInflater().inflate(R.menu.activity_time_detail_options, menu);
        return true;
    }
}