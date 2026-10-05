package com.example.webplatform.data.services.common;

import com.example.models.University;
import com.example.webplatform.data.entities.dto.common.GetCommonInfo;
import com.example.webplatform.data.entities.dto.common.GetContainerCommon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityService {
    public GetContainerCommon getContainerUniversity(){
        List<GetCommonInfo> universityInfos = new ArrayList<>();

        for (University u: University.values()){
            universityInfos.add(new GetCommonInfo(u.getUniversityId(), u.getTitle()));
        }

        return new GetContainerCommon(universityInfos);
    }
}
