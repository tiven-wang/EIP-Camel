package wang.tiven.eipcamel.testing;

public interface BookRepository {

	Iterable<Book> getAll();

	Book getByIsbn(String isbn);

}