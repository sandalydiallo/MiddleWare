package mp3;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Connection;
import com.zeroc.Ice.InitializationData;
import com.zeroc.Ice.InvocationFuture;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

public class IceClient{

    ManageMusicPrx music;

    public IceClient(){
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize())
        {
            com.zeroc.Ice.Properties datasize = com.zeroc.Ice.Util.createProperties();
            datasize.setProperty("Ice.MessageSizeMax", "100024");
            com.zeroc.Ice.InitializationData initData = new com.zeroc.Ice.InitializationData();
            initData.properties = datasize;
            com.zeroc.Ice.Communicator ic = com.zeroc.Ice.Util.initialize(initData);

            com.zeroc.Ice.ObjectPrx base = ic.stringToProxy("Server:tcp -h 192.168.0.17 -p 10000:udp -h  192.168.0.17 -p 10000");
            music = ManageMusicPrx.uncheckedCast(base);
        }
    }

    public String[] getInstanceServer()
    {
        if (music == null) {
            throw new Error("Invalid proxy");
        }
        else {
            String[] listeMusic = music.getListeMusic();
            return listeMusic;
        }
    }

    public Boolean streamer(String mediaName, String ipAdress){
        music.streamerMusique( mediaName, ipAdress);
        return  true;
    }

    public  boolean stopMusique(String ipClient){
        music.stopMusique(ipClient);
        return  true;
    }
}
