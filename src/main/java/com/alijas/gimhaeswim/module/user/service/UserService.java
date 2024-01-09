package com.alijas.gimhaeswim.module.user.service;

import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.competition.entity.Competition;
import com.alijas.gimhaeswim.module.competition.repository.CompetitionEventRepository;
import com.alijas.gimhaeswim.module.competition.repository.CompetitionRepository;
import com.alijas.gimhaeswim.module.competition.request.CompetitionSaveRequest;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.repository.TeamMemberRepository;
import com.alijas.gimhaeswim.module.user.dto.UserAdminDTO;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.enums.UserStatus;
import com.alijas.gimhaeswim.module.user.repository.UserRepository;
import com.alijas.gimhaeswim.module.user.request.UserSaveRequest;
import com.alijas.gimhaeswim.module.user.request.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final TeamMemberRepository teamMemberRepository;

    public UserService(UserRepository userRepository, TeamMemberRepository teamMemberRepository) {
        this.userRepository = userRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User saveUser(UserSaveRequest userSaveRequest) {
        User user = userSaveRequest.toEntity();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(userSaveRequest.password());
        System.out.println("ENCODE :: " + encode);
        user.setPassword(encode);

        return userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user, UserUpdateRequest userUpdateRequest) {
        user.updateUser(userUpdateRequest);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserStatus(User user) {
        user.setApplyStatus(ApplyStatus.APPROVED);
        userRepository.save(user);
    }

    public Page<UserAdminDTO> getUserList(Pageable pageable, ApplyStatus applyStatus, UserStatus userStatus) {
        Page<User> page = userRepository.findAllByApplyStatusAndStatus(pageable, applyStatus, userStatus);


        Page<UserAdminDTO> map = page.map(User::toUserAdminDTO);

        map.forEach(user -> {
            Optional<User> byId = userRepository.findById(user.getId());
            Optional<TeamMember> byUser = teamMemberRepository.findByUser(byId.get());
            if (byUser.isPresent()) {
                user.setTeamName(byUser.get().getTeam().getTeamName());
            } else {
                user.setTeamName("미가입");
            }
        });

        return map;
    }

    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
