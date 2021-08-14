package com.TeQPrit.money;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler db;
    List<Contact> contacts;
    ListView r1;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        r1 = findViewById(R.id.cycle);

        db = new DatabaseHandler(this);
        contacts = db.getAllContacts();

        custom newCustom = new custom();
        r1.setAdapter(newCustom);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Newentry.class);
                startActivity(intent);
            }
        });

    }

    class custom extends BaseAdapter {

        @Override
        public int getCount() {
            return contacts.size();
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
        public View getView(final int position,View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.layers,parent,false);

            TextView t1 = convertView.findViewById(R.id.name);
            TextView t2 = convertView.findViewById(R.id.rs1);
            t1.setText(contacts.get(position).getName());
            t2.setText(contacts.get(position).get_total());
            builder = new AlertDialog.Builder(MainActivity.this);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),Updatec.class);
                    intent.putExtra("name",contacts.get(position).getName());
                    intent.putExtra("id",""+contacts.get(position).getID());
                    intent.putExtra("contact",""+contacts.get(position).get_contact());
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

}
