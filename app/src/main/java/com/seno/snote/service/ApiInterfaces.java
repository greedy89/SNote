package com.seno.snote.service;

import com.seno.snote.serviceModel.categoryModel.Category;
import com.seno.snote.serviceModel.noteModel.Note;
import com.seno.snote.serviceModel.noteModel.Update;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterfaces {

    @GET("api/tbl_katagory_note/all")
    Call<Category> getCategory();


    //http://localhost:8080/cicool/api/tbl_note/all?field=categories&filter=pribadi
//    @GET("api/tbl_note/all?field=categories&filter=pribadi")
    @GET("api/tbl_note/all")
    Call<Note> getNoteByCatagory(@Query("field") String categories, @Query("filter") String value);
//    @GET("api/tbl_note/all")

    @Multipart
    @POST("api/tbl_note/add")
    Call<Update> addData(
            @Part("title") RequestBody title,
            @Part("cotents") RequestBody content,
            @Part("dateCreated") RequestBody dateCreate,
            @Part("dateChanged") RequestBody dateChange,
            @Part("categories") RequestBody kategori,
            @Part MultipartBody.Part img

    );

    @Multipart
    @POST("api/tbl_note/update")
    Call<Update> update(
            @Part("id") RequestBody id,
            @Part("title") RequestBody title,
            @Part("cotents") RequestBody content,
            @Part("dateCreated") RequestBody dateCreate,
            @Part("dateChanged") RequestBody dateChange,
            @Part("categories") RequestBody kategori,
            @Part MultipartBody.Part img

    );
}
