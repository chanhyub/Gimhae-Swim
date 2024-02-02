package com.alijas.gimhaeswim.module.referee.service;

import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.common.enums.ApplyStatus;
import com.alijas.gimhaeswim.module.referee.dto.RefereeListDTO;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.referee.repository.RefereeRepository;
import com.alijas.gimhaeswim.module.referee.request.RefereeSaveRequest;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RefereeService {

    private final RefereeRepository refereeRepository;
    private final UserRepository userRepository;

    public RefereeService(RefereeRepository refereeRepository, UserRepository userRepository) {
        this.refereeRepository = refereeRepository;
        this.userRepository = userRepository;
    }

    public List<Referee> getRefereeList() {
        return refereeRepository.findAll();
    }

    public Page<RefereeListDTO> getRefereePage(Pageable pageable) {
        Page<Referee> refereePage = refereeRepository.findAll(pageable);
        return refereePage.map(Referee::toRefereeListDTO);
    }

    public Optional<Referee> getReferee(Long id) {
        return refereeRepository.findById(id);
    }

    @Transactional
    public void delete(Referee referee) {
        refereeRepository.delete(referee);
    }

    public void addReferee(RefereeSaveRequest refereeSaveRequest){
        Optional<User> optionalUser = userRepository.findByUsername(refereeSaveRequest.username());
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
