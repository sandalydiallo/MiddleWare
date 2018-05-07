/* Author : Cherfi Med Amine */

#include "PlayerI.h"
#include <iostream>


PlayerI::PlayerI(const string ip)
{
   port=8090;
   loadTracks();
   nbClient=0;
   this->ip=ip;
}

PlayerI::~PlayerI()
{

}

/*inscription du client pour l'identifier*/
string PlayerI::subscribe(const Ice::Current&)
{
   string url="http://"+this->ip+":"+std::to_string(port)+"/stream.mp3";          
   string idClient="C"+std::to_string(nbClient);
   Client *c=new Client(idClient,url,port);
   port++;
   nbClient++;
   clients.push_back(c);    
   return idClient;
}

Client* PlayerI::getClient(string idClient)
{
    vector<Client*>::iterator it;                                                               
     for(it=clients.begin();it!=clients.end();++it)                                             
     {                                                                                                
         if((*it)->getIdClient() == idClient)                                                             {                                                                                                  return (*it);                                                              
          }                                                                                          }                           
}

bool PlayerI::addTrack(const string& name,const string& singer,const string& author,const string& album,const string& path,const Ice::Current&){
	cout << BOLDBLUE << "[*]" << RESET << " New track added to list : " << endl;
	cout << "#########################################################" << endl;
    	Track* t=new TrackI(name,singer,author,album,path);
	list.push_back(t);
	//t->printAll();
        return true;
}

//Supprimer une chanson
bool PlayerI::deleteTrack(const string& name,const Ice::Current&){
	TrackList::iterator it;
 	for(it=list.begin();it!=list.end();++it)
	{
           if(it->get()->name == name)
	   {
              list.erase(it);
              return true;
 	   }
	}
        return false;
}
//recherche des chansons
TrackList PlayerI::searchTracks(const string& name, const string& singer, const string& album,const Ice::Current&){
	TrackList l;
        TrackList::iterator it;
        for(it=list.begin();it!=list.end();++it)
	{
           if(name!="")
           {
               if(it->get()->name == name)
                 l.push_back(it->get());
           }
           if(singer!="")
           { 
               if(it->get()->singer == singer)
                 l.push_back(it->get());
           }
           if(album!="")
           {
               if(it->get()->album == album)
	          l.push_back(it->get());
           }
         } 
	return l ;
}

//récupère la liste des morceaux 
TrackList PlayerI::getTracks(const Ice::Current&){
	return this->list;
}

//démarrer le flux de diffusion
bool PlayerI::play(const string& name,const string& idClient, const Ice::Current&)
{
     if(!trackExist(name))
        return false;
     Client* c=getClient(idClient);
     cout << "idClient : " << idClient << endl;
     string path="tracks/" + name ;
     if(libvlc_vlm_add_broadcast(c->getVlc(), idClient.c_str(),path.c_str(),c->getStreamStr().c_str(),0, NULL, true, false)!=0)
     { 
        cout << "Error brodcasting !!!!!";
 	return false;
     }
     libvlc_vlm_play_media(c->getVlc(),idClient.c_str());
     return true;
}

//arrête le flux de diffusion
void PlayerI::stop(const string& name,const string& idClient, const Ice::Current&)
{   
     Client *c=getClient(idClient);
     libvlc_vlm_stop_media(c->getVlc(),idClient.c_str());
     libvlc_vlm_release(c->getVlc()); 
     cout << "Stream stoped by client." << endl;
}

//test si une chanson existe (utilisation local)
bool PlayerI::trackExist(const string name)
{
    TrackList::iterator it;
    for(it=list.begin();it!=list.end();++it)
    {
      if(it->get()->name == name)
      {
         return true;
      }  
    }
        return false;
}

//l'url de streaming
string PlayerI::getUrl(const string& idClient,const Ice::Current&)
{
     vector<Client*>::iterator it;
     for(it=clients.begin();it!=clients.end();++it)
     {
       if((*it)->getIdClient() == idClient)
       {
          return (*it)->getUrl();
       }
     }
     return "";
}
//Charger les fichiers mp3 du dossier tracks
void PlayerI::loadTracks()
{
    struct dirent *lecture;
    DIR *rep;
    rep = opendir("tracks" );
    while ((lecture = readdir(rep))) {
        const string name = string(lecture->d_name);
        if(name.compare(".") != 0 && name.compare("..") != 0)
        {
          TrackI* t=new TrackI(name,"singer_"+name,"author_"+name,"singer_"+name,"path_"+name);
          this->list.push_back(t);
        }
    }
    closedir(rep);
}
