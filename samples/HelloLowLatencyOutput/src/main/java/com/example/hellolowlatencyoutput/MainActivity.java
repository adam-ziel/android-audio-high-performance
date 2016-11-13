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

package com.example.hellolowlatencyoutput;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import android.media.AudioTrack;
import android.media.AudioFormat;
import android.media.AudioManager;

import com.example.android.howie.HowieEngine;

public class MainActivity extends Activity
        implements OnTouchListener{

  private TextView text;

  private long streamId;

  public static native long initPlayback();

  public static native void playTone(long stream);

  public static native void stopTone(long stream);

  public static native void startStream(long stream);

  public static native void stopStream(long stream);

  public static native void destroyPlayback(long stream);

  public static final String TAG = MainActivity.class.getName();

  private TextView textLog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    System.loadLibrary("hello_low_latency_output");

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    HowieEngine.init(this);
    streamId = initPlayback();

    View layoutMain = findViewById(R.id.layoutMain);
//    layoutMain.setOnTouchListener(new View.OnTouchListener() {
//
//      @Override
//      public boolean onTouch(View v, MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//          Log.d(TAG, "Playing sound");
//          playTone(streamId);
//        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//          Log.d(TAG, "Stopping playback");
//          stopTone(streamId);
//        }
//
//        return true;
//      }
//    });

      // Set on touch listener

      View v = findViewById(R.id.button1);
      if (v != null)
          v.setOnTouchListener(this);

      v = findViewById(R.id.button2);
      if (v != null)
          v.setOnTouchListener(this);

      v = findViewById(R.id.button3);
      if (v != null)
          v.setOnTouchListener(this);

      v = findViewById(R.id.button4);
      if (v != null)
          v.setOnTouchListener(this);

      v = findViewById(R.id.button5);
      if (v != null)
          v.setOnTouchListener(this);

      v = findViewById(R.id.button6);
      if (v != null)
          v.setOnTouchListener(this);

      v = findViewById(R.id.button7);
      if (v != null)
          v.setOnTouchListener(this);

      v = findViewById(R.id.button8);
      if (v != null)
          v.setOnTouchListener(this);

      text = (TextView) findViewById(R.id.textView2);
  }

  @Override
  protected void onPause() {
    super.onPause();
    stopStream(streamId);
  }

  @Override
  protected void onResume() {
    super.onResume();
    startStream(streamId);
  }

  private void log(String message) {

    Log.d(TAG, message);
    textLog.setText(textLog.getText() + "\n" + message);
  }

    private void playSound(double frequency, int duration) {
        // AudioTrack definition
        int mBufferSize = AudioTrack.getMinBufferSize(44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT);

        AudioTrack mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                mBufferSize, AudioTrack.MODE_STREAM);

        // Sine wave
        double[] mSound = new double[44100];
        short[] mBuffer = new short[duration];
        for (int i = 0; i < mSound.length; i++) {
            mSound[i] = Math.sin((2.0*Math.PI * i/(44100/frequency)));
            mBuffer[i] = (short) (mSound[i]*Short.MAX_VALUE);
        }

        //mAudioTrack.setVolume(AudioTrack.getMaxVolume());
        mAudioTrack.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
        mAudioTrack.play();

        mAudioTrack.write(mBuffer, 0, mSound.length);
        mAudioTrack.stop();
        mAudioTrack.release();

    }


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
              playTone(streamId);
              break;
          case R.id.button2:
              playTone(streamId);
              break;
          case R.id.button3:
              playTone(streamId);
              break;
          case R.id.button4:
              playTone(streamId);
              break;
          case R.id.button5:
              playTone(streamId);
              break;
          case R.id.button6:
              playTone(streamId);
              break;
          case R.id.button7:
              playTone(streamId);
              break;
          case R.id.button8:
              playSound(440, 44100);
              break;
          default:
              return false;
        }
        break;

      // Button has been released. Stop playing the music

      case MotionEvent.ACTION_UP:
        switch (id) {
          case R.id.button1: //1 button
              stopTone(streamId);
              break;
          case R.id.button2: //2 button
              stopTone(streamId);
              break;
          case R.id.button3:
              stopTone(streamId);
              break;
          case R.id.button4:
              stopTone(streamId);
              break;
          case R.id.button5:
              stopTone(streamId);
              break;
          case R.id.button6:
              stopTone(streamId);
              break;
          case R.id.button7:
              stopTone(streamId);
              break;
          case R.id.button8:
              //stopTone(streamId);
              break;
          default:
            return false;
        }
        break;

      default:
        return false;
    }

    return false;
  }

}
