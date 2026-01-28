package sudoku;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class MainFrame extends javax.swing.JFrame {

    private ArrayList<ArrayList<SudokuBtn>> buttons = new ArrayList<ArrayList<SudokuBtn>>(); 
    private Sudoku sudoku;
    private int lastSelRow;
    private int lastSelCol;
    
    public MainFrame() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        setBackground(new Color(0,0,0));
        //setIconImage(new ImageIcon("/testjbutton/image/frameIcon.png").getImage());
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/sudoku/image/frameIcon.png")));
        congratJPanel1.setVisible(false);
        comboBoxSel1.addItem(new ComboBoxItem("Gampang","/sudoku/image/easy.png"));
        comboBoxSel1.addItem(new ComboBoxItem("Lumayan","/sudoku/image/medium.png"));
        comboBoxSel1.addItem(new ComboBoxItem("Sulit","/sudoku/image/hard.png"));
        comboBoxSel1.addItem(new ComboBoxItem("Sulit banget","/sudoku/image/very hard.png"));        
        lastSelRow = 0;
        lastSelCol = 0;
        sudoku = new Sudoku();
        for(int i=0;i<9;i++){
            buttons.add(new ArrayList<SudokuBtn>());
            for (int j = 0; j < 9; j++) {
                (buttons.get(i)).add(new SudokuBtn(0,i,j));
                SudokuBtn app = (buttons.get(i)).get(j);
                this.jPanel2.add(app);
                
                
                app.addActionListener(new java.awt.event.ActionListener() {
                                        
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        sudokuActionPerformed(evt);
                    }
                });
            }
        }
        //jPanel2.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        titleJPanel1 = new sudoku.TitleJPanel();
        newGameBtn = new javax.swing.JButton();
        checkCellSolBtn = new javax.swing.JButton();
        congratJPanel1 = new sudoku.CongratJPanel();
        comboBoxSel1 = new sudoku.ComboBoxSel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(153, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(9, 9));

        titleJPanel1.setBackground(new java.awt.Color(204, 255, 51));
        titleJPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        newGameBtn.setBackground(new java.awt.Color(0, 102, 102));
        newGameBtn.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        newGameBtn.setForeground(new java.awt.Color(33, 44, 116));
        newGameBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/image/newGame.png"))); // NOI18N
        newGameBtn.setText("Permainan Baru");
        newGameBtn.setMargin(new java.awt.Insets(3, 4, 3, 4));
        newGameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameBtnActionPerformed(evt);
            }
        });

        checkCellSolBtn.setBackground(new java.awt.Color(0, 102, 102));
        checkCellSolBtn.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        checkCellSolBtn.setForeground(new java.awt.Color(33, 44, 116));
        checkCellSolBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sudoku/image/checkCell.png"))); // NOI18N
        checkCellSolBtn.setText("<html>Cek sell<br>Solusi</html>");
        checkCellSolBtn.setIconTextGap(10);
        checkCellSolBtn.setMargin(new java.awt.Insets(3, 4, 3, 4));
        checkCellSolBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCellSolBtnActionPerformed(evt);
            }
        });

        comboBoxSel1.setBackground(new java.awt.Color(0, 102, 102));
        comboBoxSel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxSel1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 0, 204));
        jButton1.setText("Keluar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newGameBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxSel1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkCellSolBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(congratJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(titleJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(newGameBtn)
                .addGap(12, 12, 12)
                .addComponent(comboBoxSel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(checkCellSolBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(congratJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxSel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxSel1ActionPerformed

    private void checkCellSolBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCellSolBtnActionPerformed
        //sudoku.printSudokuToSolve();
        //sudoku.printSudoku();
        //System.out.println(congratJPanel1.isVisible());
        //System.out.println(sudoku.checkExist());
        SudokuBtn app = (buttons.get(lastSelRow)).get(lastSelCol);
        if(!congratJPanel1.isVisible() && sudoku.checkExist() && app.getIsEnabled()){

            int[][] sol = sudoku.getSolution();
            if (sol[lastSelRow][lastSelCol] == app.getValue()) {
                app.setBackground(new Color(0,102,0));
            }else{
                app.setBackground(Color.RED);
            }
        }
    }//GEN-LAST:event_checkCellSolBtnActionPerformed

    private void newGameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameBtnActionPerformed
        if (congratJPanel1.isVisible() || !sudoku.checkExist()) {
            newGame();
        } else {
            int decision = JOptionPane.showConfirmDialog(jPanel1, "Keluar dari permainan dan buat baru?","Game Baru",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if (decision == 0) {
                newGame();
            }
        }

    }//GEN-LAST:event_newGameBtnActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
       
            
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
         setDefaultCloseOperation(EXIT_ON_CLOSE);
        
          
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int response= JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin keluar?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if (response==0){
        System.exit(0);        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void newGame(){
        congratJPanel1.setVisible(false);
        int num = comboBoxSel1.getSelectedIndex();
        int k;
        switch (num) {
            case 0:
                k = 5;
                break;
            case 1:
                k = 51;
                break;
            case 2:
                k = 61;
                break;
            case 3:
                k = 71;
                break;        
            default:
                throw new AssertionError();
        }
 
        sudoku.createSudoku(k);
        int[][] tabToSolve = sudoku.getTabToSolve();
        for (int i = 0; i < 9; i++) {
            ArrayList<SudokuBtn> currRow = buttons.get(i);
            for (int j = 0; j < 9; j++) {
                SudokuBtn currBtn = currRow.get(j);
                if(tabToSolve[i][j] == 0){
                    currBtn.setIsEnabled(true);
                    //currBtn.setBackground(new Color(255,255,255));
                    currBtn.setBackground(new JButton().getBackground());
                }else{
                    currBtn.setIsEnabled(false);
                    currBtn.setBackground(new Color(0,0,0));
                }
                currBtn.setValue(tabToSolve[i][j]);
            }
        }        
    }
    
    
    private void sudokuActionPerformed(java.awt.event.ActionEvent evt){
        SudokuBtn app = (SudokuBtn)evt.getSource();
        if(app.getIsEnabled()){
            if(!(app.getBackground().equals(new JButton().getBackground()))){
                app.setBackground(new JButton().getBackground());
            }
            app.updateValue();
            sudoku.updateTabToSolve(app.getValue(), app.getRowId(), app.getColId());
            if(sudoku.checkSolution() && sudoku.checkExist()){
                for (int i = 0; i < 9; i++) {
                ArrayList<SudokuBtn> currRow = buttons.get(i);
                    for (int j = 0; j < 9; j++) {
                        SudokuBtn currBtn = currRow.get(j);
                        currBtn.setIsEnabled(false);
                        currBtn.setBackground(new Color(0,102,0));

                    }   
                }
                congratJPanel1.setVisible(true);
            }
            
            lastSelRow = app.getRowId();
            lastSelCol = app.getColId();
        }

    }
    
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton checkCellSolBtn;
    private sudoku.ComboBoxSel comboBoxSel1;
    private sudoku.CongratJPanel congratJPanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton newGameBtn;
    private sudoku.TitleJPanel titleJPanel1;
    // End of variables declaration//GEN-END:variables
}
