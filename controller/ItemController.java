package controller;

import exception.ItemExistException;
import exception.RepoAccessEcxeption;
import model.Item;
import repository.DAOInterface;
import repository.ItemDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleg on 23.06.2019.
 */
public class ItemController {

    DAOInterface repo = new ItemDao();
    List<Item> items;
    Item item;


    public Item saveItem(Item item) throws ItemExistException, RepoAccessEcxeption {

        items = getAllItems();
        for (Item itemFromDB : items) {
            if (itemFromDB.getName().equals(item.getName())) {
                throw new ItemExistException("Item with name : " + item.getName() + " is  present in base. Try another name");
            }
        }
        return repo.saveItem(item);
    }

    public List<Item> getAllItems() throws RepoAccessEcxeption {
        return repo.getAllItems();
    }

    public Item getItemById(Long id) throws ItemExistException, RepoAccessEcxeption {

        if (checkIfIsExist(id)) {
            return repo.getItemById(id);
        }
        throw new ItemExistException("Item with id : " + item.getId() + " is not present in base");

    }

    public Item updateItem(Item item) throws ItemExistException, RepoAccessEcxeption {

        if (checkIfIsExist(item.getId())) {
            return repo.updateItem(item);
        }
        throw new ItemExistException("Item with id : " + item.getId() + " is not present in base");
    }

    public void deleteItem(Long id) throws ItemExistException, RepoAccessEcxeption {
        if (checkIfIsExist(id)) {
            repo.deleteItem(id);
        }
        throw new ItemExistException("Item with id : " + item.getId() + " is not present in base");
    }

    private boolean checkIfIsExist(Long id) throws RepoAccessEcxeption {
        items = getAllItems();
        for (Item itemFromDB : items) {
            if (itemFromDB.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
