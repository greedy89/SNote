
package com.seno.snote.serviceModel.categoryModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable, Parcelable
{

    @SerializedName("tbl_katagory_note")
    @Expose
    private List<TblKatagoryNote> tblKatagoryNote = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2399006147694696374L;

    protected Data(Parcel in) {
        in.readList(this.tblKatagoryNote, (TblKatagoryNote.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param tblKatagoryNote
     */
    public Data(List<TblKatagoryNote> tblKatagoryNote) {
        super();
        this.tblKatagoryNote = tblKatagoryNote;
    }

    public List<TblKatagoryNote> getTblKatagoryNote() {
        return tblKatagoryNote;
    }

    public void setTblKatagoryNote(List<TblKatagoryNote> tblKatagoryNote) {
        this.tblKatagoryNote = tblKatagoryNote;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(tblKatagoryNote);
    }

    public int describeContents() {
        return  0;
    }

}
