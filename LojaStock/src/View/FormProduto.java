/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import controller.FamiliaJpaController;
import controller.ProdutoJpaController;
import controller.ImpostoJpaController;
import controller.VendaJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import metodos.ValidarFloat;
import metodos.ValidarInteiro;
import modelo.Familia;
import modelo.Imposto;
import modelo.Produto;

/**
 *
 * @author Ussimane
 */
public class FormProduto extends javax.swing.JDialog {

    /**
     * Creates new form FormProduto
     */
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    private static Produto p;
    private static String t;
    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;

    public FormProduto(java.awt.Frame parent, Produto p, String t, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        //  this.setUndecorated(true);
        this.getRootPane().setDefaultButton(jButton1);
        txtIdP.setVisible(false);
//        txtPreco.setDocument(new ValidarFloat());
//        txtmrg.setDocument(new ValidarFloat());
//        lbpv.setDocument(new ValidarFloat());
        txtcod.setDocument(new ValidarInteiro());
//        txitem.setDocument(new ValidarInteiro());
//        pprecou.setVisible(false);
        //  lbpv.setText("0.0");
//        combobox(cbimp);
        combobox2(cbf);
        //   txtQntP.setDocument(new ValidarInteiro());
        this.setLocationRelativeTo(null);
        this.p = p;
        this.t = t;
        labelTitle.setText(t);
        lbValidacao.setVisible(false);
        if (p != null) {
            Boolean b = new VendaJpaController(emf).getExist(p);
            txtIdP.setText(p.getIdproduto() + "");
            txtcod.setText(p.getCodigo());
            txtNProd.setText(p.getNome());
//            txitem.setText(p.getQitem() + "");
//            if (p.getQitem() > 1) {
//                pprecou.setVisible(true);
//            }
            cbf.setSelectedItem(p.getIdfamilia());
//            if (!b) {
//                txtNProd.setEditable(false);
//            }
//            cbimp.setSelectedItem(p.getIdimposto());
//            txtcusto.setText(String.valueOf(p.getCusto()));
//            txtmrg.setText(String.valueOf(p.getMargem()));
//            txtPreco.setText(String.valueOf(p.getMargem() * p.getCusto()));
//            lbpv.setText(String.valueOf(p.getPrecovenda()));
            //   txtQntP.setText(p.getQtdvenda()+"");               
        }
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinField1 = new com.toedter.components.JSpinField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        txtIdP = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        lbValidacao = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtNProd = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtcod = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btedfam = new javax.swing.JButton();
        btadfam = new javax.swing.JButton();
        btdelfam = new javax.swing.JButton();
        cbf = new javax.swing.JComboBox();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("dialogProd"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(238, 237, 237));

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
            .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        lbValidacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbValidacao.setForeground(new java.awt.Color(255, 51, 51));
        lbValidacao.setText("jLabel4");

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setForeground(new java.awt.Color(255, 51, 0));
        jLabel1.setText("Descricao:");

        jLabel4.setForeground(new java.awt.Color(255, 51, 0));
        jLabel4.setText("Codigo:");

        jLabel8.setForeground(new java.awt.Color(255, 51, 0));
        jLabel8.setText("Categoria:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 134, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNProd, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(txtcod)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addGap(9, 9, 9)
                .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNProd, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_ok.png"))); // NOI18N
        jButton1.setText("Aceitar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_cancel.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btedfam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"))); // NOI18N
        btedfam.setToolTipText("Editar familia seleccionada");
        btedfam.setBorder(null);
        btedfam.setBorderPainted(false);
        btedfam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btedfamActionPerformed(evt);
            }
        });

        btadfam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btadfam.setToolTipText("Adicionar nova familia");
        btadfam.setBorder(null);
        btadfam.setBorderPainted(false);
        btadfam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btadfamActionPerformed(evt);
            }
        });

        btdelfam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"))); // NOI18N
        btdelfam.setToolTipText("Apagar familia sellecionada");
        btdelfam.setBorder(null);
        btdelfam.setBorderPainted(false);
        btdelfam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdelfamActionPerformed(evt);
            }
        });

        cbf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cbf, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btadfam, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btedfam, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btdelfam, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(lbValidacao, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(65, 65, 65)
                            .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbValidacao)
                    .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btdelfam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btedfam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btadfam, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbf, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void combobox(JComboBox c) {
        List<Imposto> listai = new ImpostoJpaController(emf).findImpostoEntities();
        c.removeAllItems();
        for (Imposto lista : listai) {
            c.addItem(lista);
        }
    }

    public void combobox2(JComboBox c) {
        List<Familia> listaf = new FamiliaJpaController(emf).findFamiliaEntities();
        c.removeAllItems();
        for (Familia lista : listaf) {
            c.addItem(lista);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtcod.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira o codigo do Produto");
            txtcod.requestFocusInWindow();
            return;
        }

        if (txtNProd.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insire o nome do Produto");
            txtNProd.requestFocusInWindow();
            return;
        }

//        if (txitem.getText().trim().equals("")) {
//            lbValidacao.setVisible(true);
//            lbValidacao.setText("Insire a quantidade de Itens");
//            txitem.requestFocusInWindow();
//            return;
//        } else if (Integer.parseInt(txitem.getText()) == 0) {
//            lbValidacao.setVisible(true);
//            lbValidacao.setText("Insire a quantidade de Itens");
//            txitem.requestFocusInWindow();
//            return;
//        }
//        if (pprecou.isVisible()) {
//            if (txtcusto.getText().trim().equals("")) {
//                lbValidacao.setVisible(true);
//                lbValidacao.setText("Insira o custo unitario");
//                txtcusto.requestFocusInWindow();
//                return;
//            }
//
//            if (txtmrg.getText().trim().equals("")) {
//                lbValidacao.setVisible(true);
//                lbValidacao.setText("Insira a margem de lucro");
//                txtmrg.requestFocusInWindow();
//                return;
//            }
//            if (txtPreco.getText().trim().equals("")) {
//                lbValidacao.setVisible(true);
//                lbValidacao.setText("Insira o preco unitario");
//                txtPreco.requestFocusInWindow();
//                return;
//            }
//        }
        if (txtIdP.getText().trim().equals("")) {
            ProdutoJpaController cc = new ProdutoJpaController(emf);
            if (cc.existeCodigo(txtcod.getText()) != 0) {
                lbValidacao.setVisible(true);
                lbValidacao.setText("Este codigo ja existe!");
                txtcod.requestFocusInWindow();
                return;
            }
            Produto pro = new Produto();
            pro.setCodigo(txtcod.getText());
            pro.setNome(txtNProd.getText());
            pro.setMargem(new Float(0));
            pro.setCusto(new Float(0));
//            pro.setQitem(Integer.parseInt(txitem.getText()));
//            if (pprecou.isVisible()) {
//                pro.setPrecovenda(Float.parseFloat(lbpv.getText()));
//                pro.setMargem(Float.parseFloat(txtmrg.getText()));
//                pro.setCusto(Float.parseFloat(txtcusto.getText()));
//            } else {
//                pro.setPrecovenda(0);
//                pro.setMargem(new Float(0));
//                pro.setCusto(new Float(0));
//            }
            pro.setIdfamilia((Familia) cbf.getSelectedItem());
//            pro.setIdimposto((Imposto) cbimp.getSelectedItem());
            pro.setQtdvenda(0);
            cc.create(pro);
            
            JOptionPane.showMessageDialog(this, "Adicionado com Sucesso");
            limpar();
//            FormEntrada f = new FormEntrada(new javax.swing.JFrame(), pro, null, "Entrada no Sotck", true);
//            f.setVisible(true);
        } else {
            int respo = JOptionPane.showOptionDialog(null, "Pretende modificar os dados deste Produto?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            ProdutoJpaController cc = new ProdutoJpaController(emf);
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();/////////////////////////////
            try {
                Produto pro = new ProdutoJpaController(emf).findProdutoS(Integer.parseInt(txtIdP.getText()), em);
                String c = pro.getCodigo();
                if ((!c.equals(txtcod.getText())) && cc.existeCodigo(txtcod.getText()) != 0) {
                    lbValidacao.setVisible(true);
                    lbValidacao.setText("Este codigo ja existe!");
                    txtcod.requestFocusInWindow();
                    return;
                }

                pro.setCodigo(txtcod.getText());
                pro.setNome(txtNProd.getText());
//                if (pprecou.isVisible()) {
//                    pro.setPrecovenda(Float.parseFloat(lbpv.getText()));
//                    pro.setMargem(Float.parseFloat(txtmrg.getText()));
//                    pro.setCusto(Float.parseFloat(txtcusto.getText()));
//                } else {
//                    pro.setPrecovenda(0);
//                    pro.setMargem(new Float(0));
//                    pro.setCusto(new Float(0));
//                }
                pro.setIdfamilia((Familia) cbf.getSelectedItem());
//                pro.setIdimposto((Imposto) cbimp.getSelectedItem());
//                pro.setQitem(Integer.parseInt(txitem.getText()));

                //pro.setQtdvenda(0);
                cc.edit(pro, pro, em);
                em.getTransaction().commit();
                JOptionPane.showMessageDialog(this, "Editado com Sucesso");
            } catch (NonexistentEntityException ex) {
                em.clear();
                em.getTransaction().commit();
                Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                //   em.getTransaction().rollback();
                em.clear();
                em.getTransaction().commit();
                Logger.getLogger(FormAddEntrada.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (em != null) {
                    em.close();
                }
            }
            this.setVisible(false);
        }

    }//GEN-LAST:event_jButton1ActionPerformed
    public void limpar() {
        txtNProd.setText("");
//        txtPreco.setText("");
        txtcod.setText("");
        txtIdP.setText("");
//        txtcusto.setText("");
//        txtmrg.setText("");
//        lbpv.setText("");
//        txitem.setText("");
        lbValidacao.setVisible(false);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpar();
        this.getParent().setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btdelfamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdelfamActionPerformed
        Familia i = (Familia) cbf.getSelectedItem();
        //if (txtIdP.getText().trim().equals("")) {
        if (new ProdutoJpaController(emf).existeFamilia(i) != 0) {
            JOptionPane.showMessageDialog(this, "Esta Familia esta em uso! Nao pode ser apagada", "Atencao", JOptionPane.WARNING_MESSAGE);
            return;
            // }
        } else {
            try {
                new FamiliaJpaController(emf).destroy(i.getIdfamilia());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this, "Familia apagada com Sucesso");
        }
        combobox2(cbf);
    }//GEN-LAST:event_btdelfamActionPerformed

    private void btedfamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btedfamActionPerformed
        FormFamilia f = new FormFamilia(new javax.swing.JFrame(), (Familia) cbf.getSelectedItem(), "Editar Familia", true);
        f.setVisible(true);
        combobox2(cbf);
    }//GEN-LAST:event_btedfamActionPerformed

    private void btadfamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btadfamActionPerformed
        FormFamilia f = new FormFamilia(new javax.swing.JFrame(), null, "Adicionar Familia", true);
        f.setVisible(true);
        combobox2(cbf);
    }//GEN-LAST:event_btadfamActionPerformed

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
                FormProduto dialog = new FormProduto(new javax.swing.JFrame(), p, t, true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btadfam;
    private javax.swing.JButton btdelfam;
    private javax.swing.JButton btedfam;
    private javax.swing.JComboBox cbf;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.components.JSpinField jSpinField1;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel lbValidacao;
    private javax.swing.JTextField txtIdP;
    private javax.swing.JTextField txtNProd;
    private javax.swing.JTextField txtcod;
    // End of variables declaration//GEN-END:variables
}
