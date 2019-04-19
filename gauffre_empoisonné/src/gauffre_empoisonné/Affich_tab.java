package gauffre_empoisonné;

import java.util.*;

public class Affich_tab {
	int l, c;
	int[][] tab;
	public Affich_tab(int l,int c) {
		this.l=l;
		this.c=c;
		this.tab=new int[55][55];
	}
	
	
	//pour afficher la grille dans le terminal
	public void print_board(int l, int c, int[][] tab) {
		
		for(int i=0;i<l;i++){
			if(tab[i][0]!=1)
				System.out.print("|");
			for(int j=0;j<c;j++){
				if(i!=0 || j!=0) {	
					if(tab[i][j]==0) {
						System.out.print(" ");
					}else{
						break;
						//System.out.print("x");
					}
				}else {
					System.out.print("O");
				}
				System.out.print("|");
			}
			System.out.println("");
		}
	}
	
	
	
	
	
	
	
	
	


}