import sys, Ice
import ManageMusic
 
with Ice.initialize(sys.argv) as communicator:
    adapter = communicator.createObjectAdapterWithEndpoints("MP3Adapter", "default -p 10000")
    object = ManageMusic.ManageMusicI()
    adapter.add(object, communicator.stringToIdentity("mp3"))
    adapter.activate()
    communicator.waitForShutdown()