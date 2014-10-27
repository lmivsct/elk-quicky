/*
 * Copyright (c) 2012, vsc-technologies - www.voyages-sncf.com
 * All rights reserved.
 * 
 * Les presents codes sources sont proteges par le droit d'auteur et 
 * sont la propriete exclusive de VSC Technologies.
 * Toute representation, reproduction, utilisation, exploitation, modification, 
 * adaptation de ces codes sources sont strictement interdits en dehors 
 * des autorisations formulees expressement par VSC Technologies, 
 * sous peine de poursuites penales. 
 * 
 * Usage of this software, in source or binary form, partly or in full, and of
 * any application developed with this software, is restricted to the
 * customer.s employees in accordance with the terms of the agreement signed
 * with VSC-technologies.
 */

/**
 */
public class Gare {
    private String codeRR;
    private String nom;
    private String pays;
    private String ville;
    private String latitude;
    private String longitude;
    private String tgv;

    public Gare() {
    }

    public Gare(final String codeRR, final String nom, final String pays, final String ville, final String latitude, final String longitude, final String tgv) {
        this.codeRR = codeRR;
        this.nom = nom;
        this.pays = pays;
        this.ville = ville;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tgv = tgv;
    }

    public String getCodeRR() {
        return codeRR;
    }

    public void setCodeRR(final String codeRR) {
        this.codeRR = codeRR;
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
        return "Gare{" +
                "codeRR='" + codeRR + '\'' +
                ", nom='" + nom + '\'' +
                ", pays='" + pays + '\'' +
                ", ville='" + ville + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", tgv='" + tgv + '\'' +
                '}';
    }
}
