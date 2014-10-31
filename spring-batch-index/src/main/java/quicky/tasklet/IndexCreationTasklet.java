package quicky.tasklet;

import java.io.IOException;

import static org.elasticsearch.client.Requests.createIndexRequest;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


public class IndexCreationTasklet implements Tasklet, InitializingBean {

    private final static String INDEX_NAME = "quicky";
    
    @Autowired
    private Client elkClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.isTrue(elkClient.admin().indices().exists(new IndicesExistsRequest(INDEX_NAME)).actionGet().isExists());
    }

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) throws Exception {
        // Delete index if exists
        if (elkClient.admin().indices().exists(new IndicesExistsRequest(INDEX_NAME)).actionGet().isExists()) {
            elkClient.admin().indices().delete(new DeleteIndexRequest(INDEX_NAME));
        }

        // Create index 
        elkClient.admin().indices().create(createIndexRequest(INDEX_NAME));

        // Add mapping for the 'ville' field
        elkClient.admin().indices()
                .preparePutMapping(INDEX_NAME)
                .setType("gare")
                .setSource(getVilleMapping())
                .execute().actionGet();
        
        
        return RepeatStatus.FINISHED;
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
                    .endObject()
                .endObject();
    }
}
