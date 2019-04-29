import java.util.Random;

import gauffre_empoisonné.Grille;

public class IAAleatoire implements IA {
    Grille grille;
    Random rand;

    public IAAleatoire(Grille g) {
        grille = g;
        rand = new Random();
    }


    public void jouer() {
        int l = rand.nextInt()%grille.l;
        int c = rand.nextInt()%grille.c;
        while(grille.case_jouable(grille.l, grille.c, l, c, grille.tab)) {
            l = rand.nextInt()%grille.l;
            c = rand.nextInt()%grille.c;
        }
        grille.update_tab(grille.l, grille.c, grille.tab, l, c);
    }

}