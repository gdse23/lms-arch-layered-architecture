package lk.ijse.gdse.service.custom.impl;

import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.dto.ReturnDTO;
import lk.ijse.gdse.service.custom.IssueService;
import lk.ijse.gdse.service.exception.NotFoundException;

import java.util.List;

public class IssueServiceImpl implements IssueService {
    @Override
    public IssueDTO createIssue(IssueDTO issueDTO) {
        return null;
    }

    @Override
    public IssueDTO updateIssue(IssueDTO issueDTO) throws NotFoundException {
        return null;
    }

    @Override
    public IssueDTO findByIssueId(int issueId) throws NotFoundException {
        return null;
    }

    @Override
    public List<IssueDTO> findAllIssues() {
        return null;
    }

    @Override
    public ReturnDTO returnIssue(int issueId) throws NotFoundException {
        return null;
    }
}
