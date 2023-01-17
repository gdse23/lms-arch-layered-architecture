package lk.ijse.gdse.service.util;

import lk.ijse.gdse.dto.BookDTO;
import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.dto.MemberDTO;
import lk.ijse.gdse.dto.ReturnDTO;
import lk.ijse.gdse.entity.Book;
import lk.ijse.gdse.entity.Issue;
import lk.ijse.gdse.entity.Member;
import lk.ijse.gdse.entity.Return;

public class Convertor {

    public MemberDTO fromMember(Member member){
        return new MemberDTO(member.getId(),member.getName(),member.getAddress(),member.getContact());
    }

    public Member toMember(MemberDTO memberDTO){
        return new Member(memberDTO.getId(),memberDTO.getName(),memberDTO.getAddress(),memberDTO.getContact());
    }

    public BookDTO fromBook(Book book){
        return new BookDTO(book.getIsbn(),book.getTitle(),book.getAuthor(),book.getQty());
    }

    public Book toBook(BookDTO bookDTO){
        return new Book(bookDTO.getIsbn(),bookDTO.getTitle(),bookDTO.getAuthor(),bookDTO.getQty());
    }

    public IssueDTO fromIssue(Issue issue){
        return new IssueDTO(issue.getIssueId(),issue.getIsbn(),issue.getMemberId(),issue.getDate());
    }

    public Issue toIssue(IssueDTO issueDTO){
        return new Issue(issueDTO.getIssueId(),issueDTO.getIsbn(), issueDTO.getMemberId(),issueDTO.getDate());
    }

    public ReturnDTO fromReturn(Return returnEntity){
        return new ReturnDTO(returnEntity.getIssueId(),returnEntity.getDate());
    }

    public Return toReturn(ReturnDTO returnDTO){
        return new Return(returnDTO.getIssueId(),returnDTO.getDate());
    }



}
