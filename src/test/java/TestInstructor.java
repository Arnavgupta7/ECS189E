/**
 * Created by Arnav Gupta on 3/8/2017.
 */

import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestInstructor {
    private IAdmin admin;
    private IStudent student;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
        this.admin.createClass("Name", 2018, "Arnav", 5);
        this.admin.createClass("Name", 2017, "Arnav", 5);

        this.student.registerForClass("Hisname", "Name", 2018);
        this.student.registerForClass("Hername", "Name", 2018);
        this.student.registerForClass("Hisname", "Name", 2017);

        this.instructor.addHomework("Arnav", "Name",2017, "firstHomework", "Description");
        this.student.submitHomework("Hisname", "firstHomeork", "Answer", "Name", 2017);
    }

    @Test
    public void testMakeClass() {
        // A proper class that is created
        assertTrue(this.admin.classExists("Name", 2018));
    }
    @Test
    public void testMakeClass1() {
        // A proper class that is created and homework added
        this.instructor.addHomework("Arnav", "Name",2017, "firstHomework", "Description");
        assertTrue(this.instructor.homeworkExists("Name",2017,"firstHomework"));
    }
    @Test
    public void testWrongInstructor() {
        // Wrong instructor trying to add homework
        this.instructor.addHomework("WrongInstructor", "Name",2017, "secondHomework", "Description");
        assertFalse(this.instructor.homeworkExists("Name",2017,"secondHomework"));
    }
    @Test
    public void AssignHomeworkToNonExistentClass() {
        //assign homework to a class that does not exist

        this.instructor.assignGrade("WrongInstructor","Name",2020,"firstHomework","Hisname", 2);
        assertNull(this.instructor.getGrade("Name",2020,"firstHomework","Hisname"));
    }

    @Test
    public void testWrongInstructor2() {
        //wrong instructor trying to assign grade

        this.instructor.assignGrade("WrongInstructor","Name",2017,"firstHomework","Hisname", 2);
        assertNull(this.instructor.getGrade("Name",2017,"firstHomework","Hisname"));
    }

    @Test
    public void testCorrectAssignGrade() {
        this.instructor.assignGrade("Arnav","Name",2017,"firstHomework","Hisname", 100);
        assertEquals((long)this.instructor.getGrade("Name",2017,"firstHomework","Hisname"),100);
    }

    @Test
    public void testMissingStudent() {
        //instructor assigning grade to student not in class
        this.instructor.assignGrade("Arnav","Name",2017,"firstHomework","Hername", 2);
       // assertEquals((long)this.instructor.getGrade("Name",2017,"firstHomework","Hername"), 2);
        assertNull(this.instructor.getGrade("Name",2017,"firstHomework","Hername"));

    }
    @Test
    public void testMissingHomework() {
        //instructor assigning grade for a homework that is not assigned to the class
        this.instructor.assignGrade("Arnav","Name",2017,"secondHomework","Hisname", 2);
        assertNull(this.instructor.getGrade("Name",2017,"secondHomework","Hisname"));
    }

    @Test
    public void testUnsubmittedHomeworkGrade() {
        //instructor giving grade to a student that has not submitted the homework
        this.student.registerForClass("Hername", "Name", 2017);
        this.instructor.assignGrade("Arnav","Name",2017,"firstHomework","Hername", 90);
        assertNull(this.instructor.getGrade("Name",2017,"firstHomework","Hername"));

    }
    @Test
    public void testNegativeGrade() {
        //instructor trying to assign a grade lower than 0

        this.instructor.assignGrade("Arnav","Name",2017,"firstHomework","Hisname", -90);
        assertNull(this.instructor.getGrade("Name",2017,"firstHomework","Hisname"));
    }
}
