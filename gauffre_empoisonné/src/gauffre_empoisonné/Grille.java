package gauffre_empoisonné;

public class Grille {

	int l, c;
	int[][] tab;
	public Grille(int l,int c) {
		this.l=l;
		this.c=c;
		this.tab=new int[55][55];
	}
	
	
	// une fonction qui remplie le tableau si une case a été mangé (sa nouvel valeur sera = 1 au lieu de 0)
	public int[][] update_tab(int l, int c, int[][] tab, int x, int y){
		for (int i=0;i<l;i++) {
			for (int j=0;j<c;j++) {
				if(i>=x && j>=y)
					tab[i][j]=1;
			}
		}
		return tab;
	}
	
	//initialise le tableau a 0
	public int[][] initialiser_tab(int l, int c) {
		int[][] tab= new int[l][c];
		for(int i=0;i<l;i++){
			for(int j=0;j<c;j++){
				tab[i][j]=0;
			}
		}
		return tab;
	}
	
	// fonction en cours de costruction
	public void agrandir_tab(int l, int c,int[][] tab){
		if(l<=55 && c <= 55) {
			l++;
			c++;
		}else {
			System.out.println("le tableau et trop gros !!!");
		}
		tab=initialiser_tab(l,c);
		Affich_tab s = new Affich_tab(l,c);
		s.print_board(l,c,tab);
	}
	
	// renvoie "true" si une case peut être joué, "false" sinon
	public boolean case_jouable(int l,int c, int x, int y, int[][] tab) {
		
		if(x>=l || y>=c || x<0 || y<0 || tab[x][y]==1)
			return false;
		return true;
	}
	
}
