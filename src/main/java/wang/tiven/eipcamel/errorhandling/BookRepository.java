package wang.tiven.eipcamel.errorhandling;

public interface BookRepository {

	Iterable<Book> getAll() throws Exception;

	Book getByIsbn(String isbn) throws Exception;

}