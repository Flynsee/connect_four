package algorithmes;
import jeu.Coup;
import jeu.Jeu;
import jeu.Resultat;


public abstract class Negamax {
	
	static void recherche(Jeu j, boolean player){
		long t1=System.currentTimeMillis();
		int r=negamax(j, player);
		long t2=System.currentTimeMillis();
		long t=t2-t1;
		System.out.println("Résultat obtenu : "+ Resultat.toResultat(r));
		System.out.println("L'algorithme Negamax a tourné en "+t+ " millisecondes, soit " +t/1000+ " secondes, ou encore " + t/60000 + " minutes.");
		
	}
	
	
	static int negamax(Jeu j, boolean player){
		if (j.partieFinie){
			if(player){
				return j.etat;
			}
			else{
				return -j.etat;
			}
		}
		
		
		
		int m=-1;
		
		for(int i=0; i<j.courante.largeur; i++){
			if(j.jouer(new Coup(player, i))){
				m=Math.max(m,-negamax(j, !player));
				j.annuler();
			}		
		}
		
		
		return m;
	}
}
