package com.tth.test.model;

import java.util.ArrayList;

public class Friend {
    private String mName;
    private boolean mOnline;

    public Friend(String name, boolean online) {
        mName = name;
        mOnline = online;
    }

    public String getName() {
        return mName;
    }

    public boolean isOnline() {
        return mOnline;
    }

    private static int lastContactId = 0;

    public static ArrayList<Friend> createContactsList(int numContacts) {
        ArrayList<Friend> contacts = new ArrayList<Friend>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Friend("Person " + ++lastContactId, i <= numContacts / 2));
        }

        return contacts;
    }
}
