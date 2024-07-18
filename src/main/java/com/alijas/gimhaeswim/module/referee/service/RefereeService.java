package com.alijas.gimhaeswim.module.referee.service;

import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.referee.dto.RefereeListDTO;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.referee.repository.RefereeRepository;
import com.alijas.gimhaeswim.module.referee.request.RefereeSaveRequest;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.enums.UserStatus;
import com.alijas.gimhaeswim.module.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RefereeService {

    private final RefereeRepository refereeRepository;
    private final UserRepository userRepository;

    public RefereeService(RefereeRepository refereeRepository, UserRepository userRepository) {
        this.refereeRepository = refereeRepository;
        this.userRepository = userRepository;
    }

    public List<Referee> getRefereeList() {
        List<Referee> refereeList = refereeRepository.findAllByStatus(ApplyStatus.APPROVED);
        if (refereeList.isEmpty()) return refereeList;
        else return refereeList.stream().filter(referee -> referee.getUser().getStatus().equals(UserStatus.ACTIVE)).toList();

    }

    public Page<RefereeListDTO> getRefereePage(Pageable pageable) {
        Page<Referee> refereePage = refereeRepository.findAllByStatus(pageable, ApplyStatus.APPROVED);
        return refereePage.map(Referee::toRefereeListDTO);
    }

    public Optional<Referee> getReferee(Long id) {
        return refereeRepository.findByIdAndStatus(id, ApplyStatus.APPROVED);
    }

    @Transactional
    public void delete(Referee referee, User user) {
        referee.setStatus(ApplyStatus.REJECTED);
        user.setStatus(UserStatus.DELETED);
        refereeRepository.save(referee);
        userRepository.save(user);
    }

    @Transactional
    public void addReferee(RefereeSaveRequest refereeSaveRequest){
        Optional<User> optionalUser = userRepository.findByUsernameAndStatus(refereeSaveRequest.username(), UserStatus.ACTIVE);
        if(optionalUser.isPresent()){
            throw new CustomException("이미 사용중인 아이디입니다.", HttpStatus.BAD_REQUEST);
        }

        User savedUser = userRepository.save(refereeSaveRequest.toEntity());
        Referee referee = Referee.builder()
                .user(savedUser)
                .status(ApplyStatus.APPROVED)
                .build();
        refereeRepository.save(referee);
    }
}
