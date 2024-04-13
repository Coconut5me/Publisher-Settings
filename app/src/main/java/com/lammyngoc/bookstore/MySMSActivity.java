package com.lammyngoc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MySMSActivity extends AppCompatActivity {

    TextView txtContactName, txtPhoneNumber;
    EditText edtMessageBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_smsactivity);
        addViews();
        getDataFromPreviousActivity();
    }

    private void getDataFromPreviousActivity() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("ContactName");
        String phone = intent.getStringExtra("PhoneNumber");
        txtContactName.setText(name);
        txtPhoneNumber.setText(phone);
    }

    private void addViews() {
        txtContactName = findViewById(R.id.txtContactName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        edtMessageBody = findViewById(R.id.edtMessageBody);
    }

    public void doSendSms1(View view) {
        sendSmsMethod1(txtPhoneNumber.getText().toString(), edtMessageBody.getText().toString());
    }

    private void sendSmsMethod1(String phoneNumber, String message) {
        final SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message,
                null, null);
    }

    public void doSendSms2(View view) {
        sendSmsMethod2(txtPhoneNumber.getText().toString(), edtMessageBody.getText().toString());
    }

    public void sendSmsMethod2(String phoneNumber, String message) {
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
                Toast.makeText(MySMSActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        }, new IntentFilter("ACTION_MSG_SENT"));
        //Gọi hàm gửi tin nhắn đi
        sms.sendTextMessage(phoneNumber, null, message,
                sentIntent, null);
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
                Toast.makeText(MySMSActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        }), new IntentFilter(DELIVERY_ACTION));

        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber,null, message,
                sentIntent, deliveryIntent);
    }
}



