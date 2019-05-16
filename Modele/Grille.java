package Modele;
import java.io.Serializable;

//La grille

public class Grille implements Serializable{
    private static final long serialVersionUID = -3453671131229703786L;

    int[][] tab;
    int l;
    int c;

    public Grille(int l,int c) {
        tab = new int[l][c];
        this.l=l;
        this.c=c;
    }
	//----------------------------------
	public Grille(Grille copy){
		l = copy.l;
		c = copy.c;
		
		tab = new int[l][c];
		for(int i = 0; i < l; i++){
			for(int j = 0; j < c; j++){
				tab[i][j] = copy.tab[i][j];
			}
		}
	}
	public void _set(int v,int l, int c){
		tab[l][c] = v;
	}
	//----------------------------------


    public int ligne() {
        return l;
    }

    public int colonne() {
        return c;
    }
	//-------------------------------------------
    public boolean caseExiste (int l,int c) {
        return l>=0 && l< ligne() && c>=0 && c< colonne();
    }

    public boolean caseExiste (Coordonnees C) {
        return caseExiste(C.ligne(),C.colonne());
    }
	//-----------------------------------------
    public int at(int l,int c) {
        if(l<0 || l>ligne() || c<0 || c>colonne()) {
            return -1;
        }
        else {
            return tab[l][c];
        }
    }


	public int at(Coordonnees coord){
		return at(coord.ligne(),coord.colonne());
	}

    public int[][] tab() {
        return tab;
    }

}
