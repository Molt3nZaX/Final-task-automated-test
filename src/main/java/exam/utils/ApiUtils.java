package exam.utils;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public class ApiUtils {
    public static HttpRequestWithBody postRequest(String url) {
        return Unirest.post(url);
    }
}