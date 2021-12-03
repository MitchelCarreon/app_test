package com.example.app_test.Utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Scenario implements Parcelable {
    public HashMap<String, String> btn_txts;
    public String scene_desc_txt;
    public String btn_type;

    public Scenario(){
        this.btn_txts = new HashMap<>();
        btn_txts.put("btn1_txt", "");
        btn_txts.put("btn2_txt", "");
        btn_txts.put("btn3_txt", "");
        btn_txts.put("btn4_txt", "");
    }

    protected Scenario(Parcel in) {
        btn_txts = (HashMap<String, String>) in.readSerializable();
        scene_desc_txt = in.readString();
        btn_type = in.readString();
    }

    public static final Creator<Scenario> CREATOR = new Creator<Scenario>() {
        @Override
        public Scenario createFromParcel(Parcel in) {
            return new Scenario(in);
        }

        @Override
        public Scenario[] newArray(int size) {
            return new Scenario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.btn_txts);
        dest.writeString(scene_desc_txt);
        dest.writeString(btn_type);
    }
}
