CREATE TABLE Member (
                        id VARCHAR(36) PRIMARY KEY ,
                        name VARCHAR(256) NOT NULL ,
                        address VARCHAR(256) NOT NULL ,
                        contact VARCHAR(256) NOT NULL
);

CREATE TABLE Book (
                      isbn VARCHAR(256) PRIMARY KEY ,
                      title VARCHAR(256) NOT NULL ,
                      author VARCHAR(256) NOT NULL ,
                      qty INT NOT NULL DEFAULT 1
);

CREATE TABLE `issue` (
                         issue_id INT AUTO_INCREMENT PRIMARY KEY ,
                         isbn VARCHAR(256) NOT NULL ,
                         memberId VARCHAR(36) NOT NULL ,
                         date DATE NOT NULL ,
                         CONSTRAINT fk_isbn FOREIGN KEY (isbn) REFERENCES Book(isbn),
                         CONSTRAINT fk_member_id FOREIGN KEY (memberId) REFERENCES Member(id)
);

CREATE TABLE  `Return` (
                           issue_id INT PRIMARY KEY ,
                           date DATE NOT NULL ,
                           CONSTRAINT fk_issue_id FOREIGN KEY (issue_id) REFERENCES issue(issue_id)
);

/* Join query to find issued count , return count and Rest Count*/

SELECT m.id AS Member_Id,
       COUNT(i.issue_id) AS Issued_Count ,
       COUNT(R.issue_id) AS Return_Count,
       (COUNT(i.issue_id)-COUNT(R.issue_id))  AS Rest_Count
        FROM Member m LEFT JOIN issue i on m.id = i.memberId
        LEFT JOIN `Return` R on i.issue_id = R.issue_id
        GROUP BY m.id;


/*Join Query to find all issued items for a given member ID*/
SELECT M.id AS memberId,i.issue_id AS issueId,i.date AS issedDate,(R.date IS NULL ) AS returnStatus FROM issue i LEFT JOIN `Return` R on i.issue_id = R.issue_id LEFT JOIN  Member M on i.memberId = M.id WHERE M.id=?;


SELECT (COUNT(i.issue_id)-COUNT(R.date)) as OrderedBooksCount FROM Member m LEFT JOIN issue i on m.id = i.memberId LEFT JOIN `Return` R on i.issue_id = R.issue_id WHERE m.id=? GROUP BY m.id;