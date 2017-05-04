package com.example.bigyoung.diyview.bean;

import java.util.List;

/**
 * Created by BigYoung on 2017/4/25.
 */

public class HomeResponseBean {

    private List<String> picture;
    private List<ItemBean> list;

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public List<ItemBean> getList() {
        return list;
    }

    public void setList(List<ItemBean> list) {
        this.list = list;
    }

}
