package vn.iotstar.service.impl;

import vn.iotstar.dao.IUserDAO_24162121;
import vn.iotstar.dao.impl.UserDAOImpl_24162121;
import vn.iotstar.entity.Users_24162121;
import vn.iotstar.service.IUserService_24162121;

public class UserServiceImpl_24162121 implements IUserService_24162121 {
    private final IUserDAO_24162121 dao = new UserDAOImpl_24162121();

    @Override
    public Users_24162121 login(String username, String password) {
        Users_24162121 u = dao.findByUsername(username);
        if (u != null && u.getPassword() != null && u.getPassword().equals(password)
                && Boolean.TRUE.equals(u.getActive())) {
            return u;
        }
        return null;
    }

    @Override
    public Users_24162121 findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public Users_24162121 findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public boolean existsUsername(String username) {
        return dao.findByUsername(username) != null;
    }

    @Override
    public boolean existsEmail(String email) {
        return dao.findByEmail(email) != null;
    }

    @Override
    public void insert(Users_24162121 user) {
        dao.insert(user);
    }

    @Override
    public void update(Users_24162121 user) {
        dao.update(user);
    }
}
