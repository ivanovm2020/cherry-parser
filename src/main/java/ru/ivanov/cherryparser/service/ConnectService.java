package ru.ivanov.cherryparser.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ConnectService {
    @Value("${proxy.ip}")
    private String proxyIp;
    @Value("${proxy.port}")
    private String proxyPort;
    @Value("${user.agent}")
    private String userAgent;
    @Value("${referrer}")
    private String referrer;

    private String connect;

    public Document getPage(String connect) {
        this.connect = connect;
        Document page = null;
        try {
            page = Jsoup.connect(connect)
                    .proxy(proxyIp, Integer.parseInt(proxyPort))
                    .userAgent(userAgent)
                    .timeout(0)
                    .referrer(referrer)
                    .header("Content-Language", "en-US")
                    .get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        };

        return page;
    }
}
