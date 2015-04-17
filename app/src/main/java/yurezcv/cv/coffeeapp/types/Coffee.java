package yurezcv.cv.coffeeapp.types;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coffee implements Parcelable {

    private String id;

    private String desc;

    private String name;

    private String imageURL;

    private String lastUpdate;

    public Coffee() {
    }

    protected Coffee(Parcel in) {
        id = in.readString();
        desc = in.readString();
        name = in.readString();
        imageURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(desc);
        dest.writeString(name);
        dest.writeString(imageURL);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Coffee> CREATOR = new Parcelable.Creator<Coffee>() {
        @Override
        public Coffee createFromParcel(Parcel in) {
            return new Coffee(in);
        }

        @Override
        public Coffee[] newArray(int size) {
            return new Coffee[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("image_url")
    public String getImageURL() {
        return imageURL;
    }

    @JsonProperty("image_url")
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    @JsonProperty("last_updated_at")
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
