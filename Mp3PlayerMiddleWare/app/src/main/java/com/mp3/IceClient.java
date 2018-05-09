package mp3;

public class IceClient{
    public String[] getDataFromServer()
    {
       /* try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize())
        {
            
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("mp3:tcp -h 10.20.10.84 -p 10000");
            ManageMusicPrx music = ManageMusicPrx.checkedCast(base);


            if(music == null)
            {
                throw new Error("Invalid proxy");
            }
            String[] test = music.getListeMusic();
            return test;

        }*/

        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize())
        {
            com.zeroc.Ice.Properties datasize = com.zeroc.Ice.Util.createProperties();
            datasize.setProperty("Ice.MessageSizeMax", "100024");
            com.zeroc.Ice.InitializationData initData = new com.zeroc.Ice.InitializationData();
            initData.properties = datasize;
            com.zeroc.Ice.Communicator ic = com.zeroc.Ice.Util.initialize(initData);

            com.zeroc.Ice.ObjectPrx base = ic.stringToProxy("mp3:tcp -h 192.168.0.17 -p 10000");
            ManageMusicPrx music = ManageMusicPrx.checkedCast(base);
            if (music == null) {
                throw new Error("Invalid proxy");
            }
            else {
                String[] test = music.getListeMusic();
                return test;
            }
        }
    }
}
