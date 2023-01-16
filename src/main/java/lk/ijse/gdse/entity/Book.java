package lk.ijse.gdse.entity;

import java.io.Serializable;

public class Book implements SuperEntity {
    private String isbn;
    private String title;
    private String author;
    private int qty;


    public Book(){
    }

    public Book(String isbn, String title, String author, int qty) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.qty = qty;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", qty=" + qty +
                '}';
    }
}
