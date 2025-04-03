package com.example.demo.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.restaurant.entity.Zone;

import java.util.UUID;

public interface ZoneRepository extends JpaRepository<Zone, UUID> {

}