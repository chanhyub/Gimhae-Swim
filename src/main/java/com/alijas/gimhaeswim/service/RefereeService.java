package com.alijas.gimhaeswim.service;

import com.alijas.gimhaeswim.domain.Referee;
import com.alijas.gimhaeswim.dto.RefereeDto;
import com.alijas.gimhaeswim.repository.RefereeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RefereeService {
    private final RefereeRepository refereeRepository;

    public List<Referee> getRefereeList(){
        List<Referee> refereeList = refereeRepository.findAll();
        return refereeList;
    }

    public Referee getReferee(Integer refereeSeq){
        Referee referee = refereeRepository.findById(refereeSeq).get();
        return referee;
    }

    public String login(String name, String pwd){
        String refereeName;
        Referee referee = refereeRepository.getReferee(name, pwd);
        if(referee == null){
            refereeName = "fail";
        } else{
            refereeName = referee.getRefereeName();
        }
        return refereeName;
    }

    public void createReferee(RefereeDto refereeDto){
        Referee referee = new Referee();
        referee.setRefereeName(refereeDto.getName());
        referee.setPassword(refereeDto.getPassword());
        refereeRepository.save(referee);
    }

    public void updateReferee(Integer refereeSeq, RefereeDto refereeDto){
        Referee referee = refereeRepository.findById(refereeSeq).get();
        referee.setRefereeName(refereeDto.getName());
        referee.setPassword(refereeDto.getPassword());
        refereeRepository.save(referee);
    }

    public void deleteReferee(Integer refereeSeq){
        refereeRepository.deleteById(refereeSeq);
    }

}
