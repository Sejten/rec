package org.bitpanda;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class RestServiceTest {
    RestClient rc;
    Headers headers;

    @Before
    public void setUp() throws MalformedURLException {
        String baseUrl = "http://jsonplaceholder.typicode.com";
        String port = "80";
        rc = new RestClient(new URL(baseUrl + ":" + port));
        headers = new Headers(
                new Header("Accept", "application/json;charset=utf-8"),
                new Header("Content-Type", "application/json;charset=UTF-8")
        );
    }

    @Test
    public void getAndValidateUsers() throws MalformedURLException {
        // given
        //when
        Response r = rc.get("users", headers, false);
        //then
        r.then().log().all().assertThat().statusCode(HttpStatus.SC_OK);
        assertThat(r.jsonPath().getJsonObject("[0].name"), is("Leanne Graham"));
    }

    @Test
    public void getNotExistingEndpoint() throws MalformedURLException {
        // given
        //when
        Response r = rc.get("xxx", headers, false);
        //then
        r.then().log().all().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void createResource() throws MalformedURLException {
        // given
        String json = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
        //when
        Response r = rc.post("posts", json, headers);
        //then
        r.then().log().all().assertThat().statusCode(HttpStatus.SC_CREATED);
    }


    @Test
    public void updateResource() throws MalformedURLException {
        // given
        String json = "    {\"id\":1,\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
        //when
        Response r = rc.put("posts/1", json, headers);
        //then
        r.then().log().all().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteResource() throws MalformedURLException {
        // given
        //when
        Response r = rc.delete("posts/1", false);
        //then
        r.then().log().all().assertThat().statusCode(HttpStatus.SC_OK);
    }
}
