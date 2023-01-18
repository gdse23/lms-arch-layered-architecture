package lk.ijse.gdse.service.custom.impl;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.DaoTypes;
import lk.ijse.gdse.dao.custom.IssueDAO;
import lk.ijse.gdse.dao.custom.QueryDAO;
import lk.ijse.gdse.dao.custom.ReturnDAO;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.dto.ReturnDTO;
import lk.ijse.gdse.entity.Issue;
import lk.ijse.gdse.entity.Return;
import lk.ijse.gdse.service.custom.IssueService;
import lk.ijse.gdse.service.exception.NotFoundException;
import lk.ijse.gdse.service.util.Convertor;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class IssueServiceImpl implements IssueService {

    private final Connection connection;

    private final IssueDAO issueDAO;

    private final ReturnDAO returnDAO;

    private final QueryDAO queryDAO;

    private final Convertor convertor;

    public IssueServiceImpl() {
        connection= DBConnection.getDbConnection().getConnection();
        issueDAO= DaoFactory.getInstance().getDAO(connection, DaoTypes.ISSUE);
        returnDAO=DaoFactory.getInstance().getDAO(connection,DaoTypes.RETURN);
        queryDAO=DaoFactory.getInstance().getDAO(connection,DaoTypes.QUERY);
        convertor=new Convertor();
    }

    @Override
    public IssueDTO createIssue(IssueDTO issueDTO) {
        if (queryDAO.availableBooksCount(issueDTO.getIsbn())<1) throw new NotFoundException("All books are issued for given isbn");

        if (queryDAO.findIssuedBooksCount(issueDTO.getMemberId())>=3) throw new NotFoundException("Member has already borrowed more than 3 books");

        Issue savedIssue = issueDAO.save(convertor.toIssue(issueDTO));

        return convertor.fromIssue(savedIssue);
    }

    @Override
    public IssueDTO updateIssue(IssueDTO issueDTO) throws NotFoundException {
        if (!issueDAO.existByPk(issueDTO.getIssueId())) throw new NotFoundException("No issue record found for given issue id");

        issueDAO.update(convertor.toIssue(issueDTO));

        return issueDTO;
    }

    @Override
    public IssueDTO findByIssueId(int issueId) throws NotFoundException {
        if (!issueDAO.existByPk(issueId)) throw new NotFoundException("No issue record found for given issue id");

        return convertor.fromIssue(issueDAO.findByPk(issueId).get());
    }

    @Override
    public List<IssueDTO> findAllIssues() {
        return issueDAO.findAll().stream().map(issue -> convertor.fromIssue(issue)).collect(Collectors.toList());
    }

    @Override
    public ReturnDTO returnIssue(int issueId) throws NotFoundException {
        if (!issueDAO.existByPk(issueId)) throw new NotFoundException("No issue record found for given issue id");

        Return savedReturn = returnDAO.save(new Return(issueId, Date.valueOf(LocalDate.now())));

        return convertor.fromReturn(savedReturn);
    }

    @Override
    public List<ReturnDTO> findAllReturns() {
        return returnDAO.findAll().stream().map(aReturn -> convertor.fromReturn(aReturn)).collect(Collectors.toList());
    }
}
