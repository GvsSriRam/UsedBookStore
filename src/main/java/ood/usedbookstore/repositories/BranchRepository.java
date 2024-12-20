package ood.usedbookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ood.usedbookstore.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository <Branch, Long> {

}
