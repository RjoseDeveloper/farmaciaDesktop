/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import controller.EntradaJpaController;
import controller.FornecedorJpaController;
import controller.ImpostoJpaController;
import controller.ProdutoJpaController;
import controller.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import metodos.ValidarFloat;
import metodos.ValidarInteiro;
import modelo.Entrada;
import modelo.EntradaPK;
import modelo.Fornecedor;
import modelo.Imposto;
import modelo.Produto;
import modelo.Vendedor;

/**
 *
 * @author Ussimane
 */
public class FormEntrada extends javax.swing.JDialog {

    /**
     * Creates new form FormProduto
     */
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    private static String t;
    private static Entrada e;
    private static Produto p;
    private static int client = 0;

    public FormEntrada(java.awt.Frame parent, Produto p, Entrada e, String t, int c, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        txtIdP.setVisible(false);
        dpk.setVisible(false);
        this.e = e;
        this.t = t;
        this.p = p;
        this.client = c;
        comboboxp(comboprod);
//        cbimp.setSelectedItem(p.getIdimposto());
        txtprecc.setDocument(new ValidarFloat());
        txtQntP.setDocument(new ValidarInteiro());
//        txtmrg.setDocument(new ValidarFloat());
//        txtpv.setDocument(new ValidarFloat());
//        lbpv.setDocument(new ValidarFloat());
        txtqi1.setDocument(new ValidarInteiro());
        txtqi2.setDocument(new ValidarInteiro());
        txtqi3.setDocument(new ValidarInteiro());
        dataf.setDateFormatString("dd/MM/yyyy");
        datae.setDateFormatString("dd/MM/yyyy");
        txtpvd1.setDocument(new ValidarFloat());
        txt_margem_lucro.setDocument(new ValidarFloat());
        txtpvd3.setDocument(new ValidarFloat());
        labelAquisic.setVisible(false);
        txtprecc.setVisible(false);
        chPago.setVisible(false);
        //labeldataf.setVisible(false);
        dataf.setVisible(false);
        //labelprec3.setVisible(false);
        //labelqt3.setVisible(false);
        //labelcomposi.setVisible(false);
        txtf3.setVisible(false);
        txtpvd3.setVisible(false);
        txtqi3.setVisible(false);
        txtqi1.setText("1");
        txtqi2.setText("1");
        // lbpv.setText("0.0");
        txtf2.setVisible(false);
        labelTitle.setText(t);
        lbValidacao.setVisible(false);
        combobox(cbfor);
        comboboxi(cbimp);
//        txtmrg.setEnabled(false);
//        txtpv.setEnabled(false);
//        cbimp.setEnabled(false);
//        lbpv.setEnabled(false);
        this.getRootPane().setDefaultButton(jButton1);
        this.setLocationRelativeTo(null);
        if (e != null) {
            //Boolean b = new VendaJpaController(emf).getExistC(p);
            txtIdP.setText(e.getEntradaPK().getIdproduto() + "");
            dpk.setDate(e.getEntradaPK().getData());
            if (e.getCusto() != null) {
                txtprecc.setText(String.valueOf(e.getCusto()));
            }
            if (e.getDatacriacao() != null) {
                dataf.setDate(e.getDatacriacao());
            }
            txtSerie.setText(e.getSerie());
            cbfor.setSelectedItem(e.getIdfornecedor());
            comboprod.setSelectedItem(e.getProduto());
            if (e.getDatacriacao() != null) {
                dataf.setDate(e.getDatacriacao());
            }
            if (e.getDataexpiracao() != null) {
                datae.setDate(e.getDataexpiracao());
            }
//            txtpvd.setText(String.valueOf(e.getPreco()));
            txtQntP.setText(String.valueOf(e.getQtd()));
            txtQntP.setEditable(false);
//            txtmrg.setText(String.valueOf(p.getMargem()));
            //    txtpv.setText(String.valueOf(p.getMargem() * p.getCusto()));
            txtqi1.setText(String.valueOf(e.getQa()));
            txtpvd1.setText(String.valueOf(e.getPreco()));
            txtf1.setText(e.getNa());
            cbimp.setSelectedItem(e.getIdimposto());
            if (e.getPb() != null) {
                txtqi2.setText(String.valueOf(e.getQb()));
                txt_margem_lucro.setText(String.valueOf(e.getPb()));
                txtf2.setText(e.getNb());
            }
            if (e.getPc() != null) {
                txtqi3.setText(String.valueOf(e.getQc()));
                txtpvd3.setText(String.valueOf(e.getPc()));
                txtf3.setText(e.getNc());
            }
            txtcbarra.setText(e.getCbarra());
//            lbpv.setText(e);
            if (t.equals("Editar o Lote/Serie")) {
                comboprod.setEnabled(false);
                //  txtQntP.setEditable(false);
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

        jLabel7 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        lbValidacao = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtQntP = new javax.swing.JTextField();
        comboprod = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtpvd1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txt_margem_lucro = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        preco_venda = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtf1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtqi1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtqi2 = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        cbimp = new javax.swing.JComboBox();
        cbfor = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btadimp = new javax.swing.JButton();
        btedimp = new javax.swing.JButton();
        btdelimp = new javax.swing.JButton();
        btdelfor = new javax.swing.JButton();
        btedfor = new javax.swing.JButton();
        btadfam = new javax.swing.JButton();
        datae = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        txtprecc = new javax.swing.JTextField();
        labelAquisic = new javax.swing.JLabel();
        dpk = new com.toedter.calendar.JDateChooser();
        dataf = new com.toedter.calendar.JDateChooser();
        chPago = new javax.swing.JCheckBox();
        txtqi3 = new javax.swing.JTextField();
        txtf3 = new javax.swing.JTextField();
        txtIdP = new javax.swing.JTextField();
        txtpvd3 = new javax.swing.JTextField();
        txtf2 = new javax.swing.JTextField();
        txtcbarra = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jButton1 = new keeptoo.KButton();
        jButton2 = new keeptoo.KButton();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(getLimpaVenda());
        setName("dialogProd"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        labelTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        kGradientPanel2.setkEndColor(new java.awt.Color(204, 204, 255));
        kGradientPanel2.setkStartColor(new java.awt.Color(255, 255, 255));

        lbValidacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbValidacao.setForeground(new java.awt.Color(255, 51, 51));
        lbValidacao.setText(".");

        jLabel3.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 51, 0));
        jLabel3.setText("Serie/Lote Nr.:");

        comboprod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 51, 0));
        jLabel2.setText("Quantidade:");

        jLabel1.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 51, 0));
        jLabel1.setText("Produto:");

        txtpvd1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 204));
        jLabel24.setText("Preço de Compra:");

        txt_margem_lucro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txt_margem_lucro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_margem_lucroKeyReleased(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 204));
        jLabel18.setText("Margem de Lucro:");

        preco_venda.setFont(new java.awt.Font("Montserrat Alternates", 1, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 204));
        jLabel20.setText("Preco de Venda:");

        txtf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtf1ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 204));
        jLabel25.setText("Composição:");

        txtqi1.setText("1");
        txtqi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtqi1ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 204));
        jLabel26.setText("Qtv.");

        txtqi2.setText("1");
        txtqi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtqi2ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 0));
        jLabel15.setText("Impostos e Precos");

        cbimp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbimp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbimpItemStateChanged(evt);
            }
        });

        cbfor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbfor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbforActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 204));
        jLabel8.setText("Imposto:");

        jLabel9.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 204));
        jLabel9.setText("Fornecedor:");

        btadimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btadimp.setToolTipText("Adicionar novo imposto");
        btadimp.setBorder(null);
        btadimp.setBorderPainted(false);
        btadimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btadimpActionPerformed(evt);
            }
        });

        btedimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"))); // NOI18N
        btedimp.setToolTipText("Editar imposto seleccinado");
        btedimp.setBorder(null);
        btedimp.setBorderPainted(false);
        btedimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btedimpActionPerformed(evt);
            }
        });

        btdelimp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"))); // NOI18N
        btdelimp.setToolTipText("Apagar imposto seleccionado");
        btdelimp.setBorder(null);
        btdelimp.setBorderPainted(false);
        btdelimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdelimpActionPerformed(evt);
            }
        });

        btdelfor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"))); // NOI18N
        btdelfor.setToolTipText("Apagar imposto seleccionado");
        btdelfor.setBorder(null);
        btdelfor.setBorderPainted(false);
        btdelfor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btdelforActionPerformed(evt);
            }
        });

        btedfor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"))); // NOI18N
        btedfor.setToolTipText("Editar imposto seleccinado");
        btedfor.setBorder(null);
        btedfor.setBorderPainted(false);
        btedfor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btedforActionPerformed(evt);
            }
        });

        btadfam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_create.png"))); // NOI18N
        btadfam.setToolTipText("Adicionar novo Fornecedor");
        btadfam.setBorder(null);
        btadfam.setBorderPainted(false);
        btadfam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btadfamActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 204));
        jLabel6.setText("Data de Expriracao:");

        txtprecc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpreccKeyReleased(evt);
            }
        });

        labelAquisic.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        labelAquisic.setForeground(new java.awt.Color(0, 0, 204));
        labelAquisic.setText("Preco de aquisicao:");

        chPago.setText("Pago");
        chPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chPagoActionPerformed(evt);
            }
        });

        txtqi3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtqi3ActionPerformed(evt);
            }
        });

        txtpvd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpvd3ActionPerformed(evt);
            }
        });

        txtf2.setText("caixa");

        txtcbarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcbarraActionPerformed(evt);
            }
        });
        txtcbarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcbarraKeyReleased(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Candara", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 204));
        jLabel21.setText("Codigo de Barras:");

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addComponent(jSeparator5)
                .addContainerGap())
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(labelAquisic))
                .addGap(30, 30, 30)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addComponent(dataf, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(dpk, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(chPago, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbfor, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbimp, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprecc, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                        .addComponent(txtpvd3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtqi3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btadimp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btadfam, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btedfor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btedimp, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btdelfor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btdelimp, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))))
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtQntP)
                                .addComponent(comboprod, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtpvd1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_margem_lucro, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(preco_venda, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbValidacao, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                        .addGap(59, 59, 59)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtcbarra)
                            .addComponent(datae, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                                .addComponent(txtqi1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addComponent(txtqi2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSerie)
                            .addComponent(txtf1))))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addComponent(lbValidacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboprod, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQntP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtpvd1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(txtcbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(txtf1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_margem_lucro, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(preco_venda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addComponent(datae, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtqi1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtqi2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(7, 7, 7)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbimp, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbfor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addComponent(btadimp, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addComponent(btdelimp, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btadfam, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btdelfor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btedimp, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btedfor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtf2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtqi3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtf3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtpvd3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelAquisic)
                    .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(dpk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(kGradientPanel2Layout.createSequentialGroup()
                            .addComponent(txtprecc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                    .addGap(14, 14, 14)
                                    .addComponent(dataf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addComponent(chPago))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setPreferredSize(new java.awt.Dimension(923, 300));

        jButton1.setText("Guardar");
        jButton1.setkBackGroundColor(new java.awt.Color(0, 102, 102));
        jButton1.setkEndColor(new java.awt.Color(102, 102, 102));
        jButton1.setkHoverEndColor(new java.awt.Color(153, 153, 153));
        jButton1.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        jButton1.setkHoverStartColor(new java.awt.Color(0, 102, 102));
        jButton1.setkSelectedColor(new java.awt.Color(0, 102, 102));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.setkBackGroundColor(new java.awt.Color(0, 102, 102));
        jButton2.setkEndColor(new java.awt.Color(153, 153, 153));
        jButton2.setkHoverEndColor(new java.awt.Color(102, 102, 102));
        jButton2.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        jButton2.setkHoverStartColor(new java.awt.Color(51, 0, 0));
        jButton2.setkSelectedColor(new java.awt.Color(0, 102, 102));
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
                .addGap(442, 442, 442)
                .addComponent(jSeparator3)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        limpar();
        this.getParent().setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        txtpvd1.getText();
        if (txtSerie.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira o nr da Serie/Lote");
            txtSerie.requestFocusInWindow();
            return;
        }
        if (comboprod.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Produto!", "Atencao", JOptionPane.WARNING_MESSAGE);
            comboprod.grabFocus();
            return;
        }

        //        if (txtprecc.getText().trim().equals("")) {
            //            lbValidacao.setVisible(true);
            //            lbValidacao.setText("Insira o preco de compra");
            //            txtprecc.requestFocusInWindow();
            //            return;
            //        }
        if (txtpvd1.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira o preco de venda");
            txtpvd1.requestFocusInWindow();
            return;
        }
        if (txtqi1.getText().trim().equals("") || (Integer.parseInt(txtqi1.getText()) <= 0)) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira a quantidade correspondente ao preco");
            txtqi1.requestFocusInWindow();
            return;
        }
        if (!txt_margem_lucro.getText().trim().equals("")) {
            if (txtqi2.getText().trim().equals("") || (Integer.parseInt(txtqi2.getText()) <= 0)) {
                lbValidacao.setVisible(true);
                lbValidacao.setText("Insira a quantidade correspondente ao preco 2");
                txtqi2.requestFocusInWindow();
                return;
            }
        }
        if (!txtpvd3.getText().trim().equals("")) {
            if (txtqi3.getText().trim().equals("") || (Integer.parseInt(txtqi3.getText()) <= 0)) {
                lbValidacao.setVisible(true);
                lbValidacao.setText("Insira a quantidade correspondente ao preco 3");
                txtqi3.requestFocusInWindow();
                return;
            }
        }

        if (txtQntP.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira a quantidade");
            txtQntP.requestFocusInWindow();
            return;
        }
        if (txtpvd1.getText().trim().equals("") && txt_margem_lucro.getText().trim().equals("") && txtpvd3.getText().trim().equals("")) {
            lbValidacao.setVisible(true);
            lbValidacao.setText("Insira o preco de venda");
            txtpvd1.requestFocusInWindow();
            return;
        }
        //        if (datae.getDate() == null) {
            //            lbValidacao.setVisible(true);
            //            lbValidacao.setText("Insira a data de expiracao");
            //            datae.requestFocusInWindow();
            //            return;
            //        }
        if (txtIdP.getText().trim().equals("")) {
            //            ClienteJpaController cc = new ClienteJpaController(emf);
            //            if(cc.existeCodigo(txtNuit.getText())!=0){
                //                lbValidacao.setVisible(true);
                //                lbValidacao.setText("Este codigo ja existe. Introduza outro");
                //                txtNuit.requestFocusInWindow();
                //                return;
                //            }
            EntityManager em = emf.createEntityManager();
            EntradaJpaController ec = new EntradaJpaController(emf);
            ProdutoJpaController pc = new ProdutoJpaController(emf);
            em.getTransaction().begin();/////////////////////////////
            try {
                Entrada ent = new Entrada();
                ent.setIdfornecedor((Fornecedor) cbfor.getSelectedItem());
                Produto oldprod = ((Produto) comboprod.getSelectedItem());
                int idp = oldprod.getIdproduto();
                ent.setEntradaPK(new EntradaPK(idp, new Date()));
                ent.setQtd(Integer.parseInt(txtQntP.getText()));
                ent.setQntinicial(ent.getQtd());
                if (!txtprecc.getText().isEmpty()) {
                    ent.setCusto(Float.parseFloat(txtprecc.getText()));
                }
                ent.setQs(0);
                //////          ent.setQv(Integer.parseInt(txtQntP.getText()));
                ent.setQv(0);
                ent.setQd(0);
                if (dataf.getDate() != null) {
                    ent.setDatacriacao(dataf.getDate());
                }
                if (datae.getDate() != null) {
                    ent.setDataexpiracao(datae.getDate());
                }
                ent.setPreco(Float.parseFloat(preco_venda.getText()));
                ent.setSerie(txtSerie.getText());
                ent.setQa(Integer.parseInt(txtqi1.getText()));
                String precoi = ent.getQa() + "x" + ent.getPreco();
                ent.setNa(txtf1.getText());

                if (!txt_margem_lucro.getText().trim().equals("")) {
                    ent.setPb(Float.parseFloat(txt_margem_lucro.getText()));
                    ent.setQb(Integer.parseInt(txtqi2.getText()));
                    ent.setNb(txtf2.getText());
                    precoi = precoi + "|" + ent.getQb() + "x" + ent.getPb();
                } else {
                    ent.setPb(null);
                    ent.setQb(null);
                }
                if (!txtpvd3.getText().trim().equals("")) {
                    ent.setPc(Float.parseFloat(txtpvd3.getText()));
                    ent.setQc(Integer.parseInt(txtqi3.getText()));
                    ent.setNc(txtf3.getText());
                    precoi = precoi + "|" + ent.getQc() + "x" + ent.getPc();
                } else {
                    ent.setPc(null);
                    ent.setQc(null);
                }
                Imposto imp = (Imposto) cbimp.getSelectedItem();
                ent.setIdimposto(imp);
                precoi = precoi + "(IVA-" + imp.getPerc() + ")";
                ent.setPrecoinicial(precoi);
                ent.setCbarra(txtcbarra.getText());
                Produto prod = pc.findProdutoS(idp, em);
                ent.setProduto(prod);
                int pq = prod.getQtstock();
                pq = pq + Integer.parseInt(txtQntP.getText());
                prod.setQtstock(pq);
                //            if (prod.getQitem() == 1) {
                    //                prod.setPrecovenda(ent.getPreco());
                    //            }
                //            if (chbx1.isSelected()) {
                    //                if (txtmrg.getText().trim().equals("")) {
                        //                    lbValidacao.setVisible(true);
                        //                    lbValidacao.setText("Insira a margem de lucro");
                        //                    txtmrg.requestFocusInWindow();
                        //                    return;
                        //                }
                    //
                    //                if (txtpv.getText().trim().equals("")) {
                        //                    lbValidacao.setVisible(true);
                        //                    lbValidacao.setText("Insira o preco da venda");
                        //                    txtpv.requestFocusInWindow();
                        //                    return;
                        //                }
                    //                if (dataf.getDate() != null) {
                        //                    ent.setDatacriacao(dataf.getDate());
                        //                }
                    //                ent.setDataexpiracao(datae.getDate());
                    //                ent.setPreco(Float.parseFloat(txtpvd.getText()));
                    //                float cu = Float.parseFloat(txtprecc.getText());
                    //                float m = Float.parseFloat(txtmrg.getText());
                    //                float pv = Float.parseFloat(lbpv.getText());
                    //                prod.setCusto(cu);
                    //                prod.setMargem(m);
                    //                prod.setPrecovenda(pv);
                    //            }

                ent.setUtilizador(new Vendedor(client));
                ent.setQstock(prod.getQtstock());
                ent.setQpac(prod.getQtdvenda());
                pc.edit(oldprod, prod, em);
                dpk.setDate(ent.getEntradaPK().getData());
                ec.create(ent, em);
                txtIdP.setText(String.valueOf(ent.getEntradaPK().getIdproduto()));

                // limpar();
                //            FormSaida f = new FormSaida(new javax.swing.JFrame(), prod, null, "Saida do Sotck", true);
                //            f.setVisible(true);
                em.getTransaction().commit();
                JOptionPane.showMessageDialog(this, "Adicionado com Sucesso");
            } catch (Exception ex) {
                //   em.getTransaction().rollback();
                em.clear();
                em.getTransaction().commit();
                Logger.getLogger(FormEntrada.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (em != null) {
                    em.close();
                }
            }
            limpar();
            //this.setVisible(false);
        } else {
            int respo = JOptionPane.showOptionDialog(null, "Pretende modificar os dados deste Lote/Serie?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            EntityManager em = emf.createEntityManager();
            EntradaJpaController ec = new EntradaJpaController(emf);
            ProdutoJpaController pc = new ProdutoJpaController(emf);
            em.getTransaction().begin();/////////////////////////////
            try {
                int idp = Integer.parseInt(txtIdP.getText());
                Entrada ent = ec.findEntradaS(new EntradaPK(Integer.parseInt(txtIdP.getText()), dpk.getDate()), em);
                Produto prod = pc.findProdutoS(idp, em);
                Float prec = ent.getPreco();

                Integer qa = ent.getQa();
                Float pb = ent.getPb();
                Integer qb = ent.getQb();
                Float prc = ent.getPc();
                Integer qc = ent.getQc();
                Integer iva = ent.getIdimposto().getPerc();
                boolean mudoupreco = false;
                ent.setIdfornecedor((Fornecedor) cbfor.getSelectedItem());
                if (!txtprecc.getText().isEmpty()) {
                    ent.setCusto(Float.parseFloat(txtprecc.getText()));
                }
                ent.setPreco(Float.parseFloat(txtpvd1.getText()));
                String precoi = ent.getQa() + "x" + ent.getPreco();
                String ser = ent.getSerie();
                ent.setSerie(txtSerie.getText());

                ent.setQa(Integer.parseInt(txtqi1.getText()));
                if (prec.floatValue() != ent.getPreco().floatValue() || qa.intValue() != ent.getQa().intValue()) {
                    mudoupreco = true;
                    System.out.println("Mudou preco pa" + prec + " " + " " + ent.getPreco() + "     " + qa + " " + ent.getQa());
                }
                ent.setNa(txtf1.getText());
                if (!txt_margem_lucro.getText().trim().equals("")) {
                    ent.setPb(Float.parseFloat(txt_margem_lucro.getText()));
                    ent.setQb(Integer.parseInt(txtqi2.getText()));
                    ent.setNb(txtf2.getText());
                    if (pb == null) {
                        mudoupreco = true;
                    } else if (pb.floatValue() != ent.getPb().floatValue() || qb.intValue() != ent.getQb().intValue()) {
                        mudoupreco = true;
                    }
                    precoi = precoi + "|" + ent.getQb() + "x" + ent.getPb();
                } else {
                    ent.setPb(null);
                    ent.setQb(null);
                    if (pb != null) {
                        mudoupreco = true;
                        System.out.println("Mudou preco pb");
                    }
                }
                if (!txtpvd3.getText().trim().equals("")) {
                    ent.setPc(Float.parseFloat(txtpvd3.getText()));
                    ent.setQc(Integer.parseInt(txtqi3.getText()));
                    ent.setNc(txtf3.getText());
                    if (prc == null) {
                        mudoupreco = true;
                    } else if (prc.floatValue() != ent.getPc().floatValue() || qc.intValue() != ent.getQc().intValue()) {
                        mudoupreco = true;
                    }
                    precoi = precoi + "|" + ent.getQc() + "x" + ent.getPc();
                } else {
                    ent.setPc(null);
                    ent.setQc(null);
                    if (prc != null) {
                        mudoupreco = true;
                        System.out.println("Mudou preco pc");
                    }
                }
                ent.setIdimposto((Imposto) cbimp.getSelectedItem());
                if (iva != ent.getIdimposto().getPerc()) {
                    mudoupreco = true;
                    System.out.println("Mudou preco iva");
                }
                precoi = precoi + "(IVA-" + ent.getIdimposto().getPerc() + ")";
                ent.setCbarra(txtcbarra.getText());
                //            if (ent.getQs() > Integer.parseInt(txtQntP.getText())) {
                    //                JOptionPane.showMessageDialog(this, ent.getQs() + " Produtos ja foram retirados do Stock!", "Atencao", JOptionPane.WARNING_MESSAGE);
                    //                txtQntP.grabFocus();
                    //                return;
                    //            }
                //            int idp = ((Produto) comboprod.getSelectedItem()).getIdproduto();
                //            Produto prod = pc.findProduto(idp);
                //            ent.setProduto(prod);
                //            //Produto prod = pc.findProduto(pro);
                //            int pq = prod.getQtstock();
                //            pq = pq - ent.getQtd();
                //            ent.setQtd(Integer.parseInt(txtQntP.getText()));
                if (dataf.getDate() != null) {
                    ent.setDatacriacao(dataf.getDate());
                }
                if (datae.getDate() != null) {
                    ent.setDataexpiracao(datae.getDate());
                }
                //            pq = pq + Integer.parseInt(txtQntP.getText());
                //            prod.setQtstock(pq);
                //            if (prod.getQitem() == 1) {
                    //                prod.setPrecovenda(ent.getPreco());
                    //            }

                //                ent.setQstock(prod.getQtstock());
                //                ent.setQpac(prod.getQtdvenda());
                Date dataref = ent.getEntradaPK().getData();
                ec.edit(e, ent, em);
                //                pc.edit(prod);
                if (mudoupreco) {
                    Entrada ne = new Entrada(new EntradaPK(ent.getEntradaPK().getIdproduto(),new Date()));
                    // ne.getEntradaPK().setData(new Date());
                    ne.setDataref(dataref);
                    ne.setSerie(ent.getSerie());
                    //                    ne.setQstock(ent.getQstock());
                    //                    ne.setQpac(ent.getQpac());
                    ne.setQstock(prod.getQtstock());
                    ne.setQpac(prod.getQtdvenda());
                    ne.setIdimposto(ent.getIdimposto());
                    ne.setNa(ent.getNa());
                    ne.setNb(ent.getNb());
                    ne.setNc(ent.getNc());
                    ne.setPb(ent.getPb());
                    ne.setPc(ent.getPc());
                    ne.setPreco(ent.getPreco());
                    ne.setQa(ent.getQa());
                    ne.setQb(ent.getQb());
                    ne.setQc(ent.getQc());
                    ne.setQv(0);
                    ne.setQd(0);
                    ne.setPrecoinicial(precoi);
                    ne.setUtilizador(new Vendedor(client));
                    ne.setQtd(0);
                    ne.setQs(0);
                    ec.create(ne, em);
                }
                if (!ser.equals(ent.getSerie())) {
                    ser = ent.getSerie();
                    List<Entrada> le = new EntradaJpaController(emf).getEntDataref(e.getEntradaPK().getData());
                    for (Entrada ee : le) {
                        ee.setSerie(ser);
                        ec.edit(ee, ee, em);
                    }
                }

                //            if (chbx1.isSelected()) {
                    //                if (txtmrg.getText().trim().equals("")) {
                        //                    lbValidacao.setVisible(true);
                        //                    lbValidacao.setText("Insira a margem de lucro");
                        //                    txtmrg.requestFocusInWindow();
                        //                    return;
                        //                }
                    //
                    //                if (txtpv.getText().trim().equals("")) {
                        //                    lbValidacao.setVisible(true);
                        //                    lbValidacao.setText("Insira o preco da venda");
                        //                    txtpv.requestFocusInWindow();
                        //                    return;
                        //                }
                    //                float cu = Float.parseFloat(txtprecc.getText());
                    //                float m = Float.parseFloat(txtmrg.getText());
                    //                float pv = Float.parseFloat(lbpv.getText());
                    //                prod.setCusto(cu);
                    //                prod.setMargem(m);
                    //                prod.setPrecovenda(pv);
                    //            }
                em.getTransaction().commit();
                JOptionPane.showMessageDialog(this, "Editado com Sucesso");
            } catch (Exception ex) {
                //   em.getTransaction().rollback();
                em.clear();
                em.getTransaction().commit();
                Logger.getLogger(FormEntrada.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (em != null) {
                    em.close();
                }
            }
            limpar();
            this.setVisible(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btdelforActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdelforActionPerformed
        Fornecedor i = (Fornecedor) cbfor.getSelectedItem();
        if (txtIdP.getText().trim().equals("")) {
            if (!new EntradaJpaController(emf).getExistF(i)) {
                JOptionPane.showMessageDialog(this, "Este Fornecedor esta em uso! Nao pode ser apagado", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else {
            try {
                new FornecedorJpaController(emf).destroy(i.getIdfornecedor());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this, "Fornecedor apagado com Sucesso");
        }
        combobox(cbfor);
    }//GEN-LAST:event_btdelforActionPerformed

    private void btedforActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btedforActionPerformed
        FormFornec f = new FormFornec(new javax.swing.JFrame(), (Fornecedor) cbfor.getSelectedItem(), "Editar Fornecedor", true);
        f.setVisible(true);
        combobox(cbfor);
    }//GEN-LAST:event_btedforActionPerformed

    private void btadfamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btadfamActionPerformed
        FormFornec f = new FormFornec(new javax.swing.JFrame(), null, "Adicionar Fornecedor", true);
        f.setVisible(true);
        combobox(cbfor);
    }//GEN-LAST:event_btadfamActionPerformed

    private void cbforActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbforActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbforActionPerformed

    private void txtf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtf1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtf1ActionPerformed

    private void txtqi2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtqi2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtqi2ActionPerformed

    private void btdelimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btdelimpActionPerformed
        Imposto i = (Imposto) cbimp.getSelectedItem();
        int ind = cbimp.getSelectedIndex();
        if (txtIdP.getText().trim().equals("")) {
            if (new ProdutoJpaController(emf).existeImposto(i) != 0) {
                JOptionPane.showMessageDialog(this, "Este imposto esta sendo usado por outros produtos. Nao pode ser apagado!", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                try {
                    new ImpostoJpaController(emf).destroy(i.getIdimposto());
                    cbimp.removeItemAt(ind);
                    cbimp.setSelectedIndex(0);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(this, "Imposto apagado com Sucesso");
            }
        } else {
            int qi = new ProdutoJpaController(emf).existeImposto(i);
            if (qi >= 1) {
                Produto p = new ProdutoJpaController(emf).findProduto(Integer.parseInt(txtIdP.getText()));
                if (qi == 1 && p.getIdimposto().getIdimposto() == i.getIdimposto()) {
                    try {
                        new ImpostoJpaController(emf).destroy(i.getIdimposto());
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(this, "Imposto apagado com Sucesso");
                    cbimp.removeItemAt(ind);
                    cbimp.setSelectedIndex(0);
                    p.setIdimposto((Imposto) cbimp.getSelectedItem());
                    try {
                        new ProdutoJpaController(emf).edit(p);
                    } catch (Exception ex) {
                        Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, "Este imposto esta sendo usado por outros produtos. Nao pode ser apagado!", "Atencao", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            try {
                new ImpostoJpaController(emf).destroy(i.getIdimposto());
                cbimp.removeItemAt(ind);
                cbimp.setSelectedIndex(0);
                p.setIdimposto((Imposto) cbimp.getSelectedItem());
                try {
                    new ProdutoJpaController(emf).edit(p);
                } catch (Exception ex) {
                    Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(FormProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(this, "Imposto apagado com Sucesso");
        }
        combobox(cbimp);
    }//GEN-LAST:event_btdelimpActionPerformed

    private void btedimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btedimpActionPerformed
        FormImposto f = new FormImposto(new javax.swing.JFrame(), (Imposto) cbimp.getSelectedItem(), "Editar Imposto", true);
        f.setVisible(true);
        comboboxi(cbimp);
    }//GEN-LAST:event_btedimpActionPerformed

    private void btadimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btadimpActionPerformed
        FormImposto f = new FormImposto(new javax.swing.JFrame(), null, "Adicionar Imposto", true);
        f.setVisible(true);
        comboboxi(cbimp);
    }//GEN-LAST:event_btadimpActionPerformed

    private void cbimpItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbimpItemStateChanged

        float i = 0;
        if (cbimp.getSelectedItem() != null) {
            i = ((Imposto) cbimp.getSelectedItem()).getPerc().floatValue() / 100;
        }
        //        if (!txtPreco.getText().trim().equals("")) {
            //            float p = Float.parseFloat(txtPreco.getText());
            //            if (p != 0) {
                //                lbpv.setText(String.valueOf(new Double(Math.ceil(p + (p * i))).floatValue()));
                //            }
            //        }
    }//GEN-LAST:event_cbimpItemStateChanged

    private void txtcbarraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcbarraKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcbarraKeyReleased

    private void txtcbarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcbarraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcbarraActionPerformed

    private void chPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chPagoActionPerformed

    private void txtqi3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtqi3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtqi3ActionPerformed

    private void txtpvd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpvd3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpvd3ActionPerformed

    private void txt_margem_lucroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_margem_lucroKeyReleased
        // TODO add your handling code here:

        double pc = Double.parseDouble(txtpvd1.getText());
        double mlucro = Double.parseDouble(txt_margem_lucro.getText());
        preco_venda.setText(""+pc*mlucro);
    }//GEN-LAST:event_txt_margem_lucroKeyReleased

    private void txtqi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtqi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtqi1ActionPerformed

    private void txtpreccKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpreccKeyReleased

    }//GEN-LAST:event_txtpreccKeyReleased

    public void comboboxi(JComboBox c) {
        List<Imposto> listai = new ImpostoJpaController(emf).findImpostoEntities();
        c.removeAllItems();
        for (Imposto lista : listai) {
            c.addItem(lista);
        }
    }

    public void comboboxp(JComboBox c) {
        c.removeAllItems();
        //c.addItem("Todos");
        List<Produto> lv = new ProdutoJpaController(emf).getProdAsc();
        if (lv.isEmpty()) {
            return;
        }
        Produto[] elements = lv.toArray(new Produto[]{});
        AutoCompleteSupport support = AutoCompleteSupport.install(
                c, GlazedLists.eventListOf(elements));
    }

    public void combobox(JComboBox c) {
        List<Fornecedor> listai = new FornecedorJpaController(emf).findFornecedorEntities();
        c.removeAllItems();
        for (Fornecedor lista : listai) {
            c.addItem(lista);
        }
    }
    public void limpar() {
//        txtIdP.setText("");
        txtIdP.setText("");
        dpk.setDate(null);
        txtQntP.setText("");
        txtqi1.setText("");
        txtprecc.setText("");
        dataf.setDate(null);
        datae.setDate(null);
        txtpvd1.setText("");
        txtSerie.setText("");
        txt_margem_lucro.setText("");
        txtpvd3.setText("");
        txtf1.setText("");
        txtf2.setText("");
        txtf3.setText("");
        txtcbarra.setText("");
        txtqi1.setText("");
        txtqi2.setText("");
        txtqi3.setText("");
//        lbpv.setText("");
//        txtmrg.setText("");
//        dpk.setDate(null);
        cbfor.setEditable(true);
        txtQntP.setEditable(true);
        lbValidacao.setVisible(false);
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
                FormEntrada dialog = new FormEntrada(new javax.swing.JFrame(), p, e, t, client, true);
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

    public int getLimpaVenda() {

        return g();

    }

    public int g() {
        return 1;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btadfam;
    private javax.swing.JButton btadimp;
    private javax.swing.JButton btdelfor;
    private javax.swing.JButton btdelimp;
    private javax.swing.JButton btedfor;
    private javax.swing.JButton btedimp;
    private javax.swing.JComboBox cbfor;
    private javax.swing.JComboBox cbimp;
    private javax.swing.JCheckBox chPago;
    private javax.swing.JComboBox comboprod;
    private com.toedter.calendar.JDateChooser datae;
    private com.toedter.calendar.JDateChooser dataf;
    private com.toedter.calendar.JDateChooser dpk;
    private keeptoo.KButton jButton1;
    private keeptoo.KButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JLabel labelAquisic;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel lbValidacao;
    private javax.swing.JTextField preco_venda;
    private javax.swing.JTextField txtIdP;
    private javax.swing.JTextField txtQntP;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txt_margem_lucro;
    private javax.swing.JTextField txtcbarra;
    private javax.swing.JTextField txtf1;
    private javax.swing.JTextField txtf2;
    private javax.swing.JTextField txtf3;
    private javax.swing.JTextField txtprecc;
    private javax.swing.JTextField txtpvd1;
    private javax.swing.JTextField txtpvd3;
    private javax.swing.JTextField txtqi1;
    private javax.swing.JTextField txtqi2;
    private javax.swing.JTextField txtqi3;
    // End of variables declaration//GEN-END:variables
}
