package com.tpi.ratescosts.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class GoogleMapsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMapsClient.class);
    private final RestTemplate restTemplate;
    private final String apiKey;

    public GoogleMapsClient(@Value("${google.maps.api-key:}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.apiKey = apiKey;
    }

    public RouteMetrics retrieveMetrics(double originLat, double originLng, double destinationLat, double destinationLng) {
        if (apiKey == null || apiKey.isBlank()) {
            LOGGER.warn("Google Maps API key not configured. Returning zero metrics.");
            return new RouteMetrics(0.0, 0.0);
        }
        String url = String.format(
                "https://maps.googleapis.com/maps/api/directions/json?origin=%f,%f&destination=%f,%f&key=%s",
                originLat, originLng, destinationLat, destinationLng, apiKey);
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map body = response.getBody();
            if (body == null || body.isEmpty()) {
                return new RouteMetrics(0.0, 0.0);
            }
            var routes = (java.util.List<Map<String, Object>>) body.get("routes");
            if (routes == null || routes.isEmpty()) {
                return new RouteMetrics(0.0, 0.0);
            }
            var legs = (java.util.List<Map<String, Object>>) routes.get(0).get("legs");
            if (legs == null || legs.isEmpty()) {
                return new RouteMetrics(0.0, 0.0);
            }
            Map<String, Object> leg = legs.get(0);
            Map<String, Object> distance = (Map<String, Object>) leg.get("distance");
            Map<String, Object> duration = (Map<String, Object>) leg.get("duration");
            double distanceMeters = distance != null ? ((Number) distance.get("value")).doubleValue() : 0.0;
            double durationSeconds = duration != null ? ((Number) duration.get("value")).doubleValue() : 0.0;
            return new RouteMetrics(distanceMeters / 1000.0, durationSeconds / 3600.0);
        } catch (Exception e) {
            LOGGER.error("Error calling Google Maps Directions API", e);
            return new RouteMetrics(0.0, 0.0);
        }
    }

    public record RouteMetrics(double distanceKm, double durationHours) {
    }
}
