package daniel.ajayi.teddythedepressionfighter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ScoreScreen extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 2;

    TextView depressionScore;

    ProgressBar progressBar;

    GridLayout hotlineGridLayout;

    TextView status;

    ImageView moreInfoPanel;

    ImageView moreInfoPanelStroke;

    ImageView closeMoreOptions;

    TextView closeMoreOptionsTxt;

    TextView moreInfoDescription;

    TextView fromWebsite;

    TextView moreInfoNum;

    ImageView moreInfoNumBg;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score_screen);

        progressBar = findViewById(R.id.progressBar);

        depressionScore = findViewById(R.id.depressionScore);

        hotlineGridLayout = findViewById(R.id.hotlineGridLayout);

        hotlineGridLayout.setVisibility(View.INVISIBLE);

        hotlineGridLayout.setVisibility(View.VISIBLE);

        status = findViewById(R.id.diagnosis);

        moreInfoPanel = findViewById(R.id.moreInfoPanel);

        moreInfoPanelStroke = findViewById(R.id.moreInfoPanelStroke);

        closeMoreOptions = findViewById(R.id.closeMoreInfo);

        closeMoreOptionsTxt = findViewById(R.id.closeMoreInfoTxt);

        moreInfoDescription = findViewById(R.id.moreInfoDescription);

        fromWebsite = findViewById(R.id.fromWebsite);

        moreInfoNum = findViewById(R.id.moreInfoNum);

        moreInfoNumBg = findViewById(R.id.moreInfoNumBg);

        depressionScore.setText(DepressionTest.depressionScore + "");

        progressBar.setProgress(DepressionTest.depressionScore);

        progressBar.setMax(10);

        setStats();


    }

    public void setStats() {

        if (DepressionTest.depressionScore >= 4 && DepressionTest.depressionScore <= 5) {

            progressBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(this, R.color.yellow), PorterDuff.Mode.SRC_IN);

            status.setText("Mild Depression. Not bad.");

        } else if (DepressionTest.depressionScore == 7 || DepressionTest.depressionScore == 6) {

            progressBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.SRC_IN);

            hotlineGridLayout.setVisibility(View.VISIBLE);

            status.setText("You may benefit from help. Hang in here.");

        }  else if (DepressionTest.depressionScore >= 8) {

            progressBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(this, R.color.red), PorterDuff.Mode.SRC_IN);

            hotlineGridLayout.setVisibility(View.VISIBLE);

            status.setText("Please get help.");

        } else if (DepressionTest.depressionScore >= 0 && DepressionTest.depressionScore <= 3) {

            progressBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(this, R.color.green), PorterDuff.Mode.SRC_IN);

            status.setText("Pretty good there, mate. Your mental health is stellar.");

        }

    }
//
//    public void requestPermission() {
//
//        if (ContextCompat.checkSelfPermission(ScoreScreen.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(ScoreScreen.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
//        }
//        else
//        {
//            startActivity(intent);
//        }
//
//    }

    public void callSAMHSA(View view) {

        if (!MainActivity.sfxMuted && view != null) {

            if (!MainActivity.btnTap.isPlaying()) {

                MainActivity.btnTap.start();

            } else {

                MainActivity.btnTap2.start();

            }

        }

        Intent callIntent = new Intent(Intent.ACTION_DIAL);

        callIntent.setData(Uri.parse("tel:1-800-662-4357"));

        startActivity(callIntent);

    }

    public void callNSP(View view) {

        if (!MainActivity.sfxMuted && view != null) {

            if (!MainActivity.btnTap.isPlaying()) {

                MainActivity.btnTap.start();

            } else {

                MainActivity.btnTap2.start();

            }

        }

        Intent callIntent = new Intent(Intent.ACTION_DIAL);

        callIntent.setData(Uri.parse("tel:1-800-273-8255"));

        startActivity(callIntent);

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

    public void openMoreInfoPanel (View view)  {

        if (!MainActivity.sfxMuted && view != null) {

            if (!MainActivity.btnTap.isPlaying()) {

                MainActivity.btnTap.start();

            } else {

                MainActivity.btnTap2.start();

            }

        }

        if (view.getTag().toString().equals("NSP")) {

            moreInfoNum.setText("National Suicide Prevention 1-800-273-TALK (8255)");

            moreInfoDescription.setTextSize(18f);

            moreInfoDescription.setText("A national network of local crisis centers that provides free and confidential emotional support to people in suicidal crisis or emotional distress 24 hours a day, 7 days a week.");

        } else {

            moreInfoNum.setText("SAMHSA’s National Helpline 1-800-662-HELP (4357)");

            moreInfoDescription.setTextSize(13f);

            moreInfoDescription.setText("A confidential, free, 24/7, information service, in English and Spanish for individuals and family members facing mental and/or substance use disorders. Provides referrals to local treatment facilities, support groups, and community-based organizations. Callers can also order free publications and other information.");

        }

        moreInfoPanel.setVisibility(View.VISIBLE);

        moreInfoPanelStroke.setVisibility(View.VISIBLE);

        closeMoreOptions.setVisibility(View.VISIBLE);

        closeMoreOptionsTxt.setVisibility(View.VISIBLE);

        moreInfoDescription.setVisibility(View.VISIBLE);

        fromWebsite.setVisibility(View.VISIBLE);

        moreInfoNum.setVisibility(View.VISIBLE);

        moreInfoNumBg.setVisibility(View.VISIBLE);

    }

    public void closeMoreInfoPanel (View view)  {

        if (!MainActivity.sfxMuted && view != null) {

            if (!MainActivity.btnTap.isPlaying()) {

                MainActivity.btnTap.start();

            } else {

                MainActivity.btnTap2.start();

            }

        }

        moreInfoPanel.setVisibility(View.INVISIBLE);

        moreInfoPanelStroke.setVisibility(View.INVISIBLE);

        closeMoreOptions.setVisibility(View.INVISIBLE);

        closeMoreOptionsTxt.setVisibility(View.INVISIBLE);

        moreInfoDescription.setVisibility(View.INVISIBLE);

        fromWebsite.setVisibility(View.INVISIBLE);

        moreInfoNum.setVisibility(View.INVISIBLE);

        moreInfoNumBg.setVisibility(View.INVISIBLE);


    }

}