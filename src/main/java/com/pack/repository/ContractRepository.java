package com.pack.repository;

import com.pack.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findAllByClientIDAndContractNumberAndContractStatusAndSigningDateAndPlanFinishDateAndUser(
            String clientID, String ContractNumber, String ContractStatus, String SigningDate, String PlanFinishDate, String user);
}

