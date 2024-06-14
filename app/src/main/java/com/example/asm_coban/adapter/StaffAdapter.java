package com.example.asm_coban.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.asm_coban.R;
import com.example.asm_coban.model.Staff;
import com.example.asm_coban.validate.Validate;

import java.util.ArrayList;
import java.util.List;

public class StaffAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Staff> staffs;
    private final ArrayList<Staff> staffsOld;
    private Activity activity;

    public StaffAdapter(ArrayList<Staff> staffs, Activity activity) {
        this.staffs = staffs;
        this.activity = activity;
        this.staffsOld = staffs;
    }

    @Override
    public int getCount() {
        return staffs.size();
    }

    @Override
    public Object getItem(int position) {
        return staffs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_card_staff, null);

        TextView tvId = (TextView) convertView.findViewById(R.id.txt_id);
        TextView tvName = (TextView) convertView.findViewById(R.id.txt_name);
        TextView tvRoom = (TextView) convertView.findViewById(R.id.txt_room);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgView);

        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);

        Staff staff = staffs.get(position);
        
        tvId.setText(staff.getId());
        tvName.setText(staff.getName());
        tvRoom.setText(staff.getRoom());
        imageView.setImageResource(staff.getImage());

        //Xóa
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Đã xóa " + staffs.get(position).getId(), Toast.LENGTH_SHORT).show();
                staffs.remove(position);
                notifyDataSetChanged();
            }
        });
        //Cập nhật
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(position);
            }
        });

        return convertView;
    }

    public void openDialog(int positon){
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.activity_add_staff);
        Window window = dialog.getWindow();

        List<Staff> list = new ArrayList<>();
        Spinner spinner = dialog.findViewById(R.id.spinner);

        list.add(new Staff("", "", "", R.drawable.a));
        list.add(new Staff("", "", "",R.drawable.b));
        list.add(new Staff("", "", "", R.drawable.c));
        list.add(new Staff("", "", "", R.drawable.d));
        list.add(new Staff("", "", "", R.drawable.e));
        list.add(new Staff("", "", "", R.drawable.f));
        list.add(new Staff("", "", "", R.drawable.g));
        list.add(new Staff("", "", "", R.drawable.h));

        ItemAdapter itemAdapter = new ItemAdapter(window.getContext(), R.layout.layout_item_spinner, list);
        spinner.setAdapter(itemAdapter);
        spinner.setSelection(positon);

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnEdit = dialog.findViewById(R.id.btn_add);
        EditText txtId = dialog.findViewById(R.id.txt_id_staff);
        EditText txtName = dialog.findViewById(R.id.txt_name_staff);
        EditText txtRoom = dialog.findViewById(R.id.txt_room_staff);
        TextView txtTitle = dialog.findViewById(R.id.txt_title_staff);

        Staff staff = staffs.get(positon);
        txtId.setText(staff.getId());
        txtName.setText(staff.getName());
        txtRoom.setText(staff.getRoom());

        txtTitle.setText("Chỉnh sửa thông tin");
        btnEdit.setText("Edit");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Staff staffSelected = (Staff) spinner.getSelectedItem();
                int img = staffSelected.getImage();
                Staff s = staffs.get(positon);

                if(!Validate.validate(txtId.getText().toString(), txtName.getText().toString(), txtRoom.getText().toString())){
                    Toast.makeText(activity, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                s.setId(txtId.getText().toString());
                s.setName(txtName.getText().toString());
                s.setRoom(txtRoom.getText().toString());
                s.setImage(img);
                Toast.makeText(activity, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                notifyDataSetChanged();
            }
        });

        if(window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String s = constraint.toString();
                if(s.isEmpty()){
                    staffs = staffsOld;
                }else{
                    ArrayList<Staff> listS = new ArrayList<>();
                    for (Staff staff :
                            staffsOld) {
                        if(staff.getName().toLowerCase().contains(s.toLowerCase())
                            || staff.getId().toLowerCase().contains(s.toLowerCase())
                            || staff.getRoom().toLowerCase().contains(s.toLowerCase())){
                            listS.add(staff);
                        }
                    }
                    staffs = listS;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = staffs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                staffs = (ArrayList<Staff>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
