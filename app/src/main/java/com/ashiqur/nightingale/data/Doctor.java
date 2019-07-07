package com.ashiqur.nightingale.data;


import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashiqur.nightingale.R;

import hivatec.ir.suradapter.ItemBinder;
import hivatec.ir.suradapter.ItemHolder;


public class Doctor implements ItemBinder{
    private String name;
    private String qualifications;//Comma Separated
    private String currentWorkPlace;
    private int photoImageResId=-1;

    public Doctor(String name, String qualifications, String currentWorkPlace, int photoImageResId) {
        this.name = name;
        this.qualifications = qualifications;
        this.currentWorkPlace = currentWorkPlace;
        this.photoImageResId = photoImageResId;
    }

    public static final String[] sectors={"Gynecologist",
            "Obstetrician",
            "Reproductive Endocrinologist",
            "Gynecologic Oncologist"};

    public Doctor(String name, String qualifications, String currentWorkPlace) {
        this.name = name;
        this.qualifications = qualifications;
        this.currentWorkPlace = currentWorkPlace;
    }

    public int getPhotoImageResId() {
        return photoImageResId;
    }

    @Override
    public int getResourceId() {
        return R.layout.recyclerview_item_doctor; //set your xml file id
    }

    @Override
    public void bindToHolder(ItemHolder itemHolder, Context context, Object o) {
        itemHolder.<TextView>find(R.id.item_recyclerview_tv1).setText(name);
        itemHolder.<TextView>find(R.id.item_recyclerview_tv2).setText(qualifications);
        itemHolder.<TextView>find(R.id.item_recyclerview_tv3).setText(currentWorkPlace);
        if(photoImageResId!=-1)
            itemHolder.<ImageView>find(R.id.imageView).setImageResource(photoImageResId);

    }

    public String getName() {
        return name;
    }

    public String getQualifications() {
        return qualifications;
    }

    public String getCurrentWorkPlace() {
        return currentWorkPlace;
    }
}

