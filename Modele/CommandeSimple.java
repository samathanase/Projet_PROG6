
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


    //On lui passe la partie et le joueur concerné
    public CommandeSimple(Partie partie,int joueur) {
        super(partie, joueur);
    }

    @Override
    public void interpreterAide() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Liste des commandes importantes:");
        System.out.println("Jouer un coup: les coordonnées de départ et d'arriver du pion. S'il y a un choix entre de capture ajouter 1 pour faire une percussion et 2 pour une aspiration");
        System.out.println("Mettre fin au tour: fin ou finTour");
        System.out.println("Pour annuler un coup: annuler");
        System.out.println("Pour refaire un coup: refaire");
        System.out.println("Pour quitter la partie:");
        System.out.println("Pour recommencer la partie: recommencer");
        System.out.println("Pour naviguer dans l'historique: b pour revenir en arrière et n pour aller un coup en avant");

        System.out.println("\nListe des autre commandes: ");
        System.out.println("Pour avoir la liste des coups possibles: coups");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}