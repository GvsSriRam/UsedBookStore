package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.Branch;

public interface BranchServiceInterface {
    Branch addBranch(Branch branch) throws EntityNotFoundException;

    Branch removeBranch(Long id) throws EntityNotFoundException;

    Branch getBranchById(Long id) throws EntityNotFoundException;
}
