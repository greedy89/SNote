
package com.seno.snote.serviceModel.categoryModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.seno.snote.ApplicationSNote;

import java.io.Serializable;

@Table(database = ApplicationSNote.class)
public class TblKatagoryNote extends BaseModel implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("category")
    @Expose
    @Column
    private String category;
    public final static Creator<TblKatagoryNote> CREATOR = new Creator<TblKatagoryNote>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TblKatagoryNote createFromParcel(Parcel in) {
            return new TblKatagoryNote(in);
        }

        public TblKatagoryNote[] newArray(int size) {
            return (new TblKatagoryNote[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7142961947219887490L;

    protected TblKatagoryNote(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public TblKatagoryNote() {
    }

    /**
     * 
     * @param id
     * @param category
     */
    public TblKatagoryNote(String id, String category) {
        super();
        this.id = id;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(category);
    }

    public int describeContents() {
        return  0;
    }

}
