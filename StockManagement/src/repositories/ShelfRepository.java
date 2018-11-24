package repositories;
import io.altar.jseproject.model.Shelf;

public class ShelfRepository extends EntityRepository <Shelf> {
	private final static ShelfRepository INSTANCE = new ShelfRepository();
	public static ShelfRepository getInstance() {
		return INSTANCE;
	}
private ShelfRepository() {}
}



