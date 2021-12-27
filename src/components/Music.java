package components;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URI;
import java.net.URL;

public class Music extends Thread{
    private File f;
    private URI uri;
    private URL url;

    public void run(){
        try{
            f=new File("C:\\Users\\maxin-12345\\IdeaProjects\\class9_28\\.idea\\Boom - Boom的歌.wav");
            uri=f.toURI();
            url= uri.toURL();
            AudioClip aau;
            aau= Applet.newAudioClip(url);
            aau.loop();
            System.out.println("run music");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}