package mp3playermiddleware;

/**
 * Created by alpha on 24/05/2018.
 */

public class ActionMusic {
    private String action;
    private String music;

    public void setAction(String action){
        this.action = action;
    }

    public  void setMusic(String music){
        this.music = music;
    }

    public  String getAction(){
        return  this.action;
    }

    public  String getMusic(){
        return  this.music;
    }
}
