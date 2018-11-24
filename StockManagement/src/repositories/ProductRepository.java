package repositories;



import io.altar.jseproject.model.Product;

public class ProductRepository extends EntityRepository<Product> {

	private final static ProductRepository INSTANCE = new ProductRepository();

	public static ProductRepository getInstance() {
		return INSTANCE;
	}

	private ProductRepository(){}
}
