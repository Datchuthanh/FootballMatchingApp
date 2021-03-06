package com.example.myclub.messagingService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.R;
import com.example.myclub.data.repository.PlayerRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingRegistrar;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private PlayerRepository playerRepository = PlayerRepository.getInstance();

    @Override
    public void onCreate() {
        // check if registration is sent to server
    }

    @Override
    public void onNewToken(String token) {
        sendRegistrationTokenToServer(token);
    }

    private void sendRegistrationTokenToServer(String token) {
        Map<String, Object> updateProfileMap = new HashMap<>();
        updateProfileMap.put("registrationToken", token);
        playerRepository.updateProfile(updateProfileMap, new CallBack<String, String>() {
            @Override
            public void onSuccess(String sucess) {
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData());
    }

    private void showNotification(Map<String, String> data) {
        String messageType = data.get("messageType");
        String contentText = "";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "example.myapplication.service.test";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("MyClub");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        if (messageType!=null && messageType.equals("NewJoinRequest")){
            String playerId = data.get("playerId");
            String playerName = data.get("playerName");
            String teamName = data.get("teamName");
             contentText = playerName + " đã gửi yêu cầu tham gia đội "+teamName +" của bạn";

        }
        else if (messageType!= null && messageType.equals("New Member")){
            String teamName = data.get("teamName");
            String memberName = data.get("memberName");
            contentText = memberName +" đã gia nhập đội "+teamName + " của bạn ";

        } else if (messageType != null && messageType.equals("Joined Team")){
            String teamName = data.get("teamName");
            contentText = "Yêu cầu gia nhập đội "+teamName + " đã được chấp nhận";

            }else if (messageType != null && messageType.equals("Accept Booking")){
            String teamName = data.get("teamName");
            String fieldName = data.get("fieldName");
            String startTime = data.get("startTime");
            String endTime = data.get("endTime");
            String dateTime = data.get("dateTime");
            contentText = "Đội "+ teamName+" đã đặt sân " +fieldName + " thành công";

        }else if (messageType != null && messageType.equals("New QueueTeam")){
            String namTeamHome = data.get("nameTeamHome");
            String nameTeamAway = data.get("nameTeamAway");
            contentText = "Đội "+nameTeamAway+ " đã đề nghị tham giá trận đấu với đội "+  namTeamHome+" của bạn" ;
        }else if (messageType != null && messageType.equals("Accept Matching")){
            String nameTeamHome = data.get("teamHomeName");
            String nameTeamAway = data.get("teamAwayName");
            String fieldName = data.get("fieldName");
            String startTime = data.get("startTime");
            String endTime = data.get("endTime");
            String dateTime = data.get("endTime");
            contentText ="Bạn có trận đấu giữa đội "+nameTeamHome+" - "+nameTeamAway +" tại sân "+ fieldName +" thời gian "+dateTime+ " lúc "+startTime+"h - "+endTime+"h";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_baseline_sports_soccer_24)
                    .setContentTitle(messageType)
                    .setContentText(contentText)
                    .setContentInfo("Info");
            notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
        }
    }
}
