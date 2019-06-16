package com.example.travelingagent.util.model;

/**
 * Created by hbh on 2017/4/20.
 * 实体类，模拟数据
 */

public class DataBean {

    public static final int PARENT_ITEM = 0;//父布局
    public static final int CHILD_ITEM = 1;//子布局

    private int type;// 显示类型
    private boolean isExpand;// 是否展开
    private DataBean childBean;

    private String ID;
    private String parentLeftTxt;
    private String parentRightTxt;
    private String childLeftTxt;
    private int childRightImage;

    public String parentbottomRightTxt;

    public String getParentLeftTxt() {
        return parentLeftTxt;
    }

    public int getChildRightImage() {
        return childRightImage;
    }


    public String getChildLeftTxt() {
        return childLeftTxt;
    }

    public String getParentRightTxt() {
        return parentRightTxt;
    }

    public String getParentbottomRightTxt() {
        return parentbottomRightTxt;
    }


    public void setParentLeftTxt(String parentLeftTxt) {
        this.parentLeftTxt = parentLeftTxt;
    }

    public void setChildLeftTxt(String childLeftTxt) {
        this.childLeftTxt = childLeftTxt;
    }

    public void setChildRightImage(int childRightImage) {
        this.childRightImage = childRightImage;
    }

    public void setParentRightTxt(String parentRightTxt) {
        this.parentRightTxt = parentRightTxt;
    }

    public void setParentbottomRightTxt(String parentbottomRightTxt) {
        this.parentbottomRightTxt = parentbottomRightTxt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public DataBean getChildBean() {
        return childBean;
    }

    public void setChildBean(DataBean childBean) {
        this.childBean = childBean;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
