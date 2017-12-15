package wang.tiven.eipcamel.testing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("bookRepository")
public class SimpleBookRepository implements BookRepository {
	
	@Override
	public Iterable<Book> getAll() {
		List<Book> books = new ArrayList<Book>();
		for (int i = 1; i < 10; i++) {
			books.add(new Book(String.valueOf(i), "This is book " + i));
		}
		return books;
	}
	
	@Override
	public Book getByIsbn(String isbn) {
		return new Book(isbn, "This is book "+isbn);
	}

}
