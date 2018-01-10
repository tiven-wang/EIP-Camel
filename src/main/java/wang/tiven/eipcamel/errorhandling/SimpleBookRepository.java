package wang.tiven.eipcamel.errorhandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component("bookRepository")
public class SimpleBookRepository implements BookRepository {
	private static Logger logger = LoggerFactory.getLogger(SimpleBookRepository.class);
	
	@Override
	public Iterable<Book> getAll() throws Exception {
		int i = new Random().nextInt(10);
		if(i > 8) {
			logger.debug("We got all books! "+i);
			List<Book> books = new ArrayList<Book>();
			for (int k = 1; k < 10; k++) {
				books.add(new Book(String.valueOf(k), "This is book " + k));
			}
			return books;
		}else if(i > 3) {
			logger.debug("We can't service now! " + i);
			throw new Exception("System is bussy! " + i);
		}else {
			logger.debug("Service is error now! " + i);
			throw new ServiceError("System is error! " + i);
		}
	}
	
	@Override
	public Book getByIsbn(String isbn) throws Exception {
		int i = new Random().nextInt(10);
		if(i > 5) {
			logger.debug("We got the book! "+i);
			return new Book(isbn, "This is book "+isbn);
		}else {
			logger.debug("We can't service now! " + i);
			throw new Exception("System is bussy! " + i);
		}
	}

}
