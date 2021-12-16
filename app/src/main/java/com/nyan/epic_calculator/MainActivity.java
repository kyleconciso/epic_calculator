package com.nyan.epic_calculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String expression = "";
    MediaPlayer vineBoom;
    MediaPlayer funnySound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exprEquals(View v) {
        Context context = Context.enter();
        Scriptable scope = context.initStandardObjects();
        context.setOptimizationLevel(-1);
        TextView screen = findViewById(R.id.screen2);

        if (!expression.equals("")) {
            try {
                String expression_filtered = expression.replace("×","*").replace("÷","/");
                Object result = context.evaluateString(scope, expression_filtered, "<cmd>", 1, null);
                screen.setText(result.toString());
            } catch (Exception e) {
                Log.e("Calculator", "An error occurred within the evaluation.");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void buttonAction(View v){
        TextView screen = findViewById(R.id.screen);
        Button btn = findViewById(v.getId());
        expression = String.join("",expression,btn.getText().toString());
        screen.setText(expression);
        exprEquals(v);

        cleanUpMediaPlayer();
        vineBoom = MediaPlayer.create(this, R.raw.vine_boom_sound);
        vineBoom.start();

        Random random = new Random();
        int randomInt = random.nextInt(11);
        if (randomInt == 1){
            funnySound = MediaPlayer.create(this, R.raw.funny_sound);
            funnySound.start();
        }

    }
    public void buttonClr(View v){
        TextView screen = findViewById(R.id.screen);
        TextView screen2 = findViewById(R.id.screen2);
        expression = "";
        screen.setText("");
        screen2.setText("");
    }
    public void cleanUpMediaPlayer(){
        if(vineBoom != null) {
            if(vineBoom.isPlaying()) {
                vineBoom.stop();
            }
            vineBoom.release();
        }
    }
}