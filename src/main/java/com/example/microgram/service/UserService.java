package com.example.microgram.service;

import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserDao userDao;

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
    public List<UserDto> getUsers(){
        List<User> users = userDao.getListUsers();
        return users.stream()
                .map(e-> buildUser(e))
                .collect(Collectors.toList());
    }
    private UserDto buildUser(User user){
        Long postQuantity = userDao.getUsersPostQuantity(user);
        Long followersQuantity = userDao.getUsersFollowerQuantity(user);
        Long followingsQuantity = userDao.getUsersFollowingQuantity(user);

        return UserDto.buildUserDTO(
                user,
                postQuantity,
                followingsQuantity,
                followersQuantity);
    }
}
