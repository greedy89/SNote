package com.seno.snote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seno.snote.R;
import com.seno.snote.serviceModel.noteModel.Data;
import com.seno.snote.serviceModel.noteModel.Note;
import com.seno.snote.serviceModel.noteModel.TblNote;
import com.seno.snote.utility.ImageUtil;

public class Tampil extends AppCompatActivity {
    private TextView title,content,dateCreate,dateChanged;
    private ImageView img;
    private final static String key = "update";
    private static String categories;
    private static TblNote data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = (TblNote) getIntent().getExtras().getSerializable("data");
        categories = getIntent().getExtras().getString("categories");
        title = (TextView)findViewById(R.id.txtTitleDetail);
        content = (TextView)findViewById(R.id.txtIsiDetail);
        dateCreate = (TextView)findViewById(R.id.txtDateCreate);
        dateChanged = (TextView)findViewById(R.id.txtDateChanged);
        title.setText(data.getTitle().toString());
        content.setText(data.getCotents().toString());
        dateCreate.setText(data.getDateCreated().toString());
        dateChanged.setText(data.getDateChanged().toString());

        img = (ImageView)findViewById(R.id.imageView);
        ImageUtil.displayImage(img,data.getImg(),null);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(Tampil.this,TambahData.class);
                Bundle b = new Bundle();
                b.putSerializable("data", data);
                b.putString("type",key);
                b.putString("categories",categories);
                intent.putExtras(b);
//                startActivity(intent);
                startActivityForResult(intent,30);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("NOTE by Category");


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==30 && resultCode==40 ){
            finish();
        }
    }

}
