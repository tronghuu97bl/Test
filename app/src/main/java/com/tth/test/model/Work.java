package com.tth.test.model;

import java.io.Serializable;

public class Work implements Serializable {
    private int workid;
    private String content;
    private String last_mdf; //last modify
    private int checked;//0 la chua hoan thanh, 1 la da hoan thanh

    public Work() {
    }

    public Work(String content, String last_mdf, int checked) {
        this.content = content;
        this.last_mdf = last_mdf;
        this.checked = checked;
    }

    public Work(int workid, String content, String last_mdf, int checked) {
        this.workid = workid;
        this.content = content;
        this.last_mdf = last_mdf;
        this.checked = checked;
    }

    public int getWorkid() {
        return workid;
    }

    public void setWorkid(int workid) {
        this.workid = workid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLast_mdf() {
        return last_mdf;
    }

    public void setLast_mdf(String last_mdf) {
        this.last_mdf = last_mdf;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
