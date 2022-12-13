package cbsp;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class SoundVisualization extends PApplet
{
    Minim minim;
    AudioPlayer player;

    @Override
    public void settings() {
        size(320, 170);
    }

    @Override
    public void setup() {
        smooth();

        minim = new Minim(this);
        player = minim.loadFile("src/main/media/Phlex - Take Me Home Tonight (feat. Caitlin Gare) [Argofox].mp3");
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
    }

    public static void main (String... args) {
        SoundVisualization pt = new SoundVisualization();
        PApplet.runSketch(new String[]{"SoundVisualization"}, pt);
    }
}

