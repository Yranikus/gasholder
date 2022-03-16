package com.example.gasholder.configuration;

import com.example.gasholder.dao.UserDao;
import com.example.gasholder.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    public UserDetailServiceImpl(UserDao userDao) {

        this.userDao = userDao;

    }

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {

        try {
            UserEntity user = userDao.findByLogin(login);
            if (user != null) {
                UserDetails userman = User.withUsername(user.getLogin()).password(user.getPassword())
                        .roles(user.getRole())
                        .build();
                return userman;
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        throw new UsernameNotFoundException(login);
    }

}
