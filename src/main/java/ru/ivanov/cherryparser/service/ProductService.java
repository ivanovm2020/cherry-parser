package ru.ivanov.cherryparser.service;

import ru.ivanov.cherryparser.domain.Product;
import ru.ivanov.cherryparser.domain.User;
import ru.ivanov.cherryparser.repos.ProductRepo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;


@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final FileService fileService;
    private final ConnectService connectService;
    private User user;
    private String url;

    @Autowired
    public ProductService (ProductRepo productRepo,
                           FileService fileService,
                           ConnectService connectService) {
        this.productRepo = productRepo;
        this.fileService = fileService;
        this.connectService = connectService;
    }

    private Integer getPrice(String price) {
        return Integer.parseInt(price.substring(0, price.indexOf(" ")));
    }
    public void add(String url, User user) {
        this.url = url;
        this.user = user;

        Document page = connectService.getPage(url);
        Element listItems = page.select("div[id=list_items]").first();
        Elements product = listItems.select("div[class=section_item]");
        List<Product> products = new ArrayList<>();

        for (Element element: product) {

                products.add(new Product(
                        element.select("div[class=product_left_text_info]").text(),
                        element.select("span[class=item_name]").text(),
                        getPrice(element.select("div[class=hide]").text()),
                        user,
                        fileService.getFile("https:" + element.select("a[href]").attr("href"))
                ));
        }

        if (products.size() > 0) {
            productRepo.saveAll(products);
        }
    }
}
