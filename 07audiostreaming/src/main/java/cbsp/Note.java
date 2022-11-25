package cbsp;

import java.io.Serializable;

public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer pitch;
    public Integer velocity;
    public Integer duration;

    public Note(Integer pitch, Integer velocity, Integer duration) {
        this.pitch = pitch;
        this.velocity = velocity;
        this.duration = duration;
    }

}

