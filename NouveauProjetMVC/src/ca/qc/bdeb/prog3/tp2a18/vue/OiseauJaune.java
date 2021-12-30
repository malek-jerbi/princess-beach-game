/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp2a18.vue;

import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.HAUTEUR;
import static ca.qc.bdeb.prog3.tp2a18.controleur.Controleur.LARGEUR;
import static java.lang.Math.sin;
import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;


/**
 *
  * @author User Daniel Diaz et Malek Jerbi 
 */
public class OiseauJaune extends Oiseau implements Bougeable, Colisionnable {

    private int animation = 0;
    private ArrayList<Image> listeAnimation = new ArrayList<>();
    private int deltaX = 4;
    boolean bonifiable; 



    /**
     *
     * @param x postion horizonale sur l'écran
     * @param y postition verticale sur l'écran 
     * @param spriteSheet image subdivisee regroupant un ensemble d'images 
     */
    public OiseauJaune (float x, float y, SpriteSheet spriteSheet) {
        super(x, y, spriteSheet, 2, 0);
        listeAnimation.add(spriteSheet.getSubImage(0, 2));
        listeAnimation.add(spriteSheet.getSubImage(1, 2));

    }

    /**
     * méthode définissant le déplacement de l'objet dans l'écran 
     */
    @Override
    public void bouger() {
        switch (animation) {
            case 0:
                this.image = listeAnimation.get(0);
                break;
            case 15:
                this.image = listeAnimation.get(1);
                animation = -1;
                break;
        }
        animation++;
                                                        

         int deplacementX =0; 
    
        x = x - deltaX;
        deplacementX = deplacementX+2; 
        
        y =  (float) (sin(0.009*x)*6+y); 
       
        
        if (x == -32) {
            detruire = true;
        }
    }

    /**
     *
     * @return valeur booleeene qui permet de savoir si le l'objet est bonifiable  
     */
    public boolean isBonifiable() {
        return bonifiable;
    }

    /**
     *
     * @param bonifiable valeur booleeene qui permet de savoir si le l'objet est bonifiable  
     */
    public void setBonifiable(boolean bonifiable) {
        this.bonifiable = bonifiable;
    }
}
