package quicky.writer;

import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import quicky.client.ElkClientFactoryBean;
import quicky.model.Hotel;


public class IndexHotelWriter implements ItemWriter<Hotel> {

    private Client elkClient;

    @Override
    public void write(final List<? extends Hotel> items) throws Exception {
        System.out.println("Add Hotels in index (" + items.size() + ")...");

        final BulkRequestBuilder bulkRequest = elkClient.prepareBulk();
        bulkRequest.setRefresh(true);

        for (Hotel hotel : items) {
            bulkRequest.add(elkClient.prepareIndex("quicky", "hotel")
                            .setSource(jsonBuilder()
                                            .startObject()
                                            .field("id", hotel.getId())
                                            .field("nom", hotel.getNom())
                                                .startObject("geolocalisation")
                                                    .field("pays", hotel.getGeolocalistation().getPays())
                                                    .field("ville", hotel.getGeolocalistation().getVille())
                                                    .field("cp", hotel.getGeolocalistation().getCodePostal())
                                                    .field("latitude", hotel.getGeolocalistation().getLatitude())
                                                    .field("longitude", hotel.getGeolocalistation().getLongitude())
                                                .endObject()
                                            .field("boost", hotel.getBoost())
                                            .field("residence", hotel.isResidence())
                                            .field("theme1", hotel.getTheme1())
                                            .field("theme2", hotel.getTheme2())
                                            .field("visuel", hotel.getVisuel())
                                            .field("etoiles", hotel.getEtoiles())
                                            .field("description", hotel.getDescription())
                                                .startObject("location")
                                                      .field("lat", hotel.getGeolocalistation().getLatitude())
                                                      .field("lon", hotel.getGeolocalistation().getLongitude())
                                                .endObject()
                                            .endObject()
                            )
            );
        }

        final BulkResponse bulkResponse = bulkRequest.execute().actionGet();

        if (bulkResponse.hasFailures()) {
            System.out.println("INDEX FAILED");
        }

    }

    @Autowired
    public void setElkClient(final ElkClientFactoryBean elkClientFactoryBean) throws Exception {
        this.elkClient = elkClientFactoryBean.getObject();
    }
}
