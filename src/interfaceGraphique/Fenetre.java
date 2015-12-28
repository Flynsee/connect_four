package interfaceGraphique;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;

import algorithmes.Profondeur;
import jeu.Coup;

public class Fenetre extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	Panneau pan;
	boolean player;
	// JButton rejouer;
	boolean joueurBlancHumain;
	boolean joueurNoirHumain;
	HashMap<Long, Byte> table;

	public Fenetre(int h, int l, boolean joueur1, boolean joueur2) {
		pan = new Panneau(h, l);
		joueurBlancHumain = joueur1;
		joueurNoirHumain = joueur2;
		init();
	}

	public Fenetre(String chemin, boolean joueur1, boolean joueur2)
			throws ClassNotFoundException, IOException {
		pan = new Panneau(chemin);
		joueurBlancHumain = joueur1;
		joueurNoirHumain = joueur2;
		init();
	}

	public void init() {
		this.table = new HashMap<Long, Byte>();

		player = true;
		if (!joueurBlancHumain) {
			this.jouerComputer();
		} else if (!joueurNoirHumain){
			Profondeur.profondeur(pan.j, true, table);
		}
		this.setTitle("Puissance 4");
		this.setSize(80 * pan.j.courante.largeur,
				200 + 80 * pan.j.courante.hauteur);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(pan);
		this.setVisible(true);
		addMouseListener(this);
		this.setResizable(false);

		// rejouer = new JButton("Rejouer");
		// rejouer.setLocation(150, 15 + 80 * (pan.j.courante.hauteur));
		// pan.add(rejouer);
		// rejouer.setLocation(10000, 0);

	}

	public void jouerHumain(int i) {
		if (pan.j.jouer(new Coup(player, i))) {
			player = !player;
		}
		pan.repaint();
	}

	public void jouerHumainComputer(int i) {
		if (pan.j.jouer(new Coup(player, i))) {
			Coup c = JoueurComputer.meilleurCoup(this.pan.j, !player, table);
			pan.j.jouer(c);
		}
		pan.repaint();

	}

	public void jouerComputer() {
		pan.j.jouer(JoueurComputer.meilleurCoup(this.pan.j, player, table));
		player = !player;
		pan.repaint();

	}

	public void mouseClicked(MouseEvent e) {
		int i = ((e.getX()) / 80);

		if (i < this.pan.j.courante.largeur) {
			System.out.print("Coup en cours ");
			if (joueurNoirHumain && joueurBlancHumain) {
				this.jouerHumain(i);
			} else {
				this.jouerHumainComputer(i);
			}
			System.out.println("- Terminé");
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
	}
}