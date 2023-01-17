package lk.ijse.gdse.service.custom.impl;

import lk.ijse.gdse.dao.DaoFactory;
import lk.ijse.gdse.dao.DaoTypes;
import lk.ijse.gdse.dao.custom.BookDAO;
import lk.ijse.gdse.dao.custom.QueryDAO;
import lk.ijse.gdse.dao.exception.ConstraintViolationException;
import lk.ijse.gdse.db.DBConnection;
import lk.ijse.gdse.dto.BookDTO;
import lk.ijse.gdse.entity.Book;
import lk.ijse.gdse.service.custom.BookService;
import lk.ijse.gdse.service.exception.DuplicateException;
import lk.ijse.gdse.service.exception.InUseException;
import lk.ijse.gdse.service.exception.NotFoundException;
import lk.ijse.gdse.service.util.Convertor;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;
    private final Convertor convertor;
    private final Connection connection;

    public BookServiceImpl() {
        connection= DBConnection.getDbConnection().getConnection();
        bookDAO= DaoFactory.getInstance().getDAO(connection, DaoTypes.BOOK );
        convertor=new Convertor();
    }

    @Override
    public BookDTO saveBook(BookDTO bookDTO) throws DuplicateException {
        //need to do some business validations

        if (bookDAO.existByPk(bookDTO.getIsbn())) throw new DuplicateException("Book already saved");

        bookDAO.save(convertor.toBook(bookDTO));

        return bookDTO;


    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) throws NotFoundException {
        //business validations
        if (!bookDAO.existByPk(bookDTO.getIsbn())) throw new NotFoundException("Book not found");

        bookDAO.update(convertor.toBook(bookDTO));

        return bookDTO;
    }

    @Override
    public void deleteBook(String isbn) throws NotFoundException, InUseException {
        //business validations

        if (!bookDAO.existByPk(isbn)) throw new NotFoundException("Book not found");

        try {
            bookDAO.deleteByPk(isbn);
        }catch (ConstraintViolationException e){
            throw new InUseException("Book already in used");
        }
    }

    @Override
    public List<BookDTO> findAllBooks() {
        return bookDAO.findAll().stream().map(book -> convertor.fromBook(book)).collect(Collectors.toList());
    }

    @Override
    public BookDTO findBookByIsbn(String isbn) throws NotFoundException {
        //business validation
        Optional<Book> optionalBook = bookDAO.findByPk(isbn);
        if (!optionalBook.isPresent()) throw new NotFoundException("Book not found");

        return convertor.fromBook(optionalBook.get());
    }

    @Override
    public List<BookDTO> searchBookByText(String text) {
        return bookDAO.searchByText(text).stream().map(book -> convertor.fromBook(book)).collect(Collectors.toList());
    }
}
