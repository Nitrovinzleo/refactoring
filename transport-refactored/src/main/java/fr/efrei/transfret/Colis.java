package fr.efrei.transfret;

public class Colis {

    public static final double DIVISEUR_VOLUME_M3 = 1_000_000.0;

    public static final int STANDARD = 0;
    public static final int FRAGILE = 1;
    public static final int DANGEREUX = 2;
    public static final int REFRIGERE = 3;

    private String description;
    private double poids;
    private double longueur;
    private double largeur;
    private double hauteur;
    private TypeMarchandise type;
    private double valeurDeclaree;

    public Colis(String description, double poids, double longueur, double largeur, double hauteur, TypeMarchandise type, double valeurDeclaree) {
        this.description = description;
        this.poids = poids;
        this.longueur = longueur;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.type = type;
        this.valeurDeclaree = valeurDeclaree;
    }

    public Colis(String description, double poids, double longueur, double largeur, double hauteur, int codeType, double valeurDeclaree) {
        this(description, poids, longueur, largeur, hauteur, versTypeMarchandise(codeType), valeurDeclaree);
    }

    private static TypeMarchandise versTypeMarchandise(int code) {
        return switch (code) {
            case 1 -> TypeMarchandise.FRAGILE;
            case 2 -> TypeMarchandise.DANGEREUX;
            case 3 -> TypeMarchandise.REFRIGERE;
            default -> TypeMarchandise.STANDARD;
        };
    }

    public double getVolume() {
        return (longueur * largeur * hauteur) / DIVISEUR_VOLUME_M3;
    }

    public String getLibelleType() {
        return type != null ? type.getLibelle() : "Inconnu";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public TypeMarchandise getType() {
        return type;
    }

    public void setType(TypeMarchandise type) {
        this.type = type;
    }

    public double getValeurDeclaree() {
        return valeurDeclaree;
    }

    public void setValeurDeclaree(double valeurDeclaree) {
        this.valeurDeclaree = valeurDeclaree;
    }
}
