package com.tpi.ratescosts.client;

import com.tpi.ratescosts.dto.TruckDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trucks-service", url = "${clients.trucks.url:http://trucks-depots-service:8083}")
public interface TruckServiceClient {

    @GetMapping("/api/trucks/{id}")
    TruckDto getTruck(@PathVariable("id") Long id);
}
