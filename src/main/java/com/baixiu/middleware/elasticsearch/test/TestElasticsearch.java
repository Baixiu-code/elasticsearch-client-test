package com.baixiu.middleware.elasticsearch.test;

import com.alibaba.fastjson2.JSONObject;
import com.baixiu.middleware.elasticsearch.test.brand.BrandIndexBean;
import com.baixiu.middleware.elasticsearch.test.brand.BrandIndexServiceImpl;
import com.baixiu.middleware.elasticsearch.transport.ElasticSearchTemplateClient;
import org.elasticsearch.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;

/**
 * @author chenfanglin1
 * @date 创建时间 2024/4/1 12:00 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@EnableAutoConfiguration
public class TestElasticsearch extends Application{
    
    
    @Autowired
    private ElasticSearchTemplateClient elasticSearchTemplate;
    
    @Autowired
    private BrandIndexServiceImpl brandIndexService;
    
    @Test
    public void testClient(){
        Client client= elasticSearchTemplate.getClient();
        System.out.println (JSONObject.toJSONString (client.admin ()));
        System.out.println (JSONObject.toJSONString (client.settings()));
    }    
    
    @Test
    public void testAddBrandIndex(){
        BrandIndexBean brandIndexBean=new BrandIndexBean ();
        brandIndexBean.setBrandId ("test1024");
        brandIndexBean.setName ("testBrandName");
        brandIndexBean.setCreated (new Date ());
        brandIndexBean.setModified (new Date());
        brandIndexBean.setCreator ("cflTest");
        brandIndexBean.setModifier ("cflTest");
        brandIndexBean.setLogo ("testLogo");
        brandIndexBean.setInitial ("T");
        brandIndexBean.setOuterId ("testOuterId");
        brandIndexBean.setStatus (1);
        brandIndexBean.setProductNum (10);      
        brandIndexService.setIndex("rd_brand_pre");
        brandIndexService.setType("rd_brand_pre");
        brandIndexService.setElasticSearchTemplate (elasticSearchTemplate);
        brandIndexService.addBean (1024L,brandIndexBean);
    }
    
    @Test
    public void testSelectBrandById(){
        BrandIndexBean brandIndexBean=brandIndexService.getById (1024L,"1024_test1024");
        System.out.println (JSONObject.toJSONString (brandIndexBean));
    }
    
}
