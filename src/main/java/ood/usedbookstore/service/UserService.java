package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.Role;
import ood.usedbookstore.model.User;
import ood.usedbookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User getUserBySUID(String suid) throws EntityNotFoundException {
        return userRepository.findBySuid(suid)
                .orElseThrow(() -> new EntityNotFoundException("User with SUID " + suid + " not found"));
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    private static boolean hasRole(User user, Role role) {
        return user.getRoles().contains(role);
    }

    @Override
    public boolean isAdmin(User user) {
        return hasRole(user, Role.ADMIN);
    }

    @Override
    public boolean isAdmin(Long id) throws EntityNotFoundException {
        return isAdmin(getUserById(id));
    }

    @Override
    public boolean isAdmin(String suid) throws EntityNotFoundException {
        return isAdmin(getUserBySUID(suid));
    }

    @Override
    public boolean isFullTimeEmployee(User user) {
        return hasRole(user, Role.FULL_TIME_EMPLOYEE) || isAdmin(user);
    }

    @Override
    public boolean isFullTimeEmployee(Long id) throws EntityNotFoundException {
        return isFullTimeEmployee(getUserById(id));
    }

    @Override
    public boolean isFullTimeEmployee(String suid) throws EntityNotFoundException {
        return isFullTimeEmployee(getUserBySUID(suid));
    }

    @Override
    public boolean isEmployee(User user) {
        return hasRole(user, Role.PART_TIME_EMPLOYEE) || isAdmin(user) || isFullTimeEmployee(user);
    }

    @Override
    public boolean isEmployee(Long id) throws EntityNotFoundException {
        return isEmployee(getUserById(id));
    }

    @Override
    public boolean isEmployee(String suid) throws EntityNotFoundException {
        return isEmployee(getUserBySUID(suid));
    }

    @Override
    public boolean isStudent(User user) {
        return hasRole(user, Role.STUDENT);
    }

    @Override
    public boolean isStudent(Long id) throws EntityNotFoundException {
        return isStudent(getUserById(id));
    }

    @Override
    public boolean isStudent(String suid) throws EntityNotFoundException {
        return isStudent(getUserBySUID(suid));
    }

}
