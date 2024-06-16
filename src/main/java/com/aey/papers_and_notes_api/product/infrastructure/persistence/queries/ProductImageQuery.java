package com.aey.papers_and_notes_api.product.infrastructure.persistence.queries;

public class ProductImageQuery {

    public static  final String PARAM_PRODUCT_ID = "productId";

    public static final String PAGINATION_PRODUCT_IMAGES = "select " +
            "image_id as imageId, " +
            "img_tx_url as url, " +
            "img_tx_description as description " +
            "from prod07_products_images " +
            "where (img_fk_product_id = :productId)";
}
