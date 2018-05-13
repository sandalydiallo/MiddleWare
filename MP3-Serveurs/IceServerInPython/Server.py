import sys, Ice
import ManageMusic

Ice.loadSlice('MP3IceInterface.ice')
 
with Ice.initialize(sys.argv) as communicator:
    adapter = communicator.createObjectAdapterWithEndpoints("ServerAdapter", "default -p 10000")
    object = ManageMusic.ManageMusicI()
    adapter.add(object, communicator.stringToIdentity("Server"))
    adapter.activate()
    communicator.waitForShutdown()