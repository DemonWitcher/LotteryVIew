package com.example.lotteryview;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LotteryView lotteryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.tv1);
        lotteryView = findViewById(R.id.lottery_view);
        if (new Random().nextInt(10) == 5) {
            textView.setText("中奖了！");
        } else {
            textView.setText("谢谢参与");
        }
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Random().nextInt(10) == 5) {
                    textView.setText("中奖了！");
                } else {
                    textView.setText("谢谢参与");
                }
                lotteryView.reset();
            }
        });
    }
}