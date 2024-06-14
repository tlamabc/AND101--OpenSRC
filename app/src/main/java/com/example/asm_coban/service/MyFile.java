package com.example.asm_coban.service;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import com.example.asm_coban.model.Account;
import com.example.asm_coban.model.Staff;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyFile {

    public static String FileAcc = "FileAcc.txt";
    public static String FileStaff = "FileStaff.txt";
    public static List<Staff> readFileList(Context context, String fileName){
        List<Staff> list = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (List<Staff>) ois.readObject();
            ois.close();
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static void writeFileList(Context context, String fileName, ArrayList<Staff> staffs){
        try {
            FileOutputStream fos = context.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(staffs);
            oos.flush();
            fos.close();
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void writeFileAcc(Context context, String fileName, List<Account> accounts){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(accounts);
            oos.flush();
            oos.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Account> readFileAcc(Context context, String fileName){
        List<Account> list = new ArrayList<>();
        try{
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);

            list =(List<Account>)ois.readObject();
            fis.close();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
