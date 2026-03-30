package com.minierp.dao;

import com.minierp.database.QueryExecutor;
import com.minierp.models.Faculty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FacultyDAO {

    public List<Faculty> findAll() {
        List<Map<String, Object>> rows = QueryExecutor.executeQuery(
            "SELECT * FROM faculty ORDER BY name");
        List<Faculty> list = new ArrayList<>();
        for (Map<String, Object> row : rows) list.add(map(row));
        return list;
    }

    public Faculty findById(int id) {
        List<Map<String, Object>> rows = QueryExecutor.executeQuery(
            "SELECT * FROM faculty WHERE id = ?", id);
        return rows.isEmpty() ? null : map(rows.get(0));
    }

    public Faculty findByUserId(int userId) {
        List<Map<String, Object>> rows = QueryExecutor.executeQuery(
            "SELECT * FROM faculty WHERE user_id = ?", userId);
        return rows.isEmpty() ? null : map(rows.get(0));
    }

    public long insert(Faculty f, String username, String password) {
        long userId = QueryExecutor.executeInsert(
            "INSERT INTO users (username, password, role) VALUES (?, ?, 'FACULTY')",
            username, password);
        if (userId < 0) return -1;
        return QueryExecutor.executeInsert(
            "INSERT INTO faculty (user_id, faculty_id, name, email, phone, department, designation, specialization) VALUES (?,?,?,?,?,?,?,?)",
            userId, f.getFacultyId(), f.getName(), f.getEmail(), f.getPhone(),
            f.getDepartment(), f.getDesignation(), f.getSpecialization());
    }

    public int update(Faculty f) {
        return QueryExecutor.executeUpdate(
            "UPDATE faculty SET name=?, email=?, phone=?, department=?, designation=?, specialization=? WHERE id=?",
            f.getName(), f.getEmail(), f.getPhone(), f.getDepartment(),
            f.getDesignation(), f.getSpecialization(), f.getId());
    }

    public int count() {
        Object r = QueryExecutor.executeScalar("SELECT COUNT(*) FROM faculty");
        return r == null ? 0 : ((Number) r).intValue();
    }

    private Faculty map(Map<String, Object> row) {
        Faculty f = new Faculty();
        f.setId(toInt(row.get("id")));
        f.setUserId(toInt(row.get("user_id")));
        f.setFacultyId(str(row.get("faculty_id")));
        f.setName(str(row.get("name")));
        f.setEmail(str(row.get("email")));
        f.setPhone(str(row.get("phone")));
        f.setDepartment(str(row.get("department")));
        f.setDesignation(str(row.get("designation")));
        f.setSpecialization(str(row.get("specialization")));
        f.setCreatedAt(str(row.get("created_at")));
        return f;
    }

    private int toInt(Object o) { return o == null ? 0 : ((Number) o).intValue(); }
    private String str(Object o) { return o == null ? "" : o.toString(); }
}
