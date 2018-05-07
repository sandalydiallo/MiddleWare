module MP3
{
    
    class fileInfo
    {
    	string name;
        string path;
        string author;
        string album;
        string genre;
        string year;
    }

    sequence<fileInfo> fileInfos;

    interface ManageMusic
    {
        string addFile(fileInfo fileInfo);

        fileInfos afficherListeMusic();	      

	    string deleteMusic(fileInfo fileInfo);
	        
	    fileInfos searchByAuthor(string author);
	        
	    fileInfos searchByName(string name);

	    fileInfos searchByGenre(string genre);

	    fileInfos searchByYear(string year);
    }
}