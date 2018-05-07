#!/usr/bin/python2.7
# -*- coding: utf-8 -*-

import os
import MP3

listeMusic = []

class FileInfos(MP3.fileInfo):

    def _get_music_path(self):
            return self.path
    pathFile=property(_get_music_path)

    def _get_music_author(self):
        return self.author
    authorMusic=property(_get_music_path)

    def _get_music_name(self):
        return self.name
    nomMusic=property(_get_music_name)

    def _get_music_album(self):
        return self.album
    albumMusic=property(_get_music_album)

    def _get_music_genre(self):
        return self.genre
    genreMusic=property(_get_music_genre)

    def _get_music_year(self):
        return self.year
    yearMusic=property(_get_music_year)


class ManageMusicI(MP3.ManageMusic):

    def addFile(self, fileInfo, current=None):
        listeMusic.append(fileInfo)
        result = 'La musique à été bien partagée'
        return result


    def afficherListeMusic(self,current=None):
        return listeMusic


    def deleteMusic(self, music,current=None):
        i = 0
        for item in listeMusic:
            if item.author == music.author:
                del listeMusic[i]
                i = i+1
                pass
        result = 'La musique à été supprimée dans le server de partage'
        return result

    def searchByAuthor(self, author,current=None):
        self.resultat = []
        for  item in listeMusic:
            if item.author == author:
                self.resultat.append(item)
                pass
            pass
        return self.resultat

    def searchByName(self, name,current=None):

        self.resultat = []
        for  item in listeMusic:
            if item.name == name:
                self.resultat.append(item)
                pass
            pass
        return self.resultat

    def searchByGenre(self, genre,current=None):
        self.resultat = []
        for  item in listeMusic:
            if item.genre == genre:
                self.resultat.append(item)
                pass
            pass
        return self.resultat

    def searchByYear(self, year,current=None):
        self.resultat = []
        for  item in listeMusic:
            if item.year == year:
                self.resultat.append(item)
                pass
            pass
        return self.resultat

