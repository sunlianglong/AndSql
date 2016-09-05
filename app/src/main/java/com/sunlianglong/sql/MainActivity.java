package com.sunlianglong.sql;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {

    private SimpleCursorAdapter adapter;
    private Button btnAdd;
    private EditText tvSex,tvName;
    private Db db;
    private SQLiteDatabase dbRead,dbWrite;
    private View.OnClickListener btAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues cv =new ContentValues();
            cv.put("name",tvName.getText().toString());
            cv.put("sex",tvSex.getText().toString());

            dbWrite.insert("user", null, cv);
            refreshListView();

        }
    };
    private AdapterView.OnItemLongClickListener ListViewItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {

            new AlertDialog.Builder(MainActivity.this).setTitle("提醒").setMessage("您确定要删除吗？").setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog,int which){

                            Cursor c = adapter.getCursor();
                            c.moveToPosition(position);

                            int itemId = c.getInt(c.getColumnIndex("_id"));
                            dbWrite.delete("user", "_id=?", new String[]{itemId + ""});
                            refreshListView();

                        }
                    }).show();


            return true;  //系统本身的反馈：震动声音等
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvName=(EditText)findViewById(R.id.etName);
        tvSex=(EditText)findViewById(R.id.etSex);
        btnAdd = (Button) findViewById(R.id.btAdd);
        btnAdd.setOnClickListener(btAddListener);

        db =new Db(this);
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();


        adapter = new SimpleCursorAdapter(this,R.layout.layout_user,null,new String[]{"name","sex"},new int[]{R.id.tvName,R.id.tvSex});
        setListAdapter(adapter);
        refreshListView();

        getListView().setOnItemLongClickListener(ListViewItemLongClickListener);

 //         Db db = new Db(this);
//        SQLiteDatabase dbWrite =db.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("name","小张");
//        cv.put("sex","女");
//        dbWrite.insert("uesr", null, cv);
//
//        cv=new ContentValues();
//        cv.put("name","小李");
//        cv.put("sex","男");
//        dbWrite.insert("user",null,cv);
//
//        dbWrite.close();

//        SQLiteDatabase dbRead = db.getReadableDatabase();
//         Cursor c = dbRead.query("uesr", null, null, null, null, null, null);
//        while(c.moveToNext()){
//          String name =  c.getString(c.getColumnIndex("name"));
//          String sex =  c.getString(c.getColumnIndex("sex"));
//            System.out.println(String.format("name=%s,sex=%s",name,sex));

        }
        private void refreshListView(){
            Cursor c =dbRead.query("user",null,null,null,null,null,null);
            adapter.changeCursor(c);//刷新
        }

}

