package lab04.eim.systems.cs.pub.ro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {
    public static final int CONTACTS_MANAGER_REQUEST_CODE = 1;

    private Button additionalFieldsButton, saveButton, cancelButton;
    private EditText nameEditText, phoneEditText, emailEditText, addressEditText,
        jobEditText, companyEditText, websiteEditText, imEditText;
    private LinearLayout additionalFieldsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        phoneEditText = (EditText) findViewById(R.id.phone_edit_text);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        addressEditText = (EditText) findViewById(R.id.address_edit_text);

        additionalFieldsLayout = (LinearLayout) findViewById(R.id.additional_fields_container);

        jobEditText = (EditText) findViewById(R.id.job_edit_text);
        companyEditText = (EditText) findViewById(R.id.company_edit_text);
        websiteEditText = (EditText) findViewById(R.id.website_edit_text);
        imEditText = (EditText) findViewById(R.id.im_edit_text);

        additionalFieldsButton = (Button) findViewById(R.id.additional_fields_btn);
        additionalFieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (additionalFieldsLayout.getVisibility() == View.INVISIBLE) {
                    additionalFieldsButton.setText("Hide Additional Fields");
                    additionalFieldsLayout.setVisibility(View.VISIBLE);
                } else {
                    additionalFieldsButton.setText("Show Additional Fields");
                    additionalFieldsLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        saveButton = (Button) findViewById(R.id.save_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveContactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                saveContactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();

                String jobTitle = jobEditText.getText().toString();
                String company = companyEditText.getText().toString();
                String website = websiteEditText.getText().toString();
                String im = imEditText.getText().toString();

                if (name != null) {
                    saveContactIntent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }

                if (phone != null) {
                    saveContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }

                if (email != null) {
                    saveContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }

                if (address != null) {
                    saveContactIntent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }

                if (jobTitle != null) {
                    saveContactIntent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }

                if (company != null) {
                    saveContactIntent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }

                ArrayList<ContentValues> contactData = new ArrayList<>();

                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }

                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }

                saveContactIntent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivityForResult(saveContactIntent, CONTACTS_MANAGER_REQUEST_CODE);
            }
        });

        cancelButton = (Button) findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        });

        Intent intent = getIntent();

        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");

            if (phone != null) {
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(ContactsManagerActivity.this, "Phone number received from intent is null!", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONTACTS_MANAGER_REQUEST_CODE) {
            setResult(resultCode, new Intent());
            finish();
        }
    }
}