package com.sun.fintrack.income.query.repository;

import com.sun.fintrack.income.domain.Income;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {

}
