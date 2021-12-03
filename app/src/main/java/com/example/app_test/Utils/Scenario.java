package com.example.app_test.Utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Scenario implements Parcelable {
    public HashMap<String, String> btn_txts;
    public String scene_desc_txt;
    public int btn_type;
    public HashMap<String, Integer> btn_paths;

    public Scenario(){
        this.btn_txts = new HashMap<>();
        this.btn_txts.put("btn1_txt", "");
        this.btn_txts.put("btn2_txt", "");
        this.btn_txts.put("btn3_txt", "");
        this.btn_txts.put("btn4_txt", "");

        this.btn_paths = new HashMap<>();
        this.btn_paths.put("btn1_path", -1);
        this.btn_paths.put("btn2_path", -1);
        this.btn_paths.put("btn3_path", -1);
        this.btn_paths.put("btn4_path", -1);
    }

    protected Scenario(Parcel in) {
        btn_txts = (HashMap<String, String>) in.readSerializable();
        scene_desc_txt = in.readString();
        btn_type = in.readInt();
        btn_paths = (HashMap<String, Integer>) in.readSerializable();
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
        dest.writeInt(btn_type);
        dest.writeSerializable(this.btn_paths);
    }
}
