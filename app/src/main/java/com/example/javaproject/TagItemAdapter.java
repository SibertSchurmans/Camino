package com.example.javaproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class TagItemAdapter extends ExpandableRecyclerViewAdapter<TagViewHolder, TagItemViewHolder>{

    public TagItemAdapter(List<? extends ExpandableGroup> groups)  {
        super(groups);
    }


    @Override
    public TagViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_tag, parent, false);

        return new TagViewHolder(v);
    }

    @Override
    public TagItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_tag_item, parent, false);
        return new TagItemViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(TagItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final TagItem tagItem = (TagItem) group.getItems().get(childIndex);
        holder.bind(tagItem);
    }

    @Override
    public void onBindGroupViewHolder(TagViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Tag tag = (Tag) group;
        holder.bind(tag);
    }


}
