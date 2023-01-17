package lk.ijse.gdse.service.custom.impl;

import lk.ijse.gdse.dto.BookDTO;
import lk.ijse.gdse.service.custom.BookService;
import lk.ijse.gdse.service.exception.DuplicateException;
import lk.ijse.gdse.service.exception.InUseException;
import lk.ijse.gdse.service.exception.NotFoundException;

import java.util.List;

public class BookServiceImpl implements BookService {
    @Override
    public BookDTO saveBook(BookDTO bookDTO) throws DuplicateException {
        return null;
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) throws NotFoundException {
        return null;
    }

    @Override
    public void deleteBook(String isbn) throws NotFoundException, InUseException {

    }

    @Override
    public List<BookDTO> findAllBooks() {
        return null;
    }

    @Override
    public BookDTO findBookByIsbn(String isbn) throws NotFoundException {
        return null;
    }

    @Override
    public List<BookDTO> searchBookByText(String text) {
        return null;
    }
}
