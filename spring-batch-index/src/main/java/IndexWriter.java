import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.springframework.batch.item.ItemWriter;

/**
 */
public class IndexWriter implements ItemWriter<Gare> {

    @Override
    public void write(final List<? extends Gare> items) throws Exception {
        System.out.println("Post Gares in index (" + items.size() + ")...");

        final Node node = nodeBuilder().node();
        final Client client = node.client();
        
        final BulkRequestBuilder bulkRequest = client.prepareBulk();

        for (Gare gare : items) {
            bulkRequest.add(client.prepareIndex("quicky", "gare")
                            .setSource(jsonBuilder()
                                            .startObject()
                                                .field("code", gare.getCodeRR())
                                                .field("nom", gare.getNom())
                                                .field("pays", gare.getPays())
                                                .field("ville", gare.getVille())
                                                .field("latitude", gare.getLatitude())
                                                .field("longitude", gare.getLongitude())
                                                .field("tgv", gare.getTgv())
                                            .endObject()
                            )
            );
        }

        final BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        
        if(bulkResponse.hasFailures()){
            System.out.println("INDEX FAILED");
        }

        node.close();
    }
}
