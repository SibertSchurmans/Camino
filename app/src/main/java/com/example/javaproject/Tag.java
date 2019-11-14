package com.example.javaproject;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Tag extends ExpandableGroup<TagItem> {
    public Tag(String title, List<TagItem> items) {
        super(title, items);
    }
}
