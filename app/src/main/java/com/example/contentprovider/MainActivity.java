package com.example.contactdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.Manifest;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnGetContactPressed(View v) {
        getPhoneContacts();
    }

    private void getPhoneContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
        } else {
            ContentResolver contentResolver = getContentResolver();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            Log.i("CONTACT_PROVIDER_DEMO", "TOTAL # of Contacts ::: " + Integer.toString(cursor.getCount()));

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    if (nameIndex >= 0 && numberIndex >= 0) {
                        String contactName = cursor.getString(nameIndex);
                        String contactNumber = cursor.getString(numberIndex);
                        Log.i("CONTACT_PROVIDER_DEMO", "Contact Name  ::: " + contactName + "   Ph #     ::: " + contactNumber);
                    } else {
                        Log.e("CONTACT_PROVIDER_DEMO", "Invalid column index for DISPLAY_NAME or NUMBER");
                    }
                }
            }
            cursor.close();
        }
    }
}
