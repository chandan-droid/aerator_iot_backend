package com.example.AeratorIoTBackend.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Enumeration;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    // Your Render backend base URL
    private final String targetBaseUrl = "https://aerator-iot-backend.onrender.com";

    private HttpHeaders buildHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (headerName.equalsIgnoreCase("host") ||
                headerName.equalsIgnoreCase("content-length") ||
                headerName.equalsIgnoreCase("transfer-encoding")) {
                continue; // skip problematic headers
            }
            headers.add(headerName, request.getHeader(headerName));
        }

        return headers;
    }

    private ResponseEntity<byte[]> forwardRequest(
            HttpServletRequest request,
            HttpMethod method,
            String body
    ) {
        String path = request.getRequestURI().replaceFirst("/proxy", "");
        String url = targetBaseUrl + path;

        HttpHeaders headers = buildHeaders(request);
        HttpEntity<String> entity = (body != null)
                ? new HttpEntity<>(body, headers)
                : new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                URI.create(url),
                method,
                entity,
                byte[].class
        );

        // Forward status, headers, and raw body
        HttpHeaders responseHeaders = new HttpHeaders();
        response.getHeaders().forEach((key, value) -> responseHeaders.put(key, value));

        return new ResponseEntity<>(
                response.getBody(),
                responseHeaders,
                response.getStatusCode()
        );
    }

    @GetMapping("/**")
    public ResponseEntity<byte[]> proxyGet(HttpServletRequest request) {
        return forwardRequest(request, HttpMethod.GET, null);
    }

    @PostMapping("/**")
    public ResponseEntity<byte[]> proxyPost(HttpServletRequest request, @RequestBody(required = false) String body) {
        return forwardRequest(request, HttpMethod.POST, body);
    }

    @PutMapping("/**")
    public ResponseEntity<byte[]> proxyPut(HttpServletRequest request, @RequestBody(required = false) String body) {
        return forwardRequest(request, HttpMethod.PUT, body);
    }

    @DeleteMapping("/**")
    public ResponseEntity<byte[]> proxyDelete(HttpServletRequest request) {
        return forwardRequest(request, HttpMethod.DELETE, null);
    }
}
