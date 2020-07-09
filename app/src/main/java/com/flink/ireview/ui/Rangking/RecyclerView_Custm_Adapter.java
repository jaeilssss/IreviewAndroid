package com.flink.ireview.ui.Rangking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flink.ireview.R;
import com.flink.ireview.RecyclerView.CustomAdapter;
import com.flink.ireview.RecyclerView.PostItem;

import java.util.ArrayList;

public class RecyclerView_Custm_Adapter extends RecyclerView.Adapter<RecyclerView_Custm_Adapter.CustomViewHolder>{
    private ArrayList<RecyclerView_PostItem> arrayList;

    public RecyclerView_Custm_Adapter(ArrayList<RecyclerView_PostItem> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postitem_porduct,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        //holder.thumbnail_image.setImageURI(arrayList.get(position));
        holder.name.setText(arrayList.get(position).getName());
        holder.category.setText(arrayList.get(position).getCategory());
        holder.writer.setText(arrayList.get(position).getWriter());
        holder.information.setText(arrayList.get(position).getInformation());
    }

    @Override
    public int getItemCount() {
        return (arrayList!=null ? arrayList.size():0);
    }


    //클래스는 같은 이름으로 두개는 못만드나??
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail_image;
        TextView name;
        TextView category;
        TextView writer;
        TextView information;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.thumbnail_image = itemView.findViewById(R.id.postitem_product_image);
            this.name=itemView.findViewById(R.id.postitem_product_name);
            this.category=itemView.findViewById(R.id.postitem_product_category);
            this.writer=itemView.findViewById(R.id.postitem_product_writer);
            this.information = itemView.findViewById(R.id.postitem_product_information);

        }
    }
}
