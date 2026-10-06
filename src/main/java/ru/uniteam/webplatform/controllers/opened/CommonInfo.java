package ru.uniteam.webplatform.controllers.opened;

import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.webplatform.controllers.CommonPrefix;
import ru.uniteam.webplatform.data.entities.dto.common.GetCommonInfo;
import ru.uniteam.webplatform.data.entities.dto.common.GetContainerCommon;
import ru.uniteam.webplatform.data.entities.dto.security.ApiResponse;
import ru.uniteam.webplatform.data.services.common.SkillService;
import ru.uniteam.webplatform.data.services.common.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CommonPrefix.PREFIX + "info")
public class CommonInfo {
    private final UniversityService universityService;
    private final SkillService skillService;
    @Autowired
    public CommonInfo(UniversityService service, SkillService skillService){
        this.universityService = service;
        this.skillService = skillService;
    }

    @GetMapping("/universities")
    public GetContainerCommon getUniversities(){
        return universityService.getContainerUniversity();
    }

    @GetMapping("/universities/{id}")
    public ResponseEntity<?> getUniversityInfoById(@PathVariable int id){
        University u = University.findById(id);
        if (u == null){
            return ResponseEntity.status(404).body(new ApiResponse("University with id=" + id + " not found"));
        }
        return ResponseEntity.ok(new GetCommonInfo(id, u.getTitle()));
    }

    @GetMapping("/skills")
    public GetContainerCommon getSkills(){
        return skillService.getSkills();
    }

    @GetMapping("/skills/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable int id){
        Skill skill = Skill.searchSkillById(id);

        if (skill == null){
            return ResponseEntity.status(404).body(new ApiResponse("Skill with id=" + id + " not found"));
        }

        return ResponseEntity.ok(new GetCommonInfo(id, skill.getTitle()));
    }
}
