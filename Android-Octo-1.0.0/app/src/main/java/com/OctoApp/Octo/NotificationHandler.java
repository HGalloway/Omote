package com.OctoApp.Octo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

public class NotificationHandler extends FirebaseMessagingService {
    private final DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference();
    Context AppContext;
    String FCMToken;
    String Username;

    public void NotificationListener(String Name, Context Context) {
        AppContext = Context;
        Username = Name;
        GetFCMToken(Username, Context);
        FirebaseApp.initializeApp(Context);

    }

    private void GetFCMToken(String Username, Context Context) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }

            FCMToken = task.getResult();
            UserDatabase.child(Username).child("Token").setValue(FCMToken);
        });
    }


    @Override
    public void onNewToken(@NonNull String Token) {
        FCMToken = Token;
        UserDatabase.child(Username).child("Token").setValue(FCMToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Messages Messages = new Messages();
        Messages.OutputCompletionMessage(remoteMessage.getNotification().getTitle() + remoteMessage.getNotification().getBody(), AppContext);
    }


    private void MakeNotification(String Title, String Content) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Title)
                .setContentText(Content).setAutoCancel(true).setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    public void SendNotificationToToken(String Token, String Title, String Body, Context Context) throws IOException {

        Thread MessageThread = new Thread() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(Context);
                String url = "http://octoserver.ddns.net/?token=" + Token + "&title=" + Title + "&body=" + Body;
                System.out.println(url);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        response -> {
                        }, error -> {
                });
                queue.add(stringRequest);
            }
        };

        MessageThread.start();

    }
}