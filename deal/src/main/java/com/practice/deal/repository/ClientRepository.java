package com.practice.deal.repository;

import com.practice.deal.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = """
    select c from Client c
    join c.passport p
    where p.series = :series and p.number = :number""")
    Client findByPassportSeriesAndNumber(@Param("series") String series, @Param("number") String number);

}
