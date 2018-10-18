package com.example.jonathan.prepcookinventory.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.example.jonathan.prepcookinventory.R;

import java.io.File;

public class IntentEmail {
    public IntentEmail(Context context) { this.context = context; }
    private Context context;


    // reply with boolean to show if sent
    public void SendEmail(File EmailAttachmentFile) {
        try {
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");

            emailIntent.putExtra(Intent.EXTRA_EMAIL, "Email Body");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "CVS EXPORT");

            //Uri extraFile = Uri.fromFile(EmailAttachmentFile);
            Uri extraFile = FileProvider.getUriForFile(context, context.getString(R.string.file_provider_authority),EmailAttachmentFile);
            //FileProvider.getUriForFile(context, string, file);


            if (extraFile != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, extraFile);
            }

            context.startActivity(Intent.createChooser(emailIntent, "Sending Email"));

        } catch (Throwable t) {
            t.printStackTrace();
            Toast.makeText(context, "Request failed try again: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }


}
