package vn.iotstar.dao;

import vn.iotstar.entity.Users_24162121;

public interface IUserDAO_24162121 {
    Users_24162121 findByUsername(String username);
    Users_24162121 findByEmail(String email);
    void insert(Users_24162121 user);
    void update(Users_24162121 user);
}
