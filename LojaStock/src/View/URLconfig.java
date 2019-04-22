/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import metodos.ValidarFloat;
import sun.misc.Version;

/**
 *
 * @author USSIMANE
 */
public class URLconfig extends javax.swing.JFrame {

    String ss;

    /**
     * Creates new form URLconfig
     */
    public URLconfig() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        this.setLocationRelativeTo(null);
        txip.setDocument(new ValidarFloat());
        jButton2.setVisible(false);
//        File arquivo = new File("Farmacia/META-INF/persistence.xml");
        File arquivo = new File("C:\\Users\\Ussimane\\Documents\\Farmacia\\src\\META-INF\\persistence.xml");
        if (arquivo.exists()) {
            System.out.println("exist");
        } else {
            JOptionPane.showMessageDialog(null, "Ficheiro de Configuracao nao existe!", "Erro", JOptionPane.WARNING_MESSAGE);
        }
        String url = null;
        try {
             RandomAccessFile raf = new RandomAccessFile(arquivo, "rw");
            raf.seek(1080); //linha que contem o numero de IP
            System.out.println(raf.readLine());
            url = raf.readLine();
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = url.indexOf(":");
        ss = url.substring(0, i);
        txip.setText(ss);
       // System.out.println(url);
       // System.out.println(url.substring(0, i));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txip = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));

        labelTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelTitle.setText("Configuracao de Conexão");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelTitle)
                .addGap(115, 115, 115))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_ok.png"))); // NOI18N
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("IP:");

        jButton2.setText("Testar Conexao");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txip, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txip, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        File arquivo = new File("Farmacia/META-INF/persistence2.xml");
//        File arquivo2 = new File("Farmacia/META-INF/persistence.xml");
        File arquivo = new File("C:\\Users\\Ussimane\\Documents\\Farmacia\\src\\META-INF\\persistence2.xml");
        File arquivo2 = new File("C:\\Users\\Ussimane\\Documents\\Farmacia\\src\\META-INF\\persistence.xml");
        if (arquivo.exists()) {

            System.out.println("exist");
        } else {
            JOptionPane.showMessageDialog(null, "Ficheiro de Configuracao nao existe!", "Erro", JOptionPane.WARNING_MESSAGE);
        }
        if (arquivo2.exists()) {
            System.out.println("exist");
            arquivo2.delete();
            try {
                arquivo2.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                arquivo2.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            RandomAccessFile raf = new RandomAccessFile(arquivo, "rw");
            RandomAccessFile raf2 = new RandomAccessFile(arquivo2, "rw");
            int c = 0;
            while (raf.getFilePointer() != raf.length()) {
                String url = raf.readLine();
                if (c == 19) {//linha que contem o numero de ip
                    int i = url.indexOf(":") + 14;
                    //int f = url.length();
                    String urli = url.substring(0, i);
                    String urlf = ":5433/vendas\"/>";//url.substring(i + ss.length(), f);
                    url = urli + txip.getText() + urlf;
                }
                raf2.write(url.getBytes());
                raf2.writeBytes("\n");
                ++c;
            }
            ss = txip.getText();
            raf.close();
            raf2.close();
            JOptionPane.showMessageDialog(null, "Configurado com sucesso");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
        }

//         BufferedReader br = new BufferedReader(fr);  
//         int i = 0;
//        try {
//            while (br.ready()) {
//                String linha = br.readLine();
//                if(linha.contains("jdbc:postgresql:")){
//                    System.out.println(linha);
//                    System.out.println("indice: "+i);
//                }
//               
//                ++i;
//            }
//            br.close(); 
//            fr.close(); 
//            //  JOptionPane.showMessageDialog(this, "Modificado com Sucesso");
//            // this.setVisible(false);
//        } catch (IOException ex) {
//            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FarmaciaPU");
        EntityManager em = null;
        String ex = null;
        try {
            emf.createEntityManager();
        } catch (Exception e) {
            ex = e.getMessage();
        } finally {
          
            if (!ex.isEmpty()) {
                JOptionPane.showMessageDialog(null, "A conexao nao foi enctontrada.,\n "
                        + "Verifique se o computador esta conectado a Rede e\n"
                        + "Certifique que o Servidor esteja ligado", "Atencao", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Conectado com sucesso");
            }
            em.close();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(URLconfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(URLconfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(URLconfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(URLconfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new URLconfig().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JTextField txip;
    // End of variables declaration//GEN-END:variables
}
