package quicky.client;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import org.elasticsearch.node.Node;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


@Component
public class ElkNodeFactoryBean implements FactoryBean<Node>, InitializingBean, DisposableBean {

    private Node node;

    @Override
    public void destroy() throws Exception {
        System.out.println("Stoping ELK node...");
        if(node!=null) {
            node.close();
        }
    }

    @Override
    public Node getObject() throws Exception {
        return node;
    }

    @Override
    public Class<Node> getObjectType() {
        return Node.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Starting ELK node...");
        node = nodeBuilder().client(true).node();
    }
    
    
}
