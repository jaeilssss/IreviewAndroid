package com.flink.ireview.Recycler_category_tree;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;

import java.util.ArrayList;

public class CategorytreeAdapter extends RecyclerView.Adapter<CategorytreeAdapter.CustomViewHolder>{

    private ArrayList<Integer> arrayList;
    private int categoryId;

    public CategorytreeAdapter(ArrayList<Integer> arrayList, int categoryId) {
        this.arrayList = arrayList;
        this.categoryId = categoryId;
    }

    @NonNull
    @Override
    public CategorytreeAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_tree_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategorytreeAdapter.CustomViewHolder holder, int position) {
            setIcon(holder.categoryCheckBox,arrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return 17;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView category_tree_item;
        protected CheckBox categoryCheckBox;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.category_tree_item = (TextView)itemView.findViewById(R.id.category_tree_item);
            this.categoryCheckBox = itemView.findViewById(R.id.category_tree_item);
        }
    }

    public void setIcon(CheckBox checkBox , int position){
        switch (position){
            case 0:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_fashion_chk);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_fashion);

                }
                break;
            case 1:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_health_chk);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_health);
                }

                break;
            case 2:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_beauty_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_beauty);
                }
                break;
            case 3:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_culture_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_culture);
                }

                break;
            case 4:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_life_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_life);

                }

                break;
            case 5:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_education_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_education);
                }

                break;
            case 6:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_interial_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_interial);
                }

                break;
            case 7:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_book_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_book);

                }

                break;
            case 8:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_appliances_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_appliances);

                }

                break;
            case 9:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_kids_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_kids);
                }

                break;
            case 10:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_it_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_it);
                }

                break;
            case 11:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_pet_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_pet);
                }

                break;
            case 12:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_car_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_car);
                }

                break;
            case 13:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_hobby_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_hobby);
                }

                break;
            case 14:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_sport_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_sport);
                }

                break;
            case 15:
                if(categoryId==position){
                    checkBox.setButtonDrawable(R.drawable.s_cate_music_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_music);
                }
                break;
            case 16:
                if(categoryId==position)
                {
                    checkBox.setButtonDrawable(R.drawable.s_cate_travel_chk);
                    checkBox.setChecked(true);
                }else{
                    checkBox.setButtonDrawable(R.drawable.s_cate_travel);
                }

                break;
        }
    }
}
