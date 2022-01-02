package daniel.ajayi.teddythedepressionfighter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements LifecycleObserver{

    public static SharedPreferences prefs;

    int numMsgsPerMin;

    int numUsedMsgsInMin;

    Typeface mTypeface;

    static Random rand;

    ImageView genNowBtn;

    TextView genNowBtnTxt;

    TextView birthDate;

    static ArrayList<String> msg;

    static ArrayList<String> pastMsgs;

    static ArrayList<String> pastMsgsLiked;

    static ArrayList<ArrayList> mailType;

    //static ArrayList<Integer> count;

    static ArrayList<String> likedMsgs;

    static ArrayList<String> dislikedMsgs;

    ArrayList<String> header;

    ImageView settingsBtn;

    ImageView depressionState;

    ImageView anxietyState;

    ImageView insecurityState;

    ImageView mailBtn;

    static String teddyName;

    static String username;

    boolean userSuppliedBirthday;

    static int totalMsgsSent;

    static int unseenMail;

    TextView teddyNameDisplayHome;

    static TextView notifCountDisplayHome;

    static TextView mailCountTxt;

    TextView pressAndHoldPrompt;

    static ImageView mailRedDot;

    GridLayout deleteEmailOptions;

    ImageView mailBg;

    ImageView mailBg2;

    ListView mail;

    TextView mailHeader;

    ImageView mailX;

    ImageView settingsOverlay;

    ImageView leaveSettingsBtn;

    TextView noMsgs;

    TextView leaveSettingsBtnTxt;

    GridLayout settingsOne;

    GridLayout settingsTwo;

    GridLayout bowTieCollection;

    ImageView bowTimePanel;

    ImageView exitBowTimePanelBtn;

    ImageView bowTieActor;

    ImageView dislikedMsgsBox;

    ImageView likedMsgsBox;

    ImageView allMsgsBox;

    ImageView dislikedMsgsPic;

    ImageView likedMsgsPic;

    ImageView allMsgsPic;

    TextView exitBowTimePanelBtnTxt;

    static boolean depression = true;

    static boolean anxiety = false;

    static boolean insecurity = false;

    static boolean musicMuted;

    static boolean sfxMuted;

    int notifCount = 0;

    ImageView musicState;

    ImageView sfxState;

    EditText nameBox;

    EditText teddyNameBox;

    int indexOfSelMail = -1;

    int userBirthday;

    EditText userBirthdayET;

    SeekBar notifFreq;

    TextView notifFreqTxt;

    ImageView bowTie;

    ImageView bowTie2;

    int notifFreqProgress;

    private AlarmManager alarmMgr;

    private PendingIntent alarmIntent;

    ArrayAdapter arrayAdapter;

    ArrayAdapter likedMsgsAdapter;

    ArrayAdapter dislikedMsgsAdapter;

    ArrayAdapter selMsgAdapter;

    Intent intent;

    ArrayList<Calendar> calendar;

    boolean change;

    int year;

    int month;

    int day;

    ImageView mailDelBtn;

    TextView mailDelBtnTxt;

    ImageView mailDelAllBtn;

    TextView mailDelAllBtnTxt;

    DatePickerDialog.OnDateSetListener dateSetListener;

    Calendar cal = Calendar.getInstance();

    int bowTieNum;

    boolean dislikedAllMsgs = false;

    ImageView likeBtn;

    TextView likeBtnTxt;

    ImageView dislikeBtn;

    TextView dislikeBtnTxt;

    ImageView moreOptionsPanel;

    ImageView shareBtn;

    TextView shareBtnTxt;

    TextView likeStatus;

    ListView selMsgList;

    ArrayList<String> selMsg;

    ImageView msgBg;

    ImageView moreOptionsX;

    MediaPlayer gameMusic;

    MediaPlayer notifGenSfx;

    static MediaPlayer btnTap;

    static MediaPlayer btnTap2;

    boolean firstOpening;

    int songPosition;

    final int ALL = 0;

    final int LIKED = 1;

    final int DISLIKED = 2;

    int mailMode = ALL;

    int frequency;

    public static boolean musicCurrentlyPlaying = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        //AppForegroundStateManager.getInstance().addListener(this);

        msg = new ArrayList<String>();

        header = new ArrayList<String>();

        change = false;

        rand = new Random();

        prefs = this.getSharedPreferences("dan.ajayi.teddythedepressionfighter", Context.MODE_PRIVATE);

        selMsg = new ArrayList<>();

        mailDelBtn = findViewById(R.id.deleteBtn);

        mailDelBtnTxt = findViewById(R.id.DeleteBtnTxt);

        mailDelAllBtn = findViewById(R.id.clearAllBtn);

        mailDelAllBtnTxt = findViewById(R.id.ClearAllBtnTxt);
//
//        intent = new Intent(this, SendMsgService.class);
//
//        intent.putExtra(SendMsgService.NOTIFICATION_ID, 1);
//
//        intent.putExtra(SendMsgService.NOTIFICATION, getNotification("asdf"));

        intent = new Intent(this, SendNotification.class);

        intent.putExtra(SendNotification.NOTIFICATION_ID, 0);

        intent.putExtra(SendNotification.NOTIFICATION, getNotification("asdf", this));

        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        calendar = new ArrayList<Calendar>();

        bowTie = findViewById(R.id.bowTie);

        bowTie2 = findViewById(R.id.bowTie2);

        frequency = prefs.getInt("frequency", 180 * 60);

        if (frequency != 0) {

            scheduleAlarm(frequency);

        }

        setBowTie();

        nameBox = findViewById(R.id.editTextTextPersonName);

        notifFreqTxt = findViewById(R.id.notifFreqTxt);

        notifFreq = findViewById(R.id.seekBar);

        notifFreq.setMax(15);

        notifFreqProgress = prefs.getInt("notifFreqProgress", 9);

        notifFreq.setProgress(notifFreqProgress);

        notifFreq.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override

            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                change = true;

                notifFreqProgress = i;

                prefs.edit().putInt("notifFreqProgress",notifFreqProgress).apply();

                String freqTxt = "";

                freqTxt = updateAlarmInfo(i);

                notifFreqTxt.setText(freqTxt);

            }

            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {

                change = true;

            }

        });

        teddyNameBox = findViewById(R.id.teddyEditText);

        depression = prefs.getBoolean("depression",true);

        anxiety = prefs.getBoolean("anxiety",false);

        insecurity = prefs.getBoolean("insecurity",false);

        totalMsgsSent = prefs.getInt("totalMsgsSent", 0);

        unseenMail = prefs.getInt("unseenMail", 0);

        username = prefs.getString("username", "username");

        teddyName = prefs.getString("teddyName", "Teddy");

        year = prefs.getInt("year", cal.get(Calendar.YEAR));

        month = prefs.getInt("month", cal.get(Calendar.MONTH));

        day = prefs.getInt("day", cal.get(Calendar.DAY_OF_MONTH));

        bowTieNum = prefs.getInt("bowTieNum", cal.get(Calendar.DAY_OF_MONTH));

        musicMuted = prefs.getBoolean("musicMuted",false);

        sfxMuted = prefs.getBoolean("sfxMuted",false);

        firstOpening = prefs.getBoolean("firstOpening",true);

        if (!sfxMuted) {

            btnTap = MediaPlayer.create(this,R.raw.hi_hat);

            btnTap2 = MediaPlayer.create(this,R.raw.hi_hat);

            btnTap.setVolume(.25f,.25f);

            btnTap2.setVolume(.25f,.25f);

            notifGenSfx = MediaPlayer.create(this,R.raw.gen_notif_sfx);

        }

        setBowTie();

        pastMsgs = getArrayFromPrefs("pastMsgs");

        pastMsgsLiked = getArrayFromPrefs("pastMsgsLiked");

        likedMsgs = getArrayFromPrefs("likedMsgs");

        dislikedMsgs = getArrayFromPrefs("dislikedMsgs");

        mailType = new ArrayList<ArrayList>();

        mailType.add(pastMsgs);

        mailType.add(likedMsgs);

        mailType.add(dislikedMsgs);

        birthDate = findViewById(R.id.birthDate);


        birthDate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)

            @Override

            public void onClick(View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

                DatePickerDialog dialog = new DatePickerDialog (

                        MainActivity.this,

                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,

                        dateSetListener,

                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

            }

        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;

                String date = month + "/" +  day + "/" +  year;

                prefs.edit().putInt("day", day).apply();

                prefs.edit().putInt("month", month).apply();

                prefs.edit().putInt("year", year).apply();

                birthDate.setText(date);

            }

        };

        mail = findViewById(R.id.emailListView);

        genNowBtn = findViewById(R.id.generateNowBtn);

        noMsgs = findViewById(R.id.noMsgs);

        musicState = findViewById(R.id.musicState);

        sfxState = findViewById(R.id.sfxState);

        genNowBtnTxt = findViewById(R.id.generateNowBtnTxt);

        settingsBtn = findViewById(R.id.settingsBtn);

        depressionState = findViewById(R.id.depressionState);

        anxietyState = findViewById(R.id.anxietyState);

        insecurityState = findViewById(R.id.insecurityState);

        birthDate = findViewById(R.id.birthDate);

        updateDepressionState(null);

        updateAnxietyState(null);

        updateInsecurityState(null);

        updateMsgs();

        mailBtn = findViewById(R.id.mailBtn);

        teddyNameDisplayHome = findViewById(R.id.teddyNameTxtMain);

        notifCountDisplayHome = findViewById(R.id.notifCountTxtMain);

        mailCountTxt = findViewById(R.id.emailCount);

        mailRedDot = findViewById(R.id.redDot);

        deleteEmailOptions = findViewById(R.id.exitEmailGL);

        pressAndHoldPrompt = findViewById(R.id.pressAndHoldPrompt);

        dislikedMsgsBox = findViewById(R.id.dislikedMsgsBox);

        likedMsgsBox = findViewById(R.id.likedMsgsBox);

        allMsgsBox = findViewById(R.id.pastMsgsBox);

        dislikedMsgsPic = findViewById(R.id.dislikedMsgs);

        likedMsgsPic = findViewById(R.id.likedMsgs);

        allMsgsPic = findViewById(R.id.pastMsgs);

        mailBg = findViewById(R.id.emailBg);

        mailBg2 = findViewById(R.id.prettyEmailBg);

        mailHeader = findViewById(R.id.messagesTxt);

        mailX = findViewById(R.id.emailX);

        settingsOverlay = findViewById(R.id.settingsBg);

        teddyNameDisplayHome.setText("NAME: " + teddyName.toUpperCase());

        updateTeddyName();

        updateTotalMsgsSent();

        leaveSettingsBtn = findViewById(R.id.exitSettings);

        leaveSettingsBtnTxt = findViewById(R.id.exitSettingsBtnTxt);

        settingsOne = findViewById(R.id.topSettingsOptions);

        settingsTwo = findViewById(R.id.settingsP2);

        bowTieCollection = findViewById(R.id.bowTieCollection);

        bowTimePanel = findViewById(R.id.bowTiePanel);

        exitBowTimePanelBtn = findViewById(R.id.leaveBowTiePanel);

        exitBowTimePanelBtnTxt = findViewById(R.id.leaveBowTiePanel2);

        bowTieActor = findViewById(R.id.teddyBowTieActor);

        mTypeface = ResourcesCompat.getFont(this, R.font.gill_sans_ultra_bold_condensed);

        updateUnseenMail();

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, pastMsgs) {

            @Override

            public View getView (int position, View convertView, ViewGroup parent) {

                TextView item = (TextView) super.getView(position,convertView,parent);

                item.setTag(msg.indexOf(item.getText()));

                item.setTypeface(mTypeface);

                item.setTextColor(Color.parseColor("#FFFFFF"));

                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);

                return item;

            }

        };

        likedMsgsAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, likedMsgs) {

            @Override

            public View getView (int position, View convertView, ViewGroup parent) {

                TextView item = (TextView) super.getView(position,convertView,parent);

                item.setTag(msg.indexOf(item.getText()));

                item.setTypeface(mTypeface);

                item.setTextColor(Color.parseColor("#FFFFFF"));

                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);

                return item;

            }

        };

        dislikedMsgsAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, dislikedMsgs) {

            @Override

            public View getView (int position, View convertView, ViewGroup parent) {

                TextView item = (TextView) super.getView(position,convertView,parent);

                item.setTag(msg.indexOf(item.getText()));

                item.setTypeface(mTypeface);

                item.setTextColor(Color.parseColor("#FFFFFF"));

                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);

                return item;

            }

        };

        mail.setAdapter(arrayAdapter);

        selMsgAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, selMsg) {

            @Override

            public View getView (int position, View convertView, ViewGroup parent) {

                TextView item = (TextView) super.getView(position,convertView,parent);

                item.setTypeface(mTypeface);

                item.setTextColor(Color.parseColor("#29ABE2"));

                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);

                item.setGravity(Gravity.CENTER);

                return item;

            }

        };

        selMsgList = findViewById(R.id.msg);

        selMsgList.setAdapter(selMsgAdapter);

        likeBtn = findViewById(R.id.likeBtn);

        likeBtnTxt = findViewById(R.id.likeTxt);

        dislikeBtn = findViewById(R.id.dislikeBtn);

        dislikeBtnTxt = findViewById(R.id.dislikeTxt);

        moreOptionsPanel = findViewById(R.id.moreOptionsPanel);

        shareBtn = findViewById(R.id.shareBtn);

        shareBtnTxt = findViewById(R.id.shareTxt);

        likeStatus = findViewById(R.id.likeStatus);

        msgBg = findViewById(R.id.textBox);

        moreOptionsX = findViewById(R.id.moreOptionsX);

        if (firstOpening) {

            generateMsg(null);

            firstOpening = false;

            prefs.edit().putBoolean("firstOpening", firstOpening).apply();

        }

    }

    public ArrayList<Integer> arrStrToInt(ArrayList<String> arrayOfStr) {

        ArrayList<Integer> nums = new ArrayList<Integer>();

        for (String s : arrayOfStr) {

            nums.add(Integer.parseInt(s));

        }

        return nums;

    }

    public ArrayList<String> arrIntToStr(ArrayList<Integer> arrayOfInt) {

        ArrayList<String> strings = new ArrayList<>();

        for (Integer i : arrayOfInt) {

            strings.add("" + i);

        }

        return strings;

    }

    public void updateTotalMsgsSent() {

        prefs.edit().putInt("totalMsgsSent",totalMsgsSent).apply();

        notifCountDisplayHome.setText(totalMsgsSent + " MESSAGES SENT");

    }

    public void updateBowTie(View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        bowTieNum = Integer.parseInt(view.getTag().toString());

        prefs.edit().putInt("bowTieNum", bowTieNum).apply();

        setBowTie();

    }

    public void changeAdapterToDisliked(View view) {

        indexOfSelMail = -1;

        mailMode = DISLIKED;

        mailDelBtnStatus();

        mailDelAllStatus();

        noMsgs.setText("No Disliked Messages");

        mail.setAdapter(dislikedMsgsAdapter);

        updateMail(null);

        recolorMailTabs("DISLIKED");

    }

    public void recolorMailTabs(String s) {

        DrawableCompat.setTint(dislikedMsgsBox.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.unselected_mail));

        DrawableCompat.setTint(likedMsgsBox.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.unselected_mail));

        DrawableCompat.setTint(allMsgsBox.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.unselected_mail));

        if (s.equals("DISLIKED")) {

            DrawableCompat.setTint(dislikedMsgsBox.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.selected_email));

            Log.i("DISLIKED","disliked msgs box");

        } else if (s.equals("LIKED")) {

            DrawableCompat.setTint(likedMsgsBox.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.selected_email));

            Log.i("LIKED","disliked msgs box");

        } else {

            DrawableCompat.setTint(allMsgsBox.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.selected_email));

            Log.i("ALL MSGS","disliked msgs box");

        }

    }

    public void changeAdapterToLiked(View view) {

        noMsgs.setText("No Liked Messages");

        mailMode = LIKED;

        mailDelBtnStatus();

        mailDelAllStatus();

        indexOfSelMail = -1;

        mail.setAdapter(likedMsgsAdapter);

        updateMail(null);

        recolorMailTabs("LIKED");

    }

    public void changeAdapterToAllMsgs(View view) {

        noMsgs.setText("No Mail");

        mailMode = ALL;

        mailDelBtnStatus();

        mailDelAllStatus();

        indexOfSelMail = -1;

        mail.setAdapter(arrayAdapter);

        updateMail(null);

        recolorMailTabs("ALL");

    }

    public void setBowTie() {

        if (bowTieNum == 1) {

            bowTie.setImageResource(R.drawable.bowtie1);

            bowTie2.setImageResource(R.drawable.bowtie1);

        } else if (bowTieNum == 2) {

            bowTie.setImageResource(R.drawable.bowtie2);

            bowTie2.setImageResource(R.drawable.bowtie2);

        }  else if (bowTieNum == 3) {

            bowTie.setImageResource(R.drawable.bowtie3);

            bowTie2.setImageResource(R.drawable.bowtie3);

        } else if (bowTieNum == 4) {

            bowTie.setImageResource(R.drawable.bowtie4);

            bowTie2.setImageResource(R.drawable.bowtie4);

        } else if (bowTieNum == 5) {

            bowTie.setImageResource(R.drawable.bowtie5);

            bowTie2.setImageResource(R.drawable.bowtie5);

        } else if (bowTieNum == 6) {

            bowTie.setImageResource(R.drawable.bowtie6);

            bowTie2.setImageResource(R.drawable.bowtie6);

        } else if (bowTieNum == 7) {

            bowTie.setImageResource(R.drawable.bowtie7);

            bowTie2.setImageResource(R.drawable.bowtie7);

        } else if (bowTieNum == 8) {

            bowTie.setImageResource(R.drawable.bowtie8);

            bowTie2.setImageResource(R.drawable.bowtie8);

        } else if (bowTieNum == 9) {

            bowTie.setImageResource(R.drawable.bowtie9);

            bowTie2.setImageResource(R.drawable.bowtie9);

        } else if (bowTieNum == 10) {

            bowTie.setImageResource(R.drawable.bowtie10);

            bowTie2.setImageResource(R.drawable.bowtie10);

        } else if (bowTieNum == 11) {

            bowTie.setImageResource(R.drawable.bowtie11);

            bowTie2.setImageResource(R.drawable.bowtie11);

        } else if (bowTieNum == 12) {

            bowTie.setImageResource(R.drawable.bowtie12);

            bowTie2.setImageResource(R.drawable.bowtie12);

        } else if (bowTieNum == 13) {

            bowTie.setImageResource(R.drawable.bowtie13);

            bowTie2.setImageResource(R.drawable.bowtie13);

        } else if (bowTieNum == 14) {

            bowTie.setImageResource(R.drawable.bowtie14);

            bowTie2.setImageResource(R.drawable.bowtie14);

        } else if (bowTieNum == 15) {

            bowTie.setImageResource(R.drawable.bowtie15);

            bowTie2.setImageResource(R.drawable.bowtie15);

        }

    }

    public String updateAlarmInfo(int i) {

        String freqTxt = "";

        if (i == 0) {

            freqTxt = "Every three weeks";

        } else if (i == 1) {

            freqTxt = "Every two weeks";

        } else if (i == 2) {

            freqTxt = "Every week";

        } else if (i == 3) {

            freqTxt = "Every three days";

        } else if (i == 4) {

            freqTxt = "Every two days";

        } else if (i == 5) {

            freqTxt = "Every day";

        } else if (i == 6) {

            freqTxt = "Every 12 hours";

        } else if (i == 7) {

            freqTxt = "Every 9 hours";

        } else if (i == 8) {

            freqTxt = "Every 6 hours";

        } else if (i == 9) {

            freqTxt = "Every 3 hours";

        } else if (i == 10) {

            freqTxt = "Every 2 hours";

        } else if (i == 11) {

            freqTxt = "Every hour";

        } else if (i == 12) {

            freqTxt = "Every 45 minutes";

        } else if (i == 13) {

            freqTxt = "Every 30 minutes";

        } else if (i == 14) {

            freqTxt = "Every 15 minutes";

        } else if (i == 15) {

            freqTxt = "Every 5 minutes";

        }

        return freqTxt;

    }

    public void updateAlarmInterval(int i) {

        Log.i("length of alarm", i + " seconds");

        //EVERYTHING IS IN SECONDS

        if (i == 0) {

            scheduleAlarm(7 * 24 * 60 * 3 * 60);

        } else if (i == 1) {

            scheduleAlarm(7 * 24 * 60 * 2 * 60);

        } else if (i == 2) {

            scheduleAlarm(7 * 24 * 60 * 60);

        } else if (i == 3) {

            scheduleAlarm(3 * 24 * 60 * 60);

        } else if (i == 4) {

            scheduleAlarm(2 * 24 * 60 * 60);

        } else if (i == 5) {

            scheduleAlarm(24 * 60 * 60);

        } else if (i == 6) {

            scheduleAlarm(12 * 60 * 60);

        } else if (i == 7) {

            scheduleAlarm(9 * 60 * 60);

        } else if (i == 8) {

            scheduleAlarm(6 * 60 * 60);

        } else if (i == 9) {

            scheduleAlarm(180 * 60);

        } else if (i == 10) {

            scheduleAlarm(120 * 60);

        } else if (i == 11) {

            scheduleAlarm(60 * 60);

        } else if (i == 12) {

            scheduleAlarm(45 * 60);

        } else if (i == 13) {

            scheduleAlarm(30 * 60);

        } else if (i == 14) {

            scheduleAlarm(15 * 60);

        } else if (i == 15) {

            scheduleAlarm(5 * 60);

        }


    }

    public void updateTeddyName() {


    }

    public static String addLikeStatus(String s) {

//        if (likedMsgs.contains(s)) {
        if (arrayContainsMostOf(likedMsgs, s)) {

            return "LIKED";

        } else if (arrayContainsMostOf(dislikedMsgs, s)) {

            return "DISLIKED";

        } else {

            return "NONE";

        }

    }


    public void generateMsg (View view) {

        if (!sfxMuted) {

            notifGenSfx.start();

        }

        unseenMail++;

        updateUnseenMail();

        totalMsgsSent++;

        updateTotalMsgsSent();

        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

//        builder.setSmallIcon(R.drawable.notification_icon);

        builder.setSmallIcon(R.drawable.ss_icon4);

        builder.setContentTitle("Message From " + teddyName);

        if (msg.isEmpty()) {

            Log.i("Empty msgs","generating some");

            updateMsgs();

        }

        if (msg.isEmpty()) {

            if (dislikedAllMsgs) {

                msg.add("We're terribly sorry! You've disliked all the messages in our repository!" +
                        " More messages will be added with the next update.");

            } else {

                msg.add("Hi " + username + "! You don't have a topic selected (depression, " +
                        "anxiety, or insecurity), but you've still scheduled for notifications, " +
                        "so here's a notification saying hi!");

            }


        }

        int indexOfMsg = rand.nextInt(msg.size());

        String body = msg.get(indexOfMsg);

        if (firstOpening) {

            body = "Welcome to Teddy, The Depression Fighter! Click the email " +
                    "icon at the bottom right to see all past messages. Click and hold on a " +
                    "message to see more options (like, dislike, share). Go to settings to change " +
                    "Teddy's bowtie, enter your birthday for a special birthday message, " +
                    "change Teddy's name, or change your name for more personalized messages. " +
                    "You can also adjust the notification frequency." +
                    " If you enjoy the app, please leave us five stars in the play store." +
                    " It will help a lot. Thank you!!";

        }

        pastMsgs.add(0, body);

        pastMsgsLiked.add(0, addLikeStatus(msg.get(indexOfMsg)));

        //count.add(count.size() + 1);

        Log.i("a","");
        Log.i("aa","");
        Log.i("aaa","");
        Log.i("NOTIFICATION","GENERATED");
        Log.i("aaa","");
        Log.i("aa","");
        Log.i("a","");



        putArrayInPrefs(pastMsgs, "pastMsgs");

        putArrayInPrefs(pastMsgsLiked, "pastMsgsLiked");

        //putArrayInPrefs(arrIntToStr(count), "count");

        Log.i("a","");
        Log.i("aa","");
        Log.i("aaa","");
        Log.i("SAVED","TO PREFS");
        Log.i("aaa","");
        Log.i("aa","");
        Log.i("a","");
        Log.i("Pastmsgs (prefs)","" + getArrayFromPrefs("pastMsgs").size());
        Log.i("PastmsgsLiked (prefs)","" + getArrayFromPrefs("pastMsgsLiked").size());


        builder.setContentText(body);

        builder.setContentIntent(pendingIntent);

        builder.setAutoCancel(true);

        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));

        NotificationManager manager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = "Your_channel_id";

            NotificationChannel channel = new NotificationChannel(channelId,"Channel human readable title", NotificationManager.IMPORTANCE_HIGH);

            manager.createNotificationChannel(channel);

            builder.setChannelId(channelId);

        }

        manager.notify(notifCount,builder.build());

        notifCount++;

    }

    public void updateUnseenMail() {

        prefs.edit().putInt("unseenMail", unseenMail).apply();

        if (unseenMail == 0) {

            mailRedDot.setVisibility(View.INVISIBLE);

            mailCountTxt.setVisibility(View.INVISIBLE);

        } else {

            mailRedDot.setVisibility(View.VISIBLE);

            mailCountTxt.setVisibility(View.VISIBLE);

        }

        String display;

        display = (unseenMail < 100) ? unseenMail + "" : "99+";

        mailCountTxt.setText(display);

    }

    public void openMail (View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }
        mailMode = ALL;

        changeAdapterToAllMsgs(null);

        mailDelBtnStatus();

        mailDelAllStatus();

        updateMail(null);

        unseenMail = 0;

        updateUnseenMail();

        recolorMailTabs("ALL");

        mailX.setVisibility(View.VISIBLE);

        if (mailType.get(mailMode).isEmpty()) {

            noMsgs.setVisibility(View.VISIBLE);

        }

        mailHeader.setVisibility(View.VISIBLE);

        mailBg.setVisibility(View.VISIBLE);

        mailBg2.setVisibility(View.VISIBLE);

        mail.setVisibility(View.VISIBLE);

        dislikedMsgsBox.setVisibility(View.VISIBLE);

        likedMsgsBox.setVisibility(View.VISIBLE);

        allMsgsBox.setVisibility(View.VISIBLE);

        dislikedMsgsPic.setVisibility(View.VISIBLE);

        likedMsgsPic.setVisibility(View.VISIBLE);

        allMsgsPic.setVisibility(View.VISIBLE);

        deleteEmailOptions.setVisibility(View.VISIBLE);

        pressAndHoldPrompt.setVisibility(View.VISIBLE);

        checkForClickOnLetter();



    }

    public void putArrayInPrefs(ArrayList<String> arrayList, String string) {

        Log.i("Putting array","in prefs");

        Log.i("size of array",""+arrayList.size());

        Log.i("name saved as",""+string);

        Set<String> tempSet = new HashSet<String>();

//        Set<String> tempSet = prefs.getStringSet(string, new HashSet<String>());

//        for (String s : arrayList) {
//
//            tempSet.add(s);
//
//        }
        for (int i = 0; i < arrayList.size(); i++) {

            String changed = arrayList.get(i);

            while (tempSet.contains(changed)) {

                changed = arrayList.get(i) + "!@#$%" + Math.random();

            }

            tempSet.add(changed);

            Log.i("Iteration count",""+i);

            Log.i("added to tempset", changed);

        }

        Log.i("size of tempset","" + tempSet.size());

        int aCounter = 0;

        for (String s : tempSet) {

            Log.i("tempest[" + aCounter + "]","" + s);

            aCounter++;

        }

        prefs.edit().putStringSet(string, tempSet).apply();

    }

    public ArrayList<String> getArrayFromPrefs(String string) {

        Set<String> tempSet = prefs.getStringSet(string, new HashSet<String>());

        ArrayList<String> arrayList = new ArrayList<String>();

        for (String s : tempSet) {

            String removeCode = s.split("!@#",2)[0];

            //Log.i("removeCode", removeCode);

            arrayList.add(removeCode);

        }

        return arrayList;

    }

    public void mailDelBtnStatus() {

        if (indexOfSelMail < 0 || mailMode != ALL) {

            mailDelBtn.setAlpha(.5f);

            mailDelBtnTxt.setAlpha(.5f);

        } else {

            mailDelBtn.setAlpha(1f);

            mailDelBtnTxt.setAlpha(1f);

        }

    }

    public void mailDelAllStatus() {

        if ((pastMsgs.isEmpty()) || (mailMode == LIKED) || (mailMode == DISLIKED)) {

            mailDelAllBtn.setAlpha(.5f);

            mailDelAllBtnTxt.setAlpha(.5f);

        } else {

            mailDelAllBtn.setAlpha(1f);

            mailDelAllBtnTxt.setAlpha(1f);

        }

    }

    public void checkForClickOnLetter() {

        mail.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (mailMode != ALL) {

                    return;

                }

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        if (moreOptionsPanel.getVisibility() == View.VISIBLE) {

            return;

        }

                indexOfSelMail = i;

                mailDelBtnStatus();

                boolean deselect = (view.getTag().toString().equals("selected")) ? true : false;

                view.setTag("selected");

                for(int j = 0; j < mailType.get(mailMode).size() + 1; j++) {

//                    mail.getAdapter().getView(j,null, mail).setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    if (mail.getChildAt(j) != null) {

                        mail.getChildAt(j).setBackgroundColor(getResources().getColor(android.R.color.transparent));

                        mail.getChildAt(j).setTag("");

                    }

                }

                if (!deselect) {

                    view.setBackgroundColor(getResources().getColor(R.color.nice_blue));

                    view.setTag("selected");

                } else {

                    indexOfSelMail = -1;

                    mailDelBtnStatus();

                }


                Log.i("this is running","" + indexOfSelMail);

            }

        });

        mail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

//                Toast.makeText(MainActivity.this, "This does what I think", Toast.LENGTH_SHORT).show();

                indexOfSelMail = i;

                Log.i("index of selection", "" + indexOfSelMail);

                openMoreOptionsPanel();

                return false;

            }

        });

    }

    public ArrayList<Integer> indexesOfSimilarElems (ArrayList<String> arrayOfSentences, String sentence) {

        ArrayList<Integer> indexes = new ArrayList<Integer>();

        if (arrayOfSentences.isEmpty()) {

            return indexes;

        }

        //Convert sentence to words
        ArrayList<String> wordsInSentence = new ArrayList<>(Arrays.asList(sentence.split(" ")));

        double totalWordCount = wordsInSentence.size();

        for (int i = 0; i < arrayOfSentences.size(); i++) {

            double sharedWords = 0;

            for (String wordInSentence : wordsInSentence) {

                if (arrayOfSentences.get(i).contains(wordInSentence)) {

                    sharedWords++;

                }

            }

            if (sharedWords / totalWordCount >= .5) {

                indexes.add(i);

            }

        }

        return indexes;

    }


    public void likeMsg(View view) {

        //play sfx

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        if (likeStatus.getText().equals("LIKED")) { //if message is already liked, neutralize

            likeStatus.setText("");

            likeStatus.setVisibility(View.INVISIBLE);

            //check everywhere in main mail for message, and unlike it there

            ArrayList<Integer> indexesInAllMail = indexesOfSimilarElems(pastMsgs, selMsgList.getAdapter().getItem(0).toString());

            for (Integer i : indexesInAllMail) {

                pastMsgsLiked.set(i, "NEUTRAL");

            }

            //check everywhere in unliked messages and remove

            ArrayList<Integer> indexesInDislikedMsgs = indexesOfSimilarElems(dislikedMsgs, selMsgList.getAdapter().getItem(0).toString());

            for (int i : indexesInDislikedMsgs) {

                dislikedMsgs.remove(i);

            }

            //check everywhere in liked messages and remove

            ArrayList<Integer> indexesInLikedMsgs = indexesOfSimilarElems(likedMsgs, selMsgList.getAdapter().getItem(0).toString());

            for (int i : indexesInLikedMsgs) {

                likedMsgs.remove(i);

            }

        } else /*if (likeStatus.getText().equals("DISLIKED"))*/ { // if message is disliked, then like it

            toast("Great! This message will show up more often from now on.");

            likeStatus.setText("LIKED");

            likeStatus.setVisibility(View.VISIBLE);


            //check everywhere in main mail for message, and like it there

            ArrayList<Integer> indexesInAllMail = indexesOfSimilarElems(pastMsgs, selMsgList.getAdapter().getItem(0).toString());

            for (Integer i : indexesInAllMail) {

                pastMsgsLiked.set(i, "LIKED");

            }

            //check everywhere in unliked messages and remove

            ArrayList<Integer> indexesInDislikedMsgs = indexesOfSimilarElems(dislikedMsgs, selMsgList.getAdapter().getItem(0).toString());

            for (int i : indexesInDislikedMsgs) {

                dislikedMsgs.remove(i);

            }

            //Check if it's in liked messages, and if not, add it

            if (!arrayContainsMostOf(likedMsgs, selMsgList.getAdapter().getItem(0).toString())) {

                likedMsgs.add(0,selMsgList.getAdapter().getItem(0).toString());

            }

        }

        updateMail(null);

        putArrayInPrefs(pastMsgsLiked, "pastMsgsLiked");

        putArrayInPrefs(likedMsgs, "likedMsgs");

        putArrayInPrefs(dislikedMsgs, "dislikedMsgs");

    }

    public void dislikeMsg(View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        if (likeStatus.getText().equals("DISLIKED")) { //if message is already disliked, neutralize

            likeStatus.setText("");

            likeStatus.setVisibility(View.INVISIBLE);

            //check everywhere in main mail for message, and neutralize it there

            ArrayList<Integer> indexesInAllMail = indexesOfSimilarElems(pastMsgs, selMsgList.getAdapter().getItem(0).toString());

            for (Integer i : indexesInAllMail) {

                pastMsgsLiked.set(i, "NEUTRAL");

            }

            //check everywhere in disliked messages and remove

            ArrayList<Integer> indexesInDislikedMsgs = indexesOfSimilarElems(dislikedMsgs, selMsgList.getAdapter().getItem(0).toString());

            for (int i : indexesInDislikedMsgs) {

                dislikedMsgs.remove(i);

            }

            //check everywhere in liked messages and remove

            ArrayList<Integer> indexesInLikedMsgs = indexesOfSimilarElems(likedMsgs, selMsgList.getAdapter().getItem(0).toString());

            for (int i : indexesInLikedMsgs) {

                likedMsgs.remove(i);

            }

        } else /*if (likeStatus.getText().equals("DISLIKED"))*/ { // if message is not already disliked, then dislike it

            toast("We're sorry you didn't like it. It won't show up again.");

            likeStatus.setText("DISLIKED");

            likeStatus.setVisibility(View.VISIBLE);

            //check everywhere in main mail for message, and like it there

            ArrayList<Integer> indexesInAllMail = indexesOfSimilarElems(pastMsgs, selMsgList.getAdapter().getItem(0).toString());

            for (Integer i : indexesInAllMail) {

                pastMsgsLiked.set(i, "DISLIKED");

            }

            //check everywhere in liked messages and remove

            ArrayList<Integer> indexesInLikedMsgs = indexesOfSimilarElems(likedMsgs, selMsgList.getAdapter().getItem(0).toString());

            for (int i : indexesInLikedMsgs) {

                likedMsgs.remove(i);

            }

            //Check if it's in disliked messages, and if not, add it

            if (!arrayContainsMostOf(dislikedMsgs, selMsgList.getAdapter().getItem(0).toString())) {

                dislikedMsgs.add(0,selMsgList.getAdapter().getItem(0).toString());

            }

        }

        updateMail(null);

        putArrayInPrefs(pastMsgsLiked, "pastMsgsLiked");

        putArrayInPrefs(likedMsgs, "likedMsgs");

        putArrayInPrefs(dislikedMsgs, "dislikedMsgs");

    }

    public void shareMsg(View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        Intent myIntent = new Intent(Intent.ACTION_SEND);

        myIntent.setType("text/plain");

        String body = "Hey! I received a message from a depression-fighting app that I wanted to share with you: \n \n '" + pastMsgs.get(indexOfSelMail) + "'";

        String download = "\n \n Download here: https://play.google.com/store/apps/details?id=daniel.ajayi.teddythedepressionfighter";

        String sub = "Check out This Awesome Message I Just Got";

        sub = "";

        //myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);

        myIntent.putExtra(Intent.EXTRA_TEXT,body + download);

        startActivity(Intent.createChooser(myIntent, "Share Using"));

    }

    public void openMoreOptionsPanel() {

        if ((mailMode == ALL && pastMsgs.get(indexOfSelMail).contains("terribly sorry")) || (mailMode == ALL && pastMsgs.get(indexOfSelMail).contains("topic selected")) || (mailMode == ALL && pastMsgs.get(indexOfSelMail).contains("personalized"))) {

            return;

        }

        selMsg.clear();

//        if (mailMode == ALL) {
//
//            selMsg.add(pastMsgs.get(indexOfSelMail));
//
//        } else if (mailMode == LIKED) {
//
//            selMsg.add(likedMsgs.get(indexOfSelMail));
//
//        } else if (mailMode == DISLIKED) {
//
//            selMsg.add(dislikedMsgs.get(indexOfSelMail));
//
//        }

        selMsg.add((String) mailType.get(mailMode).get(indexOfSelMail));

        selMsgAdapter.notifyDataSetChanged();

        likeBtn.setVisibility(View.VISIBLE);

        likeBtnTxt.setVisibility(View.VISIBLE);

        dislikeBtn.setVisibility(View.VISIBLE);

        dislikeBtnTxt.setVisibility(View.VISIBLE);

        moreOptionsPanel.setVisibility(View.VISIBLE);

        shareBtn.setVisibility(View.VISIBLE);

        shareBtnTxt.setVisibility(View.VISIBLE);

        Log.i("size of pastmsgs", "" + pastMsgs.size());

        Log.i("size of pastmsgsLiked", "" + pastMsgsLiked.size());

        if ((mailMode == ALL && pastMsgsLiked.get(indexOfSelMail).contains("DISLIKED")) || (mailMode == DISLIKED)) {

            likeStatus.setText("DISLIKED");

            likeStatus.setVisibility(View.VISIBLE);

        } else if ((mailMode == ALL && pastMsgsLiked.get(indexOfSelMail).contains("LIKED")) || (mailMode == LIKED)) {

            likeStatus.setText("LIKED");

            likeStatus.setVisibility(View.VISIBLE);

        }

        selMsgList.setVisibility(View.VISIBLE);

        msgBg.setVisibility(View.VISIBLE);

        moreOptionsX.setVisibility(View.VISIBLE);

    }

    public void closeMoreOptionsPanel(View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        likeBtn.setVisibility(View.INVISIBLE);

        likeBtnTxt.setVisibility(View.INVISIBLE);

        dislikeBtn.setVisibility(View.INVISIBLE);

        dislikeBtnTxt.setVisibility(View.INVISIBLE);

        moreOptionsPanel.setVisibility(View.INVISIBLE);

        shareBtn.setVisibility(View.INVISIBLE);

        shareBtnTxt.setVisibility(View.INVISIBLE);

        likeStatus.setVisibility(View.INVISIBLE);

        selMsgList.setVisibility(View.INVISIBLE);

        msgBg.setVisibility(View.INVISIBLE);

        moreOptionsX.setVisibility(View.INVISIBLE);

    }

    public void closeMail (View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        indexOfSelMail = -1;

        for (int j = 0; j < pastMsgs.size(); j++) {

            if (mail.getChildAt(j) != null) {

                mail.getChildAt(j).setBackgroundColor(getResources().getColor(android.R.color.transparent));

            }

        }

        mailX.setVisibility(View.INVISIBLE);

        mailHeader.setVisibility(View.INVISIBLE);

        mailBg.setVisibility(View.INVISIBLE);

        mail.setVisibility(View.INVISIBLE);

        mailBg2.setVisibility(View.INVISIBLE);

        noMsgs.setVisibility(View.INVISIBLE);

        dislikedMsgsBox.setVisibility(View.INVISIBLE);

        likedMsgsBox.setVisibility(View.INVISIBLE);

        allMsgsBox.setVisibility(View.INVISIBLE);

        dislikedMsgsPic.setVisibility(View.INVISIBLE);

        likedMsgsPic.setVisibility(View.INVISIBLE);

        allMsgsPic.setVisibility(View.INVISIBLE);

        pressAndHoldPrompt.setVisibility(View.INVISIBLE);

        deleteEmailOptions.setVisibility(View.INVISIBLE);

    }

    public void openSettings (View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }
        changeAdapterToAllMsgs(null);

        settingsOverlay.setVisibility(View.VISIBLE);

        settingsOne.setVisibility(View.VISIBLE);

        settingsTwo.setVisibility(View.VISIBLE);

        nameBox.setText(username);

        teddyNameBox.setText(teddyName);

        birthDate.setText(month + "/" +  day + "/" +  year);

        leaveSettingsBtn.setVisibility(View.VISIBLE);

        leaveSettingsBtnTxt.setVisibility(View.VISIBLE);

        notifFreqTxt.setText(updateAlarmInfo(notifFreqProgress));

    }

    public void closeSettings (View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        if (change) {

            toast("notification frequency successfully changed");

        }

        username = nameBox.getText().toString();

        change = false;

        teddyName = teddyNameBox.getText().toString();

        username = username.substring(0,1).toUpperCase() + username.substring(1);

        teddyName = teddyName.substring(0,1).toUpperCase() + teddyName.substring(1);

        prefs.edit().putString("username",username).apply();

        prefs.edit().putString("teddyName",teddyName).apply();

        teddyNameDisplayHome.setText("NAME: " + teddyName.toUpperCase());

        noMsgs.setText("No Mail");

        settingsOverlay.setVisibility(View.INVISIBLE);

        settingsOne.setVisibility(View.INVISIBLE);

        settingsTwo.setVisibility(View.INVISIBLE);

        leaveSettingsBtn.setVisibility(View.INVISIBLE);

        leaveSettingsBtnTxt.setVisibility(View.INVISIBLE);

        updateAlarmInterval(notifFreqProgress);

//        toast("Changes saved");

    }

    public void openBowTiePanel (View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        bowTieActor.setVisibility(View.VISIBLE);

        bowTie2.setVisibility(View.VISIBLE);

        bowTieCollection.setVisibility(View.VISIBLE);

        bowTimePanel.setVisibility(View.VISIBLE);

        exitBowTimePanelBtn.setVisibility(View.VISIBLE);

        exitBowTimePanelBtnTxt.setVisibility(View.VISIBLE);

    }

    public void closeBowTiePanel (View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        bowTieActor.setVisibility(View.INVISIBLE);

        bowTie2.setVisibility(View.INVISIBLE);

        bowTieCollection.setVisibility(View.INVISIBLE);

        bowTimePanel.setVisibility(View.INVISIBLE);

        exitBowTimePanelBtn.setVisibility(View.INVISIBLE);

        exitBowTimePanelBtnTxt.setVisibility(View.INVISIBLE);

    }

    public void updateSfxState (View view) {

        sfxMuted = (sfxMuted) ? false : true;

        prefs.edit().putBoolean("sfxMuted",sfxMuted).apply();


        int box = (sfxMuted) ? R.drawable.gold_box : R.drawable.white_box;

        sfxState.setImageResource(box);

        if (!sfxMuted && view != null) {

            btnTap = MediaPlayer.create(this,R.raw.hi_hat);

            btnTap2 = MediaPlayer.create(this,R.raw.hi_hat);

            btnTap.setVolume(.25f,.25f);

            btnTap2.setVolume(.25f,.25f);

            notifGenSfx = MediaPlayer.create(this,R.raw.gen_notif_sfx);

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

    }

    public void updateMusicState(View view) {

        if (view != null) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

            musicMuted = (musicMuted) ? false : true;

            prefs.edit().putBoolean("musicMuted",musicMuted).apply();

            if (musicMuted) {

                musicCurrentlyPlaying = false;

                prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();

                stopService(new Intent(this, MusicService.class));

                Log.i("!@#$%Is this shit","even running");

            } else {

                musicCurrentlyPlaying = true;

                prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();

                stopService(new Intent(this, MusicService.class));

                startService(new Intent(this, MusicService.class));

            }

        }

        int box = (musicMuted) ? R.drawable.gold_box : R.drawable.white_box;

        musicState.setImageResource(box);

    }

    public void updateMsgs() {

        msg.clear();

        if (depression) {

            addMsgs();

        }

        if (anxiety) {

            addAnxietyMsg();

        }

        if (insecurity) {

            addInsecurityMsg();

        }

        Log.i("size of msg()", msg.size() + "");


        boolean containsDislikedMsgs = true;

        int i = 0;

        int numMsgsNotDisliked = 0;

        while (containsDislikedMsgs) {

            if (dislikedMsgs.isEmpty() || msg.isEmpty()) {

                return;

            }

            if (arrayContainsMostOf(dislikedMsgs, msg.get(i))) {

                msg.remove(i);

            } else {

                Log.i("----", "----");
                Log.i("Message not removed", "" + msg.get(i));
                Log.i("i of Message n removed", "" + i);

                numMsgsNotDisliked++;

                if (numMsgsNotDisliked == msg.size()) {

                    containsDislikedMsgs = false;

                }

            }

            if (i >= msg.size() - 1) {

                i = 0;
                numMsgsNotDisliked = 0;

            } else {

                i++;

            }

            if (msg.size() == 0) {

                containsDislikedMsgs = false;

            }

        }


        if (msg.isEmpty()) {

            dislikedAllMsgs = true;

            Log.i("dislikedAllMsgs", "" + dislikedAllMsgs);

        } else {

            Log.i("messages not deleted", "" + msg);
            Log.i("disliked messages", "" + dislikedMsgs);

        }

        Log.i("msgs","" + msg);

    }

    public static void removeCode () {

//        for (String s : arrayOfSentences) {
//
//            String removeCode = s.split("!@#",2)[0];
//
//            Log.i("removeCode", removeCode);
//
//        }

    }

    public static boolean arrayContainsMostOf (ArrayList<String> arrayOfSentences, String sentence) {

        if (arrayOfSentences.isEmpty()) {

            return false;

        }

        //Convert sentence to words
        ArrayList<String> wordsInSentence = new ArrayList<>(Arrays.asList(sentence.split(" ")));

        double totalWordCount = wordsInSentence.size();

        for (String sentenceInArray : arrayOfSentences) {

            double sharedWords = 0;

            for (String wordInSentence : wordsInSentence) {

                if (sentenceInArray.contains(wordInSentence)) {

                    sharedWords++;

                }

                if (sharedWords / totalWordCount >= .5) {

                    return true;

                }

            }

        }

        return false;

    }

    public void updateDepressionState(View view) {

        if (view != null) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

            depression = (depression) ? false : true;

            prefs.edit().putBoolean("depression", depression).apply();

            toast("Messages updated");

        }

        int box = (depression) ? R.drawable.gold_box : R.drawable.white_box;

        depressionState.setImageResource(box);

        if (view != null) {

            updateMsgs();

        }

    }

    public void updateAnxietyState(View view) {

        if (view != null) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

            anxiety = (anxiety) ? false : true;

            prefs.edit().putBoolean("anxiety", anxiety).apply();

            toast("Messages updated");

        }

        int box = (anxiety) ? R.drawable.gold_box : R.drawable.white_box;

        anxietyState.setImageResource(box);

        if (view != null) {

            updateMsgs();

        }

    }

    public void updateInsecurityState(View view) {

        if (view != null) {

            insecurity = (insecurity) ? false : true;

            prefs.edit().putBoolean("insecurity", insecurity).apply();

            toast("Messages updated");

        }

        int box = (insecurity) ? R.drawable.gold_box : R.drawable.white_box;

        insecurityState.setImageResource(box);

        if (view != null) {

            updateMsgs();

        }

    }

    public void updateMail(View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        int v;

        boolean mailIsEmpty = pastMsgs.isEmpty() && mailMode == ALL || likedMsgs.isEmpty() && mailMode == LIKED || dislikedMsgs.isEmpty() && mailMode == DISLIKED;

        v = (mailIsEmpty)? View.VISIBLE : View.INVISIBLE;

        noMsgs.setVisibility(v);

        arrayAdapter.notifyDataSetChanged();

        likedMsgsAdapter.notifyDataSetChanged();

        dislikedMsgsAdapter.notifyDataSetChanged();

    }

    public ArrayList<String> copyStringArray(ArrayList<String> a) {

        ArrayList<String> arrayList = new ArrayList <>();

        for (String s : a) {

            arrayList.add(s);

        }

        return arrayList;

    }

    public void deleteAllMail (View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        if (pastMsgs.isEmpty() || mailDelAllBtn.getAlpha() < 1f) {

            Log.i("this ", "is already empty, or you are not in the main mail");

            return;

        }

        pastMsgs.clear();

        pastMsgsLiked.clear();

        //count.clear();

        putArrayInPrefs(pastMsgs, "pastMsgs");

        putArrayInPrefs(pastMsgsLiked, "pastMsgsLiked");

        updateMail(null);

        mailDelAllStatus();


    }

    public void deleteSelMail (View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        if (mailDelBtn.getAlpha() != 1f) {

            return;

        }

        if (indexOfSelMail >= 0 && !pastMsgs.isEmpty()) {

            pastMsgs.remove(indexOfSelMail);

            pastMsgsLiked.remove(indexOfSelMail);

            //count.remove(indexOfSelMail);

            putArrayInPrefs(pastMsgs, "pastMsgs");

            putArrayInPrefs(pastMsgsLiked, "pastMsgsLiked");

            //putArrayInPrefs(arrIntToStr(count), "count");

        }

        for (int j = 0; j < pastMsgs.size(); j++) {

            if (mail.getChildAt(j) != null) {

                mail.getChildAt(j).setBackgroundColor(getResources().getColor(android.R.color.transparent));

            }

        }

        indexOfSelMail = -1;

        mailDelAllStatus();

        mailDelBtnStatus();

        updateMail(null);

    }

    public void addMsgs() {

        String username2 = (username.isEmpty() ? "human" : username);

        msg.add("“Happiness is not a brilliant climax to years of grim struggle and anxiety. " +
                "It is a long succession of little decisions simply to be happy in the moment.” —J. Donald Walters");

        msg.add("You can turn social media into a positive tool by following accounts that are centered on improving your emotional state.");

        msg.add("Sometimes, we can all benefit from a good cry.");

        msg.add("Whatever your feeling, you’ll get over it. :)");

        msg.add("Emotions were never designed to be consistently on top of the world. Grief is a part of life.");

        msg.add("Don’t ask yourself not to think about it. Just distract yourself with something else. Read a book, assemble a puzzle, fly a kite, go for a walk.");

        msg.add("Crying can: relieve stress, relieve pain, regulate your mood, release toxins, " +
                "lower your blood pressure, and help you sleep. If it was bad, the body wouldn’t instinctively do it when we’re sad.");

        msg.add("Are you seeing posts that make you unhappy? Sometimes it helps to shut social media.");

        msg.add("You know the way you make excuses for other people’s behavior when they mess up? Do that for you.");

        msg.add("Give yourself permission to suck. You'll get better and better.");

        msg.add("Everything is seasonal. Trees lose leaves and gain leaves. The seasons rotate " +
                "over and over. No emotional state lasts forever. You’ll get over it, guaranteed.");

        msg.add("Whatever your feeling, you’ll get over it.");

        msg.add("You’ll find what you keep thinking about.");

        msg.add("Not everything we think is always true. Sometimes, you have to listen to your mind and say “No, that’s false.”");

        msg.add("Stop imaging fake damaging scenarios.");

        msg.add("Try to see the glass half-full today.");

        msg.add("Don’t fight tears. Crying releases stress hormones. Anger can motivate you" +
                " to solve a problem. Smiles aren’t the only solution to a problem.");

        msg.add("Hey! I think you’re an amazing human doing the best you can with some really " +
                "hard human things right now. Love, " + teddyName + ".");

        msg.add("Study, upgrade your job, read a book, take care of your body, you are your number one priority.");

        msg.add("Sometimes, exercise can dramatically change your mind in dynamic ways you couldn’t imagine.");

        msg.add("Hi it’s me, "+ teddyName + ", and right now, I want you to take a second to just appreciate and love you. You're doing a great job.");

        msg.add("There is no such thing as perfect or normal. Everyone has imperfections, so love you.");

        msg.add("You don’t have to learn how to love yourself. You just have to remember there was" +
                " nothing wrong with you to begin with. You just have to come home. - Nayyirah Waheed");

        msg.add("Your feelings are valid.");

        msg.add("Everything about you is absolutely wonderful.");

        msg.add("There is something inside you that the world needs.");

        msg.add("We all get some good days and some bad days.");

        msg.add("Vitamin D helps fight depression. Go get some sunlight!");

        msg.add("Allow yourself to grow.");

        msg.add("It’s hard but, we are all ultimately responsible for our own feelings, but this " +
                "gives you maximum control over our feelings as well. You can learn to be happy.");

        msg.add("Go for a run, pick flowers, take a bath, eat chocolate. Treat yourself.");

        msg.add("How you love you is how others will love you.");

        msg.add("Sometimes chemicals in your body causing certain emotions can clear up if you take a deep breath.");

        msg.add("Don’t fight all your emotions. Accept them. They are valid.");

        msg.add("Sometimes, it helps to just sit and observe your thought patterns. You can learn" +
                " a lot about you and your idiosyncrasies.");

        msg.add("Hi " + username2 + "! it's " + teddyName + ". Just a reminder to always talk to you, the way you’d talk to someone you love.");

        msg.add("Self-love is a secret to happiness.");

        msg.add("Stay away from people who make you feel like you are hard to love.");

        msg.add("Sometimes, the people in your life can really affect your emotional state.");

        msg.add("Sometimes it’s ok to not be ok.");

        msg.add("7.8 billion people on this planet, and not ONE of them is like you. - " + teddyName);

        msg.add("Just in case you forgot: You matter. You are loved. You are worthy. - " + teddyName);

        msg.add("You are a limited edition. - " + teddyName);

        msg.add("I just want you to know that, better days are coming. - " + teddyName);

        msg.add("Give yourself time. - " + teddyName);

        msg.add("If all you could do today was manage to hold yourself together, that’s ok. You’re doing great.");

        msg.add("Remember, you are enough. If people fail to see that, that’s on them. - " + teddyName);

        msg.add("Block out the noise. Remember, if you don't have haters, you're not doing it right. - " + teddyName);

        msg.add("If all you could do today was manage to hold yourself together, that’s ok. You’re doing great. - " + teddyName);

        msg.add("Sometimes, Name, the most consistent love you’ll get is self-love. - " + teddyName);

        msg.add("Remember to take care of yourself and be your number one supporter. - " + teddyName);

        msg.add("Your past does not dictate your future. - " + teddyName);

        msg.add("Nothing is too hard for you. Some things just require extra effort, but you can do it. - " + teddyName);

        msg.add("You don’t make mistakes. No one is born perfect, it’s all part of the learning process. You’re doing fantastic. - " + teddyName);

        msg.add("Even if you make a mistake, you’ll learn and get better. - " + teddyName);

        msg.add("It’s okay to cry sometimes. You’re human. - " + teddyName);

        msg.add("You don’t need anyone to believe in you. Look in the mirror and say, “" + username +", I believe in you. - " + teddyName);

        msg.add("If you know someone who’s depressed, please resolve never to ask them why." +
                " Depression isn’t a straightforward response to a bad situation; depression just is, like the weather.");

        msg.add("Lonely? ‘Lonely’ and ‘Lovely’ are one letter apart - perhaps that was what you meant.");

    }

    public void addInsecurityMsg() {

        String username2 = (username.isEmpty() ? "human" : username);

        msg.add("You are beautiful. - " + teddyName);

        msg.add("You are your own unique kind of beautiful. - " + teddyName);

        msg.add("Stop caring if people like you and just like yourself. - " + teddyName);

        msg.add("Your value isn’t in your doing or saying. It’s in your being. - " + teddyName);

        msg.add("You are fabulous. - " + teddyName);

        msg.add("You deserve your love and affection. - " + teddyName);

        msg.add("No one can define you. You define you. - " + teddyName);

        msg.add("Loving you will enable you to heal from emotional damage. - " + teddyName);

        msg.add("If you can only love one person in this life, make it yourself. - " + teddyName);

        msg.add("Be nice to yourself even if nobody else is. - " + teddyName);

        msg.add("Keep investing in you. - " + teddyName);

        msg.add("Whoever decided what the official standard of beauty is? You are your own " +
                "beautiful! - " + teddyName);

        msg.add("You are just fine. You’re perfect. The only thing we could all use more of is " +
                "self-acceptance. - " + teddyName);

        msg.add("It comes to a point that you realize that everyone isn’t talking about you. " +
                "Everyone is mostly worried about themselves, just like you. - " + teddyName);

        msg.add("“No one can make you feel inferior without your consent.” – Eleanor Roosevelt");

        msg.add("“If you are insecure, guess what? The rest of the world is too. Do not " +
                "overestimate the competition and underestimate yourself. You are better than you think.” – T. Harv Eker");

        msg.add("Remember, embrace the glorious mess that you are. - Elizabeth Gilbert");

    }

    public void addAnxietyMsg() {

        msg.add("Remember to rest, eat well, exercise, and take care of you. - " + teddyName);

        msg.add("Don’t doubt you! Be your number one fan. - " + teddyName);

        msg.add("A good nap can heal you. - " + teddyName);

        msg.add("There is only ONE you on this planet. You are special. - " + teddyName);

        msg.add("Devote this next year to self-love. You won’t regret it. - " + teddyName);

        msg.add("It’s ok to take a break. - " + teddyName);

        msg.add("The body is a machine. Sometimes it needs a break. - " + teddyName);

        msg.add("Look in the mirror. That’s a beautiful human staring back at you. - " + teddyName);

        msg.add("It’s none of your business what other people think about you. You just do you. - " + teddyName);

        msg.add("Relax. You are destined for greatness, easily. - " + teddyName);

        msg.add("Hydration is key. - " + teddyName);

        msg.add("Don’t worry about your future. Everything worked itself out, and everything " +
                "will continue to work itself out in the future. - " + teddyName);

        msg.add("Feeling impatient or irritated? Stress can cause that. Sometimes you need to " +
                "give yourself a mental health day. - " + teddyName);

        msg.add("Close your eyes, relax the muscles around your eyes, unclench your jaw, let " +
                "your shoulders drop, make tight fists and release, and wiggle your toes." +
                " Your body just released a lot of stress. - " + teddyName);

        msg.add("It helps to sit and analyze: what exactly is causing what I’m feeling? - " + teddyName);

        msg.add("A lot of the stuff you’re worrying about, probably won’t even happen. - " + teddyName);

        msg.add("If you accomplish nothing all day, that’s ok. Love and accept you. - " + teddyName);

        msg.add("Listening to nature sounds or reading a book can help you relax. - " + teddyName);

        msg.add("Don’t feel guilty for resting. You are not an endless machine. Even God" +
                " rested when he built earth. - " + teddyName);

        msg.add("Your past was a lesson, not a life sentence. Forgive yourself. - " + teddyName);

        msg.add("Sometimes, switching your phone to airplane mode for an hour can help fight " +
                "anxiety. - " + teddyName);

        msg.add("Vitamin D can help combat anxiety. Go get some sunlight! - " + teddyName);

        msg.add("Herbal tea is good for your brain and can help fight anxiety. - " + teddyName);

        msg.add("Lie down with your legs elevated for just 15 minutes. It can make a huge " +
                "difference. - " + teddyName);

        msg.add("“Weightless” by Marconi Union supposedly helps reduce anxiety by up to 65%. - " + teddyName);

        msg.add("You can achieve anything you set your mind to. Realistically, in one day, you " +
                "can only achieve so much. Give yourself time. - " + teddyName);

        msg.add("If you’re afraid or nervous, it just means your stepping outside your comfort " +
                "zone. You’re growing. - " + teddyName);

        msg.add("The future is an extension of now. You’re not running to get anywhere. Enjoy " +
                "each day as it is. - " + teddyName);

        msg.add("It’s ok to be busy doing nothing. - " + teddyName);

        msg.add("Give yourself permission to do nothing. Don’t overwork yourself. - " + teddyName);

        msg.add("You’d be amazed how well you might function after a good nap. The body heals " +
                "during sleep. - " + teddyName);

        msg.add("“Rule number one: Don’t sweat the small stuff. Rule number two: It’s all small stuff.” —Robert S. Eliot");

        msg.add("“The universe doesn’t allow perfection. —Stephen Hawking");

        msg.add("Remember that it is not for mortals to demand success. We can only perform" +
                " actions to increase the odds of success. All you can do is your very best," +
                " and if you do, you should be very content. - " + teddyName);

        msg.add("Don’t get so busy that you forget to live and enjoy life. - " + teddyName);

        msg.add("Even the most confident person you know has insecurities. - " + teddyName);

        msg.add("A hot bath can help relieve stress. - " + teddyName);

        msg.add("If you're feeling inferior to other people, just remember this - everybody has " +
                "to sit on a toilet and poop. Without exception. - " + teddyName);

        msg.add("You can’t be expected to know everything now. Things will unravel with " +
                "time. - " + teddyName);

        msg.add("About 90% of our fears are highly improbable. 5% are fears outside of our " +
                "control. Only 5% or less are legitimate. - " + teddyName);

        msg.add("“My anxiety doesn’t come from thinking about the future but from wanting " +
                "to control it.” —Hugh Prather");

        msg.add("Anxiety is a byproduct of living in the future. Bring yourself to the present. " +
                "Step by step. - " + teddyName);

        msg.add("Anxiety brings nothing but sorrow. Make an effort to surrender control. - " + teddyName);

        msg.add("Prayer can help relieve anxiety. - " + teddyName);

        msg.add("“No one can make you feel inferior without your consent.” – Eleanor Roosevelt");

    }

    public void toast(String s) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }

    public void scheduleAlarm(int freq) {
//
//        toast("schedule alarm method called");


        frequency = freq;

        prefs.edit().putInt("frequency", frequency).apply();

        cancelPreviousAlarms();

        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,

                SystemClock.elapsedRealtime() + (freq * 1000),

                (1000 * freq), alarmIntent);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() + (1 * 1000), alarmIntent);
//
//        }

    }

    public void cancelPreviousAlarms() {

        if (alarmMgr!= null) {

            alarmMgr.cancel(alarmIntent);

        }

    }



    public static Notification getNotification(String content, Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ss_icon4);
        return builder.build();
    }

    public void displaySize(View view) {

        if (!sfxMuted && view != null) {

            if (!btnTap.isPlaying()) {

                btnTap.start();

            } else {

                btnTap2.start();

            }

        }

        Log.i("size of pastmsgs", "" + pastMsgs.size());

        Log.i("size of pastmsgsLiked", "" + pastMsgsLiked.size());

        Log.i("size pastmsgs (prefs)", "" + getArrayFromPrefs("pastMsgs").size());

        Log.i("pastmsgsLiked (prefs)", "" + getArrayFromPrefs("pastMsgsLiked").size());

    }

    public void depressionTest(View view) {

        Intent intent = new Intent(this, DepressionTest.class);

        startActivity(intent);

        finish();

    }

//    protected void onPause() {
//
//        super.onPause();
//
//        if (this.isFinishing()) {
//
//            if (gameMusic == null) {
//
//                return;
//
//            }
//
//            if (gameMusic.isPlaying()) {
//
//                songPosition = gameMusic.getCurrentPosition();
//
//                gameMusic.pause();
//
//            }
//
//            Log.i("YOU PRESSED BACK FROM","YOUR 'HOME/MAIN' ACTIVITY");
//
//        }
//
//        Context context = getApplicationContext();
//
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//
//        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
//
//        if (!taskInfo.isEmpty()) {
//
//            ComponentName topActivity = taskInfo.get(0).topActivity;
//
//            if (!topActivity.getPackageName().equals(context.getPackageName())) {
//
//                if (gameMusic == null) {
//
//                    return;
//
//                }
//
//                if (gameMusic.isPlaying()) {
//
//                    songPosition = gameMusic.getCurrentPosition();
//
//                    gameMusic.pause();
//
//                }
//
//                Log.i("YOU LEFT The APP","");
//            }
//            else {
//
//                Log.i("YOU SWITCHED ACTIVITIES", "WITHIN YOUR APP");
//
//                if (gameMusic == null) {
//
//                    return;
//
//                }
//
//
//                if (gameMusic.isPlaying()) {
//
//                    songPosition = gameMusic.getCurrentPosition();
//
//                    gameMusic.pause();
//
//                }
//
//            }
//
//        }
//
//    }


//    @Override
//
//    protected void onResume() {
//
//        super.onResume();
//
//        if (!musicMuted) {
//
//            gameMusic = MediaPlayer.create(this,R.raw.soft_piano);
//
//            gameMusic.setLooping(true);
//
//            gameMusic.seekTo(songPosition);
//
//            gameMusic.start();
//
//        }
//
//        Log.i("App","resumed");
//
//    }

//    @Override

//    protected void onDestroy() {
//
//        super.onDestroy();
//
//        Log.i("App","Just Closed");
//
//        scheduleAlarm(frequency);
//
//        if (musicCurrentlyPlaying) {
//
//            stopService(new Intent(this, MusicService.class));
//
//            musicCurrentlyPlaying = false;
//
//            prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();
//
//        }
//
//    }

//    @Override
//
//    public void onAppForegroundStateChange(AppForegroundStateManager.AppForegroundState newState) {
//
//        if (AppForegroundStateManager.AppForegroundState.IN_FOREGROUND == newState) {
//
//            // App just entered the foreground. Do something here!
//
//        } else {
//
//            // App just entered the background. Do something here!
//
//        }
//    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)

    public void onAppBackgrounded() {

        //App in background

        stopService(new Intent(this, MusicService.class));

        musicCurrentlyPlaying = false;

        prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();


    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)

    public void onAppForegrounded() {

        // App in foreground

        musicCurrentlyPlaying = prefs.getBoolean("musicCurrentlyPlaying", false);

        Log.i("musicCurrentlyPlaying","" + musicCurrentlyPlaying);

        Log.i("musicMuted","" + musicMuted);

        if (!musicCurrentlyPlaying && !musicMuted) {

            startService(new Intent(this, MusicService.class));

            musicCurrentlyPlaying = true;

            musicMuted = false;

            prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();

            Log.i("music","wasn't playing, just turned on");

        }


    }

    @Override

    public void onBackPressed () {

    }

}