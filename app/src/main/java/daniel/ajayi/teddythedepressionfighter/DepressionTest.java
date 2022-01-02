package daniel.ajayi.teddythedepressionfighter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static daniel.ajayi.teddythedepressionfighter.MainActivity.musicCurrentlyPlaying;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.musicMuted;
import static daniel.ajayi.teddythedepressionfighter.MainActivity.prefs;

public class DepressionTest extends AppCompatActivity {

    ArrayList<String> responses = new ArrayList<>();

    ArrayList<String> questions = new ArrayList<>();

    ArrayList<Integer> numOfChosen = new ArrayList<>();

    TextView questionText;

    int numCompletedQuestions = 0;

    TextView questionTopic;

    ImageView finishBtn;

    TextView finishBtnTxt;

    ImageView backArrow;

    TextView backArrowTxt;

    ImageView nextArrow;

    TextView nextArrowTxt;

    ImageView everyday;

    ImageView overHalf;

    ImageView frequent;

    ImageView never;

    ArrayList<ImageView> options = new ArrayList<>();

    static int numQuestions;

    int currentQuestion = 0;

    static int depressionScore;

    ProgressBar testCompletionBar;





    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_depression_test);

        questionText = findViewById(R.id.questionTxt);

        questionTopic = findViewById(R.id.question_topic);

        finishBtn = findViewById(R.id.submitBtn);

        finishBtnTxt = findViewById(R.id.submitBtnTxt);

        backArrow = findViewById(R.id.backArrow);

        backArrowTxt = findViewById(R.id.backArrowTxt);

        nextArrow = findViewById(R.id.nextArrow);

        nextArrowTxt = findViewById(R.id.nextArrowTxt);

        testCompletionBar = findViewById(R.id.progressBar);

        everyday = findViewById(R.id.choiceD);

        overHalf = findViewById(R.id.choiceC);

        frequent = findViewById(R.id.choiceB);

        never = findViewById(R.id.choiceA);

        options.add(never);

        options.add(frequent);

        options.add(overHalf);

        options.add(everyday);

        questions.add("Has your mood been persistently sad?");

        questions.add("Have you been having difficulty sleeping for most of the past two weeks (Chronic Insomnia)?");

        questions.add("Have you been waking up at times different from when you usually wake, such as at 2 to 4 am?");

        questions.add("Has your normal sleep schedule changed from what it usually is?");

        questions.add("Have you been sleeping less at night and sleeping more during the day?");

        questions.add("Have you been sleeping far more than you usually sleep? (Hypersomnia)");

        questions.add("Have you been finding tasks that you usually enjoy far less interesting to you (anhedonia)?");

        questions.add("Have you been deriving far less pleasure from activities that you typically enjoy?");

        questions.add("Have you been blaming yourself for things that may not have been your fault?");

        questions.add("Have you been feeling abnormal amounts of guilt?");

        questions.add("Have you been feeling worthless and unneeded?");

        questions.add("Have you been experiencing far less energy than you usually have?");

        questions.add("Have you been feeling fatigue or a loss of energy?");

        questions.add("Have you been struggling to stay concentrated on a task at hand?");

        questions.add("Have you been experiencing a diminished ability to think or concentrate?");

        questions.add("Have you been experiencing recurrent thoughts of death?");

        questions.add("Have you been recurrently imagining suicidal scenarios without a specific plan to act?");

        questions.add("Have you attempted suicide or created a specific plan for committing suicide?");

        questions.add("To the best of your knowledge, do you have a psychotic disorder?");

        questions.add("Have you experienced the recent death of a loved one?");

        questions.add("Have you experienced any other briefly traumatic events in the recent past?");

        numQuestions = questions.size();

        addEmptyStrings(numQuestions);

        testCompletionBar.setMax(numQuestions);

        testCompletionBar.setProgress(0);

        testCompletionBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(this, R.color.dark_blue), PorterDuff.Mode.SRC_IN);

        checkFinish(null);

        updateQuestionBtnStatus(null);


    }

    public void addEmptyStrings(int j) {

        for (int i = 0; i < j; i++) {

            responses.add("");

        }

    }

    public void updateQuestionBtnStatus(View view) {

        if (!MainActivity.sfxMuted && view != null) {

            if (!MainActivity.btnTap.isPlaying()) {

                MainActivity.btnTap.start();

            } else {

                MainActivity.btnTap2.start();

            }

        }

        if (currentQuestion == 0) {

            backArrow.setAlpha(.5f);

            backArrowTxt.setAlpha(.5f);

        } else {

            backArrow.setAlpha(1f);

            backArrowTxt.setAlpha(1f);

        }

        if (currentQuestion == numQuestions) {

            nextArrow.setAlpha(.5f);

            nextArrowTxt.setAlpha(.5f);

        } else {

            nextArrow.setAlpha(1f);

            nextArrowTxt.setAlpha(1f);

        }

    }

    public void updateResponses (View view) {

        if (!MainActivity.sfxMuted && view != null) {

            if (!MainActivity.btnTap.isPlaying()) {

                MainActivity.btnTap.start();

            } else {

                MainActivity.btnTap2.start();

            }

        }

        Log.i("This","is getting run. yuppers.");
//
//        for (ImageView iv : options) {
//
//            iv.setColorFilter(ContextCompat.getColor(this, R.color.lighter_blue));
//
//        }
//
//        ((ImageView)view).setColorFilter(ContextCompat.getColor(this, R.color.dark_blue));

        responses.set(currentQuestion, (view.getTag().toString()));

        updateQuestion(findViewById(R.id.nextArrow));

    }

    public void selectionColor () {

        for (ImageView iv : options) {

            iv.setColorFilter(ContextCompat.getColor(this, R.color.lighter_blue));

        }

        if (responses.get(currentQuestion) == "") {

            return;

        }

        options.get(selectedChoice(responses.get(currentQuestion))).setColorFilter(ContextCompat.getColor(this, R.color.dark_blue));

    }

    public int selectedChoice(String s) {

        if (s.toLowerCase().contains("never")) {

            return 0;

        } else if (s.toLowerCase().contains("less")) {

            return 1;

        } else if (s.toLowerCase().contains("more")) {

            return 2;

        } else {

            return 3;

        }

    }

    public void checkFinish (View view) {

        int v = (numCompletedQuestions == numQuestions) ? View.VISIBLE : View.INVISIBLE;

            finishBtn.setVisibility(v);

            finishBtnTxt.setVisibility(v);

    }

    public void updateQuestion (View view) {

        if (view.getTag().toString().equals("NEXT")) {

            if (!MainActivity.sfxMuted && view != null) {

                if (!MainActivity.btnTap.isPlaying()) {

                    MainActivity.btnTap.start();

                } else {

                    MainActivity.btnTap2.start();

                }

            }

            if (currentQuestion + 1 == numQuestions) {

                updateProgressBar(null);

                selectionColor();

                checkFinish(null);

                if (finishBtn.getVisibility() == View.INVISIBLE) {

                    toast("You skipped a question.");

                }

                return;

            }

            currentQuestion++;

        } else if (view.getTag().toString().equals("BACK")) {

            Log.i("Action performed","you just went back");

            if (!MainActivity.sfxMuted && view != null) {

                if (!MainActivity.btnTap.isPlaying()) {

                    MainActivity.btnTap.start();

                } else {

                    MainActivity.btnTap2.start();

                }

            }

            if (currentQuestion == 0) {

                return;

            }

            currentQuestion--;

        }

        questionText.setText(questions.get(currentQuestion));

        updateQuestionTopic();

        updateQuestionBtnStatus(null);

        updateProgressBar(null);

        selectionColor();

        checkFinish(null);

    }

    public void updateQuestionTopic() {

        if (currentQuestion <= 0) {

            questionTopic.setText("A. Mood");

        } else if (currentQuestion <= 5) {

            questionTopic.setText("B. Sleep Patterns");

        } else if (currentQuestion <= 7) {

            questionTopic.setText("C. Activity Interest Levels");

        } else if (currentQuestion <= 10) {

            questionTopic.setText("E. Guilt or Worthlessness");

        } else if (currentQuestion <= 12) {

            questionTopic.setText("F. Energy Levels");

        } else if (currentQuestion <= 14) {

            questionTopic.setText("G. Concentration Levels");

        } else if (currentQuestion <= 17) {

            questionTopic.setText("H. Pyschomotor State");

        } else {

            questionTopic.setText("I. Other Conditions");

        }

    }

    public void goHome (View view) {

        if (!MainActivity.sfxMuted && view != null) {

            if (!MainActivity.btnTap.isPlaying()) {

                MainActivity.btnTap.start();

            } else {

                MainActivity.btnTap2.start();

            }

        }

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        finish();

    }

    public int numNonEmpty(ArrayList<String> arrayList) {

        int count = 0;

        for (String s : arrayList) {

            if (s.equals("")) {

                count++;

            }

        }

        return arrayList.size() - count;

    }

    public void updateProgressBar (View view) {

        numCompletedQuestions = numNonEmpty(responses);

        testCompletionBar.setProgress(numCompletedQuestions);

    }

    public void computeScore() {

        int numSevereSymptoms = numSymptoms(2);

        // KEY SYMPTOMS: persistent sadness, most days, loss of interests, most days, fatigue or low energy, most days

        if (numSevereSymptoms < 4) {

            depressionScore = numSevereSymptoms;

            //no depression

        } else if (numSevereSymptoms == 4) {

            //mild depression

            depressionScore = 4;



        } else if (numSevereSymptoms == 5 || numSevereSymptoms == 6) {

            //moderate depression

            depressionScore = numSevereSymptoms;

        } else if (numSevereSymptoms >= 7) {

            depressionScore = numSevereSymptoms;

            //severe depression

        }

        if (depressionScore < 6 && vitalSymptoms()) {

            depressionScore = 6;

        }

        if (selectedChoice(responses.get(17)) >= 2) {

            depressionScore += 3;

        }

        if (depressionScore < 0) {

            depressionScore = 0;

        }

        if (depressionScore > 10) {

            depressionScore = 10;

        }

    }

    public int numSymptoms(int threshold) {

        int count = 0;

        for (String s: responses) {

            if (selectedChoice(s) >= threshold) {

                count++;

            }

        }

        return count;

    }

    public boolean vitalSymptoms() {

        boolean depressed = false;

//      persistent sadness most days or loss of interest most days or fatigue or low energy most days*/) {

        // consistent low mood
        if (selectedChoice(responses.get(0)) >= 2) {

            depressed = true;

        }

        // loss of interest
        if (selectedChoice(responses.get(6)) >= 2 || selectedChoice(responses.get(7)) >= 2) {

            depressed = true;

        }

        // fatigue/low energy most days
        if (selectedChoice(responses.get(11)) >= 2 || selectedChoice(responses.get(12)) >= 2) {

            depressed = true;

        }

        return depressed;

    }

    public void submit (View view) {

        computeScore();

        Intent intent = new Intent(this, ScoreScreen.class);

        startActivity(intent);

        finish();

    }

    public void toast(String s) {

        Toast.makeText(DepressionTest.this, "" + s, Toast.LENGTH_SHORT).show();

    }


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

        if (!musicCurrentlyPlaying && !musicMuted) {

            startService(new Intent(this, MusicService.class));

            musicCurrentlyPlaying = true;

            prefs.edit().putBoolean("musicCurrentlyPlaying", musicCurrentlyPlaying).apply();

        }


    }

    @Override

    public void onBackPressed () {

    }

}