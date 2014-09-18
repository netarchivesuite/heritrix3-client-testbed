package dk.netarkivet.heritrix3.rest;


import java.io.IOException;

public interface RestClient {

    String getEngine() throws IOException;

    String getJob(String job);

}
