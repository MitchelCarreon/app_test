package com.example.app_test.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.app_test.R;

public class Adventure_overview {
    private String title;
    private String description;
    private Class activity_class_name; // Stores class/activity name as String. Use Class.forName(String) to pass to an intent.
    public Drawable background;
    private Context context;


    public Adventure_overview(Context context, String title, String description, Class activity_class_name, int background_id) {
        this.title = title;
        this.description = description;
        this.activity_class_name = activity_class_name;
        this.background = ContextCompat.getDrawable(context, background_id);
    }

    public Drawable getBackground() {
        return background;
    }

    public Class getActivity_class_name() {
        return activity_class_name;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
