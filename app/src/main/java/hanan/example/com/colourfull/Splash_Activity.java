package hanan.example.com.colourfull;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Splash_Activity extends AppCompatActivity {
    Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);
        final ConstraintLayout splash = (ConstraintLayout)findViewById(R.id.splash_main);
        hideNavBar();
        Button startbutton = findViewById(R.id.startbutton);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          Intent startgame = new Intent(Splash_Activity.this, MainActivity.class);
                          startActivity(startgame);
                          overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                          finish();
                      }
                  },100);
            }
        });
        Button instrBtn = (Button) findViewById(R.id.instructions);
        instrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(R.style.Scale_Out);
            }
        });
        Button about = (Button) findViewById(R.id.about_button);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl();
            }
        });
    }

    public void openDialog(int type) {
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.instructions);
        Button exitBtn = (Button) mDialog.findViewById(R.id.exit);
        mDialog.setCancelable(false);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.getWindow().getAttributes().windowAnimations = R.style.Scale_In;
                mDialog.dismiss();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().getAttributes().windowAnimations = type;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.show();
            }
        },150);
    }
    public void hideNavBar(){
        this.getWindow().getDecorView().
                setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    public void openUrl(){
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://hangamedev.wixsite.com/website"));
        startActivity(browser);
    }
}
