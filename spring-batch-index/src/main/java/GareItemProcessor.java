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


import org.springframework.batch.item.ItemProcessor;

/**
 */
public class GareItemProcessor implements ItemProcessor<Gare, Gare> {


    @Override
    public Gare process(final Gare gare) throws Exception {
        final String nomGare = gare.getNom().toUpperCase();
        final String paysGare = gare.getPays().toUpperCase();
        final String villeGare = gare.getVille().toUpperCase();
        final String latGare = gare.getLatitude().toUpperCase();
        final String longGare = gare.getLongitude().toUpperCase();
        final String tgvGare = gare.getTgv().toUpperCase();

        final Gare transformedGare = new Gare(gare.getCodeRR(), nomGare, paysGare, villeGare, latGare, longGare, tgvGare);
        //System.out.println("Converting (" + gare + ") into (" + transformedGare + ") into (");
        return transformedGare;
    }


}
