#!/usr/bin/python2.7
# -*- coding: utf-8 -*-

import vlc
import time

class Streamer:
	"""docstring for ClassName"""
	def __init__(self):
		self.instance = ""

	def streamer(self,media_name,ip_client):

		self.instance = vlc.Instance("--input-repeat=999999")
		media_name = "musiques/" + media_name # Le chemin de la musique 
		lecteur = ip_client # Cette variable l'adresse ip du client pour le quel on stream la musique

		sout = '#transcode{acodec=mp3,ab=128,channels=2,samplerate=44100}:http{dst=:8090/'+ ip_client+'.mp3}'
		self.instance.vlm_add_broadcast(lecteur, media_name, sout, 0, [], True, False)
		self.instance.vlm_play_media(lecteur)

		# On accedera au stream par le lien : ip_serveur:8090/media_name.mp3

	def stopMusic(self,ip_client):
		self.instance.vlm_del_media(ip_client)
