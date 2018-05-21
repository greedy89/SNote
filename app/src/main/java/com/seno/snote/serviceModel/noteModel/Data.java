
package com.seno.snote.serviceModel.noteModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable, Parcelable
{

    @SerializedName("tbl_note")
    @Expose
    private List<TblNote> tblNote = null;
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
    private final static long serialVersionUID = 8372681137563591852L;

    protected Data(Parcel in) {
        in.readList(this.tblNote, (com.seno.snote.serviceModel.noteModel.TblNote.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param tblNote
     */
    public Data(List<TblNote> tblNote) {
        super();
        this.tblNote = tblNote;
    }

    public List<TblNote> getTblNote() {
        return tblNote;
    }

    public void setTblNote(List<TblNote> tblNote) {
        this.tblNote = tblNote;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(tblNote);
    }

    public int describeContents() {
        return  0;
    }

}
