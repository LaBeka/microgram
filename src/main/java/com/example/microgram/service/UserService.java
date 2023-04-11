package com.example.microgram.service;

import com.example.microgram.dao.RegisterUserDao;
import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userDao.loadUserByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public HashMap<String, Object> findUserByEmail(String email){
        Optional<User> getUser = userDao.userExistsEmail(email);
        HashMap<String, Object> stringObjectHashMap = returnUserData(getUser, email);
        return stringObjectHashMap;
    }

    public HashMap<String, Object> findUserByAccount(String accountName) {
        Optional<User> getUser = userDao.userExistsAccount(accountName);
        return returnUserData(getUser, accountName);
    }

    public HashMap<String, Object> findUserByName(String name) {
        Optional<User> getUser = userDao.userExistsName(name);
        return returnUserData(getUser, name);
    }

    private HashMap<String, Object> returnUserData(Optional<User> getUser, String data) {
        HashMap<String, Object> returnData = new HashMap<>();
        if (!getUser.isPresent()) {
            ResultDto result = ResultDto.builder()
                    .message("User by '" + data + "' does not exist")
                    .build();
            returnData.put("error", result);
            return returnData;
        }
        UserDto user = buildUser(getUser.get());
                returnData.put("userFound", user);
        return returnData;
    }

    public Optional<List<UserDto>> getUsers(){
        List<User> users = userDao.getListUsers();
        return Optional.of(users.stream()
                .map(e-> buildUser(e))
                .collect(Collectors.toList()));
    }

    private UserDto buildUser(User user){
        Long postQuantity = userDao.getUsersPostQuantity(user);
        Long followersQuantity = userDao.getCountUsersFollower(user);
        Long followingsQuantity = userDao.getCountUsersFollowing(user);

        return UserDto.buildUserDTO(
                user,
                postQuantity,
                followingsQuantity,
                followersQuantity);
    }
}
