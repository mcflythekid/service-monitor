package com.example.demo;

import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.net.InetAddress;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

@RestController
public class CheckController {

    public static final String SUCCESS = "<span class='success'>Online</span>";
    public static final String ERROR = "<span class='error'>Error</span>";

    @CrossOrigin(origins = "*")
    @GetMapping("/ping")
    public String ss(@RequestParam("address") String addressString) throws IOException {


        InetAddress address = InetAddress.getByName(addressString);
        if (address.isReachable(10000)){
            return SUCCESS;
        }
        return ERROR;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/url")
    public String url(@RequestBody UrlDto payload)  {

        String msg;
        String html = "";
        try {
            html = Jsoup.connect(payload.getUrl()).validateTLSCertificates(false).timeout(3000).get().text();
            if (html.contains(payload.getTest())){
                msg = SUCCESS;
            } else {
                msg = ERROR;
            }
        } catch (Exception e){
            e.printStackTrace();
            msg = ERROR;
            html = e.getMessage();
        }

        if (html.length() > 100){
            html = html.substring(0, 99);
        }

        return msg + "<br>" + escapeHtml4(html);
    }
}
