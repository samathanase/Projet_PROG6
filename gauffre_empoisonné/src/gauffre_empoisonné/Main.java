package gauffre_empoisonné;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int MAX_l = 80, MAX_c = 80;
		int l,c;
		
		///////////////////////////////////////////////////////////////
		System.out.println("nombre de lignes >1 ?");
		Scanner sc = new Scanner(System.in);
		do {
			l=sc.nextInt();
			if(l<2 || l > MAX_l)
				System.out.println("nombre de lignes non valide, veuillez tapez de nouveau");
		}while(l<2 || l > MAX_l);
		System.out.println("nombre de colonnes > 1 ?");
		do {
			c=sc.nextInt();
			if(c<2 || c > MAX_c)
				System.out.println("nombre de colonnes non valide, veuillez tapez de nouveau");
		}while(c<2 || c > MAX_c);
		//////////////////////////////////////////////////////////////
		
		Affich_tab s = new Affich_tab(l,c);
		Grille g = new Grille(l,c);
		int[][] tab = g.initialiser_tab(l,c);
		s.print_board(l,c,tab);
		int player = 1,i=1;
		int x, y;
		while(tab[0][1]!=1||tab[1][0]!=1) {
			System.out.println("Joueur "+player+" à toi de jouer !!");
			System.out.println("pour aggrandir la grille, tapez -3, sinon donnez les coordonnées d'une case ");
			x=sc.nextInt();
			/*while(x==-3) {
				tab=g.agrandir_tab(l, c);
				s.print_board(l,c,tab);
				x=sc.nextInt();
				System.out.println("pour aggrandir la grille, tapez -3, sinon donnez les coordonnées d'une case ");
			}*/
			y=sc.nextInt();
			while(!g.case_jouable(l,c,x,y,tab)) {
				System.out.println("Joueur "+player+" veuillez entrer des coordonnées valides (une case vide)");
				x=sc.nextInt();
				y=sc.nextInt();
			}
			if(x==0 && y ==0)
				break;
			tab = g.update_tab(l,c,tab,x, y);
			s.print_board(l, c, tab);
			player=i%2+1;
			i++;
		}
		System.out.println("Joueur "+player+" a perdu !!");
	}
}
