package com.example.Service;

import android.net.Uri;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class DownloadContent extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child(strings[0] + "/" + strings[2]).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        return null;
    }
}
