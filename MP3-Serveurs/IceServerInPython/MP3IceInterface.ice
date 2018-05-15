module MP3
{

    class FileInfo
    {
        string name;
        string path;
        string author;
        string album;
        string genre;
        string year;
    }

    sequence<FileInfo> fileInfos;

    sequence<string> listMusiques;

    interface ManageMusic
    {

        listMusiques getListeMusic();       

        bool streamerMusique(string mediaName,string ipClient);

        bool stopMusique(string ipClient);



        string addFile(FileInfo fileInfo);

        fileInfos afficherListeMusic();

        string deleteMusic(FileInfo fileInfo);

        fileInfos searchByAuthor(string author);

        fileInfos searchByName(string name);

        fileInfos searchByGenre(string genre);

        fileInfos searchByYear(string year);
    }

}