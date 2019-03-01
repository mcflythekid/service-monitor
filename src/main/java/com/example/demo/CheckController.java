package com.example.demo;

import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;
import static org.apache.commons.lang3.StringEscapeUtils.escapeJson;

@RestController
public class CheckController {

    private static String pingMsg(String address){
        String output = "";
        try {
            String s;
            List<String> commands = new ArrayList<>();
            commands.add("ping");
            commands.add("-c 4");
            commands.add(address);
            ProcessBuilder processbuilder = new ProcessBuilder(commands);
            Process process = processbuilder.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = stdInput.readLine()) != null) {
                output += s + "\n";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(output);
        return output;
    }


    private static Float pingPercent(String address){
        final String regex = "\\s([0_9\\.])\\%\\s";
        Pattern myPattern = Pattern.compile(regex);
        Matcher regexMatcher = myPattern.matcher(pingMsg(address));
        if (regexMatcher.find()){
            return 100F - Float.valueOf(regexMatcher.group(1));
        }
        return 0F;
    }

    public static void main(String[] args) {
        System.out.println(pingPercent("lazylearn.com"));
    }


    public static final String SUCCESS = "<span class='success'>Online</span>";
    public static final String ERROR = "<span class='error'>Error</span>";

    @CrossOrigin(origins = "*")
    @GetMapping("/ping")
    public String ss(@RequestParam("address") String addressString) throws IOException {


        Float result = pingPercent(addressString);
        if (result > 0){
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
