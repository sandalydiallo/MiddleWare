#!/usr/bin/python2.7
# -*- coding: utf-8 -*-

import os
import MP3
import VlcStreamer

listeMusic = []
s = VlcStreamer.streamer.Streamer() 

class ManageMusicI(MP3.ManageMusic):


    def getListeMusic(self, current=None):
        # To do: implement methode
        listeMusic = os.listdir("musiques/")
        return listeMusic


    def streamerMusique(self, mediaName, ipClient, current=None):
    	s.streamer(mediaName,ipClient)
        return True

    def stopMusique(self,ipClient, current=None):
    	s.stopMusic(ipClient)
    	return True
