package com.alijas.gimhaeswim.module;

import com.alijas.gimhaeswim.config.security.CustomUserDetails;
import com.alijas.gimhaeswim.exception.CustomException;
import com.alijas.gimhaeswim.module.competition.dto.CompetitionListDTO;
import com.alijas.gimhaeswim.module.competition.service.CompetitionService;
import com.alijas.gimhaeswim.module.team.entity.TeamMember;
import com.alijas.gimhaeswim.module.team.service.TeamMemberService;
import com.alijas.gimhaeswim.module.team.service.TeamService;
import com.alijas.gimhaeswim.module.user.entity.User;
import com.alijas.gimhaeswim.module.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;


@Controller
public class ViewController {

    private final CompetitionService competitionService;

    private final UserService userService;

    private final TeamMemberService teamMemberService;

    private final TeamService teamService;

    public ViewController(CompetitionService competitionService, UserService userService, TeamMemberService teamMemberService, TeamService teamService) {
        this.competitionService = competitionService;
        this.userService = userService;
        this.teamMemberService = teamMemberService;
        this.teamService = teamService;
    }

    @GetMapping({"/", ""})
    public String index(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 5) Pageable pageable,
            Model model,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            HttpSession session
    ) {
        Page<CompetitionListDTO> competitionListDTOS = competitionService.findAll(pageable);
        model.addAttribute("competitionList", competitionListDTOS);

        if (customUserDetails != null) {
            Optional<User> optionalUser = userService.getUser(customUserDetails.getUser().getId());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute("user", user.toUserDTO());

                Optional<TeamMember> userTeam = teamMemberService.getUserTeam(user);
                if (userTeam.isPresent()) {
                    System.out.println("TEST");
                    session.setAttribute("team", userTeam.get().toDTO());
                }
                else {
                    session.setAttribute("team", null);
                }
            }
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails != null) {
            throw new CustomException("이미 경기인으로 등록이 되어있습니다.", HttpStatus.BAD_REQUEST);
        }
        return "join";
    }
}
