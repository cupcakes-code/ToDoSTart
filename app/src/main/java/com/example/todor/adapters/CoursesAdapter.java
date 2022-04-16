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

import com.example.todor.AddCourseActivity;
import com.example.todor.CourseDetailActivity;
import com.example.todor.EditCourseActivity;
import com.example.todor.R;
import com.example.todor.TermDetailActivity;
import com.example.todor.database.AppDatabase;
import com.example.todor.database.Course;
import com.example.todor.database.Term;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseVH> {

    List<Course> list;
    Context context;
    AppDatabase appDatabase;

    public CoursesAdapter(List<Course> list, Context context) {
        this.list = list;
        this.context = context;
        appDatabase=AppDatabase.getDbInstance(context);
    }

    @NonNull
    @Override
    public CourseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_layout,null);
        return new CourseVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseVH holder, int position) {
        holder.tTv.setText(list.get(position).title);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CourseDetailActivity.class);
                intent.putExtra("id",list.get(holder.getAdapterPosition()).id);
                intent.putExtra("title",list.get(holder.getAdapterPosition()).title);
                intent.putExtra("sd",list.get(holder.getAdapterPosition()).startDate);
                intent.putExtra("ed",list.get(holder.getAdapterPosition()).endDate);
                intent.putExtra("status",list.get(holder.getAdapterPosition()).status);
                intent.putExtra("in",list.get(holder.getAdapterPosition()).instructorName);
                intent.putExtra("ip",list.get(holder.getAdapterPosition()).instructorPhone);
                intent.putExtra("ie",list.get(holder.getAdapterPosition()).instructorEmail);
                intent.putExtra("note",list.get(holder.getAdapterPosition()).note);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    appDatabase.courseDao().deleteCourse(list.get(holder.getAdapterPosition()));
                    list=appDatabase.courseDao().getAllCourses(list.get(holder.getAdapterPosition()).termId);
                    CoursesAdapter.this.notifyDataSetChanged();
                    Toast.makeText(context, "Course deleted successfully", Toast.LENGTH_SHORT).show();

            }
        });

        holder.editIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, EditCourseActivity.class);
                intent.putExtra("id",list.get(holder.getAdapterPosition()).id);
                intent.putExtra("title",list.get(holder.getAdapterPosition()).title);
                intent.putExtra("sd",list.get(holder.getAdapterPosition()).startDate);
                intent.putExtra("ed",list.get(holder.getAdapterPosition()).endDate);
                intent.putExtra("status",list.get(holder.getAdapterPosition()).status);
                intent.putExtra("in",list.get(holder.getAdapterPosition()).instructorName);
                intent.putExtra("ip",list.get(holder.getAdapterPosition()).instructorPhone);
                intent.putExtra("ie",list.get(holder.getAdapterPosition()).instructorEmail);
                intent.putExtra("note",list.get(holder.getAdapterPosition()).note);
                intent.putExtra("termId",list.get(holder.getAdapterPosition()).termId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CourseVH extends RecyclerView.ViewHolder{
        TextView tTv;
        ImageView editIv,deleteIv;
        public CourseVH(@NonNull View itemView) {
            super(itemView);
            tTv=itemView.findViewById(R.id.tTv);
            deleteIv=itemView.findViewById(R.id.deleteIv);
            editIv=itemView.findViewById(R.id.editIv);
        }
    }
}
