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