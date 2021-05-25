package com.example.materialdesign.model;


/**
 *  Used to create a Table of Contents in the MainActivity
 *  The ExpandableRecyclerView Item requires a type so we know which one is the parent and which are its children
 */
public enum TableOfContentsType {

    NORMAL(1), TITLE(2), SUB_TITLE(3);

    private final int value;

    TableOfContentsType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}


