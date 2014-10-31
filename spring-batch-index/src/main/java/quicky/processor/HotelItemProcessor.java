package quicky.processor;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import quicky.model.Geolocalisation;
import quicky.model.Hotel;
import quicky.model.HotelRaw;

public class HotelItemProcessor implements ItemProcessor<HotelRaw, Hotel> {

    @Override
    public Hotel process(final HotelRaw hotelRaw) throws Exception {

        final Hotel transformedHotel = new Hotel(hotelRaw.getId(), hotelRaw.getNom(), new Geolocalisation(hotelRaw.getVille(),
                hotelRaw.getCodePostal(), hotelRaw.getPays(), NumberFormat.getInstance(Locale.US).parse(hotelRaw.getLatitude()).floatValue(),
                NumberFormat.getInstance(Locale.US).parse(hotelRaw.getLongitude()).floatValue()),
                hotelRaw.getBoost(), (!StringUtils.isEmpty(hotelRaw.getResidence()) && hotelRaw.getResidence().equalsIgnoreCase("OUI")),
                hotelRaw.getTheme1(), hotelRaw.getTheme2(), hotelRaw.getVisuel(), NumberFormat.getInstance(Locale.US).parse(hotelRaw.getEtoiles())
                .floatValue(), hotelRaw.getDescription());

        return transformedHotel;
    }
}
