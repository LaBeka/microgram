package com.example.microgram.service;

import com.example.microgram.dao.FollowDao;
import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.FollowUserDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.Follow;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.microgram.dto.FollowUserDto.buildUserFollow;

@Service
@AllArgsConstructor
public class FollowService {
    private FollowDao followDao;
    private UserDao userDao;

    public ResultDto follow(Long userBeingFollowed, User follower){
        Optional<User> getUserBeingFollowed = userDao.userExistsID(userBeingFollowed);
        if (!getUserBeingFollowed.isPresent()) {
            return ResultDto.builder()
                    .message("There is no such user to follow!")
                    .isTrue(false)
                    .build();
        }

        Optional<Follow> iAlreadyFollow = followDao.alreadyFollowing(getUserBeingFollowed.get(), follower);

        if (iAlreadyFollow.isPresent()) {
            String message = followDao.unFollow(getUserBeingFollowed.get(), follower);
            return ResultDto.builder()
                    .message(message)
                    .isTrue(false)
                    .build();
        }

        String message = followDao.toFollow(getUserBeingFollowed.get(), follower);
        return ResultDto.builder()
                .message(message)
                .isTrue(true)
                .build();
    }

    public ResultDto unFollow(Long userBeingFollowed, User follower){
        Optional<User> getUserBeingFollowed = userDao.userExistsID(userBeingFollowed);
        if (!getUserBeingFollowed.isPresent()) {
            return ResultDto.builder()
                    .message("There is no such user to follow!")
                    .isTrue(false)
                    .build();
        }

        Optional<Follow> iAlreadyFollow = followDao.alreadyFollowing(getUserBeingFollowed.get(), follower);

        if (iAlreadyFollow.isPresent()) {
            String message = followDao.unFollow(getUserBeingFollowed.get(), follower);
            return ResultDto.builder()
                    .message(message)
                    .isTrue(false)
                    .build();
        }

        String message = followDao.toFollow(getUserBeingFollowed.get(), follower);
        return ResultDto.builder()
                .message(message)
                .isTrue(true)
                .build();
    }

    public Optional<List<FollowUserDto>> getMyFollowings(User user){
        List<User> users = followDao.getUsersFollowings(user);
        return Optional.of(users.stream()
                .map(u-> buildUserFollow(u))
                .collect(Collectors.toList()));
    }

    public Optional<List<FollowUserDto>> getMyFollowers(User user){
        List<User> users = followDao.getUsersFollowers(user);
        return Optional.of(users.stream()
                .map(u-> buildUserFollow(u))
                .collect(Collectors.toList()));
    }
}
