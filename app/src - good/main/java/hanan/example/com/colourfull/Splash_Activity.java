package hanan.example.com.colourfull;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Splash_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);
        Button startbutton = findViewById(R.id.startbutton);
        final Intent startgame = new Intent(this, MainActivity.class);
        final Context splash = this;
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(startgame);
                finish();
            }
        });
    }

}
