package interfaceGraphique;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JPanel;

import jeu.Jeu;

public class Panneau extends JPanel {

	
	
	private static final long serialVersionUID = 1L;
	Jeu j;

	Panneau(int h, int l) {
		j = new Jeu(h, l);
	}

	Panneau(String chemin) throws ClassNotFoundException, IOException {
		j = new Jeu(chemin);
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.PINK);
		g.fillRect(-50, -50, 1500, 1500);
		g.setColor(Color.RED);
		// g.drawLine(10+25, -50, 10+25, 1500);
		for (int i = 0; i < j.courante.hauteur; i++) {
			g.setColor(Color.RED);
			g.drawLine(-50, 80 + 80 * i, 1500, 80 + 80 * i);
		}

		for (int l = 0; l < j.courante.largeur; l++) {
			g.setColor(Color.RED);
			g.drawLine(80 + 80 * l, -50, 80 + 80 * l, 1500);
			for (int h = 0; h < j.courante.hauteur; h++) {
				switch (j.courante.tableau[h][l]) {
				case BLANC:
					g.setColor(Color.WHITE);
					break;
				case NOIR:
					g.setColor(Color.BLACK);
					break;
				case VIDE:
					g.setColor(Color.PINK);
					break;
				}
				g.fillOval(15 + 80 * l, 15 + 80 * (j.courante.hauteur - h - 1),
						50, 50);
			}
		}

		g.setColor(Color.RED);
		g.fillRect(0, (this.j.courante.hauteur) * 80, 1500, 1500);
		if (j.historique.isEmpty()) {
			g.setColor(Color.WHITE);
		} else if (j.historique.getFirst().joueur) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.WHITE);
		}
		g.fillOval(200, (this.j.courante.hauteur) * 80 + 40, 50, 50);
		g.drawString("C'est au tour de: ", 100,
				(this.j.courante.hauteur) * 80 + 70);

		if (this.j.partieFinie) {
			if (j.etat == 1) {
				g.setColor(Color.WHITE);
				g.fillRect(0, (this.j.courante.hauteur) * 80, 1500, 1500);
				g.setColor(Color.BLACK);
				g.drawString("Félicitation: le joueur BLANC a gagné !", 100,
						(this.j.courante.hauteur) * 80 + 70);
			} else if (j.etat == -1) {
				g.setColor(Color.BLACK);
				g.fillRect(0, (this.j.courante.hauteur) * 80, 1500, 1500);
				g.setColor(Color.WHITE);
				g.drawString("Félicitation: le joueur NOIR a gagné !", 100,
						(this.j.courante.hauteur) * 80 + 70);
			} else if (j.etat == 0) {
				g.setColor(Color.RED);
				g.fillRect(0, (this.j.courante.hauteur) * 80, 1500, 1500);
				g.setColor(Color.PINK);
				g.drawString("C'est une égalité !", 100,
						(this.j.courante.hauteur) * 80 + 70);
			}
		}

	}
}
