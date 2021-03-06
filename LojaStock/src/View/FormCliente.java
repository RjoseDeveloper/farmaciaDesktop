/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import controller.ProdutoJpaController;
import controller.VendaJpaController;
import controller.ClienteJpaController;
import controller.EmpresaJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import metodos.ValidarFloat;
import metodos.ValidarInteiro;
import metodos.ValidarString;
import modelo.Produto;
import modelo.Cliente;
import modelo.Empresa;

/**
 *
 * @author Ussimane
 */
public class FormCliente extends javax.swing.JDialog {

    /**
     * Creates new form FormProduto
     */
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    private static Cliente p;
    private static String t;
    private static int posi;
    private static TableModel tm;
    private int pos;

    public FormCliente(java.awt.Frame parent, Cliente p, TableModel tm, int pos, String t, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        //  this.setUndecorated(true);
        this.getRootPane().setDefaultButton(jButton1);
        txtIdP.setVisible(false);
        txtNuit.setDocument(new ValidarInteiro());
        this.setLocationRelativeTo(null);
        this.p = p;
        this.t = t;
        this.tm = tm;
        this.pos=pos;
        labelTitle.setText(t);
        lbValidacao.setVisible(false);
        combobox(cbemp);
        if (p != null) {
          //  tm.setValueAt("sfsdfsdfsdfsdf", 1, 1);
            //Boolean b = new VendaJpaController(emf).getExistC(p);
            txtIdP.setText(p.getIdcliente() + "");
            txtNuit.setText(p.getCodigo());
            txtcel1.setText(p.getCell());
            cbemp.setSelectedItem(p.getIdempresa());
            txtenderec.setText(p.getEndereco());
            txtemail.setText(p.getEmail());
            txtresp.setText(p.getNome());
            chEstado.setSelected(!p.getEstado());
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

        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        lbValidacao = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel8 = new javax.swing.JLabel();
        txtNuit = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtresp = new javax.swing.JTextField();
        cbemp = new javax.swing.JComboBox();
        btadfam = new javax.swing.JButton();
        chEstado = new javax.swing.JCheckBox();
        txtenderec = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtcel1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtIdP = new javax.swing.JTextField();
        jButton1 = new keeptoo.KButton();
        jButton2 = new keeptoo.KButton();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("dialogProd"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        labelTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(labelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );

        lbValidacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbValidacao.setForeground(new java.awt.Color(255, 51, 51));
        lbValidacao.setText(".");

        kGradientPanel1.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setkStartColor(new java.awt.Color(204, 153, 255));

        jLabel8.setText("Email:");

        jLabel5.setText("Nome:");

        cbemp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btadfam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btadfam.setToolTipText("Adicionar nova Empresa");
        btadfam.setBorder(null);
        btadfam.setBorderPainted(false);
        btadfam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btadfamActionPerformed(evt);
            }
        });

        chEstado.setBackground(new java.awt.Color(204, 204, 255));
        chEstado.setText("Desabilitado");

        jLabel1.setText("Codigo:");

        jLabel4.setText("Telemovel:");

        jLabel2.setText(" Empresa:");

        jLabel3.setText("Endereco:");

        jButton1.setText("Guardar");
        jButton1.setkHoverColor(new java.awt.Color(51, 51, 51));
        jButton1.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        jButton1.setkSelectedColor(new java.awt.Color(0, 102, 102));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        jButton2.setkSelectedColor(new java.awt.Color(0, 102, 102));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtresp)
                                    .addComponent(txtenderec)))
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(15, 15, 15))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)))
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNuit)
                                    .addComponent(txtcel1)
                                    .addComponent(txtemail)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                                .addGap(0, 278, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                        .addComponent(chEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                        .addComponent(cbemp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btadfam, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(92, 92, 92))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNuit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtresp, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtenderec, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbemp, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btadfam, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chEstado)
                .addGap(11, 11, 11)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(26, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lbValidacao, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbValidacao))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void limpar() {
        txtIdP.setText("");
        txtNuit.setText("");
        txtcel1.setText("");
        // txtemp.setText("");
        txtenderec.setText("");
        txtemail.setText("");
        txtresp.setText("");
        lbValidacao.setVisible(false);
    }

    private void btadfamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btadfamActionPerformed
        FormEmpresa f = new FormEmpresa(new javax.swing.JFrame(), null, null, -1, "Adicionar Empresa", true);
        f.setVisible(true);
        combobox(cbemp);
    }//GEN-LAST:event_btadfamActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
         limpar();
        this.getParent().setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
         if (txtNuit.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira o codigo");
            txtNuit.requestFocusInWindow();
            return;
        }
//        if (txtemp.getText().trim().equals("")) {
//            lbValidacao.setVisible(true);
//            lbValidacao.setText("Insira o nome do Cliente / Empresa");
//            txtemp.requestFocusInWindow();
//            return;
//        }
        if (txtresp.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira o Nome");
            txtresp.requestFocusInWindow();
            return;
        }
        Empresa id = null;
        if (cbemp.getSelectedItem() == null) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Seleccione uma Empresa/organizacao");
            cbemp.requestFocusInWindow();
            return;
        } else {
            id = (Empresa) cbemp.getSelectedItem();
        }
        if (txtIdP.getText().trim().equals("")) {
            ClienteJpaController cc = new ClienteJpaController(emf);
            if (cc.existeCodigo(txtNuit.getText(), id) != 0) {
                lbValidacao.setVisible(true);
                lbValidacao.setText("Este codigo ja existe. Introduza outro");
                txtNuit.requestFocusInWindow();
                return;
            }
            Cliente pro = new Cliente();
            //  pro.setNuit(txtNuit.getText());
            pro.setCell(txtcel1.getText());
            pro.setCodigo(txtNuit.getText());
            pro.setIdempresa(id);
            pro.setEndereco(txtenderec.getText());
            pro.setEmail(txtemail.getText());
            pro.setNome(txtresp.getText());
            if (chEstado.isSelected()) {
                pro.setEstado(false);
            } else {
                pro.setEstado(true);
            }
            cc.create(pro);
            limpar();
            JOptionPane.showMessageDialog(this, "Adicionado com Sucesso");
        } else {
            int respo = JOptionPane.showOptionDialog(null, "Pretende modificar os dados deste Cliente?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            try {
                ClienteJpaController cc = new ClienteJpaController(emf);
                Cliente pro = cc.findCliente(Integer.parseInt(txtIdP.getText()));
                String c = pro.getCodigo();
                if ((!c.equals(txtNuit.getText())) && cc.existeCodigo(txtNuit.getText(), id) != 0) {
                    lbValidacao.setVisible(true);
                    lbValidacao.setText("Este codigo ja existe. Introduza outro");
                    txtNuit.requestFocusInWindow();
                    return;
                }
//                pro.setNuit(txtNuit.getText());
                pro.setCell(txtcel1.getText());
                pro.setCodigo(txtNuit.getText());
                Empresa ide = (Empresa) cbemp.getSelectedItem();
                pro.setIdempresa(ide);
                pro.setEndereco(txtenderec.getText());
                pro.setEmail(txtemail.getText());
                pro.setNome(txtresp.getText());
                if (chEstado.isSelected()) {
                    pro.setEstado(false);
                } else {
                    pro.setEstado(true);
                }
                new ClienteJpaController(emf).edit(pro);
                tm.setValueAt(pro, pos, 0);
                tm.setValueAt(pro.getCodigo(), pos, 1);
                tm.setValueAt(pro.getNome(), pos, 2);
                tm.setValueAt(pro.getEndereco(), pos, 3);
                tm.setValueAt(pro.getIdempresa().getNome(), pos, 4);
                tm.setValueAt(pro.getCell(), pos, 5);
                tm.setValueAt(pro.getEmail(), pos, 6);
                if (pro.getEstado()) {
                    tm.setValueAt("Sim", pos, 7);
                } else {
                    tm.setValueAt("Não", pos, 7);
                }
                limpar();
                JOptionPane.showMessageDialog(this, "Editado com Sucesso");
                this.setVisible(false);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(FormProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormCliente dialog = new FormCliente(new javax.swing.JFrame(), p, tm, posi, t, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public void combobox(JComboBox c) {
        List<Empresa> listaemp = new EmpresaJpaController(emf).findEmpresaEntities();
        c.removeAllItems();
        for (Empresa lista : listaemp) {
            c.addItem(lista);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btadfam;
    private javax.swing.JComboBox cbemp;
    private javax.swing.JCheckBox chEstado;
    private keeptoo.KButton jButton1;
    private keeptoo.KButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel lbValidacao;
    private javax.swing.JTextField txtIdP;
    private javax.swing.JTextField txtNuit;
    private javax.swing.JTextField txtcel1;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtenderec;
    private javax.swing.JTextField txtresp;
    // End of variables declaration//GEN-END:variables
}
