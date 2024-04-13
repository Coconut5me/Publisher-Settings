package com.lammyngoc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lammyngoc.bookstore.model.MyContact;

import org.w3c.dom.Text;

public class MySmsAdvancedActivity extends AppCompatActivity {
    TextView txtContactName, txtPhoneNumber;
    EditText edtMessageBody;
    RadioButton radSmsMethod1, radSmsMethod2, radSmsMethod3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sms_advanced);
        addViews();
        getDataFromPreviousActivity();
    }

    private void getDataFromPreviousActivity() {
        Intent intent=getIntent();
        MyContact contact=(MyContact)intent.getSerializableExtra("SELECTED_CONTACT");
        txtContactName.setText(contact.getContactName());
        txtPhoneNumber.setText(contact.getPhoneNumber());
    }

    private void addViews() {
        txtContactName=findViewById(R.id.txtContactName);
        txtPhoneNumber=findViewById(R.id.txtPhoneNumber);
        edtMessageBody=findViewById(R.id.edtMessageBody);
        radSmsMethod1=findViewById(R.id.radSmsMethod1);
        radSmsMethod2=findViewById(R.id.radSmsMethod2);
        radSmsMethod3=findViewById(R.id.radSmsMethod3);
    }

    public void doSendMessage(View view) {
        String phoneNumber=txtPhoneNumber.getText().toString();
        String message=edtMessageBody.getText().toString();
        if(radSmsMethod1.isChecked())
        {
            sendSmsMethod1(phoneNumber,message);
        }
        else if(radSmsMethod2.isChecked())
        {
            sendSmsMethod2(phoneNumber,message);
        }
        else if(radSmsMethod3.isChecked())
        {
            sendSmsMethod3(phoneNumber,message);
        }
    }

    private void sendSmsMethod3(String phoneNumber, String message) {
        String SENT_ACTION = "SMS_SENT_ACTION";
        String DELIVERY_ACTION = "SMS_DELIVERED_ACTION";
        PendingIntent sentIntent = PendingIntent.getBroadcast(
                this,100,
                new Intent(SENT_ACTION), PendingIntent.FLAG_IMMUTABLE);

        PendingIntent deliveryIntent = PendingIntent.getBroadcast(
                this, 200,
                new Intent(DELIVERY_ACTION), PendingIntent.FLAG_IMMUTABLE);

        registerReceiver((new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                String msg = "Send OK";
                if (result != Activity.RESULT_OK) {
                    msg = "Send failed";
                }
                Toast.makeText(MySmsAdvancedActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        }), new IntentFilter(DELIVERY_ACTION));

        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber,null, message,
                sentIntent, deliveryIntent);
    }

    private void sendSmsMethod2(String phoneNumber, String message) {
        final SmsManager sms = SmsManager.getDefault();
        Intent msgSent = new Intent("ACTION_MSG_SENT");
        final PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, msgSent, PendingIntent.FLAG_IMMUTABLE);
        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                String msg = "Send OK";
                if (result != Activity.RESULT_OK) {
                    msg = "Send failed";
                }
                Toast.makeText(MySmsAdvancedActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        }, new IntentFilter("ACTION_MSG_SENT"));
        //Gọi hàm gửi tin nhắn đi
        sms.sendTextMessage(phoneNumber, null, message,
                sentIntent, null);
    }

    private void sendSmsMethod1(String phoneNumber, String message) {
        final SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message,
                null, null);
    }
}