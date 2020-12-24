package dev.atharvakulkarni.messenger;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chat_person extends AppCompatActivity
{
    EditText message_edittext;
    FrameLayout send;
    String message;
    //TextView message_textview;
    FirebaseFirestore db;
    private String saveCurrentTime, saveCurrentDate;
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;
    private LinearLayoutManager linearLayoutManager;
    private final List<Messages1> messagesList = new ArrayList<>();
    private FirebaseAuth mAuth;
    String messageSenderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_person);

        message_edittext = findViewById(R.id.editText_message);
      //  message_textview = findViewById(R.id.message_textview);
        send = findViewById(R.id.send);
        mAuth = FirebaseAuth.getInstance();

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        messageSenderId = mAuth.getCurrentUser().getUid();

        IntializeControllers();

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SendMessage();


            }
        });

        DisplayLastSeen();




    }

    private void IntializeControllers()
    {
        messageAdapter = new MessageAdapter(messagesList,chat_person.this);
        userMessagesList = (RecyclerView) findViewById(R.id.private_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
       // linearLayoutManager.setReverseLayout(true);
       // linearLayoutManager.setStackFromEnd(true);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());
    }

    private void DisplayLastSeen()
    {

    }

    private void SendMessage()
    {
        message = message_edittext.getText().toString();
       // message_textview.setText(message);

        if (TextUtils.isEmpty(message))
        {
            Toast.makeText(this, "first write your message...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Create a new user with a first and last name
            Map<String, Object> msg = new HashMap<>();
            msg.put("from", messageSenderId);
            msg.put("time", saveCurrentTime);
            msg.put("date", saveCurrentDate);
            msg.put("text", message);

            // Add a new document with a generated ID
            db.collection(messageSenderId).document("person").collection("Adwait").document(String.valueOf(System.currentTimeMillis())).set(msg).addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        private static final String TAG = "a";

                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(chat_person.this, "success", Toast.LENGTH_SHORT).show();
                            message_edittext.setText("");
                            onStart();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        private static final String TAG = "b";

                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
       /* DocumentReference docRef = db.collection(messageSenderId).document("person").collection("Adwait");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            private static final String TAG = "s";

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                       // message_textview.setText();



                        Messages1 messages = document.toObject(Messages1.class);
                        messagesList.add(messages);
                        messageAdapter.notifyDataSetChanged();
                        userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    }
                    else
                    {
                        Log.d(TAG, "No such document");
                    }
                }
                else
                {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/

        db.collection(messageSenderId).document("person").collection("Adwait")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                               // Log.d(TAG, document.getId() + " => " + document.getData());

                                Messages1 messages = document.toObject(Messages1.class);
                                messagesList.add(messages);
                                messageAdapter.notifyDataSetChanged();
                                userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
                            }
                        }
                        else
                        {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}