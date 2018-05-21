package com.seno.snote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.seno.snote.ApplicationSNote;
import com.seno.snote.R;
import com.seno.snote.adapter.AdapterNote;
import com.seno.snote.service.ApiClient;
import com.seno.snote.service.ApiInterfaces;
import com.seno.snote.serviceModel.noteModel.Note;
import com.seno.snote.serviceModel.noteModel.TblNote;
import com.seno.snote.serviceModel.noteModel.TblNote_Table;


import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotebyCatagory extends AppCompatActivity {
    private RecyclerView rv;
    private String data ;
    private TextView txtkategori;
    private AdapterNote mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteby_catagory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data = getIntent().getStringExtra("categories");
        txtkategori = (TextView)findViewById(R.id.txtCategory);
        txtkategori.setText(data);
        rv = (RecyclerView)findViewById(R.id.recyclerViewNote);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        getNote();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(NotebyCatagory.this,TambahData.class);
                Bundle b = new Bundle();
                b.putString("categories",data);
                b.putString("type","save");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getNote(){
        ApiInterfaces interfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Note> note = interfaces.getNoteByCatagory("categories",data);
        note.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                Note note = response.body();
                if(note!=null){
                    if(note.getData().getTblNote().size()>0){
                        setAdapterList(note.getData().getTblNote());
                        savedb(note.getData().getTblNote());
                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(NotebyCatagory.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(NotebyCatagory.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                call.cancel();
                getListOffline();
            }
        });


    }

    private void setAdapterList(List<TblNote> items) {
        //set data and list adapter
        mAdapter = new AdapterNote(NotebyCatagory.this, items);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterNote.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TblNote obj, int position) {
                Intent intent = new Intent(NotebyCatagory.this,Tampil.class);
                Bundle b = new Bundle();
                b.putSerializable("data", obj);
                b.putString("categories",data);
                intent.putExtras(b);
                startActivityForResult(intent,20);
            }
        });

    }

    public void savedb(List<TblNote> items){

        FlowManager.getDatabase(ApplicationSNote.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<TblNote>() {
                            @Override
                            public void processModel(TblNote orderItem, DatabaseWrapper wrapper) {

                                orderItem.save();


                            }

                        }).addAll(items).build())  // add elements (can also handle multiple)
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        Toast.makeText(getApplicationContext(),"Data Tersimpan",Toast.LENGTH_SHORT).show();

                    }
                }).build().execute();
    }

    private void getListOffline (){
        setAdapterList(getDataNoteOffline());
    }

    public List<TblNote> getDataNoteOffline (){

        List<TblNote> news = SQLite.select()
                .from(TblNote.class)
                    .where(TblNote_Table.categories.is(data))
                .queryList();
        return news;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNote();
    }

}
