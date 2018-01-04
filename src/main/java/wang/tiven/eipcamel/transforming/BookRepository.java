package wang.tiven.eipcamel.transforming;

public interface BookRepository {

	Iterable<Book> getAll();

	Book getByIsbn(String isbn);

}