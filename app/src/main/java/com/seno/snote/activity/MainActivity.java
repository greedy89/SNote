package com.seno.snote.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.seno.snote.ApplicationSNote;
import com.seno.snote.R;
import com.seno.snote.adapter.AdapterKategori;
import com.seno.snote.service.ApiClient;
import com.seno.snote.service.ApiInterfaces;
import com.seno.snote.serviceModel.categoryModel.Category;
import com.seno.snote.serviceModel.categoryModel.TblKatagoryNote;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rV;
    private AdapterKategori mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rV = (RecyclerView)findViewById(R.id.recyclerView);
        rV.setLayoutManager(new LinearLayoutManager(this));
        rV.setHasFixedSize(true);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
        getCategory();
    }
    private void getCategory(){
        ApiInterfaces apiInterfaces = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Category> kategory = apiInterfaces.getCategory();

        kategory.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                Category c = response.body();
                if(response.body()!=null){
                    if(c.getData().getTblKatagoryNote().size()>0){
                        savedb(c.getData().getTblKatagoryNote());
                        setAdapterList(c.getData().getTblKatagoryNote());
//                        Toast.makeText(MainActivity.this, "sukses", Toast.LENGTH_LONG).show();
                    }
                }else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                call.cancel();
                getListOffline();
            }
        });
    }

    private void setAdapterList(List<TblKatagoryNote> items) {

        //set data and list adapter
        mAdapter = new AdapterKategori(MainActivity.this, items);
        mAdapter.setOnItemClickListener(new AdapterKategori.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TblKatagoryNote obj, int position) {
//                Toast.makeText(MainActivity.this, "cik", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,NotebyCatagory.class);
                intent.putExtra("categories",obj.getCategory());
                startActivity(intent);
            }
        });
        rV.setAdapter(mAdapter);

        // on item list clicked

    }

    public void savedb(List<TblKatagoryNote> items){

        FlowManager.getDatabase(ApplicationSNote.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<TblKatagoryNote>() {
                            @Override
                            public void processModel(TblKatagoryNote orderItem, DatabaseWrapper wrapper) {

                                orderItem.save();


                            }

                        }).addAll(items).build())  // add elements (can also handle multiple)
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
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

        setAdapterList(getDataCategoryOffline());

    }

    public List<TblKatagoryNote> getDataCategoryOffline (){

        List<TblKatagoryNote> news = SQLite.select()
                .from(TblKatagoryNote.class)
                //    .where(User_Table.age.greaterThan(18))
                .queryList();
        return news;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCategory();
    }
}
