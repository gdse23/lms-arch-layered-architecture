package lk.ijse.gdse.service.custom.impl;

import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.dto.MemberDTO;
import lk.ijse.gdse.service.custom.MemberService;
import lk.ijse.gdse.service.exception.DuplicateException;
import lk.ijse.gdse.service.exception.InUseException;
import lk.ijse.gdse.service.exception.NotFoundException;

import java.util.List;

public class MemberServiceImpl implements MemberService {
    @Override
    public MemberDTO saveMember(MemberDTO memberDTO) throws DuplicateException {
        return null;
    }

    @Override
    public MemberDTO updateMember(MemberDTO memberDTO) throws NotFoundException {
        return null;
    }

    @Override
    public void deleteMember(String memberId) throws NotFoundException, InUseException {

    }

    @Override
    public List<MemberDTO> findAllMembers() {
        return null;
    }

    @Override
    public MemberDTO findByMemberId(String memberId) throws NotFoundException {
        return null;
    }

    @Override
    public List<IssueDTO> findAllIssuesByMemberId() {
        return null;
    }

    @Override
    public List<MemberDTO> searchMembersByText(String text) {
        return null;
    }
}
