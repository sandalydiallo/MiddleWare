#!/usr/bin/python2.7
# -*- coding: utf-8 -*-

import os
import MP3

listeMusic = []


class ManageMusicI(MP3.ManageMusic):

    def getListeMusic(self, current=None):
        # To do: implement methode
        listeMusic = os.listdir("Musiques/")
        return listeMusic


    def streamerMusique(self,current=None):
        return True
