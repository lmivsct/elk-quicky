/*
 * Copyright (c) 2012, vsc-technologies - www.voyages-sncf.com
 * All rights reserved.
 * 
 * Les presents codes sources sont proteges par le droit d'auteur et 
 * sont la propriete exclusive de VSC Technologies.
 * Toute representation, reproduction, utilisation, exploitation, modification, 
 * adaptation de ces codes sources sont strictement interdits en dehors 
 * des autorisations formulees expressement par VSC Technologies, 
 * sous peine de poursuites penales. 
 * 
 * Usage of this software, in source or binary form, partly or in full, and of
 * any application developed with this software, is restricted to the
 * customer.s employees in accordance with the terms of the agreement signed
 * with VSC-technologies.
 */
package quicky.tasklet;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import quicky.client.ElkClientFactoryBean;

/**
 */
public class IndexValidateTasklet implements Tasklet {

    private Client elkClient;

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) throws Exception {

        final CountResponse gareCountResponse = elkClient.prepareCount(IndexCreationTasklet.INDEX_NAME).setTypes("gare").setQuery(QueryBuilders
                .matchAllQuery()).execute().actionGet();

        final CountResponse hotelCountResponse = elkClient.prepareCount(IndexCreationTasklet.INDEX_NAME).setTypes("hotel").setQuery(QueryBuilders
                .matchAllQuery()).execute().actionGet();

        if (gareCountResponse.getCount() != 3172) {
            System.out.println("Gare documents indexed lower than expected number (" + gareCountResponse.getCount() + " instead of " + 3172 + ")");
        }

        if (hotelCountResponse.getCount() != 186) {
            System.out.println("Hotel documents indexed lower than expected number (" + hotelCountResponse.getCount() + " instead of " + 186 + ")");
        }

        return RepeatStatus.FINISHED;
    }

    @Autowired
    public void setElkClient(final ElkClientFactoryBean elkClientFactoryBean) throws Exception {
        this.elkClient = elkClientFactoryBean.getObject();
    }
}
