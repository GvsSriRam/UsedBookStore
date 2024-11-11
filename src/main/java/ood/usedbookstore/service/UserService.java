package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.Role;
import ood.usedbookstore.model.User;
import ood.usedbookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    @Override
    public boolean isAuthorized(String suid, Set<Role> roles) throws EntityNotFoundException {
        User user = getUserBySUID(suid);

        for (Role role : roles) {
            if (user.getRoles().contains(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAuthorized(Long id, Set<Role> roles) throws EntityNotFoundException {
        User user = getUserById(id);

        for (Role role : roles) {
            if (user.getRoles().contains(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAuthorized(User user, Set<Role> roles){
        for (Role role : roles) {
            if (user.getRoles().contains(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAdmin(User user) {
        return user.getRoles().contains(Role.ADMIN);
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
        return user.getRoles().contains(Role.FULL_TIME_EMPLOYEE) || isAdmin(user);
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
        return user.getRoles().contains(Role.PART_TIME_EMPLOYEE) || isAdmin(user) || isFullTimeEmployee(user);
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
        return user.getRoles().contains(Role.STUDENT);
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
