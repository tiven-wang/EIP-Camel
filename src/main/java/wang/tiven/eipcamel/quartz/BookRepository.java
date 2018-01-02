package wang.tiven.eipcamel.quartz;

public interface BookRepository {

	Iterable<Book> getAll();

	Book getByIsbn(String isbn);
	
	void check();
}