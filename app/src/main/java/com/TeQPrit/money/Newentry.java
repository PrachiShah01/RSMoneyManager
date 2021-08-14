package com.TeQPrit.money;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Newentry extends AppCompatActivity {
    DatabaseHandler db;
    EditText e1,e2,e3,e5,description;
    ImageView i1;
    Button b1;
    public final int PICK_CONTACT = 2015;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newentry);

        db = new DatabaseHandler(this);

        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        e5 = findViewById(R.id.e5);

        description = findViewById(R.id.discription);

        b1 = findViewById(R.id.b1);
        i1 = findViewById(R.id.contact);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        getWindow().setLayout((int)(width*.8),(int)(width*1.2));

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, PICK_CONTACT);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = e3.getText().toString().equals("")?"0":e3.getText().toString();
                String s1 = e2.getText().toString().equals("")?"0":e2.getText().toString();
                String s2 = description.getText().toString().equals("")?"-":description.getText().toString();

                if(e1.getText().toString().trim().equals("") || e5.getText().toString().trim().equals("")){
                    Toast.makeText(Newentry.this,"Enter Name And Contact",Toast.LENGTH_SHORT).show();
                }
                else {

                    if(s.equals("0") && s1.equals("0"))
                        s2 = "New Entry";

                    db.addContact(new Contact(e1.getText().toString(), ""+(Integer.valueOf(s1)-Integer.valueOf(s)),e5.getText().toString(),s2));

                    String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Calendar.getInstance().getTime());
                    String time2 = time.substring(0,4).concat("/").concat(time.substring(4,6)).concat("/").concat(time.substring(6,8)).concat("_").concat(time.substring(9,11)).concat(":").concat(time.substring(11,13)).concat(":").concat(time.substring(13,15));

                    db.addRecord(new Record(e1.getText().toString(),e5.getText().toString(),time2,""+(Integer.valueOf(s1)-Integer.valueOf(s)),""+(Integer.valueOf(s1)-Integer.valueOf(s)),s2));

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int column2 = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            e1.setText(cursor.getString(column2));
            e5.setText(cursor.getString(column));
        }
    }
}
