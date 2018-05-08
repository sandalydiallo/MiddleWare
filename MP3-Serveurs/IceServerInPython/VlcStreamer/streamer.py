# -*- coding: utf-8 -*-
import vlc
import time


def streamer(self,media_name,ip_client)

	media_name = "../Musiques/" + media_name # Le chemin de la musique 
	lecteur = ip_client # Cette variable l'adresse ip du client pour le quel on stream la musique

	sout = '#transcode{acodec=mp3,ab=128,channels=2,samplerate=44100}:http{dst=:8090/'+ media_name+'}'
	instance = vlc.Instance("")
	instance.vlm_add_broadcast(lecteur, media_name, sout, 0, [], True, False)
	instance.vlm_play_media(lecteur)

	# On accedera au stream par le lien : ip_serveur:8090/media_name.mp3