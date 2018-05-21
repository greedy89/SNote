
package com.seno.snote.serviceModel.noteModel;

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
public class TblNote extends BaseModel implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("title")
    @Expose
    @Column
    private String title;
    @SerializedName("cotents")
    @Expose
    @Column
    private String cotents;
    @SerializedName("dateCreated")
    @Expose
    @Column
    private String dateCreated;
    @SerializedName("dateChanged")
    @Expose
    @Column
    private String dateChanged;
    @SerializedName("categories")
    @Expose
    @Column
    private String categories;
    @SerializedName("img")
    @Expose
    @Column
    private String img;
    public final static Creator<TblNote> CREATOR = new Creator<TblNote>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TblNote createFromParcel(Parcel in) {
            return new TblNote(in);
        }

        public TblNote[] newArray(int size) {
            return (new TblNote[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3139481379690944433L;

    protected TblNote(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.cotents = ((String) in.readValue((String.class.getClassLoader())));
        this.dateCreated = ((String) in.readValue((String.class.getClassLoader())));
        this.dateChanged = ((String) in.readValue((String.class.getClassLoader())));
        this.categories = ((String) in.readValue((String.class.getClassLoader())));
        this.img = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public TblNote() {
    }

    /**
     * 
     * @param id
     * @param title
     * @param img
     * @param dateCreated
     * @param cotents
     * @param categories
     * @param dateChanged
     */
    public TblNote(String id, String title, String cotents, String dateCreated, String dateChanged, String categories, String img) {
        super();
        this.id = id;
        this.title = title;
        this.cotents = cotents;
        this.dateCreated = dateCreated;
        this.dateChanged = dateChanged;
        this.categories = categories;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCotents() {
        return cotents;
    }

    public void setCotents(String cotents) {
        this.cotents = cotents;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(String dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(cotents);
        dest.writeValue(dateCreated);
        dest.writeValue(dateChanged);
        dest.writeValue(categories);
        dest.writeValue(img);
    }

    public int describeContents() {
        return  0;
    }

}
