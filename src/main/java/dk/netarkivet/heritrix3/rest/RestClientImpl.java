package dk.netarkivet.heritrix3.rest;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Partially stolen from https://github.com/truemped/heritrix-rest-client
 */
public class RestClientImpl implements RestClient {

    private HttpClient httpClient;
    private String baseUrl;

    public RestClientImpl(HttpClient httpClient, String baseUrl) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
    }

    @Override
    public String getEngine() throws IOException {
        String url = baseUrl;
        HttpGet get = new HttpGet(url);
        get.addHeader("accept", "application/xml");
        final HttpResponse response = httpClient.execute(get);
        final HttpEntity entity = response.getEntity();
        InputStream resultIs =  entity.getContent();
        return IOUtils.toString(resultIs);
    }

    @Override
    public String getJob(String job) {
        throw new RuntimeException("not implemented");
    }
}
