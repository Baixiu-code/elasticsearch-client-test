package com.baixiu.middleware.elasticsearch.test;

import com.google.common.base.Strings;
import com.jd.rd.product.domain.biz.service.search.domain.BrandIndexBean;
import com.jd.rd.product.domain.biz.service.search.domain.BrandSearchBean;
import com.jd.rd.product.domain.biz.service.search.service.EsAbstractIndexService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author wuyanghong3
 * date  2020/11/18
 */
@Slf4j
public class BrandIndexServiceImpl extends AbstractTransportSearchIndexService<BrandSearchBean, BrandIndexBean> {

    public static String defaultRouting = "0";

    @Override
    public QueryBuilder getQueryBuilder(BrandSearchBean searchBean) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 1.租户ID TODO 必传参数校验
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
}
