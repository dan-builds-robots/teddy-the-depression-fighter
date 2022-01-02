package daniel.ajayi.teddythedepressionfighter;

import android.app.Service;

import android.content.Intent;

import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import static daniel.ajayi.teddythedepressionfighter.MainActivity.musicMuted;

public class MusicService extends Service {

    MediaPlayer player;

    @Nullable

    @Override

    public IBinder onBind(Intent intent) {

        return null;

    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("made it","to service");

        Log.i("asdfasdf","asdfasdfasdfasdf");

        if (musicMuted) {

            return START_STICKY;

        }

        player = MediaPlayer.create(this,R.raw.soft_piano);

        //player.setVolume(50,50);

        player.setLooping(true);

        player.start();

        return START_STICKY;

    }

    public boolean isPlaying() {

        if (player == null) {

            return false;

        }

        return player.isPlaying();

    }

    @Override

    public void onDestroy() {

        super.onDestroy();

        player.stop();

    }

}
