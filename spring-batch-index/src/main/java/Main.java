import java.io.IOException;

import static org.elasticsearch.client.Requests.createIndexRequest;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@EnableAutoConfiguration
public class Main {

    private final static String INDEX_NAME = "quicky";

    public static void main(String[] args) throws IOException {

        final Node node = nodeBuilder().node();
        final Client client = node.client();

        // Delete index if exists
        if (client.admin().indices().exists(new IndicesExistsRequest(INDEX_NAME)).actionGet().isExists()) {
            client.admin().indices().delete(new DeleteIndexRequest(INDEX_NAME));
        }

        // Create index 
        client.admin().indices().create(createIndexRequest(INDEX_NAME));
        
        // Add mapping for the 'ville' field
        client.admin().indices()
                .preparePutMapping(INDEX_NAME)
                .setType("gare")
                .setSource(getVilleMapping())
                .execute().actionGet();

        // Populate index
        ApplicationContext ctx = SpringApplication.run(Main.class, args);

        node.close();
    }

    public static XContentBuilder getVilleMapping() throws IOException {
        return jsonBuilder()
                .startObject()
                        .startObject("properties")
                            .startObject("ville")
                                .field("type", "string")
                                .startObject("fields")
                                    .startObject("raw")
                                        .field("type", "string")
                                        .field("index", "not_analyzed")
                                .endObject()
                            .endObject()
                        .endObject()
                .endObject();
    }
}