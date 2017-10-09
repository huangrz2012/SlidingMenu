package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SlidingMenu mMenu;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.layout);
        mMenu = (SlidingMenu) findViewById(R.id.id_menu);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        

    }

    public void toggleMenu(View view)
    {
        mMenu.toggle();
    }

    public List<String> getData() {
        List<String> data = new ArrayList<String>();
        for(int i = 0; i < 26; i++) {
            data.add((char)('A' + i)+"");
        }
        return data;
    }

    public void toggleMenu2(View view)
    {
        if(mMenu.getisOPen())
        {
            mMenu.toggle();
        }

    }
}
