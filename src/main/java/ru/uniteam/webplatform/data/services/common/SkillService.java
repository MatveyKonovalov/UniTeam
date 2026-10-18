package ru.uniteam.webplatform.data.services.common;

import ru.uniteam.models.Skill;
import ru.uniteam.webplatform.data.entities.dto.common.GetCommonInfo;
import ru.uniteam.webplatform.data.entities.dto.common.GetContainerCommon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillService {
    public GetContainerCommon getSkills(){
        List<GetCommonInfo> skills = new ArrayList<>();

        for (Skill skill: Skill.values()){
            skills.add(new GetCommonInfo(skill.getId(), skill.getTitle()));
        }
        return new GetContainerCommon(skills);
    }
}
