package quicky.processor;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import quicky.model.Gare;
import quicky.model.GareRaw;
import quicky.model.Geolocalisation;

public class GareItemProcessor implements ItemProcessor<GareRaw, Gare> {

    @Override
    public Gare process(final GareRaw gareRaw) throws ParseException {

        final Gare transformedGare = new Gare(gareRaw.getNom(), new Geolocalisation(gareRaw.getVille(), "", gareRaw.getPays(),
                NumberFormat.getInstance(Locale.FRANCE).parse(gareRaw.getLatitude()).floatValue(), NumberFormat.getInstance(Locale.FRANCE).parse(gareRaw
                .getLongitude()).floatValue()), 
                !StringUtils.isEmpty(gareRaw.getTgv()) && gareRaw
                .getTgv()
                .equalsIgnoreCase("OUI"));
        return transformedGare;
    }
}
