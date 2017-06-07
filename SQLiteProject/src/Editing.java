/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TA NI
 */
/*
public class Editing {
   private void doneAtttendanceGivingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doneAtttendanceGivingButtonActionPerformed
        try {
            // TODO add your handling code here:
            //get the student id from the detected student's reg
            String detectedStudentReg = attendanceInfo.getRegOfDetectedStudent();
            Home.opendb();

            String getStudentIdQuery = "SELECT ID FROM STUDENT WHERE reg = ?";

            Home.pst = Home.conn.prepareStatement(getStudentIdQuery);
            Home.pst.setString(1, detectedStudentReg);
            //Home.pst.execute();
            Home.rs = Home.pst.executeQuery();
            int detectedStudentId = Home.rs.getInt("ID");

            System.out.println("detectedStudentId=" + detectedStudentId);

            //get the course id from the given course code
            int courseId;
            String getCourseIdQuery = "SELECT ID FROM COURSE WHERE CODE = ?";
            Home.pst = Home.conn.prepareStatement(getCourseIdQuery);
            Home.pst.setString(1, takeAttendanceCourseCode);
            Home.rs = Home.pst.executeQuery();

            courseId = Home.rs.getInt("ID");

            //give attendance to that student in that day
            // if this combination does not exist in attendance table
            String attendanceTableCheckQuery = "SELECT ID FROM attendance WHERE student_id = ? AND course_id = ? AND date = ?";

            Home.pst = Home.conn.prepareStatement(attendanceTableCheckQuery);
            Home.pst.setInt(1, detectedStudentId);
            Home.pst.setInt(2, courseId);
            Home.pst.setString(3, date);
            Home.rs = Home.pst.executeQuery();

            if (Home.rs.next()) {
                JOptionPane.showMessageDialog(null, "this student already has attendance to this day at this course");
            } else {

                // if this combination exists in pivot table
                String pivotTableCheckQuery = "SELECT ID FROM pivot WHERE student_id = ? AND course_id = ?";
                Home.pst = Home.conn.prepareStatement(pivotTableCheckQuery);
                Home.pst.setInt(1, detectedStudentId);
                Home.pst.setInt(2, courseId);
                Home.rs = Home.pst.executeQuery();
                //if this student is in this course, it gets attendance
                if (Home.rs.next()) {
                    String giveAttendanceQuery = "INSERT INTO attendance (STUDENT_ID,COURSE_ID,DATE,ATTN) VALUES (?,?,?,?)";

                    System.out.println("conn " + Home.conn);

                    Home.pst = Home.conn.prepareStatement(giveAttendanceQuery);
                    Home.pst.setInt(1, detectedStudentId);
                    Home.pst.setInt(2, courseId);
                    Home.pst.setString(3, date);
                    Home.pst.setInt(4, numberOfAttendance);

                    Home.pst.execute();
                    //student added, so increment counter
                    countOfAttendance++;
                    currentStudentFormatLabel.setText("Saving format: " + currentStudentFormat + countOfAttendance + ".bmp");

                } else {
                    JOptionPane.showMessageDialog(null, "this student is not assigned in this course");
                }
            }

            Home.closedb();
        } catch (SQLException ex) {
            Logger.getLogger(AddFrame.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_doneAtttendanceGivingButtonActionPerformed
           
           
}
*/
