package com.example.microgram.service;

import com.example.microgram.dao.FollowDao;
import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.FollowDto;
import com.example.microgram.dto.ResultDto;
import com.example.microgram.entity.Follow;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FollowService {
    private FollowDao followDao;
    private UserDao userDao;

    public ResultDto follow(FollowDto data){
        Optional<User> getUserToFollower = userDao.userExistsID(data.getUserBeingFollowed());
        if (!getUserToFollower.isPresent()) {
            return ResultDto.builder()
                    .message("There is no such user to follow!")
                    .build();
        }
        Optional<User> getUserFollowing = userDao.userExistsID(data.getUserFollowing());
        if (!getUserFollowing.isPresent()) {
            return ResultDto.builder()
                    .message("User not found!")
                    .build();
        }
        Optional<Follow> getFollow = followDao.getIdenticalFollow(data);
        if (getFollow.isPresent()) {
            return ResultDto.builder()
                    .message("This user is already following another user!")
                    .build();
        }

        String answer = followDao.toFollow(data);
        return ResultDto.builder()
            .message(answer)
            .build();
    }

    public ResultDto unfollow(FollowDto data){
        Optional<User> getUserToFollower = userDao.userExistsID(data.getUserBeingFollowed());
        if (!getUserToFollower.isPresent()) {
            return ResultDto.builder()
                    .message("There is no such user to follow!")
                    .build();
        }
        Optional<User> getUserFollowing = userDao.userExistsID(data.getUserFollowing());
        if (!getUserFollowing.isPresent()) {
            return ResultDto.builder()
                    .message("User not found!")
                    .build();
        }
        Optional<Follow> getFollow = followDao.getIdenticalFollow(data);
        if (!getFollow.isPresent()) {
            return ResultDto.builder()
                    .message("The follow does not exist!")
                    .build();
        }
        String answer = followDao.unFollow(getFollow.get());
        return ResultDto.builder()
                .message(answer)
                .build();
    }
}
