package umc.SukBakJi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.SukBakJi.domain.model.dto.InterestTopicsDTO;
import umc.SukBakJi.domain.model.dto.LabDetailResponseDTO;
import umc.SukBakJi.domain.model.dto.LabResponseDTO;
import umc.SukBakJi.domain.model.entity.Lab;
import umc.SukBakJi.domain.model.entity.Member;
import umc.SukBakJi.domain.model.entity.ResearchTopic;
import umc.SukBakJi.domain.repository.LabRepository;
import umc.SukBakJi.domain.repository.MemberRepository;
import umc.SukBakJi.global.apiPayload.code.status.ErrorStatus;
import umc.SukBakJi.global.apiPayload.exception.GeneralException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabService {

    private final LabRepository labRepository;

    @Autowired
    public LabService(LabRepository labRepository) {
        this.labRepository = labRepository;
    }

    @Autowired
    private MemberRepository memberRepository;

    public List<LabResponseDTO> searchLabsByTopicName(String topicName) {
        List<Lab> labs = labRepository.findLabsByResearchTopicName(topicName);
        return labs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private LabResponseDTO convertToDTO(Lab lab) {
        LabResponseDTO dto = new LabResponseDTO();
        dto.setLabId(lab.getId());
        dto.setUniversityName(lab.getUniversityName());
        dto.setDepartment(lab.getDepartment());
        dto.setProfessorName(lab.getProfessorName());
        dto.setResearchTopics(lab.getResearchTopics().stream().map(rt -> rt.getTopicName()).collect(Collectors.toList()));
        return dto;
    }

    public LabDetailResponseDTO getLabDetail(Long labId) {
        Lab lab = labRepository.findById(labId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LAB_NOT_FOUND));

        List<String> researchTopics = lab.getResearchTopics().stream()
                .map(ResearchTopic::getTopicName)
                .collect(Collectors.toList());

        return new LabDetailResponseDTO(
                lab.getProfessorName(),
                lab.getUniversityName(),
                lab.getDepartment(),
                lab.getProfessorAcademic(),
                lab.getProfessorProfile(),
                lab.getLabLink(),
                researchTopics
        );
    }

    public InterestTopicsDTO getInterestTopics(Member member) {
        List<String> topics = member.getMemberResearchTopics()
                .stream()
                .map(memberResearchTopic -> "#" + memberResearchTopic.getResearchTopic().getTopicName())
                .collect(Collectors.toList());

        return InterestTopicsDTO.builder()
                .topics(topics)
                .build();
    }
}
