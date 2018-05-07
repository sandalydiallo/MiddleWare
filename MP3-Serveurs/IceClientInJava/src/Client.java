import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import MP3.*;
public class Client{
    public static void main(String[] args)
    {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args))
        {
            
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("mp3:tcp -h 192.168.43.214 -p 10000");
            ManageMusicPrx music = ManageMusicPrx.checkedCast(base);
            
            // com.zeroc.Ice.ObjectPrx base2 = communicator.stringToProxy("mp:default -p 10001");
            // ManageMusicPrx music2 = ManageMusicPrx.checkedCast(base2);
            // fileInfo file2 = new fileInfo("serv2", "path", "author","album","genre", "year");
            // System.out.println(music2.addFile(file2));



            if(music == null)
            {
                throw new Error("Invalid proxy");
            }
            
            int input=-1;
            do{
            		
                System.out.println("");
                System.out.println("************ Menu Client en Java ******************");
                System.out.println("1. Partager une musique");
                System.out.println("2. Recherhcer musique");
                System.out.println("3. Supprimer une musique");
                System.out.println("4. Afficher la liste des musiques partagées");
                System.out.println("5. Sortir du programme");
                System.out.println("");
                System.out.print("SVP selectionner une option entre 1-5\r\n");
                
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                
                try {
                    input = Integer.parseInt(br.readLine());
                    
                    if(input < 0 || input > 5) {
                        System.out.println("Vous avez chois une option invalide, svp essayer encore\r\n");
                    }else {
                    	if (input == 1) {
                    		/*L'ordre des paramètres de la fonction addFile();
                             * name, path, author, album, genre, year,
                             */
                    		System.out.print("\033[H\033[2J");
                    		System.out.println("Entrer les information de la musique");
                    		System.out.println("");
                    		
                    		System.out.println("le titre");
                    		String name = br.readLine();
                    		
                    		System.out.println("le chemin");
                    		String path = br.readLine();
                    		
                    		System.out.println("le nom du chanteur");
                    		String author = br.readLine();
                    		
                    		System.out.println("l'album");
                    		String album = br.readLine();
                    		
                    		System.out.println("le genre");
                    		String genre= br.readLine();
                    		
                    		System.out.println("l'année");
                    		String year= br.readLine();
                    		
                    		fileInfo file = new fileInfo(name, path, author,album,genre, year);
                    		
                    		System.out.println(music.addFile(file));
                    	}
    					if (input == 2) {
    						System.out.print("\033[H\033[2J");
    						System.out.println("Rechercher une musique");
                    		System.out.println("");
                    		
    	            		 System.out.println("Choisir le critère");
    	                     System.out.println("1. Titre musique ");
    	                     System.out.println("2. Le nom du chanteur");
    	                     System.out.println("3. Genre");
    	                     System.out.println("4. Année");
    	                     
    	                     String delToDel ="";
    	                     fileInfo []tab=music.searchByName("");
    	                     int input2 = Integer.parseInt(br.readLine());
    	                     
    	                     if (input2 ==1){
    	                    	 System.out.println("Donner le Titre de la musique");
    	                    	 delToDel = br.readLine();
    	                    	 tab = music.searchByName(delToDel);
    	                     }
    	                     
    	                     if (input2 ==2){
    	                    	 System.out.println("Donner le nom du chanteur");
    	                 		delToDel = br.readLine();
    	                 		tab = music.searchByAuthor(delToDel);
    	                     }
    	                     if (input2 ==3){
    	                    	 System.out.println("Donner le Genre de la musique");
    	                 		delToDel = br.readLine();
    	                 		tab = music.searchByGenre(delToDel);
    	                     }
    	                     
    	                     if (input2 ==4){
    	                    	 System.out.println("Donner l'Année de la musique");
    	                 		delToDel = br.readLine();
    	                 		tab = music.searchByYear(delToDel);
    	                     }
                    		
                    		 
                            if (tab.length == 0){
                            	System.out.println("Aucune music trouver. Verifier le nom et réesayer");
                            }else{
                            	System.out.println("Titre-----------Chanteur--------Album------------Genre");
                                for (fileInfo fileInfo : tab) {
                    				System.out.println(fileInfo.name +"         "+fileInfo.author + "      "+ fileInfo.album + "           " + fileInfo.genre);
                    			}
                            }
                            	
                            
    					                		
                    	}
    					if (input == 3) {
    						System.out.print("\033[H\033[2J");
    						System.out.println("Donner le nom de la musique à supprimer");
    						String input3 = br.readLine();
    						if (input3.equals("")){
    							 System.out.println("Il faut saisir le nom de la musique pour supprimer");
    						}else{
    							fileInfo []tab=music.searchByName(input3);
        						
        			            if(tab.length != 0){
        			            	System.out.println(music.deleteMusic(tab[0]));
	        			            System.out.println("\n");
	        			            System.out.println("La liste après la suppression d'une music donner en paramettre");
	        			            System.out.println("Titre-----------Chanteur--------Album------------Genre-----------Année");
	        			            for (fileInfo fileInfo : music.afficherListeMusic()) {
	        			            	System.out.println(fileInfo.name +"         "+fileInfo.author + "     "+ fileInfo.album + "          " + fileInfo.genre+"   "+fileInfo.year);
	        			            }
        			            }
        			            else{
    			            	System.out.println("La liste est vide ou le nom saisie n'existe dans la liste de partage");
        						}
        			            
    						}
    						
    						
                    	}
    					if (input == 4) {
    						System.out.print("\033[H\033[2J");
    				        if (music.afficherListeMusic().length ==0){
    				        	System.out.println("La liste de partage est vide");
    				        }else{
    				        	System.out.println("------------------------La lite des music partagée --------------------------------------");
    				        	System.out.println("");
    				        	System.out.println("Titre-----------Chanteur--------Album------------Genre-----------Année");
    				            for (fileInfo fileInfo : music.afficherListeMusic()) {
    				            	System.out.println(fileInfo.name +"         "+fileInfo.author + "     "+ fileInfo.album + "          " + fileInfo.genre+"   "+fileInfo.year);
    							}
    				        }
    				        	
    			            
                    	}
                    }
                } catch (IOException ioe) {
                    System.out.println("IO error trying to read your input!\r\n");
                    System.exit(1);
                }
            }while(input != 5);
        }
    }
}
