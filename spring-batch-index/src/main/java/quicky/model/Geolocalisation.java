package quicky.model;

/**
 */
public class Geolocalisation {
    
    private String ville;
    private String codePostal;
    private String pays;
    
    private float latitude;
    private float longitude;

    public Geolocalisation(final String ville, final String codePostal, final String pays, final float latitude, final float longitude) {
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(final long latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(final long longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Geolocalisation{" +
                "ville='" + ville + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", pays='" + pays + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
