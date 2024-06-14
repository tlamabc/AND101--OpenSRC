package com.example.asm_coban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.asm_coban.adapter.ItemAdapter;
import com.example.asm_coban.adapter.StaffAdapter;
import com.example.asm_coban.model.Staff;
import com.example.asm_coban.service.MyFile;
import com.example.asm_coban.validate.Validate;

import java.util.ArrayList;
import java.util.List;

public class QuanLyNhanVien extends AppCompatActivity {
    String fileName = "ASM_QuanLyNhanVien.txt";

    StaffAdapter staffAdapter;
    Spinner spinner;
    Dialog dialog;
    ArrayList<Staff> staffs;
    Toolbar toolbar;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlinhanvien);

        listView = findViewById(R.id.lstStaff);

        toolbar = findViewById(R.id.toolbar_staff);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_24);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        staffs =(ArrayList<Staff>) MyFile.readFileList(this, fileName);

        if(staffs.isEmpty()){
            staffs = new ArrayList<>();
            staffs.add(new Staff("NV001", "Vu Ba Huan", "Đào tạo", R.drawable.a));
            staffs.add(new Staff("NV002", "Nguyen Van An", "Kế toán",R.drawable.b));
            staffs.add(new Staff("NV003", "Nguyen Thi Van", "Hành chính", R.drawable.c));
            staffs.add(new Staff("NV004", "Tran Thi B", "Đào tạo", R.drawable.d));
            staffs.add(new Staff("NV005", "Pham Thi C", "Hành chính", R.drawable.e));
            staffs.add(new Staff("NV006", "Le Van D", "Đào tạo", R.drawable.f));
            staffs.add(new Staff("NV007", "Vu Ba Huy", "Kế toán", R.drawable.g));
            staffs.add(new Staff("NV008", "Phi Van F", "Hành chính", R.drawable.h));
        }

        staffAdapter = new StaffAdapter(staffs, this);
        listView.setAdapter(staffAdapter);


        dialog = new Dialog(QuanLyNhanVien.this);

        dialog.setContentView(R.layout.activity_add_staff);
        Window window = dialog.getWindow();
        if(window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        spinner = dialog.findViewById(R.id.spinner);

        List<Staff> list = new ArrayList<>();

        list.add(new Staff("", "", "", R.drawable.a));
        list.add(new Staff("", "", "",R.drawable.b));
        list.add(new Staff("", "", "", R.drawable.c));
        list.add(new Staff("", "", "", R.drawable.d));
        list.add(new Staff("", "", "", R.drawable.e));
        list.add(new Staff("", "", "", R.drawable.f));
        list.add(new Staff("", "", "", R.drawable.g));
        list.add(new Staff("", "", "", R.drawable.h));

        ItemAdapter itemAdapter = new ItemAdapter(this, R.layout.layout_item_spinner, list);
        spinner.setAdapter(itemAdapter);
    }

    public void clearForm(EditText txtId, EditText txtName, EditText txtRoom){
        txtId.setText("");
        txtName.setText("");
        txtRoom.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.item_search_toolbar).getActionView();

        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.baseline_search_24));
        MenuItem itemAddMember = menu.findItem(R.id.item_add_member);

        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//        searchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        searchAutoComplete.setHint("Nhập mã, tên nhân viên hoặc phòng ban");
        searchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));

        searchView = (SearchView) menu.findItem(R.id.item_search_toolbar).getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setNavigationIcon(null);
                itemAddMember.setVisible(false);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_24);
                itemAddMember.setVisible(true);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Lấy dữ liệu khi người dùng nhấn nút tìm kiếm
                Toast.makeText(QuanLyNhanVien.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý sự kiện khi người dùng nhập liệu vào SearchView
                staffAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_search_toolbar){

        }
        if(id == R.id.item_add_member){
            dialog.show();
            Button btnCancel = dialog.findViewById(R.id.btn_cancel);
            Button btnAdd = dialog.findViewById(R.id.btn_add);
            EditText txtId = dialog.findViewById(R.id.txt_id_staff);
            EditText txtName = dialog.findViewById(R.id.txt_name_staff);
            EditText txtRoom = dialog.findViewById(R.id.txt_room_staff);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearForm(txtId, txtName, txtRoom);
                    dialog.dismiss();
                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = txtId.getText().toString();
                    String name = txtName.getText().toString();
                    String room = txtRoom.getText().toString();
                    Staff staffSelected = (Staff) spinner.getSelectedItem();
                    int img = staffSelected.getImage();

                    if(!Validate.validate(id, name, room)){
                        Toast.makeText(QuanLyNhanVien.this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    staffs.add(new Staff(id, name, room, img));

                    StaffAdapter myAdapter = new StaffAdapter(staffs, QuanLyNhanVien.this);
                    listView.setAdapter(myAdapter);
                    clearForm(txtId, txtName, txtRoom);
                    dialog.cancel();
                    Toast.makeText(QuanLyNhanVien.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyFile.writeFileList(this, fileName, staffs);
    }

//    public List<Staff> readFile(){
//        List<Staff> list = new ArrayList<>();
//        try {
//            FileInputStream fis = openFileInput(fileName);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            list = (List<Staff>) ois.readObject();
//            ois.close();
//            fis.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return list;
//    }
//    public void writeFile(ArrayList<Staff> staffs){
//        try {
//            FileOutputStream fos = this.openFileOutput(this.fileName, MODE_PRIVATE);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(this.staffs);
//            Log.e("Check", "test");
//            oos.flush();
//            fos.close();
//            oos.close();
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.e("Check", "Chek");
//        }
//    }
}