package lk.ijse.gdse.service.custom;

import lk.ijse.gdse.dto.BookDTO;
import lk.ijse.gdse.service.SuperService;
import lk.ijse.gdse.service.exception.DuplicateException;
import lk.ijse.gdse.service.exception.InUseException;
import lk.ijse.gdse.service.exception.NotFoundException;

import java.util.List;

public interface BookService extends SuperService {
    public BookDTO saveBook(BookDTO bookDTO) throws DuplicateException;

    public BookDTO updateBook(BookDTO bookDTO)  throws NotFoundException;

    public void deleteBook(String isbn) throws NotFoundException, InUseException;

    public List<BookDTO> findAllBooks();

    public BookDTO findBookByIsbn(String isbn) throws NotFoundException;

    public List<BookDTO> searchBookByText(String text) ;
}
