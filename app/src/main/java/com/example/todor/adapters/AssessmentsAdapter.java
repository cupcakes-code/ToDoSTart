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

import com.example.todor.AssessmentDetailsActivity;
import com.example.todor.CourseDetailActivity;
import com.example.todor.EditAssessmentActivity;
import com.example.todor.EditCourseActivity;
import com.example.todor.R;
import com.example.todor.database.AppDatabase;
import com.example.todor.database.Assessment;

import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentVH> {

    List<Assessment> list;
    Context context;
    AppDatabase appDatabase;

    public AssessmentsAdapter(List<Assessment> list, Context context) {
        this.list = list;
        this.context = context;
        appDatabase=AppDatabase.getDbInstance(context);
    }

    @NonNull
    @Override
    public AssessmentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_layout,null);
        return new AssessmentVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentVH holder, int position) {

        holder.tTv.setText(list.get(position).title);

        holder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appDatabase.assessmentDao().deleteAssessment(list.get(holder.getAdapterPosition()));
                list=appDatabase.assessmentDao().getAllAssessments(list.get(holder.getAdapterPosition()).courseId);
                AssessmentsAdapter.this.notifyDataSetChanged();
                Toast.makeText(context, "Asssessment deleted successfully", Toast.LENGTH_SHORT).show();

            }
        });

        holder.editIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, EditAssessmentActivity.class);
                intent.putExtra("id",list.get(holder.getAdapterPosition()).id);
                intent.putExtra("title",list.get(holder.getAdapterPosition()).title);
                intent.putExtra("ed",list.get(holder.getAdapterPosition()).endDate);
                intent.putExtra("courseId",list.get(holder.getAdapterPosition()).courseId);
                // test maybe
                //intent.putExtra("test", list.get(holder.getAdapterPosition()).test);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AssessmentDetailsActivity.class);
                intent.putExtra("id",list.get(holder.getAdapterPosition()).id);
                intent.putExtra("title",list.get(holder.getAdapterPosition()).title);
                intent.putExtra("ed",list.get(holder.getAdapterPosition()).endDate);
                //tetst
                //intent.putExtra("test", list.get(holder.getAdapterPosition()).test);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AssessmentVH extends RecyclerView.ViewHolder{
        TextView tTv;
        ImageView editIv,deleteIv;
        public AssessmentVH(@NonNull View itemView) {
            super(itemView);
            tTv=itemView.findViewById(R.id.tTv);
            deleteIv=itemView.findViewById(R.id.deleteIv);
            editIv=itemView.findViewById(R.id.editIv);
        }
    }
}
