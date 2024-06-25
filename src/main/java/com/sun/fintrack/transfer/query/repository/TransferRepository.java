package com.sun.fintrack.transfer.query.repository;

import com.sun.fintrack.transfer.domain.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
