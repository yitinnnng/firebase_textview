package com.example.firebase_textview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.CDATASection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {




    Button b1,b2,b3;
    TextView t1,t2;
    ListView LV;
    int x_last = 0;
    String x_select;

    TextString text = new TextString(MainActivity.this);

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        t1=(TextView)findViewById(R.id.editText);
        t2=(TextView)findViewById(R.id.editText2);
        LV=(ListView)findViewById(R.id.LV);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("data_text");
        final DatabaseReference myRef2 = myRef.child("data01");

        firebase_select(myRef2);

        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                x_last+=1;
                myRef2.child(String.valueOf(x_last)).setValue(new TextString(t1.getText().toString(),t2.getText().toString()));
                firebase_select(myRef2);

            }
            });
        b2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                myRef2.child(x_select).setValue(new TextString(t1.getText().toString(),t2.getText().toString()));
                firebase_select(myRef2);


            }
        });
        b3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                myRef2.child(x_select).removeValue();
                firebase_select(myRef2);

            }
        });

        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView t1_s = (TextView)view.findViewById(R.id.text1);
                x_select = t1_s.getText().toString();
                TextView t2_s = (TextView)view.findViewById(R.id.text2);
                t1.setText(t2_s.getText().toString());
                TextView t3_s = (TextView)view.findViewById(R.id.text3);
                t2.setText(t3_s.getText().toString());

            }
        });

    }

    private void firebase_select(DatabaseReference db) {
        final List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int x_sum = (int)dataSnapshot.getChildrenCount();

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    TextString user_data = ds.getValue(TextString.class);
                    Map<String,Object> item = new HashMap<String,Object>();
                    item.put("id",ds.getKey());
                    item.put("z1",user_data.getZ1());
                    item.put("z3",user_data.getZ2());
                    items.add(item);
                    x_last = Integer.parseInt(ds.getKey());


                }
                SimpleAdapter SA = new SimpleAdapter(context,items,R.layout.textstring,new String[]{"id","z1","z2"},new int []{R.id.text1,R.id.text2,R.id.text3});
                LV.setAdapter(SA);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}