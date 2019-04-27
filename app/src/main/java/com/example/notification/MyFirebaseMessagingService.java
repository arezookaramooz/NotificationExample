package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.URL;
import java.util.Map;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private String CONTENT_TEXT = "Expand me to see a detailed message!";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

         RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.view_expanded_notification);
        expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        expandedView.setTextViewText(R.id.notification_message, remoteMessage.getNotification().getBody());

        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.view_collapsed_notification);
        collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_pawprint)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(CONTENT_TEXT)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0))
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}