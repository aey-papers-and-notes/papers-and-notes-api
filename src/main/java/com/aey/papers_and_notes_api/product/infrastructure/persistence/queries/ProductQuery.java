package com.aey.papers_and_notes_api.product.infrastructure.persistence.queries;

public class ProductQuery {
    public static  final String PARAM_PRODUCT_LIMIT = "limit";
    public static  final String PARAM_PRODUCT_OFFSET = "offset";

    public static final String PAGINATION_PRODUCT = "select " +
            "product_id as productId, " +
            "prd_tx_name as name, " +
            "prd_tx_description as description, " +
            "prd_nu_price as price, " +
            "prd_nu_stock as stock, " +
            "prd_tx_images_url as imageUrl, " +
            "prd_dt_created_at as createdAt, " +
            "prd_dt_updated_at as updatedAt, " +
            "prd_st_is_active as isActive, " +
            "prd_fk_brand_id as brandId " +
            "from prod01_products " +
            "where (prd_st_is_active = true) " +
            "limit coalesce(:limit, 10) " +
            "offset coalesce(:offset, 0)";

    public static final String COUNT_AVAILABLE_PRODUCTS = "select " +
            "count(*) " +
            "from prod01_products " +
            "where prd_st_is_active = true";
}
