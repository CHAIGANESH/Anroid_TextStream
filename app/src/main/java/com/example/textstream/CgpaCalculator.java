package com.example.textstream;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class CgpaCalculator extends AppCompatActivity {

    private Spinner semesterSpinner, gradeSpinner;
    private EditText courseNameEditText;
    private TextView courseListText, gpaText, cgpaText;
    private Button addCourseButton, calculateGpaButton, calculateCgpaButton;

    private HashMap<String, ArrayList<Integer>> semesterGrades = new HashMap<>();
    private ArrayList<String> currentSemesterCourses = new ArrayList<>();
    private ArrayList<Integer> currentSemesterGrades = new ArrayList<>();

    private final HashMap<String, Integer> gradeMap = new HashMap<String, Integer>() {{
        put("O", 10);
        put("A+", 9);
        put("A", 8);
        put("B+", 7);
        put("B", 6);
        put("C", 5);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa_calculator);

        // Initialize UI components
        semesterSpinner = findViewById(R.id.semester_spinner);
        gradeSpinner = findViewById(R.id.grade_spinner);
        courseNameEditText = findViewById(R.id.course_name);
        courseListText = findViewById(R.id.course_list_text);
        gpaText = findViewById(R.id.gpa_text);
        cgpaText = findViewById(R.id.cgpa_text);
        addCourseButton = findViewById(R.id.add_course_button);
        calculateGpaButton = findViewById(R.id.calculate_gpa_button);
        calculateCgpaButton = findViewById(R.id.calculate_cgpa_button);

        // Initialize semester spinner
        ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(this,
                R.array.semesters, android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);

        // Initialize grade spinner
        ArrayAdapter<CharSequence> gradeAdapter = ArrayAdapter.createFromResource(this,
                R.array.grades, android.R.layout.simple_spinner_item);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);

        // Button event listeners
        addCourseButton.setOnClickListener(v -> addCourse());
        calculateGpaButton.setOnClickListener(v -> calculateGpa());
        calculateCgpaButton.setOnClickListener(v -> calculateCgpa());
    }

    private void addCourse() {
        String courseName = courseNameEditText.getText().toString().trim();
        String grade = (String) gradeSpinner.getSelectedItem();

        if (courseName.isEmpty()) {
            Toast.makeText(this, "Enter course name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!gradeMap.containsKey(grade)) {
            Toast.makeText(this, "Invalid grade selected", Toast.LENGTH_SHORT).show();
            return;
        }

        int gradePoint = gradeMap.get(grade);
        currentSemesterCourses.add(courseName);
        currentSemesterGrades.add(gradePoint);

        courseListText.append("\n" + courseName + " - " + grade);

        courseNameEditText.setText("");
    }

    private void calculateGpa() {
        if (currentSemesterGrades.isEmpty()) {
            Toast.makeText(this, "No courses added for this semester", Toast.LENGTH_SHORT).show();
            return;
        }

        double gpa = currentSemesterGrades.stream().mapToDouble(a -> a).average().orElse(0.0);
        String semester = (String) semesterSpinner.getSelectedItem();

        semesterGrades.put(semester, new ArrayList<>(currentSemesterGrades));
        currentSemesterGrades.clear();
        currentSemesterCourses.clear();

        gpaText.setText("GPA for " + semester + ": " + String.format("%.2f", gpa));
        courseListText.setText("Courses:");
    }

    private void calculateCgpa() {
        if (semesterGrades.isEmpty()) {
            Toast.makeText(this, "No GPA calculated for any semester", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalGradePoints = 0;
        int totalCourses = 0;

        for (ArrayList<Integer> grades : semesterGrades.values()) {
            int semesterGradePoints = 0;
            for (int grade : grades) {
                semesterGradePoints += grade;
            }
            totalGradePoints += semesterGradePoints;
            totalCourses += grades.size();

            Log.d("CGPA", "Semester grade points: " + semesterGradePoints);
        }

        if (totalCourses > 0) {
            double cgpa = totalGradePoints / totalCourses;
            cgpaText.setText("CGPA: " + String.format("%.2f", cgpa));
            Log.d("CGPA", "Total grade points: " + totalGradePoints + ", Total courses: " + totalCourses);
        } else {
            cgpaText.setText("CGPA: N/A");
        }
    }

}