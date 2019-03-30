package hanan.example.com.colourfull;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private int RGBvalue = 0;
    private int colourrand;
    private ProgressBar progressBar;
    private boolean alreadyChecked = false;
    private int updateBar = 0;
    private int updateBar100 = 0;
    private int score = 0;
    private int maxtime = 6000;
    private int delay = 1000;
    private boolean initialized = false;
    private boolean nextRound = true;
    public boolean delayNoPress = false;
    private static boolean inGameOver = false;
    private Handler handler = new Handler();
    SharedPreferences savedprefs;
    SharedPreferences.Editor editor;
    private int highscore;
    String colourarray[] = {"Blue", "Red", "Yellow", "Green"};
    int RGBarray[] = {R.color.myBLUE, R.color.myRED, R.color.myYELLOW, R.color.myGREEN};
    int scoreColor = R.color.myWHITE;
    //Dialog myDialog;
    public Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavBar();
        init();
    }

    public void gameover() {
        if (MainActivity.this.timer != null) {
            MainActivity.this.timer.cancel();
            MainActivity.this.timer.purge();
            MainActivity.this.timer = null;
        }
        if (inGameOver) {
            return;
        }
        inGameOver = true;
        highscore();
        Intent gameEnd = new Intent(MainActivity.this, GameOver_Activity.class);
        gameEnd.putExtra("HIGHSCORENUMB",highscore);
        gameEnd.putExtra("SCORENUMB",score);
        startActivity(gameEnd);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }


    public void highscore(){
        if (score >= highscore){
            highscore = score;
            savedprefs = getSharedPreferences("Prefsfile", MODE_PRIVATE);
            editor = savedprefs.edit();
            editor.putInt("Highscore", highscore);
            editor.apply();
        }
    }


    class UpdateScreen extends TimerTask {
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (updateBar <= maxtime && initialized) {
                        updateBar += 35;
                        updateBar100 = updateBar * 100;

                    } else if (updateBar >= maxtime && !nextRound) {
                        alreadyChecked = true;
                        Joystickview joystickview = (Joystickview)findViewById(R.id.joystickview);
                        joystickview.stopListener();
                        gameover();

                    } else {

                        nextRound = false;
                        int prevnumb;
                        initialized = true;
                        Random rand = new Random();
                        colourrand = rand.nextInt(4);
                        Random randRGB = new Random();
                        prevnumb = RGBvalue;
                        RGBvalue = randRGB.nextInt(4);
                        while (RGBvalue == prevnumb) {
                            RGBvalue = randRGB.nextInt(4);
                        }
                        TextView colourtext = findViewById(R.id.colourtext);
                        colourtext.setTextColor(getResources().getColor(RGBarray[RGBvalue]));
                        colourtext.setText(colourarray[colourrand]);
                        updateBar = 0;
                        updateBar100 = 0;
                        alreadyChecked = false;

                    }
                    progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setProgress(updateBar100 / maxtime);
                }
            });

        }
    }
    private void init(){
        inGameOver = false;
        if (timer == null) {
            timer = new Timer();
        }
        TimerTask timertask = new UpdateScreen();
        timer.scheduleAtFixedRate(timertask, 0, 35);
        savedprefs = getSharedPreferences("Prefsfile", MODE_PRIVATE);
        highscore = savedprefs.getInt("Highscore",0);
        Joystickview joystick = (Joystickview) findViewById(R.id.joystickview);
        joystick.setEnabled(true);
        joystick.setOnMoveListener(new Joystickview.OnMoveListener() {
            @Override
            public void onDirection(int mydirection) {
                if (mydirection > 0) {
                    if (!delayNoPress) {
                        TextView rightwrong = findViewById(R.id.answer);
                        rightwrong.setTextColor(getResources().getColor(scoreColor));
                        if (mydirection == (colourrand + 1)) {
                            if (!alreadyChecked) {
                                score += 1;
                                maxtime -= 100;
                                delay = 10 * maxtime / 100;
                                nextRound = true;
                                if (nextRound) {
                                    delayNoPress = true;
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            delayNoPress = false;
                                        }
                                    }, delay);
                                    updateBar = maxtime;
                                    updateBar100 = updateBar * 100;
                                }
                                if (maxtime <= 0) {
                                    maxtime = 100;
                                }
                                alreadyChecked = true;
                            }
                            rightwrong.setText(Integer.toString(score));
                        } else {
                            alreadyChecked = true;
                            if (MainActivity.this.timer != null) {
                                MainActivity.this.timer.cancel();
                                MainActivity.this.timer.purge();
                                MainActivity.this.timer = null;
                            }
                            Joystickview joystickview = (Joystickview)findViewById(R.id.joystickview);
                            joystickview.stopListener();
                            gameover();

                        }
                    }

                }
            }
        });
    }
    public void hideNavBar() {
        this.getWindow().getDecorView().
                setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onPause() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = null;
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (timer == null) {
            timer = new Timer();
        }
        TimerTask timertask = new UpdateScreen();
        timer.scheduleAtFixedRate(timertask, 0, 40);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = null;
                super.onDestroy();
    }
}
