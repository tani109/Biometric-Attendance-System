import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tanjila Mawla Tania
 */
public class AddFrame extends javax.swing.JFrame {

    String months[] = {"none", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    int day, year;
    String monthName;
    String currentDate;
    int numberOfAttendance;
    String courseCodes[] =getCourseCodeFromDB();
    int batches[] = getBatches();
    String courseCode;//this is the course code of the course that teacher will give attendance
    //it will read value from the combo box in the takeAttendance panel
    static int currentStudentNumber = 0;
    String fingerprintFolderName; // this folder has the fingerprints of a certain course in a certain date to match
    //matlab will write the logfile in this folder and java will read that file and give attendance
    String logFileName;//it is inside Logfiles folder of source
    //it saves the data of matched fingerprint

    /**
     * Creates new form AddFrame
     */
    public AddFrame() {

        initComponents();

        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }
        for (int i = 1; i < 13; i++) {
            monthComboBox.addItem(months[i]);
        }
        for (int i = 2017; i <= 2050; i++) {
            yearComboBox.addItem(i);
        }
        for (int i = 0; courseCodes[i] != null; i++) {
            courseCodeComboBox.addItem(courseCodes[i]);
            courseToBeAddedComboBox.addItem(courseCodes[i]);
            courseToBeAddedPerStudentComboBox.addItem(courseCodes[i]);
            courseToBeDeletedPerStudentComboBox.addItem(courseCodes[i]);
            courseToBeDeletedPerBatchComboBox.addItem(courseCodes[i]);
        }

        for (int i = 0; batches[i] > 0; i++) {
            //System.out.println("inside for loop "+batches[i]);
            batchToBeAddedComboBox.addItem(batches[i]);
            batchToBeDeletedComboBox.addItem(batches[i]);

        }
        for (int i = 1; i <= 3; i++) {
            numberOfAttendanceComboBox.addItem(i);
        }

    }

    public int[] getBatches() {
        int[] existingBatches = new int[50];
        try {
            // returns the number of existing batches in database
            Home.opendb();

            int count = 0;
            String getBatchesQuery = "SELECT batch FROM student GROUP BY batch";
            Home.pst = Home.conn.prepareStatement(getBatchesQuery);
            Home.rs = Home.pst.executeQuery();
            while (Home.rs.next()) {
                //System.out.println(Home.rs.getInt("batch"));
                existingBatches[count] = Home.rs.getInt("batch");
                //System.out.println("iside while "+existingBatches[count]);
                count++;

            }
            Home.rs.close();
            Home.closedb();
        } catch (SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existingBatches;
    }

    public void addToAttendanceTable(int studentId, int courseId, String currentDate, int numberOfAttendance) {
        try {
            Home.opendb();
            String attendanceTableCheckQuery = "SELECT ID FROM attendance WHERE student_id = ? AND course_id = ? AND date = ?";

            Home.pst = Home.conn.prepareStatement(attendanceTableCheckQuery);
            Home.pst.setInt(1, studentId);
            Home.pst.setInt(2, courseId);
            Home.pst.setString(3, currentDate);
            Home.rs = Home.pst.executeQuery();

            if (Home.rs.next()) {
                System.out.println("this student already has attendance to this day at this course");
                Home.closedb();
            } else {

                String giveAttendanceQuery = "INSERT INTO attendance (STUDENT_ID,COURSE_ID,DATE,ATTN) VALUES (?,?,?,?)";

                //System.out.println("conn " + Home.conn);
                Home.pst = Home.conn.prepareStatement(giveAttendanceQuery);
                Home.pst.setInt(1, studentId);
                Home.pst.setInt(2, courseId);
                Home.pst.setString(3, currentDate);
                Home.pst.setInt(4, numberOfAttendance);

                Home.pst.execute();
                System.out.println("attn gievn to " + studentId);
                Home.closedb();
                //student added, so increment counter

            }
        } catch (SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getCourseIdFromCourseCode(String courseCode) {
        int courseId = 0;//set a default value
        try {
            // TODO add your handling code here:
            //get the course id from the given course code

            Home.opendb();
            String getCourseIdQuery = "SELECT ID FROM COURSE WHERE CODE = ?";
            Home.pst = Home.conn.prepareStatement(getCourseIdQuery);
            Home.pst.setString(1, courseCode);
            Home.rs = Home.pst.executeQuery();

            while (Home.rs.next()) {
                courseId = Home.rs.getInt("ID");
                //System.out.println("***course id " + courseId);
            }
            Home.rs.close();
            Home.closedb();

        } catch (SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courseId;

    }

    public String[] getCourseCodeFromDB() {
        String courseCodes[] = new String[500];

        try {
            Home.opendb();

            String courseCodeQuery = "SELECT * FROM COURSE";
            Home.pst = Home.conn.prepareStatement(courseCodeQuery);
            Home.rs = Home.pst.executeQuery();
            //System.out.println(Home.rs);

            int indexCount = 0;
            while (Home.rs.next()) {

                courseCodes[indexCount] = Home.rs.getString("code");

                //System.out.println(courseCodes[indexCount] + '\n');
                indexCount++;
            }
            Home.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courseCodes;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        courseEntryPanel = new javax.swing.JPanel();
        courseCodeLabel = new javax.swing.JLabel();
        courseTitleTextfield = new javax.swing.JTextField();
        courseTitleLabel = new javax.swing.JLabel();
        courseCodeTextField = new javax.swing.JTextField();
        courseCreditLabel = new javax.swing.JLabel();
        courseCreditTextField = new javax.swing.JTextField();
        addCourseButton = new javax.swing.JButton();
        addCourseLabel = new javax.swing.JLabel();
        studentEntryPanel = new javax.swing.JPanel();
        stuNameLabel = new javax.swing.JLabel();
        regTextField = new javax.swing.JTextField();
        regLabel = new javax.swing.JLabel();
        stuNameTextField = new javax.swing.JTextField();
        batchLabel = new javax.swing.JLabel();
        batchTextField = new javax.swing.JTextField();
        addStudentButton = new javax.swing.JButton();
        addStudentLabel = new javax.swing.JLabel();
        addBatchToCoursePanel = new javax.swing.JPanel();
        courseToBeAddedLabel = new javax.swing.JLabel();
        batchToBeAddedLabel = new javax.swing.JLabel();
        addBatchToCourseButton = new javax.swing.JButton();
        batchToBeAddedComboBox = new javax.swing.JComboBox();
        courseToBeAddedComboBox = new javax.swing.JComboBox();
        addBatchToCourseLabel = new javax.swing.JLabel();
        addStudentToCoursePanel = new javax.swing.JPanel();
        courseToBeAddedPerStudentLabel = new javax.swing.JLabel();
        regNoToBeAddedTextfield = new javax.swing.JTextField();
        regNoToBeAddedLabel = new javax.swing.JLabel();
        courseToBeAddedPerStudentComboBox = new javax.swing.JComboBox();
        addStudentToCourseButton = new javax.swing.JButton();
        addStudentToCourseLabel = new javax.swing.JLabel();
        takeAttendancePanel = new javax.swing.JPanel();
        dateLabel = new javax.swing.JLabel();
        dayComboBox = new javax.swing.JComboBox();
        monthComboBox = new javax.swing.JComboBox();
        yearComboBox = new javax.swing.JComboBox();
        takeAttendanceCourseCodeLabel = new javax.swing.JLabel();
        numberOfAttendanceLabel = new javax.swing.JLabel();
        numberOfAttendanceComboBox = new javax.swing.JComboBox();
        nextTakeAttendancetButton = new javax.swing.JButton();
        courseCodeComboBox = new javax.swing.JComboBox();
        takeAttendanceLabel = new javax.swing.JLabel();
        matchFingerprintPanel = new javax.swing.JPanel();
        matchFingerprintButton = new javax.swing.JButton();
        giveAttendanceButton = new javax.swing.JButton();
        matchFingerprintLabel = new javax.swing.JLabel();
        deleteBatchFromCoursePanel = new javax.swing.JPanel();
        courseToBeAddedLabel1 = new javax.swing.JLabel();
        batchToBeAddedLabel1 = new javax.swing.JLabel();
        deleteBatchFromCourseButton = new javax.swing.JButton();
        batchToBeDeletedComboBox = new javax.swing.JComboBox();
        courseToBeDeletedPerBatchComboBox = new javax.swing.JComboBox();
        deleteBatchFromCourseLable = new javax.swing.JLabel();
        deleteStudentFromCoursePanel = new javax.swing.JPanel();
        courseToBeAddedPerStudentLabel1 = new javax.swing.JLabel();
        regNoToBeDeletedTextField = new javax.swing.JTextField();
        regNoToBeAddedLabel1 = new javax.swing.JLabel();
        courseToBeDeletedPerStudentComboBox = new javax.swing.JComboBox();
        deleteStudentFromCourseButton = new javax.swing.JButton();
        deleteStudentFromCourseLabel = new javax.swing.JLabel();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        courseEntryPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        courseEntryPanel.setLayout(null);

        courseCodeLabel.setText("Course Code");
        courseEntryPanel.add(courseCodeLabel);
        courseCodeLabel.setBounds(110, 90, 80, 29);
        courseEntryPanel.add(courseTitleTextfield);
        courseTitleTextfield.setBounds(110, 40, 214, 40);

        courseTitleLabel.setText("Course Title");
        courseEntryPanel.add(courseTitleLabel);
        courseTitleLabel.setBounds(110, 10, 80, 29);
        courseEntryPanel.add(courseCodeTextField);
        courseCodeTextField.setBounds(110, 120, 214, 40);

        courseCreditLabel.setText("Credit");
        courseEntryPanel.add(courseCreditLabel);
        courseCreditLabel.setBounds(110, 170, 80, 29);
        courseEntryPanel.add(courseCreditTextField);
        courseCreditTextField.setBounds(110, 200, 214, 40);

        addCourseButton.setText("ADD");
        addCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCourseButtonActionPerformed(evt);
            }
        });
        courseEntryPanel.add(addCourseButton);
        addCourseButton.setBounds(190, 255, 53, 30);

        addCourseLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/addLabelImage.png"))); // NOI18N
        courseEntryPanel.add(addCourseLabel);
        addCourseLabel.setBounds(0, 0, 390, 520);

        getContentPane().add(courseEntryPanel);
        courseEntryPanel.setBounds(5, 1, 390, 520);

        studentEntryPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        studentEntryPanel.setLayout(null);

        stuNameLabel.setText("Student Name");
        studentEntryPanel.add(stuNameLabel);
        stuNameLabel.setBounds(110, 90, 80, 29);
        studentEntryPanel.add(regTextField);
        regTextField.setBounds(110, 50, 214, 40);

        regLabel.setText("Registration no");
        studentEntryPanel.add(regLabel);
        regLabel.setBounds(110, 20, 120, 29);
        studentEntryPanel.add(stuNameTextField);
        stuNameTextField.setBounds(110, 120, 214, 40);

        batchLabel.setText("Batch");
        studentEntryPanel.add(batchLabel);
        batchLabel.setBounds(110, 160, 80, 29);
        studentEntryPanel.add(batchTextField);
        batchTextField.setBounds(110, 190, 214, 40);

        addStudentButton.setText("ADD");
        addStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentButtonActionPerformed(evt);
            }
        });
        studentEntryPanel.add(addStudentButton);
        addStudentButton.setBounds(180, 240, 60, 30);

        addStudentLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/addLabelImage.png"))); // NOI18N
        studentEntryPanel.add(addStudentLabel);
        addStudentLabel.setBounds(-10, 0, 400, 510);

        getContentPane().add(studentEntryPanel);
        studentEntryPanel.setBounds(5, 1, 390, 520);

        addBatchToCoursePanel.setPreferredSize(new java.awt.Dimension(400, 300));
        addBatchToCoursePanel.setLayout(null);

        courseToBeAddedLabel.setText("Course Code");
        addBatchToCoursePanel.add(courseToBeAddedLabel);
        courseToBeAddedLabel.setBounds(119, 100, 80, 29);

        batchToBeAddedLabel.setText("Batch");
        addBatchToCoursePanel.add(batchToBeAddedLabel);
        batchToBeAddedLabel.setBounds(120, 20, 80, 29);

        addBatchToCourseButton.setText("ADD");
        addBatchToCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBatchToCourseButtonActionPerformed(evt);
            }
        });
        addBatchToCoursePanel.add(addBatchToCourseButton);
        addBatchToCourseButton.setBounds(150, 200, 80, 40);

        batchToBeAddedComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batchToBeAddedComboBoxActionPerformed(evt);
            }
        });
        addBatchToCoursePanel.add(batchToBeAddedComboBox);
        batchToBeAddedComboBox.setBounds(120, 50, 130, 30);

        addBatchToCoursePanel.add(courseToBeAddedComboBox);
        courseToBeAddedComboBox.setBounds(120, 130, 130, 30);

        addBatchToCourseLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/addLabelImage.png"))); // NOI18N
        addBatchToCoursePanel.add(addBatchToCourseLabel);
        addBatchToCourseLabel.setBounds(2, -1, 400, 520);

        getContentPane().add(addBatchToCoursePanel);
        addBatchToCoursePanel.setBounds(0, 1, 400, 520);

        addStudentToCoursePanel.setPreferredSize(new java.awt.Dimension(400, 300));
        addStudentToCoursePanel.setLayout(null);

        courseToBeAddedPerStudentLabel.setText("Course Code");
        addStudentToCoursePanel.add(courseToBeAddedPerStudentLabel);
        courseToBeAddedPerStudentLabel.setBounds(110, 110, 100, 29);
        addStudentToCoursePanel.add(regNoToBeAddedTextfield);
        regNoToBeAddedTextfield.setBounds(110, 50, 214, 40);

        regNoToBeAddedLabel.setText("Registration No");
        addStudentToCoursePanel.add(regNoToBeAddedLabel);
        regNoToBeAddedLabel.setBounds(110, 20, 160, 29);

        addStudentToCoursePanel.add(courseToBeAddedPerStudentComboBox);
        courseToBeAddedPerStudentComboBox.setBounds(110, 140, 210, 30);

        addStudentToCourseButton.setText("ADD");
        addStudentToCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentToCourseButtonActionPerformed(evt);
            }
        });
        addStudentToCoursePanel.add(addStudentToCourseButton);
        addStudentToCourseButton.setBounds(180, 220, 80, 40);

        addStudentToCourseLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/addLabelImage.png"))); // NOI18N
        addStudentToCoursePanel.add(addStudentToCourseLabel);
        addStudentToCourseLabel.setBounds(2, -1, 400, 520);

        getContentPane().add(addStudentToCoursePanel);
        addStudentToCoursePanel.setBounds(0, 1, 400, 520);

        takeAttendancePanel.setPreferredSize(new java.awt.Dimension(400, 300));
        takeAttendancePanel.setLayout(null);

        dateLabel.setText("Add date");
        takeAttendancePanel.add(dateLabel);
        dateLabel.setBounds(90, 20, 140, 20);

        dayComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayComboBoxActionPerformed(evt);
            }
        });
        takeAttendancePanel.add(dayComboBox);
        dayComboBox.setBounds(90, 50, 50, 30);

        monthComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthComboBoxActionPerformed(evt);
            }
        });
        takeAttendancePanel.add(monthComboBox);
        monthComboBox.setBounds(150, 50, 60, 30);

        yearComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearComboBoxActionPerformed(evt);
            }
        });
        takeAttendancePanel.add(yearComboBox);
        yearComboBox.setBounds(220, 50, 80, 30);

        takeAttendanceCourseCodeLabel.setText("Course Code");
        takeAttendancePanel.add(takeAttendanceCourseCodeLabel);
        takeAttendanceCourseCodeLabel.setBounds(90, 90, 80, 29);

        numberOfAttendanceLabel.setText("Number of Attendance");
        takeAttendancePanel.add(numberOfAttendanceLabel);
        numberOfAttendanceLabel.setBounds(90, 160, 150, 20);

        takeAttendancePanel.add(numberOfAttendanceComboBox);
        numberOfAttendanceComboBox.setBounds(90, 180, 50, 30);

        nextTakeAttendancetButton.setText("NEXT");
        nextTakeAttendancetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextTakeAttendancetButtonActionPerformed(evt);
            }
        });
        takeAttendancePanel.add(nextTakeAttendancetButton);
        nextTakeAttendancetButton.setBounds(150, 230, 73, 40);

        takeAttendancePanel.add(courseCodeComboBox);
        courseCodeComboBox.setBounds(90, 120, 160, 30);

        takeAttendanceLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/addLabelImage.png"))); // NOI18N
        takeAttendancePanel.add(takeAttendanceLabel);
        takeAttendanceLabel.setBounds(0, 0, 400, 520);

        getContentPane().add(takeAttendancePanel);
        takeAttendancePanel.setBounds(0, 1, 400, 520);

        matchFingerprintPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        matchFingerprintPanel.setLayout(null);

        matchFingerprintButton.setText("Match Fingerprint Using Matlab");
        matchFingerprintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchFingerprintButtonActionPerformed(evt);
            }
        });
        matchFingerprintPanel.add(matchFingerprintButton);
        matchFingerprintButton.setBounds(90, 40, 230, 60);

        giveAttendanceButton.setText("Give Attendance");
        giveAttendanceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giveAttendanceButtonActionPerformed(evt);
            }
        });
        matchFingerprintPanel.add(giveAttendanceButton);
        giveAttendanceButton.setBounds(90, 130, 230, 60);

        matchFingerprintLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/addLabelImage.png"))); // NOI18N
        matchFingerprintPanel.add(matchFingerprintLabel);
        matchFingerprintLabel.setBounds(2, -1, 400, 520);

        getContentPane().add(matchFingerprintPanel);
        matchFingerprintPanel.setBounds(0, 1, 400, 520);

        deleteBatchFromCoursePanel.setPreferredSize(new java.awt.Dimension(400, 300));
        deleteBatchFromCoursePanel.setLayout(null);

        courseToBeAddedLabel1.setText("Course Code");
        deleteBatchFromCoursePanel.add(courseToBeAddedLabel1);
        courseToBeAddedLabel1.setBounds(119, 100, 80, 29);

        batchToBeAddedLabel1.setText("Batch");
        deleteBatchFromCoursePanel.add(batchToBeAddedLabel1);
        batchToBeAddedLabel1.setBounds(120, 20, 80, 29);

        deleteBatchFromCourseButton.setText("Delete");
        deleteBatchFromCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBatchFromCourseButtonActionPerformed(evt);
            }
        });
        deleteBatchFromCoursePanel.add(deleteBatchFromCourseButton);
        deleteBatchFromCourseButton.setBounds(150, 200, 80, 40);

        deleteBatchFromCoursePanel.add(batchToBeDeletedComboBox);
        batchToBeDeletedComboBox.setBounds(120, 50, 130, 30);

        deleteBatchFromCoursePanel.add(courseToBeDeletedPerBatchComboBox);
        courseToBeDeletedPerBatchComboBox.setBounds(120, 130, 130, 30);

        deleteBatchFromCourseLable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/addLabelImage.png"))); // NOI18N
        deleteBatchFromCoursePanel.add(deleteBatchFromCourseLable);
        deleteBatchFromCourseLable.setBounds(2, -1, 400, 520);

        getContentPane().add(deleteBatchFromCoursePanel);
        deleteBatchFromCoursePanel.setBounds(0, 1, 400, 520);

        deleteStudentFromCoursePanel.setPreferredSize(new java.awt.Dimension(400, 300));
        deleteStudentFromCoursePanel.setLayout(null);

        courseToBeAddedPerStudentLabel1.setText("Course Code");
        deleteStudentFromCoursePanel.add(courseToBeAddedPerStudentLabel1);
        courseToBeAddedPerStudentLabel1.setBounds(110, 110, 100, 29);
        deleteStudentFromCoursePanel.add(regNoToBeDeletedTextField);
        regNoToBeDeletedTextField.setBounds(110, 50, 214, 40);

        regNoToBeAddedLabel1.setText("Registration No");
        deleteStudentFromCoursePanel.add(regNoToBeAddedLabel1);
        regNoToBeAddedLabel1.setBounds(110, 20, 160, 29);

        deleteStudentFromCoursePanel.add(courseToBeDeletedPerStudentComboBox);
        courseToBeDeletedPerStudentComboBox.setBounds(110, 140, 210, 30);

        deleteStudentFromCourseButton.setText("Delete");
        deleteStudentFromCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteStudentFromCourseButtonActionPerformed(evt);
            }
        });
        deleteStudentFromCoursePanel.add(deleteStudentFromCourseButton);
        deleteStudentFromCourseButton.setBounds(170, 190, 90, 50);

        deleteStudentFromCourseLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/addLabelImage.png"))); // NOI18N
        deleteStudentFromCoursePanel.add(deleteStudentFromCourseLabel);
        deleteStudentFromCourseLabel.setBounds(2, -11, 400, 520);

        getContentPane().add(deleteStudentFromCoursePanel);
        deleteStudentFromCoursePanel.setBounds(0, 11, 400, 510);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentButtonActionPerformed

        // TODO add your handling code here:
        String reg = regTextField.getText();
        String name = stuNameTextField.getText();
        String batchName = batchTextField.getText();

        //check if all fields have values
        //if any missing then show error message
        if (!(!reg.isEmpty() && !name.isEmpty() && !batchName.isEmpty())) {
            System.out.println("no value passed in one or more fields");
        } //if all fields have value, add to database
        else {
            System.out.println("value passed.");
            int batch = Integer.parseInt(batchName);
            System.out.println("reg " + reg + "name " + name + "batch " + batch);

            String sql = "INSERT INTO STUDENT (REG,STU_NAME,BATCH) VALUES (?,?,?)";

            try {
                Home.opendb();
                Home.pst = Home.conn.prepareStatement(sql);
                Home.pst.setString(1, reg);
                Home.pst.setString(2, name);
                Home.pst.setInt(3, batch);
                System.out.println("pst pre error");

                Home.pst.execute();
                Home.closedb();
                JOptionPane.showMessageDialog(null, "Student Added Succesfully");
                
                batches = getBatches();
                batchToBeAddedComboBox.removeAllItems();
                batchToBeDeletedComboBox.removeAllItems();
                
                for (int i = 0; batches[i] > 0; i++) {
                    //System.out.println("inside for loop "+batches[i]);
                    batchToBeAddedComboBox.addItem(batches[i]);
                    batchToBeDeletedComboBox.addItem(batches[i]);

                }
                //register fingerprint of this student to dataset

            } catch (SQLException ex) {
                Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_addStudentButtonActionPerformed

    private void addCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCourseButtonActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        String code = courseCodeTextField.getText();
        String title = courseTitleTextfield.getText();
        String creditName = courseCreditTextField.getText();

        if (!(!code.isEmpty() && !title.isEmpty() && !creditName.isEmpty())) {
            System.out.println("no value passed in one or more fields in course add method");
        } else {
            try {

                double credit = Double.parseDouble(creditName);

                Home.opendb();

                String sql = "INSERT INTO COURSE (CODE,TITLE,CREDIT) VALUES (?,?,?)";
                Home.pst = Home.conn.prepareStatement(sql);
                Home.pst.setString(1, code);
                Home.pst.setString(2, title);
                Home.pst.setDouble(3, credit);
                System.out.println("pst pre error");

                Home.pst.execute();

                Home.closedb();
                JOptionPane.showMessageDialog(null, "Course Added Succesfully");
                
                courseCodes = getCourseCodeFromDB();
                
                courseCodeComboBox.removeAllItems();
                courseToBeAddedComboBox.removeAllItems();
                courseToBeAddedPerStudentComboBox.removeAllItems();
                courseToBeDeletedPerStudentComboBox.removeAllItems();
                courseToBeDeletedPerBatchComboBox.removeAllItems();
                for (int i = 0; courseCodes[i] != null; i++) {
                    courseCodeComboBox.addItem(courseCodes[i]);
                    courseToBeAddedComboBox.addItem(courseCodes[i]);
                    courseToBeAddedPerStudentComboBox.addItem(courseCodes[i]);
                    courseToBeDeletedPerStudentComboBox.addItem(courseCodes[i]);
                    courseToBeDeletedPerBatchComboBox.addItem(courseCodes[i]);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_addCourseButtonActionPerformed

    private void addBatchToCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBatchToCourseButtonActionPerformed
        // TODO add your handling code here:

        try {
            // TODO add your handling code here:
            //get the course id from the given course code
            String codeToBeAdded = (String) courseToBeAddedComboBox.getSelectedItem();
            System.out.print(codeToBeAdded + " and ");
            Home.opendb();
            int courseId = getCourseIdFromCourseCode(codeToBeAdded);
            System.out.println(courseId);
            //get the students' id from the given batch
            int batch = (int) batchToBeAddedComboBox.getSelectedItem();
            System.out.println(batch);
            Home.closedb();
            String getStudentIdQuery = "SELECT ID FROM STUDENT WHERE BATCH = ?";
            Home.opendb();
            Home.pst = Home.conn.prepareStatement(getStudentIdQuery);
            System.out.println("pst executed");
            Home.pst.setInt(1, batch);
            //Home.pst.execute();
            Home.rs = Home.pst.executeQuery();

            //enter each student to pivot table against the course
            while (Home.rs.next()) {

                int studentId = Home.rs.getInt("ID");
                System.out.println("student id " + studentId + " , course id " + courseId);
                String insertPivotQuery = "INSERT INTO PIVOT (STUDENT_ID,COURSE_ID) VALUES (?,?)";
                Home.pst = Home.conn.prepareStatement(insertPivotQuery);
                Home.pst.setInt(1, studentId);
                Home.pst.setInt(2, courseId);
                Home.pst.execute();
            }
            Home.closedb();
            JOptionPane.showMessageDialog(null, batch + " Batch Added Succesfully to course" + codeToBeAdded);

        } catch (SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_addBatchToCourseButtonActionPerformed

    private void addStudentToCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStudentToCourseButtonActionPerformed
        try {
            // TODO add your handling code here:
            //get the course id from the given course code
            String codeToBeAdded = (String) courseToBeAddedPerStudentComboBox.getSelectedItem();
            System.out.println(codeToBeAdded);

            int courseId = getCourseIdFromCourseCode(codeToBeAdded);
            System.out.println("***course id " + courseId);

            //get the students' id from the given batch
            String regNo = regNoToBeAddedTextfield.getText();
            String getStudentIdQuery = "SELECT ID FROM STUDENT WHERE REG = ?";
            Home.opendb();
            Home.pst = Home.conn.prepareStatement(getStudentIdQuery);
            Home.pst.setString(1, regNo);
            //Home.pst.execute();
            Home.rs = Home.pst.executeQuery();

            //enter each student to pivot table against the course
            int studentId = Home.rs.getInt("ID");
            Home.rs.close();
            Home.closedb();
            Home.opendb();
            System.out.println("student id " + studentId + " , course id " + courseId);
            String insertPivotQuery = "INSERT INTO PIVOT (STUDENT_ID,COURSE_ID) VALUES (?,?)";
            Home.pst = Home.conn.prepareStatement(insertPivotQuery);
            Home.pst.setInt(1, studentId);
            Home.pst.setInt(2, courseId);
            Home.pst.execute();
            Home.rs.close();
            Home.closedb();
            JOptionPane.showMessageDialog(null, regNo + " Added Succesfully to course" + codeToBeAdded);

        } catch (SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_addStudentToCourseButtonActionPerformed

    private void dayComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dayComboBoxActionPerformed

    private void monthComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthComboBoxActionPerformed

    private void yearComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearComboBoxActionPerformed

    private void nextTakeAttendancetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextTakeAttendancetButtonActionPerformed
        // TODO add your handling code here:

        int day, year;
        String monthName;

        day = (int) dayComboBox.getSelectedItem();
        monthName = (String) monthComboBox.getSelectedItem();
        year = (int) yearComboBox.getSelectedItem();
        courseCode = (String) courseCodeComboBox.getSelectedItem();
        numberOfAttendance = (int) numberOfAttendanceComboBox.getSelectedItem();

        System.out.println(day + "@@" + monthName + "@@" + year);
        String date = null;

        //System.out.println(a+"th "+b+", "+c);
        int monthNum = getMonthIndex(monthName);
        //day=year+"-"+monthNum+"-"+date;
        /*ajaira
         if(monthNum<10 && day<10)
         date=year+"-0"+monthNum+"-0"+day;
         else if(monthNum<10)
         date=year+"-0"+monthNum+"-"+day;
         else if(day<10)
         date=year+"-"+monthNum+"-0"+day;
         else*/
        date = year + "-" + monthNum + "-" + day;
        currentDate = date;

        System.out.println(date);
        //addToDB(day);
        //takeAttendancePanel.removeAll();
        fingerprintFolderName = courseCode + "_" + date;
        logFileName = fingerprintFolderName;

        System.out.println(fingerprintFolderName);
        Home.home.getContentPane().removeAll();
        Home.home.menuPanel.setBounds(400, 0, 300, 520);
        Home.home.add(Home.home.menuPanel);
        Home.home.getContentPane().add(AddFrame.matchFingerprintPanel);
        Home.home.repaint();
        Home.home.setVisible(true);

        currentStudentNumber = 0;

    }//GEN-LAST:event_nextTakeAttendancetButtonActionPerformed

    private void matchFingerprintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matchFingerprintButtonActionPerformed
        // TODO add your handling code here:
        String command = "D:\\MatlabI\\R2016b\\bin\\matlab.exe -nodisplay -nosplash -nodesktop -minimize -r \"run('E:\\Study_new\\Project300\\fingerprot\\B\\match_fprec');\" -logfile src\\Logfiles\\"
                + logFileName + ".txt";
        try {
            Process process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_matchFingerprintButtonActionPerformed

    private void giveAttendanceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giveAttendanceButtonActionPerformed
        //this method reads the text file named logfile where all the reg no are saved
        //registration numbers are saved, which we read here and find the student id from the reg
        //then check if such a student exists
        //then check if that student has takn that course
        //then give him attendace for that course in that day
        BufferedReader br = null;
        try {
            // TODO add your handling code here:
            br = new BufferedReader(new FileReader("src\\Logfiles\\" + logFileName + ".txt"));
            String line, reg_file, reg = null;
            int courseId = getCourseIdFromCourseCode(courseCode);
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == 'R') {
                    System.out.println(line);
                    reg_file = line.split(":")[1];
                    System.out.println(reg_file);
                    reg = reg_file.split("_")[0];
                    System.out.println(reg);

                    Home.opendb();
                    String getStudentIdQuery = "SELECT ID FROM STUDENT WHERE reg = ?";
                    Home.pst = Home.conn.prepareStatement(getStudentIdQuery);
                    Home.pst.setString(1, reg);
                    Home.rs = Home.pst.executeQuery();
                    if (!Home.rs.isBeforeFirst()) {
                        System.out.println("No such student");
                        Home.closedb();
                    } else {
                        System.out.println("in else");
                        int studentId = Home.rs.getInt("id");

                        //now check if that reg is in pivot table to see if that reg is in that course
                        //addToAttendanceTable(studentId, courseId, currentDate, numberOfAttendance);
                        String getPivotRowQuery = "SELECT ID FROM PIVOT WHERE student_id = ? AND course_id = ?";
                        Home.pst = Home.conn.prepareStatement(getPivotRowQuery);
                        Home.pst.setInt(1, studentId);
                        Home.pst.setInt(2, courseId);
                        Home.rs = Home.pst.executeQuery();
                        Home.closedb();
                        if (!Home.rs.isBeforeFirst()) {
                            System.out.println("student " + line + " is not assigned in course " + courseCode);
                        } else {
                            //if such a row exists in pivot give attendance to this student
                            //insert row in attendance table
                            addToAttendanceTable(studentId, courseId, currentDate, numberOfAttendance);
                        }

                    }
                }

            }
            JOptionPane.showMessageDialog(null, "Attendance giving finished");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_giveAttendanceButtonActionPerformed

    private void deleteBatchFromCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBatchFromCourseButtonActionPerformed

        for (int i = 0; batches[i] > 0; i++) {
            //System.out.println("inside for loop "+batches[i]);

            batchToBeDeletedComboBox.addItem(batches[i]);

        }
        try {
            // TODO add your handling code here:
            //get the course id from the given course code
            String codeToBeDeleted = (String) courseToBeDeletedPerBatchComboBox.getSelectedItem();

            Home.opendb();
            int courseId = getCourseIdFromCourseCode(codeToBeDeleted);

            //get the students' id from the given batch
            int batch = (int) batchToBeDeletedComboBox.getSelectedItem();
            String getStudentIdQuery = "SELECT ID FROM STUDENT WHERE BATCH = ?";
            Home.closedb();
            Home.opendb();
            Home.pst = Home.conn.prepareStatement(getStudentIdQuery);

            System.out.println("pst executed");
            Home.pst.setInt(1, batch);
            //Home.pst.execute();
            Home.rs = Home.pst.executeQuery();

            //enter each student to pivot table against the course
            while (Home.rs.next()) {

                int studentId = Home.rs.getInt("ID");
                System.out.println("student id " + studentId + " , course id " + courseId);
                String deletePivotQuery = "DELETE FROM PIVOT WHERE STUDENT_ID = ? AND COURSE_ID = ?";
                Home.pst = Home.conn.prepareStatement(deletePivotQuery);
                Home.pst.setInt(1, studentId);
                Home.pst.setInt(2, courseId);
                Home.pst.execute();
            }
            Home.closedb();
            JOptionPane.showMessageDialog(null, batch + " Batch Deleted Succesfully from course " + codeToBeDeleted);

        } catch (SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_deleteBatchFromCourseButtonActionPerformed

    private void deleteStudentFromCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteStudentFromCourseButtonActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            //get the course id from the given course code
            String codeToBeDeleted = (String) courseToBeDeletedPerStudentComboBox.getSelectedItem();
            System.out.println(codeToBeDeleted);

            int courseId = getCourseIdFromCourseCode(codeToBeDeleted);
            System.out.println("***course id " + courseId);

            //get the students' id from the given batch
            String regNo = regNoToBeDeletedTextField.getText();
            String getStudentIdQuery = "SELECT ID FROM STUDENT WHERE REG = ?";
            Home.opendb();
            Home.pst = Home.conn.prepareStatement(getStudentIdQuery);
            Home.pst.setString(1, regNo);
            //Home.pst.execute();
            Home.rs = Home.pst.executeQuery();

            //enter each student to pivot table against the course
            int studentId = Home.rs.getInt("ID");
            Home.rs.close();

            System.out.println("student id " + studentId + " , course id " + courseId);
            String insertPivotQuery = "DELETE FROM PIVOT WHERE STUDENT_ID= ? AND COURSE_ID = ?";
            Home.pst = Home.conn.prepareStatement(insertPivotQuery);
            Home.pst.setInt(1, studentId);
            Home.pst.setInt(2, courseId);
            Home.pst.execute();
            Home.closedb();
            JOptionPane.showMessageDialog(null, regNo + " Deleted Succesfully From course" + codeToBeDeleted);

        } catch (SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_deleteStudentFromCourseButtonActionPerformed

    private void batchToBeAddedComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batchToBeAddedComboBoxActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_batchToBeAddedComboBoxActionPerformed

    void addToDB(String date) {
        try {

            //int batch = Integer.parseInt(batchTextField.getText());
            String sql = "INSERT INTO JADU (DOB) VALUES (?)";

            System.out.println("conn " + Home.conn);

            Home.opendb();
            Home.pst = Home.conn.prepareStatement(sql);
            Home.pst.setString(1, date);

            try {
                System.out.println("pst pre error");

                Home.pst.execute();
                Home.closedb();
                //register fingerprint of this student to dataset

            } catch (SQLException ex) {
                Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    int getMonthIndex(String monthName) {
        int index = 0;
        for (int i = 1; i < 13; i++) {
            if (months[i].equals(monthName)) {
                index = i;

                System.out.println(monthName + "=" + index);
                break;
            }
        }
        return index;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBatchToCourseButton;
    private javax.swing.JLabel addBatchToCourseLabel;
    public static javax.swing.JPanel addBatchToCoursePanel;
    private javax.swing.JButton addCourseButton;
    private javax.swing.JLabel addCourseLabel;
    private javax.swing.JButton addStudentButton;
    private javax.swing.JLabel addStudentLabel;
    private javax.swing.JButton addStudentToCourseButton;
    private javax.swing.JLabel addStudentToCourseLabel;
    public static javax.swing.JPanel addStudentToCoursePanel;
    private javax.swing.JLabel batchLabel;
    private javax.swing.JTextField batchTextField;
    public javax.swing.JComboBox batchToBeAddedComboBox;
    private javax.swing.JLabel batchToBeAddedLabel;
    private javax.swing.JLabel batchToBeAddedLabel1;
    private javax.swing.JComboBox batchToBeDeletedComboBox;
    private javax.swing.JComboBox courseCodeComboBox;
    private javax.swing.JLabel courseCodeLabel;
    private javax.swing.JTextField courseCodeTextField;
    private javax.swing.JLabel courseCreditLabel;
    private javax.swing.JTextField courseCreditTextField;
    public static javax.swing.JPanel courseEntryPanel;
    private javax.swing.JLabel courseTitleLabel;
    private javax.swing.JTextField courseTitleTextfield;
    public javax.swing.JComboBox courseToBeAddedComboBox;
    private javax.swing.JLabel courseToBeAddedLabel;
    private javax.swing.JLabel courseToBeAddedLabel1;
    private javax.swing.JComboBox courseToBeAddedPerStudentComboBox;
    private javax.swing.JLabel courseToBeAddedPerStudentLabel;
    private javax.swing.JLabel courseToBeAddedPerStudentLabel1;
    private javax.swing.JComboBox courseToBeDeletedPerBatchComboBox;
    private javax.swing.JComboBox courseToBeDeletedPerStudentComboBox;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JComboBox dayComboBox;
    private javax.swing.JButton deleteBatchFromCourseButton;
    private javax.swing.JLabel deleteBatchFromCourseLable;
    public static javax.swing.JPanel deleteBatchFromCoursePanel;
    private javax.swing.JButton deleteStudentFromCourseButton;
    private javax.swing.JLabel deleteStudentFromCourseLabel;
    public static javax.swing.JPanel deleteStudentFromCoursePanel;
    private javax.swing.JButton giveAttendanceButton;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JButton matchFingerprintButton;
    private javax.swing.JLabel matchFingerprintLabel;
    public static javax.swing.JPanel matchFingerprintPanel;
    private javax.swing.JComboBox monthComboBox;
    private javax.swing.JButton nextTakeAttendancetButton;
    private javax.swing.JComboBox numberOfAttendanceComboBox;
    private javax.swing.JLabel numberOfAttendanceLabel;
    private javax.swing.JLabel regLabel;
    private javax.swing.JLabel regNoToBeAddedLabel;
    private javax.swing.JLabel regNoToBeAddedLabel1;
    private javax.swing.JTextField regNoToBeAddedTextfield;
    private javax.swing.JTextField regNoToBeDeletedTextField;
    private javax.swing.JTextField regTextField;
    private javax.swing.JLabel stuNameLabel;
    private javax.swing.JTextField stuNameTextField;
    public static javax.swing.JPanel studentEntryPanel;
    private javax.swing.JLabel takeAttendanceCourseCodeLabel;
    private javax.swing.JLabel takeAttendanceLabel;
    public static javax.swing.JPanel takeAttendancePanel;
    private javax.swing.JComboBox yearComboBox;
    // End of variables declaration//GEN-END:variables
}
