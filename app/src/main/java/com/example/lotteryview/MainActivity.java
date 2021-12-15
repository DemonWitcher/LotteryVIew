package com.example.lotteryview;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.tv1);
        if (new Random().nextInt(10) == 5) {
            textView.setText("中奖了！");
        } else {
            textView.setText("谢谢参与");
        }
    }
}