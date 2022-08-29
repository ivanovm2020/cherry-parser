package ru.ivanov.cherryparser.controller;

import ru.ivanov.cherryparser.domain.Product;
import ru.ivanov.cherryparser.domain.ProductType;
import ru.ivanov.cherryparser.domain.User;
import ru.ivanov.cherryparser.service.ProductTypeService;
import ru.ivanov.cherryparser.repos.ProductRepo;
import ru.ivanov.cherryparser.repos.ProductTypeRepo;
import ru.ivanov.cherryparser.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final ProductRepo productRepo;
    private final ProductTypeRepo productTypeRepo;
    private final ProductService productService;
    private final ProductTypeService productTypeService;

    @Autowired
    public MainController(ProductRepo productRepo,
            ProductTypeRepo productTypeRepo,
            ProductService productService,
            ProductTypeService productTypeService) {
        this.productRepo = productRepo;
        this.productTypeRepo = productTypeRepo;
        this.productService = productService;
        this.productTypeService = productTypeService;
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {

        Iterable<Product> products = productRepo.findAll();
        Iterable<ProductType> productTypes = productTypeRepo.findAll();

        if (((Collection<?>) productTypes).size() == 0) {
            productTypeService.add();
        }

        productTypes = productTypeRepo.findAll();

        if (filter != null && !filter.isEmpty()) {
            products = productRepo.findAllByDescriptionContains(filter);
        } else {
            products = productRepo.findAll();
        }

        model.addAttribute("products", products);
        model.addAttribute("productTypes", productTypes);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String productTypeId,
            Map<String, Object> model) {

        if(productTypeId != null && !productTypeId.isEmpty()) {
            Iterable<ProductType> findProductTypes;
            findProductTypes = productTypeRepo.findById(Integer.parseInt(productTypeId));
            productService.add(
                    ((List<ProductType>) findProductTypes).get(0).getFilename(),
                    user);

            Iterable<Product> products = productRepo.findAll();
            Iterable<ProductType> productTypes = productTypeRepo.findAll();
            model.put("products", products);
            model.put("productTypes", productTypes);
        }

        return "main";
    }
}