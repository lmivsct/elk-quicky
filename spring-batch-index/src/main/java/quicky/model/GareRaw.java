package quicky.model;

public class GareRaw {
    private String nom;
    private String pays;
    private String ville;
    private String latitude;
    private String longitude;
    private String tgv;

    public GareRaw() {
    }

    public GareRaw(final String nom, final String pays, final String ville, final String latitude, final String longitude, final String tgv) {
        this.nom = nom;
        this.pays = pays;
        this.ville = ville;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tgv = tgv;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(final String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(final String ville) {
        this.ville = ville;
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

    public String getTgv() {
        return tgv;
    }

    public void setTgv(final String tgv) {
        this.tgv = tgv;
    }

    @Override
    public String toString() {
        return "quicky.model.Gare{" +
                "nom='" + nom + '\'' +
                ", pays='" + pays + '\'' +
                ", ville='" + ville + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", tgv='" + tgv + '\'' +
                '}';
    }
}
