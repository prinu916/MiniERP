package com.minierp.controllers;

<<<<<<< HEAD
import java.util.List;

import com.minierp.ai.TimetableGeneratorAI;
import com.minierp.models.Timetable;
import com.minierp.services.TimetableService;
=======
import com.minierp.services.TimetableService;
import com.minierp.ai.TimetableGeneratorAI;
import com.minierp.models.Timetable;
import java.util.List;
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb

public class TimetableController {
    private final TimetableService service = new TimetableService();
    private final TimetableGeneratorAI ai = new TimetableGeneratorAI();

    public List<Timetable> getAll() { return service.getAll(); }
    public List<Timetable> getBySemesterAndCourse(int semester, int courseId) {
        return service.getBySemesterAndCourse(semester, courseId);
    }
<<<<<<< HEAD
    public List<Timetable> getByFacultyId(int facultyId) {
        return service.getByFacultyId(facultyId);
    }
=======
>>>>>>> 5174977120bda675bfdcbe4c15dac73ac972c0cb
    public boolean add(Timetable t) { return service.addEntry(t); }
    public boolean delete(int id) { return service.deleteEntry(id); }

    public List<Timetable> generateWithAI(int courseId, int semester) {
        return ai.generate(courseId, semester);
    }

    public void saveGeneratedTimetable(List<Timetable> list) {
        for (Timetable t : list) service.addEntry(t);
    }
}
