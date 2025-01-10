package com.practice.loanproperties.repository;

import com.practice.loanproperties.model.LoanProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPropertiesRepository extends JpaRepository<LoanProperties, Long> {

    LoanProperties findFirstByOrderByUpdateTime();
}
