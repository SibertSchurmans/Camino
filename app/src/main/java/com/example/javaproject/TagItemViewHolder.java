package com.example.javaproject;

import android.app.Activity;
import android.content.Intent;
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
    String fullName, name, id;

    public TagItemViewHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.txtItem);
        itemView.setOnClickListener(this);
    }

    public void bind(TagItem tagItem){
        fullName = tagItem.name;
        String[] parts = fullName.split("@");
        name = parts[0];
        id = parts[1];
        mTextView.setText(name);
    }

    @Override
    public void onClick(View v) {


        Intent intent = new Intent(v.getContext(), PoiClickedActivity.class);
        intent.putExtra("Id", Integer.parseInt(id));
        v.getContext().startActivity(intent);

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
