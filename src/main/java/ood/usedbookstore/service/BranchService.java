package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.Branch;
import ood.usedbookstore.repositories.BranchRepository;
import org.springframework.stereotype.Service;

@Service("{BranchService")
public class BranchService implements BranchServiceInterface {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch addBranch(Branch branch) {
        return branchRepository.saveAndFlush(branch);
    }

    @Override
    public Branch removeBranch(Long id) throws EntityNotFoundException {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch with ID " + id + " not found"));
        branchRepository.delete(branch);
        return branch;
    }

    @Override
    public Branch getBranchById(Long id) throws EntityNotFoundException {
        return branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch with ID " + id + " not found"));
    }
}
