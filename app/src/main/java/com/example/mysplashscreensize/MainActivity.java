package com.example.mysplashscreensize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "spinner";

    private Button btn_show;
    private Context mContext;
    private Button btn_show_menu;
    private ImageView imageView_logo;
    private EditText editTextNumber_returnHeight;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();




    }

    private void initPopWindow(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup, null, false);
        Button btn_xixi = (Button) view.findViewById(R.id.btn_mappingStyle);
        Button btn_hehe = (Button) view.findViewById(R.id.btn_returnHeight);
        Button btn_submitReturnHeight = (Button) view.findViewById(R.id.btn_submitReturnHeight);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 50, 0);

        //设置popupWindow里的按钮的事件
        btn_xixi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了嘻嘻~", Toast.LENGTH_SHORT).show();
            }
        });
        btn_hehe.setOnClickListener(v1 -> {
            Toast.makeText(MainActivity.this, "你点击了呵呵~", Toast.LENGTH_SHORT).show();
            popWindow.dismiss();
        });

        if (btn_submitReturnHeight == null)
            Log.d(TAG, "initPopWindow: sub btn null");
        assert btn_submitReturnHeight != null;
        btn_submitReturnHeight.setOnClickListener(v12 -> {
            editTextNumber_returnHeight = (EditText) view.findViewById(R.id.editTextNumber_returnHeight);
            int height = Integer.parseInt(editTextNumber_returnHeight.getText().toString());

            Toast.makeText(MainActivity.this, "height: " + height, Toast.LENGTH_SHORT).show();
        });


    }

    // PopWindow   感知设置
    private void initPopWindowPerception(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup_perception, null, false);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 0, 0);

        Switch switch_visualAvoid = (Switch) view.findViewById(R.id.switch_visualAvoid);
        switch_visualAvoid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(MainActivity.this, "视觉避障：" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void initUI(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);




        //Popup Menu
//        btn_show_menu = (Button)findViewById(R.id.btn_show_menu);
//        btn_show_menu.setOnClickListener(new View.OnClickListener() {
        imageView_logo = (ImageView) findViewById(R.id.imageView_logo);
        imageView_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this,imageView_logo);
                popup.getMenuInflater().inflate(R.menu.menu_pop, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_jp_style1:
                                Toast.makeText(MainActivity.this,"日本手~",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.item_usa_style2:
                                Toast.makeText(MainActivity.this,"美国手~",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.item_cn_style3:
                                Toast.makeText(MainActivity.this,"中国手~",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.item_returnHeight:
                                Toast.makeText(MainActivity.this,"你点了返航高度~",
                                        Toast.LENGTH_SHORT).show();
                                //Popup Window
                                mContext = MainActivity.this;
                                initPopWindow(v);
                                break;
                            case R.id.item_perception:
                                Toast.makeText(MainActivity.this,"你点了感知设置~",
                                        Toast.LENGTH_SHORT).show();
                                mContext = MainActivity.this;
                                initPopWindowPerception(v);
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView centerTextView = (TextView)findViewById(R.id.centerTextView);
        String item = (String)parent.getItemAtPosition(position);
        Log.i(TAG, "onItemSelected: " + parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}