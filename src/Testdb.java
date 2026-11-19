/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author X1 YOGA
 */
import java.sql.SQLException;
public class Testdb {
public static void main(String[] args) throws Exception {

    EXempleJdbc test = new EXempleJdbc();

    test.loadDriver();

    // CREATE
    test.create("Amrani", "Sara", 21);
    test.create("Laouy", "Asma", 23);
    test.create("Ali", "Ahmed", 25);

  
    // AFFICHER
    test.listPersons();
}
} 
