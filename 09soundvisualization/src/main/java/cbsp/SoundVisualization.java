package cbsp;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class SoundVisualization extends PApplet
{
    Minim minim;
    AudioPlayer player;

    float counter = 0;
    int direction = 0; //0 = to the right; 1 = to the left
    float relative_position = 0;
    FFT fft;

    @Override
    public void settings() {
        size(320, 170);
    }

    @Override
    public void setup() {
        smooth();

        counter = 0;

        minim = new Minim(this);
        player = minim.loadFile("src/main/media/Phlex - Take Me Home Tonight (feat. Caitlin Gare) [Argofox].mp3", 256);
        fft = new FFT( player.bufferSize(), player.sampleRate() );
        player.play();
    }

    @Override
    public void draw() {

        // TODO Task 1-7

        // You can use predefined variables such as:
        //	width: screen width,
        //  height: screen height,
        //  mouseX: x coordinate of current mouse cursor,
        //  mouseY: y coordinate of current mouse cursor...

        // For handling the keyboard and mouse input, just
        // define new public void methods with the name
        // keyPressed(), mousePressed()...

        //task1();
        //task2();
        //task3();
        //task4();
        //task5();
        //task6();
        task7();

    }

    public void task1(){
        ellipse(25+counter, height/2, height/10, height/10);

        if(25+counter > 295){
            direction = 1;
        } else if (25+counter < 25) {
            direction = 0;
        }

        if(direction == 0){
            counter += 270F / 750;
        } else if (direction == 1) {
            counter -= 270F / 750;
        }
    }

    public void task2(){
        if(keyPressed){
            if(player.isPlaying()){
                player.pause();
            }
            else {
                player.play();
            }
        }
    }

    public void task3(){
        relative_position = (float) player.position()/ (float) player.length();
        float drawing_position = relative_position*270; // multiply by distance
        ellipse(drawing_position+25, height/2, height/10, height/10);

        if(mousePressed){
            player.play(mouseX*1000);
        }
    }

    public void task4(){
        clear();
        ellipse(width/2, height/2, height/2*player.mix.level(), height/2*player.mix.level());
    }

    public void task5(){
        float x_pos = (((float) player.position()/ (float) player.length())* 5 * 320) % 320;
        int y_offset = ((int)(((float) player.position()/ (float) player.length())*100)/20)*40;
        ellipse(x_pos, y_offset, height/2*player.mix.level(), height/2*player.mix.level());
    }

    public void task6(){
        //clear();
        background(152,190,100);
        float[] buffer = player.mix.toArray();
        float rect_width=(float) (1.25*width/buffer.length);
        for(int i = 0; i < buffer.length; i++){
            float rect_height = ((buffer[i]+1)/2)*height;
            rect((int)(i*rect_width),(int)(height-rect_height),(int)rect_width,(int)rect_height);
        }
    }

    void task7(){
        float[] buffer = player.mix.toArray();
        fft.forward(buffer);
        clear();
        float rect_width=(float)width/fft.specSize();
        for(int i = 0; i < fft.specSize(); i++){
            float rect_height = (sqrt(fft.getBand(i))/15)*height;
            rect((int)(i*rect_width),(int)(height-rect_height),(int)rect_width,(int)rect_height);
        }
    }

    public static void main (String... args) {
        SoundVisualization pt = new SoundVisualization();
        PApplet.runSketch(new String[]{"SoundVisualization"}, pt);
    }
}

