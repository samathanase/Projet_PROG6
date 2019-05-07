package Modele;
import Configuration.Configuration; //Penser à importer la classe

//Petit test de la classe config

public class TestConfig {
    public static void main(String[] args) {

        //Pour afficher un message de log suivant le niveau de sévérité
        //Penser à mettre la classe dans le message 
        Configuration.instance().logger().info("TestConfig: Test");
        Configuration.instance().logger().warning("Test");
        Configuration.instance().logger().severe("Test");

        //Pour récupérer un paramètre: Configuration.instance().lis("l'option")
        System.out.println("Nom joueur 1: "+Configuration.instance().lis("nomJoueur1"));

        Configuration.instance().changerPropriete("nomJoueur1", "Djoko");
        System.out.println("Nom joueur 1: "+Configuration.instance().lis("nomJoueur1"));
        Configuration.instance().sauvegarderPropriete();

    }
}