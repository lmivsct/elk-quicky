package quicky.model;

public class Gare {
    private String nom;
    private Geolocalisation geolocalistation;
    private boolean tgv;

    public Gare(final String nom, final Geolocalisation geolocalistation, final boolean tgv) {
        this.nom = nom;
        this.geolocalistation = geolocalistation;
        this.tgv = tgv;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public Geolocalisation getGeolocalistation() {
        return geolocalistation;
    }

    public void setGeolocalistation(final Geolocalisation geolocalistation) {
        this.geolocalistation = geolocalistation;
    }

    public boolean isTgv() {
        return tgv;
    }

    public void setTgv(final boolean tgv) {
        this.tgv = tgv;
    }

    @Override
    public String toString() {
        return "Gare{" +
                "nom='" + nom + '\'' +
                ", geolocalistation=" + geolocalistation +
                ", tgv=" + tgv +
                '}';
    }
}
