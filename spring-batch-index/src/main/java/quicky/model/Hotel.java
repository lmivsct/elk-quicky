package quicky.model;

/**
 */
public class Hotel {
    
    private String id;
    private String nom;
    private Geolocalisation geolocalistation;
    private String boost;
    private boolean residence;
    private String theme1;
    private String theme2;
    private String visuel;
    private float etoiles;
    private String description;

    public Hotel(final String id, final String nom, final Geolocalisation geolocalistation, final String boost, final boolean residence, final String theme1, final String theme2, final String visuel, final float etoiles, final String description) {
        this.id = id;
        this.nom = nom;
        this.geolocalistation = geolocalistation;
        this.boost = boost;
        this.residence = residence;
        this.theme1 = theme1;
        this.theme2 = theme2;
        this.visuel = visuel;
        this.etoiles = etoiles;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
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

    public String getBoost() {
        return boost;
    }

    public void setBoost(final String boost) {
        this.boost = boost;
    }

    public boolean isResidence() {
        return residence;
    }

    public void setResidence(final boolean residence) {
        this.residence = residence;
    }

    public String getTheme1() {
        return theme1;
    }

    public void setTheme1(final String theme1) {
        this.theme1 = theme1;
    }

    public String getTheme2() {
        return theme2;
    }

    public void setTheme2(final String theme2) {
        this.theme2 = theme2;
    }

    public String getVisuel() {
        return visuel;
    }

    public void setVisuel(final String visuel) {
        this.visuel = visuel;
    }

    public float getEtoiles() {
        return etoiles;
    }

    public void setEtoiles(final float etoiles) {
        this.etoiles = etoiles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", geolocalistation=" + geolocalistation +
                ", boost='" + boost + '\'' +
                ", residence=" + residence +
                ", theme1='" + theme1 + '\'' +
                ", theme2='" + theme2 + '\'' +
                ", visuel='" + visuel + '\'' +
                ", etoiles='" + etoiles + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
