package com.sun.fintrack.asset.query.repository;

import com.sun.fintrack.asset.domain.Asset;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
