package fr.cyu.coffeeclasses.vanilla.database.dao;

import fr.cyu.coffeeclasses.vanilla.entity.user.Administrator;

public class AdministratorDAO extends GenericDAO<Administrator> {
	// Singleton
	private static final AdministratorDAO INSTANCE = new AdministratorDAO();
	private AdministratorDAO() {
		super(Administrator.class);
	}
	public static AdministratorDAO getInstance() {
		return INSTANCE;
	}
}