import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Arnav on 3/7/2017.
 */
public class TestAdmin {

    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.admin.createClass("Name", 2018, "Arnav", 5);
        this.student.registerForClass("Hisname", "Name", 2018);
        this.student.registerForClass("Hername", "Name", 2018);

    }

    @Test
    public void testMakeClass() {
        // A proper class that is created
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    @Test
    public void testMakeNegativeYearClass() {
        //Negative year
        this.admin.createClass("Test", -017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", -017));
    }

    @Test
    public void testPastClass() {
        // A class cannot be in the past
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    public void testUniquePairs() {
        //CLass with the same name and year pair should not be allowed
        this.admin.createClass("Name", 2018, "Instructor", 5);
        assertNotEquals(this.admin.getClassInstructor("Name", 2018), "Instructor");
    }

    @Test
    public void testMoreThanTwoClasses() {
        //no instructor can be assigned to more than 2 courses per year
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Tester", 2017, "Instructor", 15);
        this.admin.createClass("Testers", 2017, "Instructor", 15);

        assertFalse(this.admin.classExists("Testers", 2017));
    }
    @Test
    public void testGreaterThanZeroCapacity() {
        //Maximum capacity of the class should be greater than 0
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }

    /////////////////////// changeCapacity///////////////////

    @Test
    public void testCorrectChange() {
        // Testing that the class capacity is change to more than enrolled students.
        this.admin.changeCapacity("Name", 2018, 3);
        assertEquals(this.admin.getClassCapacity("Name", 2018), 3);
    }

    @Test
    public void testCapacityChangeLessThanEnrolled() {
        //checking class capacity is changed to less than the enrolled students.
        this.admin.changeCapacity("Name", 2018, 1);
        assertEquals(this.admin.getClassCapacity("Name", 2018), 5);
    }

}

