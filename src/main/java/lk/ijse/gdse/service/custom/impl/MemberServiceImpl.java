package lk.ijse.gdse.service.custom.impl;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.DaoTypes;
import lk.ijse.gdse.dao.custom.IssueDAO;
import lk.ijse.gdse.dao.custom.MemberDAO;
import lk.ijse.gdse.dao.custom.QueryDAO;
import lk.ijse.gdse.dao.custom.ReturnDAO;
import lk.ijse.gdse.dao.util.DBUtil;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.dto.MemberDTO;
import lk.ijse.gdse.dto.Status;
import lk.ijse.gdse.entity.Member;
import lk.ijse.gdse.service.custom.MemberService;
import lk.ijse.gdse.service.exception.DuplicateException;
import lk.ijse.gdse.service.exception.InUseException;
import lk.ijse.gdse.service.exception.NotFoundException;
import lk.ijse.gdse.service.util.Convertor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MemberServiceImpl implements MemberService {

    private final Connection connection;

    private final MemberDAO memberDAO;

    private final QueryDAO queryDAO;

    private final IssueDAO issueDAO;

    private final ReturnDAO returnDAO;


    private final Convertor convertor;

    public MemberServiceImpl() {
        connection= DBConnection.getDbConnection().getConnection();
        memberDAO= DaoFactory.getInstance().getDAO(connection, DaoTypes.MEMBER);
        queryDAO=DaoFactory.getInstance().getDAO(connection,DaoTypes.QUERY);
        returnDAO= DaoFactory.getInstance().getDAO(connection,DaoTypes.RETURN);
        issueDAO =DaoFactory.getInstance().getDAO(connection,DaoTypes.ISSUE);
        convertor=new Convertor();
    }

    @Override
    public MemberDTO saveMember(MemberDTO memberDTO) throws DuplicateException {
        //business validation

        if (memberDAO.existByPk(memberDTO.getId())) throw new DuplicateException("Member already saved!");

        memberDAO.save(convertor.toMember(memberDTO));

        return memberDTO;
    }

    @Override
    public MemberDTO updateMember(MemberDTO memberDTO) throws NotFoundException {

        if (!memberDAO.existByPk(memberDTO.getId())) throw new NotFoundException("Member not found!");

        memberDAO.update(convertor.toMember(memberDTO));
        return memberDTO;
    }

    @Override
    public void deleteMember(String memberId) throws NotFoundException, InUseException {

        //business validations

        if (!memberDAO.existByPk(memberId)) throw new NotFoundException("Member not found!");

        if (queryDAO.findIssuedBooksCount(memberId)>0) throw new InUseException("Member has already borrowed some books");

        //lets apply database transactions

        try {
            connection.setAutoCommit(false);

            List<IssueDTO> returnList = queryDAO.findAllIssuesByMemberId(memberId).stream().filter(issueDTO -> issueDTO.getStatus().equals(Status.RETURNED)).collect(Collectors.toList());

            for (IssueDTO issueDTO : returnList) {
                returnDAO.deleteByPk(issueDTO.getIssueId());
            }

            queryDAO.findAllIssuesByMemberId(memberId).stream().forEach(issueDTO -> issueDAO.deleteByPk(issueDTO.getIssueId()));


            memberDAO.deleteByPk(memberId);

            connection.commit();
        }catch (Throwable t){
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public List<MemberDTO> findAllMembers() {
         return memberDAO.findAll().stream().map(member -> convertor.fromMember(member)).collect(Collectors.toList());
    }

    @Override
    public MemberDTO findByMemberId(String memberId) throws NotFoundException {

        if (!memberDAO.existByPk(memberId)) throw new NotFoundException("Member not found!");


        return convertor.fromMember(memberDAO.findByPk(memberId).get());
    }

    @Override
    public List<IssueDTO> findAllIssuesByMemberId(String memberId) {
        return queryDAO.findAllIssuesByMemberId(memberId);
    }

    @Override
    public List<MemberDTO> searchMembersByText(String text) {
        return memberDAO.searchMembersByText(text).stream().map(member -> convertor.fromMember(member)).collect(Collectors.toList());

    }


}
