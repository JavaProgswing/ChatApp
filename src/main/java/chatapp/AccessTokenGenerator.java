//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package chatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import static chatapp.DataExtractor.BASE_URL;

public class AccessTokenGenerator {
    private static String access_token;
    private static String name;
    private static StringBuffer password;
    private static boolean isValidAccessToken = false;
    private static int last_status_code = -1;
    private static HttpURLConnection last_request_content = null;

    public AccessTokenGenerator() {
    }

    public AccessTokenGenerator(String name, StringBuffer password) throws IOException {
        AccessTokenGenerator.name = name;
        AccessTokenGenerator.password = password;
        final String url = String.format("%s/login/users/%s?password=%s", BASE_URL, URLEncoder.encode(name, StandardCharsets.UTF_8), URLEncoder.encode(String.valueOf(password), StandardCharsets.UTF_8));
        Response access_token_resp = Requests.get(url);
        last_status_code = access_token_resp.status_code;
        last_request_content = access_token_resp.requestContent;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        try {
            System.out.println(url);
            System.out.println(access_token_resp.status_code);
            System.out.println(access_token_resp.content);
            Authorization auth = gson.fromJson(access_token_resp.content, Authorization.class);
            access_token = auth.getAccess_token();
            isValidAccessToken = true;
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        CompletableFuture.runAsync(() -> {
            while (true) {
                try {
                    Thread.sleep(1800000L);
                } catch (InterruptedException var3) {
                    InterruptedException e = var3;
                    e.printStackTrace();
                }

                try {
                    regenerateAccess_token(password);
                } catch (IOException var2) {
                    IOException ex = var2;
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void regenerateAccess_token(StringBuffer password) throws IOException {
        AccessTokenGenerator.password = password;
        Response access_token_resp = Requests.get(String.format("%s/users/login/%s?password=%s", BASE_URL, name, password));
        last_status_code = access_token_resp.status_code;
        last_request_content = access_token_resp.requestContent;
        if (access_token_resp.status_code == 200) {
            isValidAccessToken = true;
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Authorization auth = (Authorization) gson.fromJson(access_token_resp.content, Authorization.class);
            access_token = auth.getAccess_token();
        }

    }

    public static String getAccess_token() {
        return access_token;
    }

    public static String getName() {
        return name;
    }

    public static StringBuffer getPassword() {
        return password;
    }

    public static boolean isValidAccessToken() {
        return isValidAccessToken;
    }

    public static int getLast_status_code() {
        return last_status_code;
    }

    public static HttpURLConnection getLast_request_content() {
        return last_request_content;
    }
}
