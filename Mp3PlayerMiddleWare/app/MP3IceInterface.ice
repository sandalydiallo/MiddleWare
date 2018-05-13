module MP3
{
    sequence<string> listMusiques;

    interface ManageMusic
    {

        listMusiques getListeMusic();       

        bool streamerMusique(string mediaName,string ipClient);
    }
}