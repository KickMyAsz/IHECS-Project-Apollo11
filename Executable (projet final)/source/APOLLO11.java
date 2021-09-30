import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class APOLLO11 extends PApplet {

/*
  APOLLO11 PROJET CODE IHECS
  Notre projet : réaliser un programme qui permet de lancer l'enregistrement radio de l'équipe d'Apollo 11 lorsque celle-ci 
  à aluni en juillet 1969. Ce programme est une sorte de "mini expérience auditive" comme l'on pourrait en trouver dans certains 
  musées lors de nos visites.
  
  >>> ATTENTION A LIRE IMPERATIVEMENT : Pour rendre ce projet plus créatif, nous avons utilisé une librairie officielle mis à disposition 
  >>> par Processing : "Sound" (qui est facilement trouvable dans l'onglet "Sketch" > "importer une librairie...>Ajouter une librairie> taper "sound" dans la barre de recherche et téléchargez le").
  >>> Une fois la librairie installée, il faut relancer Processing.
  
  Merci et bonne découverte/correction !
  
*/

//------------------------ VARIABLES
// Déclaration de nos variables ainsi que nos fichiers utilisés

//Déclarations de nos tableaux (pour faciliter notre vie et ne pas écrire des centaines de fois la "quasi" même chose)
float[] EtoileX = new float[500];
float[] EtoileY = new float[500];
float[] EtoileVitesse = new float[500];

//Importons notre librairie et déclarons notre musique & enregistrement

SoundFile Musique;
SoundFile Apollo11;

//Déclaration de nos images
PImage wallpaper, NASA_logo, Apollo_curseur, Apollo_logo, waves, Apollo_programme, amstrong, colins, aldrin;
//Déclaration polices écritures
PFont roboto;

// ------------------------ SETUP
//Nous initialisons toutes les infos au lancement du programme
public void setup() {

 //---- Infos basiques
 
 

 //---- Images
 wallpaper = loadImage("APOLLO11_wallpaper.jpg");
 NASA_logo = loadImage("NASA_Logo.png");
 NASA_logo.resize(80,80);
 Apollo_programme = loadImage("Apollo_programme.png");
 Apollo_programme.resize(60,60);
 Apollo_curseur = loadImage("APOLLO11_curseur.png");
 Apollo_curseur.resize(28,28);
 Apollo_logo = loadImage("APOLLO11_Logo.png");
 Apollo_logo.resize(150,150);
 amstrong = loadImage("Amstrong_sign.png");
 amstrong.resize(80,87);
 aldrin = loadImage("Aldrin_sign.png");
 aldrin.resize(125,32);
 colins = loadImage("Colins_sign.png");
 colins.resize(125,24);
 waves = loadImage("animation_music.gif");
 waves.resize(300,202);

 //---- Musique & Enregistrement
 Musique = new SoundFile(this, "NASA_Music.wav");
 Apollo11 = new SoundFile(this, "Apollo11_enregistrement.wav");
 Apollo11.loop();
 Apollo11.pause();
 Musique.loop();
 Musique.amp(0.05f);

 //---- Police d'écriture
 roboto = createFont("RobotoSlab-Regular.ttf", 128, true);
 
 //---- Notre wallpaper
 background (wallpaper);
 fill (80,100);
 rectMode(CORNER);
 rect (0,0,width,height);
 
 // ---- Nos étoiles animées
 //Ici, nous avons dessiné des étoiles (des traits tous petits) en leurs donnant une couleur et une taille
  stroke(255);
  strokeWeight(1);
  //Ensuite, on construit une boucle explicant que si il y a -500 étoiles à l'écran, il faut en faire apparaitre
  int i = 0;
  while(i < 500) {
    //Ici, on fait en sorte que nos étoiles apparaissent aléatoirement sur l'axe X et l'axe Y
    EtoileX [i] = random(0, width);
    EtoileY [i] = random(0, height);
    //Pareil pour la vitesse de celles-ci
    EtoileVitesse [i] = random(1,2);
    //Et pour finir, ici on s'assure bien de ne pas faire une boucle "infinie" en lui imposant une incrémentation
    i = i + 1;
  }
}

// ------------------------ DRAW
//Ensuite, nous énumérons toutes nos fonctions personnalisées dans "draw" pour faciliter la lecture de notre code.
public void draw() {
  
  arriere_plan_etoiles();
  signatures();
  images_enregistrement_parallax();
  curseur();
  titre();
}

// ------------ ARRIERE PLAN & ANIMATION ETOILES
//Ici, notre wallpaper ainsi que notre animation d'étoiles qui se déplacent
public void arriere_plan_etoiles() {
  //Notre wallpaper
  background (wallpaper);
  fill (80,100);
  rectMode(CORNER);
  rect (0,0,width,height);
  //Nos étoiles
  stroke(255);
  strokeWeight(1);
  /*De nouveau, une autre boucle dans la fonction draw cette fois-ci pour continuellement actualiser notre animation
  et donc, faire du mouvement */
  int i = 0;
  while(i < 500) {
    point(EtoileX [i], EtoileY [i]);
    //Ici, nous faisons en sorte que l'étoile bouge sur notre axe X pour créer de l'animation sur l'écran
    EtoileX [i] = EtoileX [i] - EtoileVitesse [i];
    //Ici, on donne une condition: si l'étoile sort de l'écran, elle reviendra à sa position de départ
    if(EtoileX [i] < 0) {
      EtoileX [i] = width;
    } i = i + 1;
  }  
  
  //Logo NASA & Programme Apollo
  imageMode(CENTER);
  image(NASA_logo,width/2-50,550);
  image(Apollo_programme,width/2+50,550);
} 

//------------ LES SIGNATURES DES ASTRONAUTES D'APOLLO 11
public void signatures() {
  
  imageMode(CENTER);
  tint(0);
  image(amstrong,width/2,50);
  image(aldrin,width/2-175,50);
  image(colins,width/2+175,50);
  noTint();
}

//------------ ACTIVATION IMAGES ENREGISTREMENT (si fonction mouseClicked à été activé : voir tout en bas du code)
//Ici, nous demandons que si l'enregistrement joue (càd qu'il y a eu un click souris) alors les images apparaissent
public void images_enregistrement_parallax() {

if (Apollo11.isPlaying()) {
   
  //Grâce à la fonction "map", on peut modifier les coordonnées des images par rapport au déplacement de notre souris
   imageMode(CENTER);
   
   //Parallaxe ondes radio 
   float wavesX=map(mouseX,0,width,0,75);
   float wavesY=map(mouseY,0,height,0,50);
   image(waves,wavesX+width/2-125,wavesY+height/2);
   
   //Parallaxe Logo Apollo11
   float Apollo_logoX=map(mouseX,0,width,0,35);
   float Apollo_logoY=map(mouseY,0,height,0,35);
   image(Apollo_logo,Apollo_logoX+width/2+150,Apollo_logoY+height/2);
 }
}

// ------------ CURSEUR SOURIS
//Ici, Une fonction pour transformer notre souris en patch Apollo 11 lorsque l'on reste appuyé sur la souris
public void curseur() {

  if (mousePressed) {
     cursor(Apollo_curseur);
     }else{
     cursor(ARROW); }
}

// ------------ TEXTE
//Ici, notre fonction avec tout notre texte.
public void titre() {
  //QUOTE NEIL AMSTRONG
  fill(0,40);
  noStroke();
  rectMode(CENTER);
  rect(width/2,300,550,400);
  textAlign(CENTER,BOTTOM);
  textFont(roboto,18);
  fill(255);
  text("“ That's one small step for man, one giant leap for mankind. ”",width/2,150);
  rect(width/2,180,250,0.2f);
  textFont(roboto,12);
  text("- Neil Amstrong", width/2,175);
  //INSTRUCTIONS PLAY/PAUSE
  textFont(roboto,10);
  text("PLAY/PAUSE the communications by clicking with your mouse anywhere",width/2,490);
  //DATE 29 JUILLET 1969
  textFont(roboto,14);
  text("20th July 1969",900,25);
} 

//------------ COMMANDE ENREGISTREMENT PLAY/PAUSE
/* Ici, nous allons pouvoir gérer le play/pause de l'enregistrement de l'alunissage en demandant que si l'utilisateur
clique avec sa souris, l'enregistrement fait play et si l'utilisateur reclick, alors pause */
public void mouseClicked() {
 
 if (Apollo11.isPlaying()) {
    Apollo11.pause();
    }else{
    Apollo11.play();
  }
}
  public void settings() {  size(960,600);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "APOLLO11" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
