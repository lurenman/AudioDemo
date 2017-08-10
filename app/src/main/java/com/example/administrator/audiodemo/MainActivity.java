package com.example.administrator.audiodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView tv_click1;
    private TextView tv_click2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariables();
        initViews();
        getData();
    }


    private void initVariables()
    {
    }

    private void initViews()
    {
        tv_click1= (TextView) findViewById(R.id.tv_click1);
        tv_click2= (TextView) findViewById(R.id.tv_click2);

        tv_click1.setOnClickListener(this);
        tv_click2.setOnClickListener(this);
    }
    private void getData()
    {
    }

    @Override
    public void onClick(View view)
    {
        if (view==tv_click1)
        {
            Intent intent=new Intent(MainActivity.this,demo1Activity.class);
            startActivity(intent);
        }
        if (view==tv_click2)
        {
            Intent intent=new Intent(MainActivity.this,demo2Activity.class);
            startActivity(intent);
        }


    }
}
