package lk.ijse.gdse.service.custom;

import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.dto.ReturnDTO;
import lk.ijse.gdse.service.SuperService;
import lk.ijse.gdse.service.exception.NotFoundException;

import java.util.List;

public interface IssueService extends SuperService {

    public IssueDTO createIssue(IssueDTO issueDTO);

    public IssueDTO updateIssue(IssueDTO issueDTO) throws NotFoundException;

    public IssueDTO findByIssueId(int issueId) throws NotFoundException;

    public List<IssueDTO> findAllIssues();

    public ReturnDTO returnIssue(int issueId) throws NotFoundException;
}
