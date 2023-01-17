package lk.ijse.gdse.dao.custom.impl;

import lk.ijse.gdse.dao.custom.QueryDAO;
import lk.ijse.gdse.dao.util.DBUtil;
import lk.ijse.gdse.dto.IssueDTO;
import lk.ijse.gdse.dto.Status;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    private final Connection connection;

    public QueryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<IssueDTO> findAllIssuesByMemberId(String memberId)  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT i.issue_id,i.isbn,M.id,i.date,R.date is not null as returnStatus  FROM issue i LEFT JOIN Member M on i.memberId = M.id LEFT JOIN `Return` R on i.issue_id = R.issue_id WHERE M.id=? GROUP BY i.issue_id", memberId);
            List<IssueDTO> issueDTOList = new ArrayList<>();
            while (rst.next()){

                IssueDTO issueDTO = new IssueDTO(rst.getInt("issue_id"), rst.getString("isbn"), memberId, rst.getDate("date"), rst.getBoolean("returnStatus") ?
                        Status.RETURNED : Status.NOT_RETURNED);

                issueDTOList.add(issueDTO);
            }
            return issueDTOList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int findIssuedBooksCount(String memberId)  {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT (COUNT(i.issue_id)-COUNT(R.date)) as OrderedBooksCount FROM Member m LEFT JOIN issue i on m.id = i.memberId LEFT JOIN `Return` R on i.issue_id = R.issue_id WHERE m.id=? GROUP BY m.id", memberId);
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int availableBooksCount(String isbn) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT(b.qty-COUNT(i.issue_id)+COUNT(R.date)) as availableCount FROM Book b LEFT JOIN issue i on b.isbn = i.isbn LEFT JOIN `Return` R on i.issue_id = R.issue_id WHERE b.isbn =? GROUP BY b.isbn", isbn);
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
