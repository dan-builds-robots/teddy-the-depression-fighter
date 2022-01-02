package daniel.ajayi.teddythedepressionfighter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.HashSet;
import java.util.Set;

import static daniel.ajayi.teddythedepressionfighter.MainActivity.addLikeStatus;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.anxiety;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.depression;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.insecurity;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.mailCountTxt;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.mailRedDot;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.msg;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.notifCountDisplayHome;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.pastMsgs;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.pastMsgsLiked;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.prefs;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.rand;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.teddyName;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.totalMsgsSent;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.unseenMail;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.username;


//public class SendNotification {
//
//    public SendNotification() {
//
//        Log.i("stuff is actually","happening here");
//
//        new MainActivity().generateMsg(null);
//
//        Log.i("stuff is actually","happening here");
//
//    }
//
//}

public class SendNotification extends BroadcastReceiver {

    int notifCount = 0;

    public static String NOTIFICATION_ID = "notification-id";

    public static String NOTIFICATION = "notification";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    public void onReceive(Context context, Intent intent2) {

        Intent intent1 = new Intent(context, SendMsgService.class);

        context.startService(intent1);
//
//        context.startForegroundService(intent1);

//        new SendMsgService().startForeground(0, new MainActivity().getNotification("asdf", context));

//        Log.i("stuff is actually","happening here");
//
//
//        unseenMail++;
//
//        int i = unseenMail;
//
//
//        prefs.edit().putInt("unseenMail", i).apply();
//
//        if (unseenMail == 0) {
//
//            mailRedDot.setVisibility(View.INVISIBLE);
//
//            mailCountTxt.setVisibility(View.INVISIBLE);
//
//        } else {
//
//            mailRedDot.setVisibility(View.VISIBLE);
//
//            mailCountTxt.setVisibility(View.VISIBLE);
//
//        }
//
//        String display;
//
//        display = (unseenMail < 100) ? unseenMail + "" : "99+";
//
//        mailCountTxt.setText(display);
//
//
//
//
//        totalMsgsSent++;
//
//
//
//        prefs.edit().putInt("totalMsgsSent",totalMsgsSent).apply();
//
//        notifCountDisplayHome.setText(totalMsgsSent + " MESSAGES SENT");
//
//
////        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
////
////        Notification notification = intent.getParcelableExtra(NOTIFICATION);
////
////        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
////
////        notificationManager.notify(id, notification);
//
//        Intent intent = new Intent(context, MainActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
//
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//
//        builder.setSmallIcon(R.drawable.notification_icon);
//
//        builder.setSmallIcon(R.drawable.ss_icon4);
//
//        builder.setContentTitle("Message From " + teddyName.substring(0,1).toUpperCase() + teddyName.substring(1));
//
//        if (msg.isEmpty()) {
//
//            Log.i("Empty msgs","generating some");
//
//            new MainActivity().updateMsgs();
//
//        }
//
//        if (msg.isEmpty()) {
//
//            if (new MainActivity().dislikedAllMsgs) {
//
//                msg.add("We're terribly sorry! You've disliked all the messages in our repository!" +
//                        " More messages will be added with the next update.");
//
//            } else {
//
//                msg.add("Hi " + username + "! You don't have a topic selected (depression, " +
//                        "anxiety, or insecurity), but you've still scheduled for notifications, " +
//                        "so here's a notification saying hi!");
//
//            }
//
//
//        }
//
//        int indexOfMsg = rand.nextInt(msg.size());
//
//        String body = msg.get(indexOfMsg);
//
//        if (new MainActivity().firstOpening) {
//
//            body = "Welcome to Teddy, The Depression Fighter! Click the email " +
//                    "icon at the bottom right to see all past messages. Click and hold on a " +
//                    "message to see more options (like, dislike, share). Go to settings to change " +
//                    "Teddy's bowtie, enter your birthday for a special birthday message, " +
//                    "change Teddy's name, or change your name for more personalized messages. " +
//                    "You can also adjust the notification frequency." +
//                    " If you enjoy the app, please leave us five stars in the play store." +
//                    " It will help a lot. Thank you!!";
//
//        }
//
//        pastMsgs.add(0, body);
//
//        pastMsgsLiked.add(0, addLikeStatus(msg.get(indexOfMsg)));
//
//        //count.add(count.size() + 1);
//
//        Log.i("a","");
//        Log.i("aa","");
//        Log.i("aaa","");
//        Log.i("NOTIFICATION","GENERATED");
//        Log.i("aaa","");
//        Log.i("aa","");
//        Log.i("a","");
//
//
//
//        new MainActivity().putArrayInPrefs(pastMsgs, "pastMsgs");
//
//        new MainActivity().putArrayInPrefs(pastMsgsLiked, "pastMsgsLiked");
//
//        //putArrayInPrefs(arrIntToStr(count), "count");
//
//        Log.i("a","");
//        Log.i("aa","");
//        Log.i("aaa","");
//        Log.i("SAVED","TO PREFS");
//        Log.i("aaa","");
//        Log.i("aa","");
//        Log.i("a","");
//        Log.i("Pastmsgs (prefs)","" + new MainActivity().getArrayFromPrefs("pastMsgs").size());
//        Log.i("PastmsgsLiked (prefs)","" + new MainActivity().getArrayFromPrefs("pastMsgsLiked").size());
//
//
////
////        if (msg.isEmpty()) {
////
////            new MainActivity().addMsgs();
////
////        }
////
////        int indexOfMsg = rand.nextInt(msg.size());
////
////        pastMsgs.add(msg.get(indexOfMsg));
////
////        pastMsgsLiked.add(addLikeStatus(msg.get(indexOfMsg)));
////
////        Set<String> tempSet = new HashSet<String>();
////
////        for (String s : pastMsgs) {
////
////            tempSet.add(s);
////
////        }
////
////        prefs.edit().putStringSet("pastMsgs", tempSet).apply();
////
////        tempSet.clear();
////
////        tempSet = new HashSet<String>();
////
////        for (String s : pastMsgsLiked) {
////
////            tempSet.add(s);
////
////        }
////
////        prefs.edit().putStringSet("pastMsgsLiked", tempSet).apply();
////
////        String message = (!depression && !anxiety && !insecurity) ? "Hey " + username
////                + "! You don't have a topic selected (depression, anxiety, or insecurity), so " +
////                "here's a notification to say hello!" : msg.get(indexOfMsg);
//
//        builder.setContentText(body);
//
//        builder.setContentIntent(pendingIntent);
//
//        builder.setAutoCancel(true);
//
//        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));
//
//        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            String channelId = "Your_channel_id";
//
//            NotificationChannel channel = new NotificationChannel(channelId,"Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
//
//            manager.createNotificationChannel(channel);
//
//            builder.setChannelId(channelId);
//
//        }
//
//        manager.notify(notifCount,builder.build());
//
//        notifCount++;

    }

}
