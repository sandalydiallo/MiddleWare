# def Jouer(self, url, current=None):
        #instance = vlc.Instance()
        #player = instance.media_player_new()
        #media = instance.media_new('../MusiquesVLC/GetLucky.mp3')
        # Add the media to the player
        #player.set_media(media)
        # Play for 10 seconds then exit
        #player.play()
        #time.sleep(10)
import vlc
import time


print('test')
media_name = "test.mp3"

sout = '#transcode{acodec=mp3,ab=128,channels=2,samplerate=44100}:http{dst=:8090/' + "sample.mp3" + '}'

# sout2 = '#transcode{acodec=mp3,ab=128,channels=2,samplerate=44100}:http{dst=:8090/' + "sample2.mp3" + '}'
instance = vlc.Instance("")
instance.vlm_add_broadcast("lecteur", media_name, sout, 0, [], True, False)
# instance.vlm_add_broadcast("lecteur2", media_name, sout2, 0, [], True, False)
instance.vlm_play_media("lecteur")
time.sleep(60)
instance.vlm_pause_media("lecteur")
time.sleep(10)
instance.vlm_play_media("lecteur")
time.sleep(60)
instance.vlm_stop_media("lecteur")
# instance.vlm_play_media("lecteur2")

time.sleep(3000)