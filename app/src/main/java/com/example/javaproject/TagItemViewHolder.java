package com.example.javaproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class TagItemViewHolder extends ChildViewHolder implements View.OnClickListener{
    private TextView mTextView;

    public TagItemViewHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.txtItem);
        itemView.setOnClickListener(this);
    }

    public void bind(TagItem tagItem){
        mTextView.setText(tagItem.name);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();

//        Bundle bundle = new Bundle();
//        bundle.putString("Title", "test");
//        bundle.putInt("Image", 0);
//
//        // set Fragmentclass Arguments
//        FragmentManager fragmentManager = (Activity) v.getContext().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        PoIClickedFragment fragobj = new PoIClickedFragment();
//        fragobj.setArguments(bundle);
//
//        fragmentTransaction.replace(R.id.fragment_container, fragobj);
//        fragmentTransaction.commit();
    }
}
