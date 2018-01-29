/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Staff;

import Admin.connectionDB;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import java.sql.*;

/**
 *
 * @author redhwan
 */
public class Patient_payment extends javax.swing.JFrame {

    
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst =null;
    String patient_id ;
    long treatment_id;
    String total;
    String total_pay;
    String amount;
    String ID_staff;
    double check;
    /**
     * Creates new form Patient_payment
     */
    public Patient_payment() {
        initComponents();
        con=connectionDB.DBconnection();
        date();
        
    }
    
    public void date()
    {
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        payment_date.setText("Date "+year+"/"+(month+1)+"/"+day);
        
          
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hour = cal.get(Calendar.HOUR);
        payment_time.setText("Time "+hour+":"+(minute)+":"+second); 
    
    }
    
     public void close()
    {
    
        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    
     
     public boolean ID()
     {
         this.ID_staff = JOptionPane.showInputDialog("Enter staff ID to CONFIRM :");
     
     String sql="select ID from staff where ID=? ;";
        try 
        {
        
            pst = con.prepareStatement(sql);
            pst.setString(1,ID_staff);
            rs = pst.executeQuery();
            if(rs.next())
                return true;
            else
                return false;
        }
        catch(Exception ex)
        {
             JOptionPane.showMessageDialog(null,ex);
        }
        return false;
     }
    
     
       private void update_table()
    {
        try
        {
            String sql = "select treatment_id,patient_id,price from treatment where patient_id=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, this.patient_id);
            rs = pst.executeQuery();
            table_payment.setModel(DbUtils.resultSetToTableModel(rs));
        
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
            
        }
        
        finally 
        {
            try 
            {
                rs.close();
                pst.close();
                
            }
            catch(Exception ex)
            {}
        
        }
      
    }
       
       
       public void ids()
     {
        try
        {
            String sql = "select MAX(payment_ID) as payment_ID from payment";
            pst = con.prepareStatement(sql);
            rs=pst.executeQuery();
            if (rs.next())
            {
                long appNO = rs.getLong("payment_ID");
                String app = Long.toString(appNO+1);
                id_payment.setText(app);
                patient_payment.setText(patient_id);
                
                Calendar cal = new GregorianCalendar();
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int year = cal.get(Calendar.YEAR);
                    int minute = cal.get(Calendar.MINUTE);
                    int hour = cal.get(Calendar.HOUR);
                    date_payment.setText(""+year+"/"+(month+1)+"/"+day+" "+hour+":"+(minute));
            }
        } 
        catch(Exception ex ) 
        {
            JOptionPane.showMessageDialog(null,ex);
        }



     }
       
     private void total()
     {
     
         try
         {
     
            String sql = "select price from treatment where patient_id=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, this.patient_id);
            rs = pst.executeQuery();
            
            if (rs.next())
            {
                String prices1 = rs.getString("price");
                double prices2 = Double.parseDouble(prices1);
                this.total += prices2;
                total_payment.setText(""+prices2);
            }
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
            
        }
     
     
     }
       
     private void total_payment()
     {
     
         try
         {
     
            String sql = "select total_pay from patient where ID=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, this.patient_id);
            rs = pst.executeQuery();
            
            if (rs.next())
            {
                this.total_pay = rs.getString("total_pay");
                 JOptionPane.showMessageDialog(null,"total_pay"+ this.total_pay);
            }
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
            
        }
     
     
     }
     
     
     private void update_total_pay ()
     {
       
         
        if (this.total_pay == null)
         {
            try 
             {
               
                double too = Double.parseDouble(this.amount);
                 String sql = "update patient SET total_pay = '"+(too)+"' where ID ='"+this.patient_id+"'";
                 pst = con.prepareStatement(sql);
                 pst.execute();

                    JOptionPane.showMessageDialog(null, "Updated");
                    this.check=too;
             }
             catch(Exception ex)
             {
                 JOptionPane.showMessageDialog(null, ex);
             }
       }
         
         else 
         {
         
         try 
             {
               double to = Double.parseDouble(this.total_pay);
               double too = Double.parseDouble(this.amount);
               double tooo = to+too;
                 String sql = "update patient SET total_pay = '"+(tooo)+"' where ID ='"+this.patient_id+"'";
                 pst = con.prepareStatement(sql);
                 pst.execute();

                    JOptionPane.showMessageDialog(null, "Updated");
                    this.check=tooo;
             }
             catch(Exception ex)
             {
                 JOptionPane.showMessageDialog(null, ex);
             }
         
        
         }
     }
     
     
      private void clear_pay()
     {
         String s = this.total;
         double total_payment = Double.parseDouble(s);
         if (check >= total_payment)
         {
         String sql = "delete from treatment where patient_id=?";
        
        try 
            {
                    pst = con.prepareStatement(sql);
                    pst.setString(1, this.patient_id);
                    pst.execute();
                
                    JOptionPane.showMessageDialog(null,"Full Payment");
                
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null,ex);
            }
         
         }
     }
     
       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        search_payment = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        id_payment = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        patient_payment = new javax.swing.JLabel();
        amount_payment = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        date_payment = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        total_payment = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        set_price = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_payment = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        paid_payment = new javax.swing.JLabel();
        outstandingl_payment = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        payment_date = new javax.swing.JMenu();
        payment_time = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        search_payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_paymentActionPerformed(evt);
            }
        });
        search_payment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_paymentKeyReleased(evt);
            }
        });

        jLabel1.setText("Search ( Patient  ) ID");

        jLabel3.setText("Payment ID");

        jLabel4.setText("Patient ID");

        jLabel6.setText("Date / time");

        jLabel7.setText("Amount");

        jButton1.setText("Pay");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("History");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setText("Total :");

        total_payment.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel5.setText("Price");

        jToggleButton1.setText("Set price");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        table_payment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_payment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_paymentMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_payment);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel8.setText("Paid  :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel9.setText("Out Standing :");

        jMenu1.setText("File");

        jMenuItem1.setText("Main menu");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        payment_date.setText("Date");
        jMenuBar1.add(payment_date);

        payment_time.setText("Time");
        jMenuBar1.add(payment_time);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(search_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(amount_payment)
                                    .addComponent(id_payment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(patient_payment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(date_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(set_price, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButton1)
                        .addGap(144, 144, 144)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(paid_payment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(total_payment, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(44, 44, 44)
                        .addComponent(outstandingl_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(search_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(patient_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(date_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(amount_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(total_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(set_price, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton1))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paid_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(outstandingl_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void search_paymentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_paymentKeyReleased
        // TODO add your handling code here:
        try 
        {
        
        
        String sql = "select * from treatment where patient_id=?";
        pst = con.prepareStatement(sql);
        pst.setString(1, search_payment.getText());
        
        rs=pst.executeQuery();
        
        if(rs.next())
        {
            this.patient_id = search_payment.getText();
            update_table();
            ids();
            total();
            total_payment();
        
        }
        
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }//GEN-LAST:event_search_paymentKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        close();
        Payment_History p = new Payment_History();
        p.setVisible(true);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void search_paymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_paymentActionPerformed
        // TODO add your handling code here:
        
        
        
    }//GEN-LAST:event_search_paymentActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        try 
        {
        
            String value1 = set_price.getText();
            double value2 = Double.parseDouble(value1);
            String sql = "update treatment SET price='"+value2+"' where treatment_id='"+this.treatment_id+"'";
            
            pst = con.prepareStatement(sql);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Updated");
               update_table();
               total();
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        
        if (ID())
        {
        
        try 
        {
            String sql = "insert into payment (patient_id,date_time,amount,staff_id) values (?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, patient_payment.getText());
            pst.setString(2, date_payment.getText());
            pst.setString(3, amount_payment.getText());
            pst.setString(4, this.ID_staff);
            this.amount= amount_payment.getText();
          
            pst.execute();
            update_total_pay();
            clear_pay();
            JOptionPane.showMessageDialog(null, "Saved");
            pst.close();
            
                
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
        finally 
        {
            try 
            {
                pst.close();
                
            }
            catch(Exception ex)
            {}
            
        }
    
        }
        else
           JOptionPane.showMessageDialog(null, "Staff ID NOT FOUND plese try again");
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void table_paymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_paymentMouseClicked
        
        
        try 
        {
        
        int row = table_payment.getSelectedRow();
        String Table_click = table_payment.getModel().getValueAt(row, 0).toString();
        
        String sql = "select treatment_id,price from treatment where treatment_id='"+Table_click+"'";
        pst= con.prepareStatement(sql);
        rs=pst.executeQuery();
        
        if(rs.next())
        {
            this.treatment_id = Long.parseLong(rs.getString("treatment_id"));
            String add10 = rs.getString("price");
            set_price.setText(add10);
        
        }
        
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        
        
        
    }//GEN-LAST:event_table_paymentMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        close();
        Staff_main s = new Staff_main();
        s.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(Patient_payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Patient_payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Patient_payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Patient_payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Patient_payment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amount_payment;
    private javax.swing.JLabel date_payment;
    private javax.swing.JLabel id_payment;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel outstandingl_payment;
    private javax.swing.JLabel paid_payment;
    private javax.swing.JLabel patient_payment;
    private javax.swing.JMenu payment_date;
    private javax.swing.JMenu payment_time;
    private javax.swing.JTextField search_payment;
    private javax.swing.JTextField set_price;
    private javax.swing.JTable table_payment;
    private javax.swing.JLabel total_payment;
    // End of variables declaration//GEN-END:variables
}
