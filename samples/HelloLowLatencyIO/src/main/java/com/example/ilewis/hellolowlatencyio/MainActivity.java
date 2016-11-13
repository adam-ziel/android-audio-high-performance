/*
 * Copyright 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.ilewis.hellolowlatencyio;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**********/

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.example.android.howie.HowieEngine;

public class MainActivity extends Activity
     implements OnTouchListener, OnClickListener{

    private TextView text;

    private static final double lowNote = -24;
    private static final double noteRange = 36;
    private static final double referenceFreq = 440;
    private static final double lowRes = .2f;
    private static final double resRange = .7f;

    private static final double noteBase = Math.pow(2.0, 1.0 / 12.0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.loadLibrary("hello_low_latency_io");
        HowieEngine.init(this);
        stream = initStream();

        final View main = findViewById(R.id.main);
        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX() / (float) main.getWidth();
                float y = motionEvent.getY() / (float) main.getHeight();

                double note = y * noteRange + lowNote;
                double freq = referenceFreq * Math.pow(noteBase, note);
                setParams(
                        stream,
                        (float) (freq / 48000.0),
                        .95f,
                        x * 3.f
                );
                return true;
            }
        });

        // Set on touch listener

        View v = findViewById(R.id.button1);
        if (v != null)
            v.setOnTouchListener(this);

        v = findViewById(R.id.button2);
        if (v != null)
            v.setOnTouchListener(this);

        v = findViewById(R.id.button3);
        if (v != null)
            v.setOnClickListener(this);

        v = findViewById(R.id.button4);
        if (v != null)
            v.setOnClickListener(this);

        v = findViewById(R.id.button5);
        if (v != null)
            v.setOnClickListener(this);

        v = findViewById(R.id.button6);
        if (v != null)
            v.setOnClickListener(this);

        v = findViewById(R.id.button7);
        if (v != null)
            v.setOnClickListener(this);

        v = findViewById(R.id.button8);
        if (v != null)
            v.setOnClickListener(this);

        text = (TextView) findViewById(R.id.textView2);
    }

    private long stream;

    private native long initStream();

    private native void setParams(long stream, float frequency, float resonance, float gain);

    // On touch

    @Override
    /*
     * This method is used for detecting touches which means it differentiates between presses and releases
     */
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        int id = v.getId();

        switch (action) {
            // Button is held down, start playing the music

            case MotionEvent.ACTION_DOWN:
                switch (id) {
                    case R.id.button1:
                        //start 1
                        final View main = findViewById(R.id.main);
                        main.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                float x = motionEvent.getX() / (float) main.getWidth();
                                float y = motionEvent.getY() / (float) main.getHeight();

                                double note = y * noteRange + lowNote;
                                double freq = referenceFreq * Math.pow(noteBase, note);
                                setParams(
                                        stream,
                                        (float) (freq / 48000.0),
                                        .95f,
                                        x * 3.f
                                );
                                return true;
                            }
                        });
                        break;

                    case R.id.button2:
                        //start 2
                        break;
                    //The rest follow a similar logic. Idk if you want them to trip on press or clicks
                    //I have set up and labelled buttons 1-8
                    default:
                        return false;
                }
                break;

            // Button has been released. Stop playing the music

            case MotionEvent.ACTION_UP:
                switch (id) {
                    case R.id.button1: //1 button

                        break;

                    case R.id.button2: //2 button

                        break;
                    //should have a stop action for every button that has a start from press
                    default:
                        return false;
                }
                break;

            default:
                return false;
        }

        return false;
    }

    // On click which means a press down and then a press up (release). Im not sure which you want the buttons to go.

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button3:
                //trigger button 3 on full click
                break;

            case R.id.button4:

                break;

            //if you want to have the buttons used as clicks put them in here
        }
    }
}
