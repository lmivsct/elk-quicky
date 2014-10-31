package quicky.writer;

import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import quicky.model.Gare;


public class IndexGareWriter implements ItemWriter<Gare> {


    @Autowired
    private Client elkClient;

    @Override
    public void write(final List<? extends Gare> items) throws Exception {
        System.out.println("Add Gares in index (" + items.size() + ")...");

        final BulkRequestBuilder bulkRequest = elkClient.prepareBulk();

        for (Gare gare : items) {
            bulkRequest.add(elkClient.prepareIndex("quicky", "gare")
                            .setSource(jsonBuilder()
                                            .startObject()
                                                .field("nom", gare.getNom())
                                                    .startObject("geolocalisation")
                                                    .field("pays", gare.getGeolocalistation().getPays())
                                                    .field("ville", gare.getGeolocalistation().getVille())
                                                    .field("latitude", gare.getGeolocalistation().getLatitude())
                                                    .field("longitude", gare.getGeolocalistation().getLongitude())
                                                    .endObject()
                                                .field("tgv", gare.isTgv())
                                            .endObject()
                            )
            );
        }

        final BulkResponse bulkResponse = bulkRequest.execute().actionGet();

        if (bulkResponse.hasFailures()) {
            System.out.println("INDEX FAILED");
        }


    }
}
