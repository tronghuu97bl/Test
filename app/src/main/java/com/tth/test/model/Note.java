package com.tth.test.model;

import java.io.Serializable;

public class Note implements Serializable {
    private int noteid;
    private String title;
    private String content;
    private String last_mdf;
    private int checked;
    private int secure;

    public Note() {
    }

    public Note(int noteid, String title, String content, String last_mdf, int checked, int secure) {
        this.noteid = noteid;
        this.title = title;
        this.content = content;
        this.last_mdf = last_mdf;
        this.checked = checked;
        this.secure = secure;

    }

    public Note(String title, String content, String last_mdf, int checked, int secure) {
        this.title = title;
        this.content = content;
        this.last_mdf = last_mdf;
        this.checked = checked;
        this.secure = secure;
    }

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public int getSecure(){
        return secure;
    }
    public void setSecure(int secure){
        this.secure = secure;
    }
}
