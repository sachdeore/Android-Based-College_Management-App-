package com.rohya.collegemanagement;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rohya.collegemanagement.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesDownload extends AppCompatActivity {

    RecyclerView recyclerView;
    NotesAdapter adapter;
    List<Note> noteList;

    private Context context;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_download);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteList = new ArrayList<>();
        adapter = new NotesAdapter(this, noteList);
        recyclerView.setAdapter(adapter);

        db.collection("notes")
                .orderBy("name")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(NotesDownload.this, "Error retrieving notes", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        noteList.clear();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            Note note = document.toObject(Note.class);
                            note.setId(document.getId());
                            noteList.add(note);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Note note = noteList.get(position);

                Toast.makeText(NotesDownload.this, note.getName(), Toast.LENGTH_SHORT).show();
                downloadFile(note.getValue(), note.getName());
            }
        });
    }

    public void downloadFile(String url, String fileName) {
        // Create a download manager
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        // Create a request for the download
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("My File Download");
        request.setDescription("Downloading file...");

        // Set the destination path to the Downloads folder
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName+".pdf");

        // Enqueue the download
        downloadManager.enqueue(request);
    }
    @Override
    protected void onPostCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
