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

    // Base URL of your Render backend
    private final String targetBaseUrl = "https://aerator-iot-backend.onrender.com";

    private HttpHeaders buildHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    // Handle GET requests
    @GetMapping("/**")
    public ResponseEntity<String> proxyGet(HttpServletRequest request) {
        String path = request.getRequestURI().replaceFirst("/proxy", "");
        String url = targetBaseUrl + path;

        HttpEntity<String> entity = new HttpEntity<>(buildHeaders(request));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    // Handle POST requests
    @PostMapping("/**")
    public ResponseEntity<String> proxyPost(HttpServletRequest request, @RequestBody String body) {
        String path = request.getRequestURI().replaceFirst("/proxy", "");
        String url = targetBaseUrl + path;

        HttpHeaders headers = buildHeaders(request);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    // Handle PUT requests
    @PutMapping("/**")
    public ResponseEntity<String> proxyPut(HttpServletRequest request, @RequestBody String body) {
        String path = request.getRequestURI().replaceFirst("/proxy", "");
        String url = targetBaseUrl + path;

        HttpHeaders headers = buildHeaders(request);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    // Handle DELETE requests
    @DeleteMapping("/**")
    public ResponseEntity<String> proxyDelete(HttpServletRequest request) {
        String path = request.getRequestURI().replaceFirst("/proxy", "");
        String url = targetBaseUrl + path;

        HttpEntity<String> entity = new HttpEntity<>(buildHeaders(request));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
