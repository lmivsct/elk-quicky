package quicky.model;

/**
 */
public class HotelRaw {
    
    private String id;
    private String nom;
    private String ville;
    private String codePostal;
    private String pays;
    private String boost;
    private String residence;
    private String theme1;
    private String theme2;
    private String visuel;
    private String latitude;
    private String longitude;
    private String etoiles;
    private String description;
    
    public HotelRaw(){
        
    }

    public HotelRaw(final String id, final String nom, final String ville, final String codePostal, final String boost, final String residence, 
            final String theme1, final String theme2, final String visuel, final String latitude, final String longitude, final String pays, final String etoiles, final String description) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
        this.boost = boost;
        this.residence = residence;
        this.theme1 = theme1;
        this.theme2 = theme2;
        this.visuel = visuel;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getVille() {
        return ville;
    }

    public void setVille(final String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(final String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(final String pays) {
        this.pays = pays;
    }

    public String getBoost() {
        return boost;
    }

    public void setBoost(final String boost) {
        this.boost = boost;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(final String residence) {
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }

    public String getEtoiles() {
        return etoiles;
    }

    public void setEtoiles(final String etoiles) {
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
                ", ville='" + ville + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", pays='" + pays + '\'' +
                ", boost='" + boost + '\'' +
                ", residence='" + residence + '\'' +
                ", theme1='" + theme1 + '\'' +
                ", theme2='" + theme2 + '\'' +
                ", visuel='" + visuel + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", etoiles='" + etoiles + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
