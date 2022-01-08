import org.junit.Before;
import org.junit.Test;
import java.util.*;
import util.*;

import static org.junit.Assert.*;

public class OrganizationTest {
    IterableOrganization daemon;
    IterableOrganization ion;
    @Before
    public void setup() {
        DataManager baseManager = new DataManager();
        daemon = baseManager.base_Source("input.txt");

        ion = new OrgImplement("Mehmet",5000);
        ion.addEmployee("Yunus",2000,"Mehmet");
        ion.addEmployee("Hakan",3000,"Mehmet");
        ion.addEmployee("Ayse",1000,"Yunus");

    }

    @Test
    public void testGetSize() {
        assertEquals(12, daemon.NumberOfEmployees());

        assertEquals(4, ion.NumberOfEmployees());
    }

    @Test
    public void testAllEmployees() {
        List<String> actualResult = daemon.EmployeesOfCompany();
        String expected = "[Mustafa Turksever," +
                " Halil Sengonca, Ugur Guclu, Emre Kosar," +
                " Ahmet Egeli, Sedat Tunc, Bora Kuzey," +
                " Oguz Demir, Onder Bati, Erdem Altin," +
                " Mehmet Bilir, Bahar Karaoglan]";
        assertEquals(expected,actualResult.toString());

        actualResult = ion.EmployeesOfCompany();
        expected = "[Mehmet, Yunus, Ayse, Hakan]";
        assertEquals(expected,actualResult.toString());
    }

    @Test
    public void testprintEmployees() {
        daemon.print("Halil Sengonca");
    }

    @Test
    public void testGetCost() {
        double expected = 15500.0;
        double actual = daemon.getCost("Halil Sengonca");

        assertEquals(expected,actual,0.0);
    }

    @Test
    public void testIterator() {
        List<String> names = new ArrayList<String>();
        for (IEmployee e: daemon) {
            names.add(e.getName());
        }
        Set<String> actual = new HashSet<String>(names);
        Set<String> expected = new HashSet<String>(daemon.EmployeesOfCompany());

        assertEquals(expected,actual);
    }
}