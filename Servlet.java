/**
 * Created by oleg on 02.07.2019.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.ItemController;
import exception.ItemExistException;
import exception.RepoAccessEcxeption;
import model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = "/items")
public class Servlet extends HttpServlet {
    ItemController itemController = new ItemController();
    Item item = new Item();


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch (action) {
            case "list":
                // http://localhost:8080/items?action=list
                try {
                    resp.getWriter().print(itemController.getAllItems());
                } catch (RepoAccessEcxeption e) {
                    resp.setStatus(500);
                    resp.getWriter().print("Error" + e.getMessage());
                }
            case "getById":
                //  http://localhost:8080/items?action=getById&id=3011
                try {
                    resp.getWriter().print(itemController.getItemById(Long.parseLong(req.getParameter("id"))));
                } catch (ItemExistException e) {
                    resp.getWriter().print("Item with id " + req.getParameter("id") + " not found");
                } catch (RepoAccessEcxeption e) {
                    resp.setStatus(500);
                    resp.getWriter().print("Error" + e.getMessage());
                }
            case "save":
                doPost(req, resp);
            case "update":
                //  http://localhost:8080/items?action=update
                doPut(req, resp);
            case "delete":
                //  http://localhost:8080/items?action=delete&id=3042
                try {
                    itemController.deleteItem(Long.parseLong(req.getParameter("id")));
                    doGet( req, resp);
                } catch (ItemExistException e) {
                    resp.getWriter().print("Item with id " + req.getParameter("id") + " not found");
                } catch (RepoAccessEcxeption e) {
                    resp.setStatus(500);
                    resp.getWriter().print("Error" + e.getMessage());
                }
            default:
                try {
                    resp.getWriter().print(itemController.getAllItems());
                } catch (RepoAccessEcxeption e) {
                    resp.setStatus(500);
                    resp.getWriter().print("Error" + e.getMessage());                }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        item = mappingItemFromJSONInStringPresentation(req);
        try {
            itemController.updateItem(item);
            resp.getWriter().println("Item  updated.");
        } catch (ItemExistException e) {
            resp.setStatus(500);
            resp.getWriter().print("Item with id " + item.getId() + " is not found  in Data Base.");
        } catch (RepoAccessEcxeption e) {
            resp.setStatus(500);
            resp.getWriter().print("Error" + e.getMessage());        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        item = mappingItemFromJSONInStringPresentation(req);
        try {
            itemController.saveItem(item);
            resp.getWriter().println("Item  saved.");
        } catch (ItemExistException e) {
            resp.setStatus(500);
            resp.getWriter().print("Item with name " + item.getName() + " is already present in Data Base.");
        } catch (RepoAccessEcxeption e) {
            resp.setStatus(500);
            resp.getWriter().print("Error" + e.getMessage());        }
    }

    private Item mappingItemFromJSONInStringPresentation(HttpServletRequest req) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        String line;

        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return item = objectMapper.readValue(stringBuilder.toString(), Item.class);
    }
}
