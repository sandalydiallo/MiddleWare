import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


import MP3.*;
public class Client{
    public static void main(String[] args)
    {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args))
        {
            
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("mp3:default -p 10000");
            ManageMusicPrx music = ManageMusicPrx.checkedCast(base);
         

            if(music == null)
            {
                throw new Error("Invalid proxy");
            }
            String[] test  = music.getListeMusic();
            System.out.println(Arrays.toString(test));
        }
    }
}
