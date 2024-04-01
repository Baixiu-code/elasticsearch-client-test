package com.baixiu.middleware.elasticsearch.test.brand;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * @author baixiu
 * @date 2024年04月01日
 */
@Data
public class BrandSearchBean {

    /**
     * 租户 ID
     */
    private Long tenantId;

    /**
     * 品牌ID
     */
    private String brandId;
    
    /**
     * 品牌名称
     */
    private String name;
    
    /**
     * logo链接
     */
    private String logo;

    /**
     * 创建人
     */
    private String creator;
    
    /**
     * 创建时间
     */
    private Date created;
    
    /**
     * 修改人
     */
    private String  modifier;
    
    /**
     * 修改时间
     */
    private Date modified;
    
    /**
     * 状态 1:有效,0:无效
     */
    private Integer status;

    /**
     * 批量查询
     */
    private List<String> ids;
}
