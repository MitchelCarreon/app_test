package com.example.app_test.adapter;

public class Adventure_overview {
    private String title;
    private String description;
    private Class activity_class_name; // Stores class/activity name as String. Use Class.forName(String) to pass to an intent.



    public Adventure_overview(String title, String description, Class activity_class_name) {
        this.title = title;
        this.description = description;
        this.activity_class_name = activity_class_name;
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
