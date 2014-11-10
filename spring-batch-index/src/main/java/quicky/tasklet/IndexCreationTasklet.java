package quicky.tasklet;

import static org.elasticsearch.client.Requests.createIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.client.Client;
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
        elkClient.admin().indices().create(createIndexRequest(INDEX_NAME));
     
        
        return RepeatStatus.FINISHED;
    }

    @Autowired
    public void setElkClient(final ElkClientFactoryBean elkClientFactoryBean) throws Exception {
        this.elkClient = elkClientFactoryBean.getObject();
    }
}
