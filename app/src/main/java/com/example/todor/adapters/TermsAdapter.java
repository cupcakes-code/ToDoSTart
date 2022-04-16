package com.example.todor.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todor.App;
import com.example.todor.R;
import com.example.todor.TermDetailActivity;
import com.example.todor.database.AppDatabase;
import com.example.todor.database.Course;
import com.example.todor.database.Term;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermVH> {

    List<Term> list;
    Context context;
    AppDatabase appDatabase;

    public TermsAdapter(List<Term> list, Context context) {
        this.list = list;
        this.context = context;
        appDatabase= AppDatabase.getDbInstance(context);
    }

    @NonNull
    @Override
    public TermVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.term_list_layout,null);
        return new TermVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermVH holder, int position) {
        holder.tTv.setText(list.get(position).title);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, TermDetailActivity.class);
                intent.putExtra("id",list.get(holder.getAdapterPosition()).id);
                intent.putExtra("title",list.get(holder.getAdapterPosition()).title);
                intent.putExtra("sd",list.get(holder.getAdapterPosition()).startDate);
                intent.putExtra("ed",list.get(holder.getAdapterPosition()).endDate);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Course> courseList=appDatabase.courseDao().getAllCourses(list.get(holder.getAdapterPosition()).id);
                if (courseList.size()>0){
                    Toast.makeText(context, "This term has 1 or more courses. Delete courses before deleting the whole term", Toast.LENGTH_SHORT).show();

                }else {
                    appDatabase.termDao().deleteTerm(list.get(holder.getAdapterPosition()));
                    list=appDatabase.termDao().getAllTerms();
                    TermsAdapter.this.notifyDataSetChanged();
                    Toast.makeText(context, "Term deleted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TermVH extends RecyclerView.ViewHolder{
        TextView tTv;
        ImageView deleteIv;
        public TermVH(@NonNull View itemView) {
            super(itemView);
            tTv=itemView.findViewById(R.id.tTv);
            deleteIv=itemView.findViewById(R.id.deleteIv);
        }
    }
}
