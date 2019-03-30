package hanan.example.com.colourfull;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class GameOver_Activity extends AppCompatActivity {
    private int highScore;
    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameoverscreen);
        hideNavBar();
        gameover();
    }

    public void gameover(){
        Intent myIntent = getIntent();
        highScore = myIntent.getIntExtra("HIGHSCORENUMB", 0);
        score = myIntent.getIntExtra("SCORENUMB", 0);
        TextView scoreV = (TextView) findViewById(R.id.score);
        scoreV.setText("score: " + Integer.toString(score));
        TextView highscoreV = (TextView) findViewById(R.id.highscoreV);
        highscoreV.setText(Integer.toString(highScore));
        Button replay = (Button) findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replay = new Intent(GameOver_Activity.this, MainActivity.class);
                startActivity(replay);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });
        ImageButton menu = (ImageButton) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(GameOver_Activity.this, Splash_Activity.class);
                startActivity(menu);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
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
}



