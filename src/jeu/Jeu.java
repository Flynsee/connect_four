package jeu;
import java.io.IOException;
import java.util.LinkedList;


public class Jeu{
	public LinkedList<Coup> historique;
	public Grille courante;
	public int etat;  //état actuel de la partie du point de vue des blancs
	public long adresse;
	public long adresseSymetrique;
	public boolean partieFinie;
	
	
	public Jeu(int h,int l){
		historique=new LinkedList<Coup>();
		courante=new Grille(h,l);
		partieFinie=false;
		adresse=0;
		adresseSymetrique=0;
	}
	
	
	public Jeu(String path) throws IOException, ClassNotFoundException{
		historique=new LinkedList<Coup>();
		partieFinie=false;
		Grille temp=new Grille(path);
		courante=new Grille(temp.hauteur,temp.largeur);
		for (int i=0; i<courante.largeur; i++){
			for (int j=0; j<courante.hauteur; j++){
				switch (temp.tableau[j][i]){
				case BLANC : 
					this.jouer(new Coup(Cellule.BLANC,i));
				break;
				case NOIR : 
					this.jouer(new Coup(Cellule.NOIR,i));
				break;
				default:
				break;
				}
			}
		}
		adresseSymetrique=this.adresseSymetrique();
	}
	
	public Jeu(String path, boolean b) throws IOException, ClassNotFoundException{
		historique=new LinkedList<Coup>();
		partieFinie=false;
		Grille temp=new Grille(path, b);
		courante=new Grille(temp.hauteur,temp.largeur);
		for (int i=0; i<courante.largeur; i++){
			for (int j=0; j<courante.hauteur; j++){
				switch (temp.tableau[j][i]){
				case BLANC : 
					this.jouer(new Coup(Cellule.BLANC,i));
				break;
				case NOIR : 
					this.jouer(new Coup(Cellule.NOIR,i));
				break;
				default:
				break;
				}
			}
		}
		adresseSymetrique=this.adresseSymetrique();
	}
	
	public Jeu(int h, int l, long ad){
		historique=new LinkedList<Coup>();
		courante=new Grille(h,l);
		partieFinie=false;
		adresse=0;
		long copie=ad;
		int puissance=0;
		Coup c;
		while(copie>0){
			if(copie%3==1){
				c=new Coup(Cellule.BLANC, puissance%l);
				this.jouer(c);
			}
			else if(copie%3==2){
				c=new Coup(Cellule.NOIR, puissance%l);
				this.jouer(c);
			}
			copie=copie/3;
			puissance++;
		}
		adresseSymetrique=this.adresseSymetrique();
	}
	
	public boolean jouer(Coup c){
		if (courante.isJouable(c) && this.partieFinie==false){	
			historique.push(c);
			if (historique.size()>=courante.hauteur*courante.largeur){
				this.etat=0;
				this.partieFinie=true;
			}
			
			if(courante.isGagnant(c)){
				if (c.joueur){
					this.etat=1;
					this.partieFinie=true;
				}
				else{
					this.etat=-1;
					this.partieFinie=true;
				}
				
			}
				
			int i=courante.jouer(c);
			
			if(c.joueur){
				adresse+=puissance3(this.parcours(i,c.colonne));
				adresseSymetrique+=puissance3(this.parcours(i,this.courante.largeur-1-c.colonne));
			}
			else{
				adresse+=2*puissance3(this.parcours(i,c.colonne));
				adresseSymetrique+=2*puissance3(this.parcours(i,this.courante.largeur-1-c.colonne));
			}
			return true;
		}		
		return false;
}
	
	public void annuler(){
		Coup c=(historique.getFirst());
		int i=this.courante.annuler(c);
		if(c.joueur){
			adresse-=puissance3(this.parcours(i,c.colonne));
			adresseSymetrique-=puissance3(this.parcours(i,this.courante.largeur-1-c.colonne));
		}
		else{
			adresse-=2*puissance3(this.parcours(i,c.colonne));
			adresseSymetrique-=2*puissance3(this.parcours(i,this.courante.largeur-1-c.colonne));
		}
		historique.remove();
		this.partieFinie=false;
		
	}
	
	
	public void print(){
		courante.print();
		if(partieFinie){
			Resultat.toResultat(etat).print();
		}
		else{
			System.out.println("Partie en cours");
		}
		System.out.println();
	}
	
	public void jouerListe(Cellule[] joueurs, int[] positions){
		if (joueurs.length==positions.length){
			for (int i=0;i<joueurs.length; i++){
				this.jouer(new Coup(joueurs[i],positions[i]));
			}
		}
	}
	
	public void jouerListe(Boolean joueur1, int[] positions){
		for (int i=0;i<positions.length-1; i+=2){
				this.jouer(new Coup(joueur1,positions[i]));
				this.jouer(new Coup(!joueur1,positions[i+1]));
			}
		if ((positions.length/2)*2 !=positions.length){
			this.jouer(new Coup(joueur1,positions[positions.length-1]));		
			}
	}
	
	public int parcours(int ligne, int colonne){
		// doit renvoyer une bijection des cases de la Grille vers [0,largeur*hauteur-1]
		//dans l'idéal, cette bijection permet facilement de repérer la Grille symétrique
		
		return (colonne+ligne*this.courante.largeur);
		//return (colonne*this.courante.hauteur+ligne);
		
	}
	
	public int parcoursReciproque(int numero){
		// bijection reciproque de parcours
		
		return numero%this.courante.largeur;// renvoi la colonne
		//return numero/this.courante.hauteur;// renvoi la colonne
		
		
	}
	
	
	static long puissance3 (int b){
		if (b==0){ return 1 ;}
		else { return 3*puissance3(b-1); }
	}
	

	
	public long adresseSymetrique(){
		long copie=adresse;
		long adrSym=0;
		int puissance=0;
		while(copie>0){
			if(copie%3!=0){
				adrSym+=(copie%3)*Jeu.puissance3(this.parcours(puissance/this.courante.largeur, this.courante.largeur-1-puissance%this.courante.largeur));
			}
			copie=copie/3;
			puissance++;
		}
		return adrSym;
	}
	
	
/*	public long adresseSymetriqueHB(){
		long copie=adresse;
		long adrSym=0;
		int puissance=0;
		while(copie>0){
			if(copie%3!=0){
				adrSym+=(copie%3)*Jeu.puissance3(this.parcours(this.courante.hauteur-1-puissance/this.courante.largeur, puissance%this.courante.largeur));
			}
			copie=copie/3;
			puissance++;
		}
		return adrSym;
	}
	
	public long adresseSymetriqueHBDG(){
		long copie=adresse;
		long adrSym=0;
		int puissance=0;
		while(copie>0){
			if(copie%3!=0){
				adrSym+=(copie%3)*Jeu.puissance3(this.parcours(this.courante.hauteur-1-puissance/this.courante.largeur, this.courante.largeur-1-puissance%this.courante.largeur));
			}
			copie=copie/3;
			puissance++;
		}
		return adrSym;
	}*/
	
}
	
	

