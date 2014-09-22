package dk.netarkivet.heritrix3.rest;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

public class RestClientImplTest {

    /**
     * This test uses a keystore containing the h3 self-signed key.
     * @throws Exception
     */
    @org.testng.annotations.Test
    public void testGetEngineWithKeystore() throws Exception {
        int port = 8443;
        String hostname = "pc609.sb.statsbiblioteket.dk";
        String userName = "admin";
        String password = "admin";
        KeyStore trust;
        SSLSocketFactory socketFactory;
        Scheme sch;
        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        trust = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream instream = getClass().getClassLoader().getResourceAsStream("test_keystore.jks") ;
        trust.load(instream, "111111".toCharArray());
        socketFactory =  new SSLSocketFactory(trust);
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
        sch = new Scheme("https", socketFactory, port);
        DefaultHttpClient client = new DefaultHttpClient();
        client.getConnectionManager().getSchemeRegistry().register(sch);
        client.getCredentialsProvider().setCredentials(new AuthScope(hostname, port),
                new UsernamePasswordCredentials(userName, password));

        String baseUrl = "https://" + hostname + ":" + Integer.toString(port) + "/engine/";
        RestClient restClient = new RestClientImpl(client, baseUrl);
        System.out.println(restClient.getEngine());

    }

    /**
     * In this test, we connect to the h3 instance using a TrustManager that accepts any certificate, so there is no
     * need for a keystore.
     * @throws Exception
     */
    @org.testng.annotations.Test
      public void testGetEngineWithoutKeystore() throws Exception {
          int port = 8443;
          String hostname = "pc609.sb.statsbiblioteket.dk";
          String userName = "admin";
          String password = "admin";
          // Create a trust manager that does not validate certificate chains
          TrustManager[] trustAllCerts = new TrustManager[] {
              new X509TrustManager() {
                  public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                      return new X509Certificate[0];
                  }
                  public void checkClientTrusted(
                      java.security.cert.X509Certificate[] certs, String authType) {
                      }
                  public void checkServerTrusted(
                      java.security.cert.X509Certificate[] certs, String authType) {
                  }
              }
          };
          SSLSocketFactory socketFactory;
          Scheme sch;
          HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
          SSLContext sc = SSLContext.getInstance("SSL");
          sc.init(null, trustAllCerts, new java.security.SecureRandom());
          socketFactory = new SSLSocketFactory(sc);
          socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
          sch = new Scheme("https", socketFactory, port);
          DefaultHttpClient client = new DefaultHttpClient();
          client.getConnectionManager().getSchemeRegistry().register(sch);
          client.getCredentialsProvider().setCredentials(new AuthScope(hostname, port),
                  new UsernamePasswordCredentials(userName, password));
          String baseUrl = "https://" + hostname + ":" + Integer.toString(port) + "/engine/";
          RestClient restClient = new RestClientImpl(client, baseUrl);
          System.out.println(restClient.getEngine());
      }

}
