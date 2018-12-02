package com.example.emily.qrcontact;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.parameter.EmailType;
import ezvcard.property.StructuredName;

public class Make extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        Picasso.get().setLoggingEnabled(true);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Button refresh = (Button) findViewById(R.id.button);
        final EditText firstName = (EditText) findViewById(R.id.firstName);
        final EditText lastName = (EditText) findViewById(R.id.lastName);
        final EditText phone = (EditText) findViewById(R.id.phone);
        final EditText email = (EditText) findViewById(R.id.email);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VCard vcard = new VCard();
                StructuredName fullName = new StructuredName();
                fullName.setFamily(String.valueOf(lastName.getText()));
                fullName.setGiven(String.valueOf(firstName.getText()));
                vcard.setStructuredName(fullName);
                vcard.addEmail(String.valueOf(email.getText()), EmailType.WORK);
                vcard.addTelephoneNumber(String.valueOf(phone.getText()));
                String fullContact = Ezvcard.write(vcard).version(VCardVersion.V3_0).go();
                Log.d("myTag", fullContact);

                Log.d("myTag", "Registered Click");
                Picasso.get().load("https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + fullContact).into(imageView);




            }
        });
        loadImage(imageView);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_scan:
                        Intent intent1 = new Intent(Make.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_make:
                        break;
                    case R.id.navigation_help:
                        Intent intent3 = new Intent(Make.this, Help.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });


    }
    private void loadImage(ImageView toSet) {
        Log.d("fucntion", "Default code");
        Picasso.get().load("https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=Example").into(toSet);
    }

}
