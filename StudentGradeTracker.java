import java.util.*;

/**
 * Student Grade Tracker - CodeAlpha Java Programming Internship
 * Console app to add students, record grades and view statistics.
 */
public class StudentGradeTracker {

    // ---------- Student model ----------
    static class Student {
        private final String name;
        private final List<Double> grades = new ArrayList<>();

        Student(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        List<Double> getGrades() {
            return grades;
        }

        void addGrade(double grade) {
            grades.add(grade);
        }

        double getAverage() {
            if (grades.isEmpty()) return 0;
            double sum = 0;
            for (double g : grades) sum += g;
            return sum / grades.size();
        }

        double getHighest() {
            return grades.isEmpty() ? 0 : Collections.max(grades);
        }

        double getLowest() {
            return grades.isEmpty() ? 0 : Collections.min(grades);
        }

        String getLetterGrade() {
            double avg = getAverage();
            if (avg >= 90) return "A";
            if (avg >= 80) return "B";
            if (avg >= 70) return "C";
            if (avg >= 60) return "D";
            return "F";
        }
    }

    // ---------- App state ----------
    private static final List<Student> students = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    // ---------- Main ----------
    public static void main(String[] args) {
        System.out.println("=== STUDENT GRADE TRACKER ===");
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: addStudent(); break;
                case 2: addGrades(); break;
                case 3: viewStudents(); break;
                case 4: summaryReport(); break;
                case 5: running = false; break;
                default: System.out.println("Invalid choice. Try 1-5.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n1. Add student");
        System.out.println("2. Add grades to a student");
        System.out.println("3. View all students");
        System.out.println("4. Summary report");
        System.out.println("5. Exit");
    }

    // ---------- Features ----------
    private static void addStudent() {
        System.out.print("Student name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        if (findStudent(name) != null) {
            System.out.println("Student already exists.");
            return;
        }
        students.add(new Student(name));
        System.out.println("Added " + name + ".");
    }

    private static void addGrades() {
        if (students.isEmpty()) {
            System.out.println("No students yet. Add one first.");
            return;
        }
        System.out.print("Student name: ");
        Student s = findStudent(sc.nextLine().trim());
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Enter grades (0-100). Type -1 to stop.");
        while (true) {
            double g = readDouble("Grade: ");
            if (g == -1) break;
            if (g < 0 || g > 100) {
                System.out.println("Grade must be between 0 and 100.");
                continue;
            }
            s.addGrade(g);
        }
        System.out.printf("%s now has %d grade(s).%n", s.getName(), s.getGrades().size());
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to show.");
            return;
        }
        System.out.printf("%n%-15s %-8s %-8s %-8s %-6s%n", "Name", "Average", "Highest", "Lowest", "Grade");
        System.out.println("-------------------------------------------------");
        for (Student s : students) {
            System.out.printf("%-15s %-8.2f %-8.2f %-8.2f %-6s%n",
                    s.getName(), s.getAverage(), s.getHighest(), s.getLowest(), s.getLetterGrade());
        }
    }

    private static void summaryReport() {
        List<Student> graded = new ArrayList<>();
        for (Student s : students) {
            if (!s.getGrades().isEmpty()) graded.add(s);
        }
        if (graded.isEmpty()) {
            System.out.println("No grades recorded yet.");
            return;
        }
        Student top = graded.get(0), bottom = graded.get(0);
        double total = 0;
        for (Student s : graded) {
            total += s.getAverage();
            if (s.getAverage() > top.getAverage()) top = s;
            if (s.getAverage() < bottom.getAverage()) bottom = s;
        }
        System.out.println("\n--- SUMMARY REPORT ---");
        System.out.println("Students with grades : " + graded.size());
        System.out.printf("Class average        : %.2f%n", total / graded.size());
        System.out.printf("Top performer        : %s (%.2f)%n", top.getName(), top.getAverage());
        System.out.printf("Lowest performer     : %s (%.2f)%n", bottom.getName(), bottom.getAverage());
    }

    // ---------- Helpers ----------
    private static Student findStudent(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}

