package com.alijas.gimhaeswim.module.referee.service;

import com.alijas.gimhaeswim.module.referee.dto.RefereeListDTO;
import com.alijas.gimhaeswim.module.referee.entity.Referee;
import com.alijas.gimhaeswim.module.referee.repository.RefereeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RefereeService {

    private final RefereeRepository refereeRepository;

    public RefereeService(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
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
}
