package com.minierp.services;

<<<<<<< HEAD
import java.util.List;

import com.minierp.dao.TimetableDAO;
import com.minierp.models.Timetable;
=======
import com.minierp.dao.TimetableDAO;
import com.minierp.models.Timetable;
import java.util.List;
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb

public class TimetableService {
    private final TimetableDAO dao = new TimetableDAO();

    public List<Timetable> getAll() { return dao.findAll(); }
    public List<Timetable> getBySemesterAndCourse(int semester, int courseId) {
        return dao.findBySemesterAndCourse(semester, courseId);
    }
<<<<<<< HEAD
    public List<Timetable> getByFacultyId(int facultyId) {
        return dao.findByFacultyId(facultyId);
    }
    public boolean addEntry(Timetable t) { 
        if (!AuthService.getInstance().isAdmin()) throw new SecurityException("Admin only");
        return dao.insert(t) > 0; 
    }
    public boolean deleteEntry(int id) { 
        if (!AuthService.getInstance().isAdmin()) throw new SecurityException("Admin only");
        return dao.delete(id) > 0; 
    }
=======
    public boolean addEntry(Timetable t) { return dao.insert(t) > 0; }
    public boolean deleteEntry(int id) { return dao.delete(id) > 0; }
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
    public void clearAll() { dao.deleteAll(); }
}
