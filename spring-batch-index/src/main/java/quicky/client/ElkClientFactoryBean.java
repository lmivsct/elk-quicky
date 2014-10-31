package quicky.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ElkClientFactoryBean implements FactoryBean<Client>, InitializingBean, DisposableBean, BeanFactoryAware {

    @Autowired
    private Node node;

    private Client client;


    @Override
    public void destroy() throws Exception {
        System.out.println("Stoping ELK client...");
        if (client != null) {
            client.close();
        }
    }

    @Override
    public Client getObject() throws Exception {
        return client;
    }

    @Override
    public Class<Client> getObjectType() {
        return Client.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Starting ELK client...");
        if (node != null) {
            client = node.client();
        }
    }

    public void setNode(final Node node) {
        this.node = node;
    }

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        
    }
}
