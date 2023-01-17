package lk.ijse.gdse.service.custom;

import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.dto.MemberDTO;
import lk.ijse.gdse.entity.Member;
import lk.ijse.gdse.service.SuperService;
import lk.ijse.gdse.service.exception.DuplicateException;
import lk.ijse.gdse.service.exception.InUseException;
import lk.ijse.gdse.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface MemberService extends SuperService {

    public MemberDTO saveMember(MemberDTO memberDTO) throws DuplicateException;

    public MemberDTO updateMember(MemberDTO memberDTO) throws NotFoundException;

    public void deleteMember(String memberId) throws NotFoundException, InUseException, SQLException, ClassNotFoundException;

    public List<MemberDTO> findAllMembers() ;

    public MemberDTO findByMemberId(String memberId) throws NotFoundException;

    public List<IssueDTO> findAllIssuesByMemberId(String memberId);

    public List<MemberDTO> searchMembersByText(String text);
}
