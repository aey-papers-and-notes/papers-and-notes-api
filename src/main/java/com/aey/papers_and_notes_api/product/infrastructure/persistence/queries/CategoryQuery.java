package com.aey.papers_and_notes_api.product.infrastructure.persistence.queries;

public class CategoryQuery {
    public static  final String PARAM_PRODUCT_ID = "productId";

    public static final String CATEGORIES_BY_PRODUCT_ID = "select " +
            "category_id as categoryId, " +
            "cat_tx_name as name, " +
            "cat_st_is_active as isActive, " +
            "cat_dt_created_at as createdAt, " +
            "cat_dt_updated_at as updatedAt " +
            "from prod03_categories pc  " +
            "inner join prod04_products_categories ppc  " +
            "on pc.category_id = ppc.rpc_fk_category_id " +
            "where ppc.rpc_fk_product_id = (:productId) " +
            "and pc.cat_st_is_active = true";

}
