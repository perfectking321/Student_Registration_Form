import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.*;

public class StudentRegistrationForm extends JFrame {
    private JTextField txtStudentId, txtName, txtEmail, txtPhone;
    private JComboBox<String> cmbCourse, cmbGender;
    private JTextArea txtAddress;
    private JButton btnRegister, btnClear, btnView;
    private JTable tableStudents;
    private JScrollPane scrollPane, addressScrollPane;
    private Connection connection;
    
    public StudentRegistrationForm() {
        setTitle("Student Registration System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeDatabase();
        createUI();
        loadStudentData();
    }
    
    private void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:students.db");
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS students (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "student_id TEXT UNIQUE NOT NULL," +
                        "name TEXT NOT NULL," +
                        "email TEXT NOT NULL," +
                        "phone TEXT NOT NULL," +
                        "gender TEXT NOT NULL," +
                        "course TEXT NOT NULL," +
                        "address TEXT NOT NULL)";
            stmt.execute(sql);
            stmt.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Database Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Student Registration Form", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204));
        mainPanel.add(title, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1), 
            "Student Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        txtStudentId = new JTextField(20);
        formPanel.add(txtStudentId, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(20);
        formPanel.add(txtName, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(20);
        formPanel.add(txtPhone, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        String[] genders = {"Male", "Female", "Other"};
        cmbGender = new JComboBox<>(genders);
        formPanel.add(cmbGender, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1;
        String[] courses = {"Computer Science", "Information Technology", 
                           "Electronics", "Mechanical", "Civil", "MBA"};
        cmbCourse = new JComboBox<>(courses);
        formPanel.add(cmbCourse, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTH;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        txtAddress = new JTextArea(3, 20);
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        addressScrollPane = new JScrollPane(txtAddress);
        formPanel.add(addressScrollPane, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(0, 153, 76));
        btnRegister.setForeground(Color.BLACK);
        btnRegister.setFocusPainted(false);
        
        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(255, 153, 0));
        btnClear.setForeground(Color.BLACK);
        btnClear.setFocusPainted(false);
        
        btnView = new JButton("View All");
        btnView.setBackground(new Color(0, 102, 204));
        btnView.setForeground(Color.BLACK);
        btnView.setFocusPainted(false);
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnView);
        
        String[] columns = {"ID", "Student ID", "Name", "Email", "Phone", "Gender", "Course"};
        tableStudents = new JTable(new javax.swing.table.DefaultTableModel(columns, 0));
        scrollPane = new JScrollPane(tableStudents);
        scrollPane.setPreferredSize(new Dimension(850, 200));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Students"));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        btnRegister.addActionListener(e -> registerStudent());
        btnClear.addActionListener(e -> clearForm());
        btnView.addActionListener(e -> loadStudentData());
    }
    
    private boolean validateInputs() {

        if (txtStudentId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtStudentId.requestFocus();
            return false;
        }
        
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student Name", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtName.requestFocus();
            return false;
        }
        
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(txtEmail.getText().trim()).matches()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Email", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }
        
        String phoneRegex = "^[0-9]{10}$";
        if (!txtPhone.getText().trim().matches(phoneRegex)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit Phone Number", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtPhone.requestFocus();
            return false;
        }
        
        if (txtAddress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Address", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            txtAddress.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void registerStudent() {
        if (!validateInputs()) {
            return;
        }
        
        try {
            String sql = "INSERT INTO students (student_id, name, email, phone, gender, course, address) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, txtStudentId.getText().trim());
            pstmt.setString(2, txtName.getText().trim());
            pstmt.setString(3, txtEmail.getText().trim());
            pstmt.setString(4, txtPhone.getText().trim());
            pstmt.setString(5, cmbGender.getSelectedItem().toString());
            pstmt.setString(6, cmbCourse.getSelectedItem().toString());
            pstmt.setString(7, txtAddress.getText().trim());
            
            pstmt.executeUpdate();
            pstmt.close();
            
            JOptionPane.showMessageDialog(this, 
                "Student registered successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
            loadStudentData();
            
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                JOptionPane.showMessageDialog(this, 
                    "Student ID already exists!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Database Error: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void clearForm() {
        txtStudentId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        cmbGender.setSelectedIndex(0);
        cmbCourse.setSelectedIndex(0);
        txtStudentId.requestFocus();
    }
    
    private void loadStudentData() {
        try {
            javax.swing.table.DefaultTableModel model = 
                (javax.swing.table.DefaultTableModel) tableStudents.getModel();
            model.setRowCount(0);
            
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("gender"),
                    rs.getString("course")
                };
                model.addRow(row);
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading data: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new StudentRegistrationForm().setVisible(true);
        });
    }
}