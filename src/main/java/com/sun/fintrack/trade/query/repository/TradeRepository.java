package com.sun.fintrack.trade.query.repository;

import com.sun.fintrack.trade.domain.Trade;
import com.sun.fintrack.trade.domain.enums.TradeType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {

  Optional<Trade> findByTradeSeqAndType(Long tradeSeq, TradeType type);
}
