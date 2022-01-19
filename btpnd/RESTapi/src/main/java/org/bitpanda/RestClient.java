package org.bitpanda;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class RestClient {
    protected URL baseUrl;

    private final List<Filter> filters = new ArrayList<>();

    public <T extends RestClient> RestClient(URL url) {
        this.baseUrl = url;
    }

    public Response post(String path, Object body, Headers headers) {
        return given()
                .log()
                .all()
                .baseUri(baseUrl.toString())
                .headers(filterHeaders(headers))
                .contentType(getContentType(headers))
                .body(body)
                .post(path)
                .andReturn();
    }

    public Response put(String path, Object body, Headers headers) {
        return given()
                .log()
                .all()
                .baseUri(baseUrl.toString())
                .headers(filterHeaders(headers))
                .contentType(getContentType(headers))
                .body(body)
                .put(path)
                .andReturn();
    }

    public Response get(String path, Boolean followRedirects) {
        return get(path, new Headers(), followRedirects);
    }

    public Response get(String path, Headers headers, Boolean followRedirects) {
        return given()
                .log()
                .all()
                .spec(getSpecification(followRedirects))
                .baseUri(baseUrl.toString())
                .headers(headers)
                .get(path)
                .andReturn();
    }

    public Response delete(String path, Headers headers, Boolean followRedirects) {
        return given()
                .log()
                .all()
                .spec(getSpecification(followRedirects))
                .baseUri(baseUrl.toString())
                .headers(headers)
                .delete(path)
                .andReturn();
    }

    public Response delete(String path, Boolean followRedirects) {
        return get(path, new Headers(), followRedirects);
    }

    protected RequestSpecification getSpecification(Boolean followRedirects) {
        return new RequestSpecBuilder()
                .setConfig(RestAssuredConfig
                        .newConfig()
                        .redirect(RedirectConfig
                                .redirectConfig()
                                .followRedirects(followRedirects)))
                .build();
    }

    protected String getContentType(Headers headers) {
        return headers.hasHeaderWithName("Content-Type")
                ? headers.getValue("Content-Type")
                : "application/json";
    }

    protected Headers filterHeaders(Headers headers) {
        return new Headers(headers.asList().stream().filter(h -> !h.getName().equals("Content-Type")).collect(Collectors.toList()));
    }
}
