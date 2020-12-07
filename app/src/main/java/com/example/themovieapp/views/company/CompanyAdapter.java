package com.example.themovieapp.views.company;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themovieapp.R;
import com.example.themovieapp.models.entities.ProductionCompany;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder> {

    private ArrayList<ProductionCompany> mlist;

    public CompanyAdapter(ArrayList<ProductionCompany> mlist) {
        this.mlist = mlist;
    }

    public CompanyAdapter() {

    }

    @NonNull
    @Override
    public CompanyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_item, viewGroup, false);

        return new CompanyAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.MyViewHolder myViewHolder, int i) {

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + mlist.get(i).getLogo_path()).into(myViewHolder.img_logo);
        myViewHolder.txt_company_name.setText(mlist.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void updateCompanyAdapter(ArrayList<ProductionCompany> mList) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_company_name;
        private ImageView img_logo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_company_name = (TextView) itemView.findViewById(R.id.txt_company_name);
            img_logo = (ImageView) itemView.findViewById(R.id.img_logo_comp);
        }
    }
}
