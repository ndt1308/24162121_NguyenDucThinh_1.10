package vn.iotstar.service;

import vn.iotstar.entity.Users_24162121;

public interface IUserService_24162121 {
    Users_24162121 login(String username, String password);
    Users_24162121 findByUsername(String username);
    Users_24162121 findByEmail(String email);
    boolean existsUsername(String username);
    boolean existsEmail(String email);
    void insert(Users_24162121 user);
    void update(Users_24162121 user);
}
