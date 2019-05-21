
package Modele;

// import java.util.ArrayList;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;
// import Configuration.Configuration;

/*
    Interprète une chaine de caractères écrite par l'utilisateur
    Utiliser uniquement pour l'interface en ligne de commande
*/


public class CommandeSimple extends Commande {
    String cmd;
    Partie partie;
    boolean valide;
    int joueur;

    int typeCmd;
    int valeur;
    Coordonnees pion;
    Coordonnees destination;
    int lPion,cPion,lDestination,cDestination,capture;
    Coup coup;


    //On lui passe la partie et le joueur concerné
    public CommandeSimple(Partie partie,int joueur) {
        super(partie, joueur);
    }
}