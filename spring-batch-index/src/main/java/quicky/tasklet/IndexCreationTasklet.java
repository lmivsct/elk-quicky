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
import org.springframework.beans.factory.annotation.Autowired;

import quicky.client.ElkClientFactoryBean;


public class IndexCreationTasklet implements Tasklet {

    public final static String INDEX_NAME = "quicky";

    private Client elkClient;

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) throws Exception {
        // Delete index if exists
        if (elkClient.admin().indices().exists(new IndicesExistsRequest(INDEX_NAME)).actionGet().isExists()) {
            elkClient.admin().indices().delete(new DeleteIndexRequest(INDEX_NAME));
        }

        // Create index 
        elkClient.admin().indices().create(createIndexRequest(INDEX_NAME).mapping("gare", getMappingsByJson("gare")).mapping("hotel", 
                getMappingsByJson("hotel")));
     
        
        return RepeatStatus.FINISHED;
    }

    private XContentBuilder getMappingsByJson(String type) throws IOException {
        XContentBuilder mapping = jsonBuilder()
                .startObject()
                    .startObject(type)
                        .startObject("properties")
                            .startObject("location")
                            .field("type", "geo_point")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();
        return mapping;
    }

    @Autowired
    public void setElkClient(final ElkClientFactoryBean elkClientFactoryBean) throws Exception {
        this.elkClient = elkClientFactoryBean.getObject();
    }
}
