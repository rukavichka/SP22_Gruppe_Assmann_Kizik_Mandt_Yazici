package com.example.Service;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DownloadContent extends AsyncTask<String, Void, Void> {
    Context context;
    public DownloadContent(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = storageRef.child(strings[0] + "/" + strings[1]);

        fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
            String url = uri.toString();
            downloadFile(context, strings[1], url);
        }).addOnFailureListener(e -> {
            System.out.println("Failed");
        });;
        return null;
    }

    private void downloadFile(Context context, String fileName, String url) {
        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, DIRECTORY_DOWNLOADS, fileName);
        downloadmanager.enqueue(request);
    }
}
