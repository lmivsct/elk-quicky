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
