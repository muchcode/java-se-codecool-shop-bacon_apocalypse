package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {

    private static ProductCategory filteredCategory;
    private static Supplier filteredSupplier;
    private static List<Product> filteredProduct;

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        Map params = new HashMap<>();
        params.put("category", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getAll());
        params.put("supplier", supplierDataStore.getAll());
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderFilteredProductsByCategory(Request req, Response res) {
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        String target = req.params("name");
        int id;

        for (ProductCategory cat : productCategoryDataStore.getAll()) {
            if (target.equals(cat.getName())) {
                id = cat.getId();
                filteredCategory = productCategoryDataStore.find(id);
            }
        }

        filteredProduct = filteredCategory.getProducts();

        Map params = new HashMap<>();
        params.put("category", productCategoryDataStore.getAll());
        params.put("products", filteredProduct);
        params.put("supplier", supplierDataStore.getAll());
        return new ModelAndView(params, "product/index");

    }

    public static ModelAndView renderFilteredProductsBySupplier(Request req, Response res) {
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        String target = req.params("name");
        int id;

        for (Supplier sup : supplierDataStore.getAll()) {
            if (target.equals(sup.getName())) {
                id = sup.getId();
                filteredSupplier = supplierDataStore.find(id);
            }
        }

        filteredProduct = filteredSupplier.getProducts();

        Map params = new HashMap<>();
        params.put("category", productCategoryDataStore.getAll());
        params.put("products", filteredProduct);
        params.put("supplier", supplierDataStore.getAll());
        return new ModelAndView(params, "product/index");
    }
}

