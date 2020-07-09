package com.example.zipline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zipline.API.BookingAPI;
import com.example.zipline.Model.BookingModel;
import com.example.zipline.R;
import com.example.zipline.ViewBooking;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder>implements Filterable {

    Context context;
    List<BookingModel> bookingModels;
    List<BookingModel> bookingModelsFilter;

    public BookingAdapter(Context context, List<BookingModel> bookingModels) {
        this.context = context;
        this.bookingModels = bookingModels;
        this.bookingModelsFilter = bookingModels;
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.recyclear_view_booking_history, parent, false );
        return new BookingAdapter.BookingHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, final int position) {
        holder.booking_layout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));
        holder.tv_date_recyclcler_view.setText(bookingModelsFilter.get(position).getDate());
        holder.tv_tourist_recyclear.setText(bookingModelsFilter.get(position).getTourist());
        holder.tv_zipline_type_recyclear.setText(bookingModelsFilter.get(position).getZipline_type());
        holder.tv_view_recyclearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewBooking.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("booking_id",bookingModelsFilter.get(position).getBooking_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingModelsFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String Key= charSequence.toString();
                if(Key.isEmpty()){
                    bookingModelsFilter=bookingModels;
                }else{
                    List<BookingModel> listFiltered = new ArrayList<>();
                    for(BookingModel row :bookingModels){
                        if(row.getZipline_type().toLowerCase().contains(Key.toLowerCase())){
                            listFiltered.add(row);
                        }
                    } bookingModelsFilter=listFiltered;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=bookingModelsFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                bookingModelsFilter=(List<BookingModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class BookingHolder extends RecyclerView.ViewHolder{
        private TextView tv_date_recyclcler_view,tv_zipline_type_recyclear,tv_tourist_recyclear,tv_view_recyclearView;
        private LinearLayout booking_layout;
        public BookingHolder(@NonNull View itemView) {
            super(itemView);
            booking_layout= itemView.findViewById(R.id.bookingHistory_layout);
            tv_date_recyclcler_view= itemView.findViewById(R.id.tv_date_recyclcler_view);
            tv_zipline_type_recyclear= itemView.findViewById(R.id.tv_zipline_type_recyclear);
            tv_tourist_recyclear= itemView.findViewById(R.id.tv_tourist_recyclear);
            tv_view_recyclearView= itemView.findViewById(R.id.tv_view_recyclearView);

        }
    }
}
