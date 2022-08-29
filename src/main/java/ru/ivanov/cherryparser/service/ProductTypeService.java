package ru.ivanov.cherryparser.service;

import ru.ivanov.cherryparser.domain.ProductType;
import ru.ivanov.cherryparser.repos.ProductTypeRepo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeService {

    private final ProductTypeRepo productTypeRepo;
    private final ConnectService connectService;

    @Value("${connect}")
    private String connect;

    public ProductTypeService(ProductTypeRepo productTypeRepo, ConnectService connectService) {
            this.productTypeRepo = productTypeRepo;
            this.connectService = connectService;
        }

    public void add() {
        Document page = connectService.getPage(connect);
        Element listItems = page.select("div:contains(Нетбуки & ноутбуки)").first();
        Elements product = listItems.select("div[class=top-menu-column]");
        List<ProductType> productTypes = new ArrayList<>();

        for (Element element: product) {

            if(element.select("a[href]").attr("href").contains(".html"))  {
                productTypes.add(new ProductType(
                    element.select("a[href]").first().text(),
                    "https://www.pleer.ru/" + element.select("a[href]").attr("href")));
            }
        }

        if (productTypes.size() > 0) {
            productTypeRepo.saveAll(productTypes);
            productTypes.clear();
        }
    }
}
