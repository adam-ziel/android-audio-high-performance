package com.example.hellolowlatencyoutput;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * Created by wigleyd on 11/13/2016.
 */

public class frequencyBuffer {

    private double frequency;
    public AudioTrack audioTrack;
    private double[] sound;
    private short[]buffer;
    private int intervals;



    /**
     * Constructor used for unspecified volume amount
     * @param frequency
     * @param intervals
     */
    public frequencyBuffer(double frequency, int intervals){
        this.frequency = frequency;
        this.intervals = intervals;
        int mBufferSize = AudioTrack.getMinBufferSize(intervals,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, intervals,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                mBufferSize, AudioTrack.MODE_STATIC);
        setVolume();
        instantiateValues();
        writeTheBuffer();
    }

    /**
     * Constructor used for specifiying a volume
     * @param frequency Frequency this buffer will output
     * @param intervals signal quality of the wave. More intervals is higher quality. Imagine a rahemian sum
     * @param leftVolume volume amount for the left speaker
     * @param rightVolume volume amount for the right speaker
     */
    public frequencyBuffer(double frequency, int intervals, float leftVolume, float rightVolume){
        this.frequency = frequency;
        this.intervals = intervals;
        int mBufferSize = AudioTrack.getMinBufferSize(intervals,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, intervals,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                mBufferSize, AudioTrack.MODE_STREAM);
        setVolume(leftVolume,rightVolume);
        instantiateValues();
        writeTheBuffer();
    }

    /**
     * Instantiate all of the sound values.
     */
    private void instantiateValues() {
        // Sine wave
        sound = new double[intervals];
        buffer = new short[intervals];
        for (int i = 0; i < sound.length; i++) {
            sound[i] = Math.sin((2.0 * Math.PI * i / ( intervals/ frequency)));
            buffer[i] = (short) (sound[i] * Short.MAX_VALUE);
        }
        audioTrack.write(buffer, 0, sound.length);
    }

    /**
     * Play the selected track
     */
    public void play(){
        audioTrack.play();
    }

    /**
     * Because we are statically playing and not streaming, I only want to write the buffer once
     */
    private void writeTheBuffer(){
        audioTrack.write(buffer, 0, sound.length);
    }

    /**
     * Stop playing the selected track
     */
    public void stop(){
        if (AudioTrack.PLAYSTATE_PLAYING == audioTrack.getPlayState()){
            //basically a redundancy check to ensure that we are playing in the first play
            audioTrack.stop();
            audioTrack.reloadStaticData();
            //audioTrack.release(); //I'm not sure if we want to call release here as we will be replaying the track later on
        }else {
            System.out.println("You have tried to stop a track that was not playing and I stopped you");
        }
    }

    /**
     * The volume is going to be set to a predefined value
     * @param leftVolume volume amount for the left speaker
     * @param rightVolume volume amount for the right speaker
     */
    private void setVolume(float leftVolume, float rightVolume){
        audioTrack.setStereoVolume(leftVolume, rightVolume);
    }

    /**
     * Method that simply sets the volume to the max
     */
    private void setVolume(){
        audioTrack.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
    }

    /**
     * Frees the resources related to the audio track.
     * Also idk how to delete the object in java. Usually its pretty good tho
     */
    protected void destroy(){
        audioTrack.release();
    }

}
