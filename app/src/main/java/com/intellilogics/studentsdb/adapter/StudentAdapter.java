package com.intellilogics.studentsdb.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.intellilogics.studentsdb.R;
import com.intellilogics.studentsdb.activity.UpdateActivity;
import com.intellilogics.studentsdb.db.DBHelper;
import com.intellilogics.studentsdb.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentVH> {

    ArrayList<Student> students;
    Context context;

    public StudentAdapter(ArrayList<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_student,parent,false);
        StudentVH svh = new StudentVH(view);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentVH holder, int position) {
        Student s = students.get(position);
        holder.tvName.setText(s.getName());
        holder.tvCourse.setText(s.getCourse());
        holder.tvMobile.setText(s.getMobile());
        holder.tvTotal.setText(String.valueOf(s.getTotalFee()));
        holder.tvPaid.setText(String.valueOf((s.getFeePaid())));






        holder.cardUpdate.setOnClickListener((view) ->  {
                //Toast.makeText(context, s.getName() + " will be update!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("STUDENT",s);
                context.startActivity(intent);

        });





        holder.cardDelete.setOnClickListener((view) ->  {
            //Toast.makeText(context, s.getName() + " will be deleted!", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirmation!!!!");
            builder.setMessage("Are you sure to delete " + s.getName() + "?");
            builder.setIcon(android.R.drawable.ic_menu_delete);
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DBHelper dbHelper = new DBHelper(context);

                    int result = dbHelper.deleteStudent(s.getId());

                    if(result>0)
                    {
                        Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                        students.remove(s);
                        notifyDataSetChanged();
                    }
                    else
                    {
                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("No",null);
            builder.show();

        });

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentVH extends RecyclerView.ViewHolder{
        TextView tvName, tvCourse, tvMobile, tvTotal, tvPaid;
        CardView cardUpdate, cardDelete;


        public StudentVH(@NonNull View v) {
            super(v);
            tvName=v.findViewById(R.id.tvName);
            tvCourse=v.findViewById(R.id.tvCourse);
            tvMobile=v.findViewById(R.id.tvMobile);
            tvTotal=v.findViewById(R.id.tvTotal);
            tvPaid=v.findViewById(R.id.tvPaid);

            cardDelete=v.findViewById(R.id.cardDelete);
            cardUpdate=v.findViewById(R.id.cardUpdate);

        }
    }
}
