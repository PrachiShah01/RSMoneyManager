package com.TeQPrit.money;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Updatec extends AppCompatActivity {

    EditText e1,e2,discription;
    TextView t1,t2;
    Button b1;
    ImageView b2,b3;
    ListView l1;
    List<Record> contacts = new ArrayList<>();
    List<Record> contacts2 = new ArrayList<>();
    DatabaseHandler db;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatec);

        t1 = findViewById(R.id.name);
        t2 = findViewById(R.id.con);

        e1 = findViewById(R.id.e2);
        e2 = findViewById(R.id.e3);
        discription = findViewById(R.id.discription);

        b1 = findViewById(R.id.btn1);
        b2 = findViewById(R.id.btn2);
        b3 = findViewById(R.id.btn3);

        l1 = findViewById(R.id.cycle);

        builder = new AlertDialog.Builder(Updatec.this);

        db = new DatabaseHandler(this);

        Intent intent = getIntent();
        final String s2 = intent.getStringExtra("name");
        final String s1 = intent.getStringExtra("id");
        final String s5 = intent.getStringExtra("contact");

        contacts = db.getAllRecords();

        for (int i = 0; i < contacts.size() ; i++) {
            if(contacts.get(i).getName().equals(s2) && contacts.get(i).get_contact().equals(s5))
            {
                contacts2.add(contacts.get(i));
            }
        }
        custom newCustom = new custom();
        l1.setAdapter(newCustom);

        t1.setText(s2);
        t2.setText(s5);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = e1.getText().toString().equals("")?"0":e1.getText().toString();
                String s12 = e2.getText().toString().equals("")?"0":e2.getText().toString();
                String s13 = discription.getText().toString().equals("")?"-":discription.getText().toString();

                if(s.equals("0") && s12.equals("0")) {
                    Toast.makeText(getApplicationContext(),"Enter Values",Toast.LENGTH_SHORT).show();
                }
                else {
                    db.updatecon(s2, s5, s, s12, s1, s13);

                    Contact c1 = db.getContact(Integer.valueOf(s1));
                    String total = c1.get_total();
                    String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Calendar.getInstance().getTime());
                    String time2 = time.substring(0, 4).concat("/").concat(time.substring(4, 6)).concat("/").concat(time.substring(6, 8)).concat("_").concat(time.substring(9, 11)).concat(":").concat(time.substring(11, 13)).concat(":").concat(time.substring(13, 15));

                    db.addRecord(new Record(s2, s5, time2, "" + (Integer.valueOf(s) - Integer.valueOf(s12)), total, s13));

                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                    finish();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Are You Sure?") .setTitle("Delete");

                builder.setMessage("Are You Sure?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                for (int i = 0; i < contacts2.size() ; i++) {
                                    db.deleterec(contacts2.get(i).getID());
                                }

                                Contact c1 = db.getContact(Integer.valueOf(s1));
                                String total = c1.get_total();
                                String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                                String time2 = time.substring(0,4).concat("/").concat(time.substring(4,6)).concat("/").concat(time.substring(6,8)).concat("_").concat(time.substring(9,11)).concat(":").concat(time.substring(11,13)).concat(":").concat(time.substring(13,15));

                                db.addRecord(new Record(s2,s5,time2,total,total,"Deleted, Last Transaction"));

                                db.deletecon(Integer.valueOf(s1));

                                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent1);
                                finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle("Delete");
                alert.show();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact c1 = db.getContact(Integer.valueOf(s1));
                Uri uri = Uri.parse("smsto:"+s5);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                if(Integer.valueOf(c1.get_total())<0)
                {
                    intent.putExtra("sms_body", "Hello "+c1.getName()+"\nI owe you "+c1.get_total()+" RS. I will give you ASAP.");
                }else if(Integer.valueOf(c1.get_total())>0)
                {
                    intent.putExtra("sms_body", "Hello "+c1.getName()+"\nI need "+c1.get_total()+" RS from you. Please give me ASAP.");
                }
                startActivity(intent);
            }
        });
    }

    class custom extends BaseAdapter {

        @Override
        public int getCount() {
            return contacts2.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.layers2,parent,false);

            TextView t2 = convertView.findViewById(R.id.total);
            TextView t3 = convertView.findViewById(R.id.time);
            TextView t4 = convertView.findViewById(R.id.total2);
            TextView t6 = convertView.findViewById(R.id.discription);

            t2.setText(contacts2.get(position).get_total());
            t3.setText(contacts2.get(position).get_date());
            t4.setText(contacts2.get(position).get_total2());
            t6.setText(contacts2.get(position).getDiscription());

            return convertView;
        }
    }
}
