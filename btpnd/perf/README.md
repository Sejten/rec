To run test type in bash $mvn gatling:test<br>
<br>
Test is located /src/test/scala/PerfTest.scala<br>
Test reports are located in /results<br>
I had to limit the number of users to 100 because the service could not keep up<br>
Test is explained in comments in the file.<br>
The test affected the web application - response times changed from about 200ms to about 3182ms.<br>
![](/home/piotrs/rec/btpnd/perf/response_time.jpg)<br>
The optimal response time for modern web services is the time in which the user either does not perceive or does not notice. I think it is about a second, but it depends on the service.

## Request
HTTP request: <br>
```GET https://jsonplaceholder.typicode.com/todos/1/``` <br>

```
HTTP response:
status:
200 OK
```
```
headers:
Date: Tue, 18 Jan 2022 20:15:35 GMT
Content-Type: application/json; charset=utf-8
Transfer-Encoding: chunked
Connection: keep-alive
x-powered-by: Express
x-ratelimit-limit: 1000
x-ratelimit-remaining: 998
x-ratelimit-reset: 1642534435
vary: Origin, Accept-Encoding
access-control-allow-credentials: true
cache-control: max-age=43200
pragma: no-cache
expires: -1
x-content-type-options: nosniff
etag: W/"53-hfEnumeNh6YirfjyjaujcOPPT+s"
via: 1.1 vegur
CF-Cache-Status: HIT
Age: 2529
Expect-CT: max-age=604800, report-uri="https://report-uri.cloudflare.com/cdn-cgi/beacon/expect-ct"
Report-To: {"endpoints":[{"url":"https:\/\/a.nel.cloudflare.com\/report\/v3?s=txGIhMkX2XzO2sGpED2hMkTO4OdjZ%2BUpGhfkjxulybQTDmXUAjsKRt4SI6x5Nys%2BK16sc0jAW1VdHiVzmwiOObxfcbigTeqKGmPkSQ%2FPB5YQuSPFgFta8waj6m0uiaVvT1S1MM2Zc9D5t%2B760uan"}],"group":"cf-nel","max_age":604800}
NEL: {"success_fraction":0,"report_to":"cf-nel","max_age":604800}
Server: cloudflare
CF-RAY: 6cfa7f044fa3ff00-MAD
alt-svc: h3=":443"; ma=86400, h3-29=":443"; ma=86400
content-encoding: br
```
```
body:
{
"userId": 1,
"id": 1,
"title": "delectus aut autem",
"completed": false
}
```