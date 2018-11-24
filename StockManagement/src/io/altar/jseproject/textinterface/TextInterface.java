package io.altar.jseproject.textinterface;

import java.util.Scanner;
import repositories.ProductRepository;
import repositories.ShelfRepository;
import repositories.EntityRepository;
import io.altar.jseproject.model.Product;
import io.altar.jseproject.model.Shelf;
import java.util.ArrayList;
import java.util.Iterator;

public class TextInterface {
	static ProductRepository productRepository1 = ProductRepository.getInstance();
	static ShelfRepository shelfRepository1 = ShelfRepository.getInstance();
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		initialMenu();
	}

	public static void initialMenu() {
		String newKeys = "";
		do {
			System.out.println("Please choose one of the following options:");
			System.out.println("1) Show list of products");
			System.out.println("2) Show list of shelves");
			System.out.println("3) Quit");
			newKeys = sc.nextLine();

			switch (newKeys) {
			case "1":
				showProductList();
				break;
			case "2":
				showShelfList();
				break;
			case "3":
				System.out.println("Application out.");
				break;
			default:
				System.out.println(
						"ERROR!Please choose 1 to show the list of products, choose 2 to show the list of shelves or choose 3 to quit");
				break;
			}
		} while (!newKeys.equals("1") && !newKeys.equals("2") && !newKeys.equals("3"));
	}

	public static void showProductList() {
		String newKeys = "";
		do {
			System.out.println("Please choose one of the following options:");
			System.out.println("1) Create a new product");
			System.out.println("2) Edit an existing product");
			System.out.println("3) Consult the details of a certain product");
			System.out.println("4) Remove a product");
			System.out.println("5) Return to previous screen");
			newKeys = sc.nextLine();
			switch (newKeys) {
			case "1":
				createProduct();
				break;
			case "2":
				editProduct();
				break;
			case "3":
				consultProduct();
				break;
			case "4":
				removeProduct();
				break;
			case "5":
				initialMenu();
				break;
			default:
				System.out.println(
						"Invalid input! Please choose 1 to create a new product, choose 2 to edit an existing product, choose 3 to see the detail of a specific product, choose 4 to remove a product or choose 5 to return to the previous screen.");
				break;
			}
		} while (!newKeys.equals("5"));
	}

	public static void showShelfList() {
		String newKeys = "";
		do {
			System.out.println("Please choose one of the following options:");
			System.out.println("1) Create a new shelf");
			System.out.println("2) Edit an existing shelf");
			System.out.println("3) Consult the details of a certain shelf");
			System.out.println("4) Remove a shelf");
			System.out.println("5) Return to previous screen");
			newKeys = sc.nextLine();
			switch (newKeys) {
			case "1":
				createShelf();
				break;
			case "2":
				editShelf();
				break;
			case "3":
				consultShelf();
				break;
			case "4":
				removeShelf();
				break;
			case "5":
				initialMenu();
			default:
				System.out.println(
						"Invalid input! Please choose 1 to create a new shelf, choose 2 to edit an existing shelf, choose 3 to see the detail of a specific shelf, choose 4 to remove a shelf or choose 5 to return to the previous screen.");
				break;
			}
		} while (!newKeys.equals("5"));

	}

	/**
	 * createProduct is a function which receives in the console a price, a
	 * discount and a iva of a product and inserts a product with these features
	 * in the product hashmap.
	 * 
	 */
	public static void createProduct() {
		String price;
		String discount;
		String iva;
		do {
			System.out.println("Please insert price:");
			price = sc.nextLine();
			System.out.println("Please insert discount:");
			discount = sc.nextLine();
			System.out.println("Please insert iva:");
			iva = sc.nextLine();
			if (!isAValidNumber(iva, 0.0, 100.0) || !isAValidNumber(price, 0.0, 32767.0)
					|| !isAValidNumber(discount, 0.0, 100.0)) {
				System.out.println("Please insert values within a reasonable range.");
			}
		} while (!isAValidNumber(iva, "testInt", 0.0, 100.0) || !isAValidNumber(price, 0.0, 32767.0)
				|| !isAValidNumber(discount, 0.0, 100.0));
		ArrayList<Long> shelvesListAssociatedWithProduct = new ArrayList<Long>();
		Product product1 = new Product(shelvesListAssociatedWithProduct, Integer.parseInt(discount),
				Integer.parseInt(iva), Double.parseDouble(price));
		productRepository1.createEntity(product1);
		Iterator<Product> it = productRepository1.showAll();
		while (it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}

	/**
	 * createShelf is a method which receives input from console, including
	 * shelf capacity, rent price and the product id which is to be put in shelf
	 * and insert it in the shelf hash map.
	 */

	public static void createShelf() {
		String capacity1;
		String rentPrice1;
		String productIdInShelf1;
		do {
			System.out.println("Please insert shelf capacity:");
			capacity1 = sc.nextLine();
			System.out.println("Please insert a rent price:");
			rentPrice1 = sc.nextLine();
			System.out.println(
					"Please insert the product id you want to insert in this shelf(if you dont want to insert anything please press just enter:");
			productIdInShelf1 = sc.nextLine();
			if (!isAValidNumber(rentPrice1, "testDouble", 0.0, 32767.0)) {
				System.out.println("Error! Rent price provided must be a positive integer.");
			}
		} while (!isAValidNumber(rentPrice1, "testDouble", 0.0, 32767.0) || !isAValidProductIdNumber(productIdInShelf1));

		Shelf shelf1 = new Shelf(capacity1, Long.parseLong(productIdInShelf1), Integer.parseInt(rentPrice1));
		shelfRepository1.createEntity(shelf1);
		Iterator<Shelf> it = shelfRepository1.showAll();
		while (it.hasNext()) {
			System.out.println(it.next().shelfToString());
		}
		long shelf1Id = shelf1.getId();
		productRepository1.fetchEntityById(Long.parseLong(productIdInShelf1)).addNewShelfToShelvesList(shelf1Id);
	}

	/**
	 * consultProduct receives a specific id of a product and shows its features
	 * or shows all products should all be chosen
	 */
	public static void consultProduct() {
		System.out.println("Insert a specific product id to show its features or insert all to show all products:");
		String productIdSearch = sc.nextLine();
		if (productIdSearch.equals("all")) {
			Iterator<Product> it = productRepository1.showAll();
			while (it.hasNext()) {
				System.out.println(it.next().toString());
			}
		} else {
			System.out.println(productRepository1.fetchEntityById(Long.parseLong(productIdSearch)).toString());
		}
	}

	/**
	 * consultShelf receives a specific id of a shelf and shows its features or
	 * shows all shelves should all be chosen
	 */
	public static void consultShelf() {
		System.out.println("Insert a specific shelf id to show its features or insert all to show all shelves:");
		String shelfIdSearch = sc.nextLine();
		if (shelfIdSearch.equals("all")) {
			Iterator<Shelf> it = shelfRepository1.showAll();
			while (it.hasNext()) {
				System.out.println(it.next().shelfToString());
			}
		} else {
			System.out.println(shelfRepository1.fetchEntityById(Long.parseLong(shelfIdSearch)).shelfToString());
		}
	}

	/**
	 * removeProduct is a method which receives the product which is to be
	 * removed and remove it from the product repository
	 */
	/*
	 * editar a prateleira de forma a que o id do produto que foi eliminado
	 * desapareça da prateleira associada
	 */
	public static void removeProduct() {
		System.out.println("Please insert the product id you want to remove:");
		String productIdToRemove = sc.nextLine();
		System.out.println("Are you sure you want to remove the product whose id is " + productIdToRemove + "?");
		String confirmationOfProductIdToRemove = sc.nextLine();
		if (confirmationOfProductIdToRemove.equals("yes")) {
			productRepository1.deleteEntityById(Long.parseLong(productIdToRemove));
		}
	}

	/**
	 * removeShelf is a method which receives (as a input from the user) the
	 * shelf id which is to be removed and remove it from the shelf repository
	 */
	/*
	 * editar a lista das prateleiras de forma a que o id da prateleira que foi
	 * eliminada desapareça da lista de prateleiras do produto correspondente
	 */
	/* se possa pôr prateleiras vazias */
	/* funcione se o input nao for do tipo correcto */
	/* verifique se existe o id */

	public static void removeShelf() {
		System.out.println("Please insert the shelf id you want to remove:");
		String shelfIdToRemove = sc.nextLine();
		System.out.println("Are you sure you want to remove the product whose id is " + shelfIdToRemove + "?");
		String confirmationOfShelfIdToRemove = sc.nextLine();
		if (confirmationOfShelfIdToRemove.equals("yes")) {
			shelfRepository1.deleteEntityById(Long.parseLong(shelfIdToRemove));
		}
	}

	/**
	 * editProduct() is a method which receives a new discount value, a new IVA
	 * or a new price (as inputs from the user) and change these features of the
	 * referred product in the product repository
	 */
	// ?mudar editProduct de modo a que:
	/* se possa mudar a lista de prateleiras que contêm o produto */
	/* só receba valores com sentido */
	public static void editProduct() {
		System.out.println("Please insert the id of the product you want to edit:");
		String productIdToEdit = sc.nextLine();
		Product productToEdit = productRepository1.fetchEntityById(Long.parseLong(productIdToEdit));
		System.out.println("Initial discount value: " + productToEdit.getDiscount()
				+ ". Please insert a new discount value or insert the same value:");
		int updatedProductDiscount = sc.nextInt();
		productToEdit.setDiscount(updatedProductDiscount);
		System.out.println(
				"Initial IVA: " + productToEdit.getIva() + ". Please insert a new IVA or insert the same value:");
		int updatedProductIva = sc.nextInt();
		productToEdit.setIva(updatedProductIva);
		System.out.println(
				"Initial Price: " + productToEdit.getPvp() + ". Please insert a new price or insert the same value:");
		int updatedProductPvp = sc.nextInt();
		sc.nextLine();
		productToEdit.setPvp(updatedProductPvp);
		productRepository1.changeEntityById(productToEdit);
		productRepository1.showAll();
	}

	/**
	 * the method editShelf receives the id of the shelf to change, the new
	 * capacity of this shelf, the product id which is in this shelf and a new
	 * rent price (as inputs by the user) and changes the shelf in the
	 * shelfRepository1
	 */

	public static void editShelf() {
		System.out.println("Please insert the id of the shelf you want to edit");
		String shelfIdToEdit = sc.nextLine();
		Shelf shelfToEdit = shelfRepository1.fetchEntityById(Long.parseLong(shelfIdToEdit));
		System.out.println("Initial capacity: " + shelfToEdit.getCapacity()
				+ ". Please insert a new capacity or insert the same value:");
		String updatedCapacity = sc.nextLine();
		shelfToEdit.setCapacity(updatedCapacity);
		System.out.println("Initially, the the id of product which is in this shelf is "
				+ shelfToEdit.getProductIdInShelf() + ". Please insert a new product id or the same id:");
		String updatedProductIdInTheShelf = sc.nextLine();
		shelfToEdit.setProductIdInShelf(Long.parseLong(updatedProductIdInTheShelf));
		System.out.println("Initial rent price: " + shelfToEdit.getRentPrice()
				+ ". Please insert a new rent price or insert the same value:");
		String updatedRentPrice = sc.nextLine();
		shelfToEdit.setRentPrice(Integer.parseInt(updatedRentPrice));
		shelfRepository1.changeEntityById(shelfToEdit);
	}

	/**
	 * 
	 */
	public static boolean isAValidNumber(String stringToTest, String typeToTest, double minimum, double maximum) {
		try {
			switch (typeToTest) {
			case "testInt":
				Integer.parseInt(stringToTest);
				break;
			case "testDouble":
				Double.parseDouble(stringToTest);
				break;
			}
			if (Double.parseDouble(stringToTest) <= minimum || Double.parseDouble(stringToTest) >= maximum) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			return false;
		}

	}

	public static boolean isAValidProductIdNumber(String StringToTest) {
		try {
			if (productRepository1.fetchEntityById(Long.parseLong(StringToTest)) == null) {
				System.out.println("Error! Invalid Id number.");
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

	}
}
