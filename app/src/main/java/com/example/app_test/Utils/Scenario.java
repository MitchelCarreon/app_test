package com.example.app_test.Utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Scenario implements Parcelable {
    public HashMap<String, String> btn_txts;
    public String scene_desc_txt;
    public int btn_type;
    public HashMap<String, Integer> btn_paths;
    public int num_references_to;


    public void update(String btn_txt_key, String btn_dest_key) {

        this.btn_type--;
        this.btn_txts.replace(btn_txt_key, "PICKED");
        this.btn_paths.replace(btn_dest_key, -1);

        updateBtnTxts();
        updateBtnPaths();

    }

    public void updateBtnTxts(){
        ArrayList<String> valid_txts = new ArrayList<>();

        for (Map.Entry<String, String> entry : this.btn_txts.entrySet()) {
            if (!entry.getValue().equals("PICKED") && !entry.getValue().equals("")){
                valid_txts.add(entry.getValue());
            }
        }

        HashMap<String, String> updated_btn_txts = new HashMap<>();
        for (int i = 0; i < this.btn_txts.size(); ++i){
            String btn_txt_key = String.format("btn%d_txt", i + 1);
            if (i < valid_txts.size()){
                updated_btn_txts.put(btn_txt_key, valid_txts.get(i));
            }
            else {
                updated_btn_txts.put(btn_txt_key, "");
            }
        }
        this.btn_txts = updated_btn_txts;
    }
    public void updateBtnPaths(){
        ArrayList<Integer> valid_paths = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : this.btn_paths.entrySet()) {
            if (!entry.getValue().equals(-1)){
                valid_paths.add(entry.getValue());
            }
        }

        HashMap<String, Integer> updated_btn_paths = new HashMap<>();
        for (int i = 0; i < this.btn_txts.size(); ++i){
            String btn_txt_key = String.format("btn%d_dest", i + 1);
            if (i < valid_paths.size()){
                updated_btn_paths.put(btn_txt_key, valid_paths.get(i));
            }
            else {
                updated_btn_paths.put(btn_txt_key, -1);
            }
        }
        this.btn_paths = updated_btn_paths;
    }

    public Scenario() {
        this.btn_txts = new HashMap<>();
        this.btn_txts.put("btn1_txt", "");
        this.btn_txts.put("btn2_txt", "");
        this.btn_txts.put("btn3_txt", "");
        this.btn_txts.put("btn4_txt", "");

        this.btn_paths = new HashMap<>();
        this.btn_paths.put("btn1_dest", -1);
        this.btn_paths.put("btn2_dest", -1);
        this.btn_paths.put("btn3_dest", -1);
        this.btn_paths.put("btn4_dest", -1);
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
