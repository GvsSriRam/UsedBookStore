package ood.usedbookstore.service;

import ood.usedbookstore.exceptions.EntityNotFoundException;
import ood.usedbookstore.model.User;

public interface UserServiceInterface {
    void save(User user);

    User getUserById(Long id) throws EntityNotFoundException;
    User getUserBySUID(String suid) throws EntityNotFoundException;

    boolean existsById(Long id);

    boolean isAdmin(User user);

    boolean isAdmin(Long id) throws EntityNotFoundException;

    boolean isAdmin(String suid) throws EntityNotFoundException;

    boolean isFullTimeEmployee(User user);

    boolean isFullTimeEmployee(Long id) throws EntityNotFoundException;

    boolean isFullTimeEmployee(String suid) throws EntityNotFoundException;

    boolean isEmployee(User user);

    boolean isEmployee(Long id) throws EntityNotFoundException;

    boolean isEmployee(String suid) throws EntityNotFoundException;

    boolean isStudent(User user);

    boolean isStudent(Long id) throws EntityNotFoundException;

    boolean isStudent(String suid) throws EntityNotFoundException;
}
