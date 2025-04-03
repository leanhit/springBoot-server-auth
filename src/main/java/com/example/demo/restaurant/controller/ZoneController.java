package com.example.demo.restaurant.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.demo.restaurant.dto.ZoneDto;
import com.example.demo.restaurant.dto.request.CreateZoneRequest;
import com.example.demo.restaurant.dto.response.CollectionResponse;
import com.example.demo.restaurant.service.ZoneService;
import com.example.demo.restaurant.support.PageUtils;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping("")
    @PageableAsQueryParam
    public ResponseEntity<CollectionResponse<ZoneDto>> getZones(@Parameter(hidden = true) Pageable pageable) {
        Page<ZoneDto> zones = zoneService.findAllZones(pageable);
        return ResponseEntity.ok(CollectionResponse.of(zones.getContent(), PageUtils.asPageMetadata(zones)));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ZoneDto> createZone(@RequestBody CreateZoneRequest zoneDto) {
        ZoneDto zone = zoneService.createZone(zoneDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(zone.getId()).toUri();

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location.toString()).body(zone);
    }

    @GetMapping("/{id}")
    public ZoneDto getZone(@PathVariable UUID id) {
        return zoneService.findById(id);
    }

    @PutMapping("/{id}")
    public ZoneDto updateZone(@PathVariable UUID id, @RequestBody ZoneDto zoneDto) {
        zoneDto.setId(id);
        return zoneService.updateZone(zoneDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZone(@PathVariable UUID id) {
        zoneService.deleteZone(id);
    }

    @PostMapping("/{id}/tables")
    public ResponseEntity<ZoneDto> createTable(@PathVariable UUID id, @RequestBody List<UUID> tableIds) {

        ZoneDto zone = zoneService.addTables(id, tableIds);

        return ResponseEntity.ok(zone);
    }

}
