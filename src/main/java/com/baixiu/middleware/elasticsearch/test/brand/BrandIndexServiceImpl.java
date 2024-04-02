package com.baixiu.middleware.elasticsearch.test.brand;

import com.baixiu.middleware.elasticsearch.api.AbstractTransportSearchIndexService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author baixiu
 * @date  2024年04月02日
 */
@Component
@Slf4j
public class BrandIndexServiceImpl extends AbstractTransportSearchIndexService<BrandSearchBean, BrandIndexBean> {

    public static String defaultRouting = "0";

    

    @Override
    public QueryBuilder getQueryBuilder(BrandSearchBean searchBean) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (searchBean.getTenantId() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("tenantId", searchBean.getTenantId()));
        }

        // 2.品牌ID
        if (!Strings.isNullOrEmpty(searchBean.getBrandId())) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId", searchBean.getBrandId()));
        }

        // 3.品牌名称
        if (!Strings.isNullOrEmpty(searchBean.getName())) {
            boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("name", searchBean.getName()));
        }

        // 4.状态
        if (searchBean.getStatus() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("status", searchBean.getStatus()));
        }

        // 5. 根据 ID 批量查询
        if (!CollectionUtils.isEmpty(searchBean.getIds())) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId", searchBean.getIds()));
        }
        return boolQueryBuilder;
    }

    @Override
    protected String[] getRouting4Search(BrandSearchBean searchBean) {
        return new String[0];
    }

    @Override
    public void addBean(Long tenantId, BrandIndexBean indexBean) {
        indexByRoute(builderDocId(tenantId, indexBean.getBrandId()), indexBean, defaultRouting);
    }

    private String builderDocId(Long tenantId, String brandId) {
        return String.format("%s_%s", tenantId, brandId);
    }

    @Override
    public void delete(Long tenantId, String id) {
        log.info("brand--delete tenantId:{},id:{}", tenantId, id);
        deleteByRoute(tenantId,id, defaultRouting);
    }

    @Override
    public void deleteByRoute(Long tenantId, String id, String route) {
        log.info("brand--deleteByRoute tenantId:{},id:{}", tenantId, id);
        super.deleteByRoute(tenantId, id, defaultRouting);
    }

    @Override
    public void updateById(Long aLong, String s, BrandIndexBean brandIndexBean) {
        
    }
}
