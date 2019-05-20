package Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Configuration {
    static Configuration instance = null;
    Properties prop;
    Logger log;

    protected Configuration() {
		// On charge les propriétés
		InputStream in = charge("Configuration/option.cfg");
		Properties defaut = new Properties();
		chargerProprietes(defaut, in, "option.cfg");
		// Il faut attendre le dernier moment pour utiliser le logger
		// car celui-ci s'initialise avec les propriétés
		String message = "Fichier de propriétés option.cfg chargé";
		String nom = System.getProperty("user.home") + "/.fanorona/option.cfg";
		try {
			in = new FileInputStream(nom);
			prop = new Properties(defaut);
			chargerProprietes(prop, in, nom);
			logger().info(message);
			logger().info("Fichier de propriétés " + nom + " chargé");
		} catch (FileNotFoundException e) {
			prop = defaut;
			logger().info(message);
		}
	}


    //Retourne la configuration 
    public static Configuration instance() {
        if(instance == null)
            instance = new Configuration();
        return instance;
    }

	// La méthode de chargement suivante ne dépend pas du système de fichier et sera
	// donc utilisable pour un .jar
	// Attention, par contre, le fichier doit se trouver dans le CLASSPATH
    public static InputStream charge(String nom) {
		return ClassLoader.getSystemClassLoader().getResourceAsStream(nom);
	}


    static void chargerProprietes(Properties p, InputStream in, String nom) {
			try {
				p.load(in);
			} catch (IOException e) {
				System.err.println("Impossible de charger " + nom);
				System.err.println(e.toString());
				System.exit(1);
			}
	}
	    
	//Lis la propriété demandée
	public String lis(String nom) {
		String value = prop.getProperty(nom);
		if (value != null) {
			return value;
		} else {
			throw new NoSuchElementException("Propriété " + nom + " manquante");
		}
	}

	//Renvoie le logger
	public Logger logger() {
		if (log == null) {
			log = Logger.getLogger(""); //Création du logger
			Handler[] handlers = log.getHandlers();
			if (handlers.length > 0 && handlers[0] instanceof ConsoleHandler) { //On enlève l'affichage par défaut dans la console
				log.removeHandler(handlers[0]);
			}

			log.setLevel(Level.parse(lis("logLevel"))); //Met le niveau du logger

			String sortieLog = lis("sortieLog");
			if (sortieLog.equals("console"))  { //Affichage dans la console
				ConsoleHandler consoleHandler = new ConsoleHandler();
				consoleHandler.setFormatter(new FormatLog());
				log.addHandler(consoleHandler);
			}
			else if (sortieLog.equals("defaut"))  { //Affichage dans un fichier présent dans le répertoire courant/.fanorona/fanorona.log
				try {
					//Création du répertoire s'il n'existe pas
					String chemin = repertoireCourant()+"/fanorona.log";
					FileHandler logFichier = new FileHandler(chemin);
					logFichier.setFormatter(new FormatLogFichier()); //Formatage simple du texte
					log.addHandler(logFichier); //Ajoute le fichier au logger
				}
				catch (IOException e) { //On ne peut pas charger le fichier de log
					System.err.println("Impossible de charger le fichier de sortie de log du répertoire courant");
					System.err.println(e.toString());
				}

			}
			else { //Affichage dans un fichier
				//ATTENTION quand on utilise le .JAR il faut un fichier qui soit en dehors du .JAR 
				try {
					FileHandler logFichier = new FileHandler(sortieLog);
					logFichier.setFormatter(new FormatLogFichier()); //Formatage simple du texte
					log.addHandler(logFichier); //Ajoute le fichier au logger
				}
				catch (IOException e) { //On ne peut pas charger le fichier de log
					System.err.println("Impossible de charger le fichier de sortie de log");
					System.err.println(e.toString());
				}
			}

		}
		return log;
	}

	//Changer une propriété 
	public void changerPropriete(String nomPropriete, String nouvelleValeur) {
		if (!prop.containsKey(nomPropriete)) {
			logger().warning("Configuration:changerPropriete - Propriété "+nomPropriete+" n'existe pas!");
		}
		else { //On change la valeur
			prop.put(nomPropriete, nouvelleValeur);
		}
	}

	//Création de .fanorona dans le répertoire courant
	public String repertoireCourant() {
		String chemin = System.getProperty("user.home");
		String repertoire = chemin+"/.fanorona";
		File dossier = new File(repertoire);
		if(!dossier.exists()) {
			dossier.mkdir(); //Création du répertoire
			File dossierSave = new File(repertoire+"/sauvegarde"); //Création du répertoire de sauvegarde
			dossierSave.mkdir();
		}
		return repertoire;
	} 

	//Impossible de sauvegarder directement dans le fichier JAR
	//On sauvegarde donc dans un fichier en dehors (on crée un dossier ./fanorona dans le home)
	public void sauvegarderPropriete() {
		String chemin = repertoireCourant();

		File fichier = new File(chemin+"/option.cfg");
		try {
			fichier.createNewFile(); //Création du fichier
			FileWriter ecrFichier = new FileWriter(fichier);

			String contenu = "#Niveau d'affichage des logs: OFF INFO WARNING SEVERE\n"+
			"logLevel="+lis("logLevel")+"\n\n"+
			"#Définit la sortie du log: console | defaut | ou un nom de fichier\n"+
			"sortieLog="+lis("sortieLog")+"\n\n"+
			"#faux | vrai\npleineEcran="+lis("pleinEcran")+"\n\n"+
			"#Option joueur\nnomJoueur1="+lis("nomJoueur1")+"\n"+
			"nomJoueur2="+lis("nomJoueur2")+"\n"+
			"#facile moyen difficile\n\n"+
			"difficulteIA="+lis("difficulteIA")+"\n"+
			"affichageConsole="+lis("affichageConsole")+"\n";
			ecrFichier.write(contenu);
			ecrFichier.close();
			logger().info("Sauvegarde des propriétés réussiee");
			// FileWriter fichier = new FileWriter("Configuration/option.cfg");
		} catch (Exception e) {
			logger().warning("Impossible de sauvegarder les propriétés: "+e);
		}
			
	}
}