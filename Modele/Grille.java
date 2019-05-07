package Modele;
//La grille

public class Grille {
    int [][]tab;
    int l;
    int c;

    public Grille(int l,int c) {
        tab = new int[l][c];
        this.l=l;
        this.c=c;
    }


    public int ligne() {
        return l;
    }

    public int colonne() {
        return c;
    }

    public int at(int l,int c) {
        if(l<0 || l>ligne() || c<0 || c>colonne()) {
            return -1;
        }
        else {
            return tab[l][c];
        }
    }

    public int[][] tab() {
        return tab;
    }

}