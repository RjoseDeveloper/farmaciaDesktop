package View;

import controller.EntradaJpaController;
import controller.ImpostoJpaController;
import controller.ProdutoJpaController;
import controller.SaidaJpaController;
import controller.TiposaidaJpaController;
import controller.VendaJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.awt.Color;
import static java.awt.Color.red;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import metodos.ButtonColumn;
import metodos.ValidarInteiro;
import metodos.ValidarString;
import modelo.Entrada;
import modelo.EntradaPK;
import modelo.Familia;
import modelo.Fornecedor;
import modelo.Imposto;
import modelo.Produto;
import modelo.Saida;
import modelo.SaidaPK;
import modelo.Tiposaida;
import modelo.Vendedor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author rjose
 */
public final class CadastroProduto extends javax.swing.JFrame {

    int t = 0;

    JasperPrint jpPrint;
    JasperViewer jv;
    HashMap<String, Object> par = new HashMap<String, Object>();
    Map parametros = new HashMap<>();
    List<Produto> tipoProd = new ArrayList<>();
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    DefaultTableModel dTableModel = new DefaultTableModel(null, new String[]{"id", "Codigo", "Produto", "Qtd. Armazem", "Qtd. Loja", "Preco Venda", "Detalhes", "Stock", "Editar", "Remover"});
    private int client = 0;

    @Override
    public void list() {
        super.list();
    }

    public CadastroProduto(int c) {
        initComponents();
        this.setLocation(40, 225);
        // this.setResizable(false);
        this.setTitle("Cadastro de Produto");
        loadListShow(new ProdutoJpaController(emf).getProdAsc(), "");
        // txtCampoPesq.setDocument(new ValidarString());
        this.client = c;
        this.TableProduto.setAutoCreateRowSorter(true);
        this.TableProduto.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.TableProduto.getColumnModel().getColumn(0).setMaxWidth(0);
        this.TableProduto.getColumnModel().getColumn(0).setPreferredWidth(0);
        // this.TableProduto.getColumnModel().getColumn(1).setMinWidth(0);//setPreferredWidth(10);
        this.TableProduto.getColumnModel().getColumn(1).setMaxWidth(100);
        //  this.TableProduto.getColumnModel().getColumn(1).setPreferredWidth(0);
        this.TableProduto.getColumnModel().getColumn(3).setMaxWidth(100);
        this.TableProduto.getColumnModel().getColumn(4).setMaxWidth(100);
        this.TableProduto.getColumnModel().getColumn(6).setMaxWidth(60);//setPreferredWidth(3);
        this.TableProduto.getColumnModel().getColumn(7).setMaxWidth(50);//setPreferredWidth(3);
        this.TableProduto.getColumnModel().getColumn(8).setMaxWidth(50);//setPreferredWidth(3);
        this.TableProduto.getColumnModel().getColumn(9).setMaxWidth(70);//setPreferredWidth(3);
        this.TableProduto.setRowHeight(30);
        painelprazo.setVisible(false);
        Icon id = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_ver.png"));
        Icon is = new javax.swing.ImageIcon(getClass().getResource("/Imagens/stock.png"));
        Icon ie = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_editar.png"));
        Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_borrar.png"));
        ButtonColumn bcd = new ButtonColumn(this.TableProduto, ver, 6, id);
        ButtonColumn bcs = new ButtonColumn(this.TableProduto, stk, 7, is);
        ButtonColumn bce = new ButtonColumn(this.TableProduto, editar, 8, ie);
        ButtonColumn bcr = new ButtonColumn(this.TableProduto, delete, 9, ir);
        // System.out.println(new ProdutoJpaController(emf).getProdNomeLike("P"));
        butaoOrd.setVisible(false);
        verPrazos();
    }

    public void loadListShow(List<Produto> lista, String nome) {
        while (dTableModel.getRowCount() > 0) {
            dTableModel.removeRow(0);
        }
        if (!nome.isEmpty()) {
            lista = new ProdutoJpaController(emf).getProdNomeLike(nome);//new Consultas().formarProduto("%" + nome + "%");
        }
        String[] linha = new String[]{null, null, null, null, null, null, null, null, null, null};
        for (int i = 0; i < lista.size(); i++) {
            dTableModel.addRow(linha);
            Produto p = lista.get(i);
         
            dTableModel.setValueAt(p, i, 0);
          
            dTableModel.setValueAt(p.getCodigo(), i, 1);
            dTableModel.setValueAt(p.getNome(), i, 2);
            dTableModel.setValueAt(p.getQtstock(), i, 3);
            dTableModel.setValueAt(p.getQtdvenda(), i, 4);
            List<Entrada> le = new EntradaJpaController(emf).getEntrada(p);
            String prec = "";
            for (Entrada e : le) {
                prec = e.getQa() + "x" + e.getPreco();
                if (e.getPb() != null) {
                    prec = prec + " | " + e.getQb() + "x" + e.getPb();
                }
                if (e.getPc() != null) {
                    prec = prec + " | " + e.getQc() + "x" + e.getPc();
                }
            }
            dTableModel.setValueAt(prec, i, 5);
        }
       // JTable table = new JTable(dTableModel);

    }

    public void verPrazos() {
        Long i = new EntradaJpaController(emf).getEntrFora();
        if (i > 0) {
            painelprazo.setVisible(true);
        }
    }

    Action delete = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            int respo = JOptionPane.showOptionDialog(null, "{Pretende remover esta Produto?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (respo == 1 || respo == -1) {
                return;
            }
            Produto p = (Produto) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            Boolean b = new VendaJpaController(emf).getExist(p);
            if (b) {
                try {
                    new ProdutoJpaController(emf).destroy(p.getIdproduto());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(CadastroProduto.class.getName()).log(Level.SEVERE, null, ex);
                }
                //  labelSucesso.setText("Produto removido com Sucesso");
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                loadListShow(new ProdutoJpaController(emf).getProdAsc(), "");
            } else {
                JOptionPane.showMessageDialog(null, "Este produto nao pode ser removido,\n "
                        + "porque faz parte do historial de vendas efectuadas", "Atencao", JOptionPane.WARNING_MESSAGE);
            }
        }
    };

    Action stk = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Produto p = (Produto) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            p = new ProdutoJpaController(emf).findProduto(p.getIdproduto());
            CadastroEntSaid v = null;
            try {
                v = new CadastroEntSaid(p, client);
            } catch (Exception ex) {
                Logger.getLogger(CadastroProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
            JDialog dia = new JDialog();
            dia.setModal(true);
            dia.setContentPane(v.getContentPane());
            dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
            dia.setBounds(v.getBounds());
            // dia.setSize(v.getWidth(), C);
            dia.setLocationRelativeTo(null);
            dia.setVisible(true);
            dia.setDefaultCloseOperation(0);
            loadListShow(new ProdutoJpaController(emf).getProdAsc(), "");
        }
    };
    Action editar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Produto p = (Produto) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            //Produto p = new ProdutoJpaController(emf).findProduto(id);//removeRow(modelRow);
            FormProduto f = new FormProduto(new javax.swing.JFrame(), p, "Editar Produto", true);
            f.setVisible(true);
            loadListShow(new ProdutoJpaController(emf).getProdAsc(), "");
        }
    };
    Action ver = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Produto p = (Produto) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            //Produto p = new ProdutoJpaController(emf).findProduto(id);//removeRow(modelRow);
            FormProdVista f = new FormProdVista(new javax.swing.JFrame(), p, true);
            f.setVisible(true);
        }
    };

    //  buttonColumn.setMnemonic(keyEvent.VK_D);
    /**
     *
     * @param lista
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProduto = new javax.swing.JTable();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        jButton4 = new javax.swing.JButton();
        butaoOrd = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtCampoPesq = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btSalvarProd = new keeptoo.KButton();
        jPanel10 = new javax.swing.JPanel();
        labelHistory = new javax.swing.JLabel();
        painelprazo = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TableProduto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        TableProduto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TableProduto.setModel(dTableModel);
        TableProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProdutoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProduto);

        kGradientPanel4.setkEndColor(new java.awt.Color(204, 204, 204));
        kGradientPanel4.setkStartColor(new java.awt.Color(204, 204, 255));

        jButton4.setText("Actualizar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        butaoOrd.setText("Reordenar");
        butaoOrd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butaoOrdActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 0, 153));
        jLabel9.setText("Filtrar / Codigo ou Nome");
        jLabel9.setToolTipText("");

        txtCampoPesq.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtCampoPesqMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCampoPesqMouseReleased(evt);
            }
        });
        txtCampoPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCampoPesqKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCampoPesqKeyReleased(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 204, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/docpdf.png"))); // NOI18N
        jButton2.setText("Exportar PDF");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btSalvarProd.setText("Adicionar");
        btSalvarProd.setkBackGroundColor(new java.awt.Color(0, 102, 204));
        btSalvarProd.setkEndColor(new java.awt.Color(0, 51, 102));
        btSalvarProd.setkHoverEndColor(new java.awt.Color(0, 51, 51));
        btSalvarProd.setkHoverForeGround(new java.awt.Color(51, 51, 51));
        btSalvarProd.setkHoverStartColor(new java.awt.Color(51, 0, 51));
        btSalvarProd.setkSelectedColor(new java.awt.Color(0, 102, 102));
        btSalvarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarProdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel4Layout.createSequentialGroup()
                .addGap(0, 25, Short.MAX_VALUE)
                .addGroup(kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(butaoOrd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(107, 107, 107)
                .addComponent(jLabel9)
                .addGap(11, 11, 11)
                .addComponent(txtCampoPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(203, 203, 203)
                .addComponent(btSalvarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCampoPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalvarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel4Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(butaoOrd)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kGradientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1142, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(153, 153, 153));

        labelHistory.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelHistory.setText("CONTROLO DE STOCK DE PRODUTOS");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/alert_obj.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 102));
        jLabel1.setText("Alguns produtos fora do prazo !");

        javax.swing.GroupLayout painelprazoLayout = new javax.swing.GroupLayout(painelprazo);
        painelprazo.setLayout(painelprazoLayout);
        painelprazoLayout.setHorizontalGroup(
            painelprazoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelprazoLayout.createSequentialGroup()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );
        painelprazoLayout.setVerticalGroup(
            painelprazoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelprazoLayout.createSequentialGroup()
                .addComponent(jButton3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182)
                .addComponent(painelprazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelprazo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelHistory))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu3.setBackground(new java.awt.Color(25, 255, 51));
        jMenu3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenu3.setForeground(new java.awt.Color(0, 25, 255));
        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/aplicativos.png"))); // NOI18N
        jMenu3.setText("Operações");
        jMenu3.setFont(new java.awt.Font("Lucida Console", 1, 14)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/shopp++.png"))); // NOI18N
        jMenuItem1.setText("Venda e Emprestimo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/document_write.png"))); // NOI18N
        jMenuItem3.setText("Relatorios");
        jMenu3.add(jMenuItem3);

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Close.png"))); // NOI18N
        jMenuItem4.setText("Sair");
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {

        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void TableProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProdutoMouseClicked

    }//GEN-LAST:event_TableProdutoMouseClicked

    private void txtCampoPesqMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCampoPesqMouseReleased

    }//GEN-LAST:event_txtCampoPesqMouseReleased

    private void txtCampoPesqMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCampoPesqMouseExited

    }//GEN-LAST:event_txtCampoPesqMouseExited

    private void txtCampoPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoPesqKeyReleased
        loadListShow(tipoProd, txtCampoPesq.getText());
        if (txtCampoPesq.getText().trim().equals("")) {
            loadListShow(new ProdutoJpaController(emf).getProdAsc(), "");
        }
    }//GEN-LAST:event_txtCampoPesqKeyReleased

    
    private void txtCampoPesqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoPesqKeyPressed

    }//GEN-LAST:event_txtCampoPesqKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String pl = new File("saslogo.jpg").getAbsolutePath();
        parametros.clear();
        List<Produto> ListProd = new ArrayList<>();
        parametros.put("img", pl);
        if (!txtCampoPesq.getText().isEmpty()) {
             ListProd = new ProdutoJpaController(emf).getProdNomeLike(txtCampoPesq.getText());//new Consultas().formarProduto("%" + nome + "%");
        } else {
             ListProd = new ProdutoJpaController(emf).getProdAsc();
        }
        for (int i = 0; i <  ListProd.size(); i++) {
            Produto produt =  ListProd.get(i);
            List<Entrada> le = new EntradaJpaController(emf).getEntrada(produt);
            String prec = "";
            for (Entrada e : le) {
                prec = e.getQa() + "x" + e.getPreco();
                if (e.getPb() != null) {
                    prec = prec + " | " + e.getQb() + "x" + e.getPb();
                }
                if (e.getPc() != null) {
                    prec = prec + " | " + e.getQc() + "x" + e.getPc();
                }
            }
            Familia m = new Familia();
            m.setDescricao(prec);
            produt.setIdfamilia(m);
        }
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(ListProd);
        // String path = "/relatorios/recibov.jasper";
        try {
            //String path = "C:\\Users\\Ussimane\\Desktop\\GestaoLoja\\src\\relatorios\\proproforma.jasper";
            String path = new File("relatorios/produto.jasper").getAbsolutePath();
            jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
            jv = new JasperViewer(jpPrint, false); //O false eh para nao fechar a aplicacao
            JDialog viewer = new JDialog(new javax.swing.JFrame(), "Relatorio de Produtos", true);
            viewer.setSize(1024, 768);
            viewer.setLocationRelativeTo(null);
            viewer.getContentPane().add(jv.getContentPane());
            viewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(CadastroProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void butaoOrdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butaoOrdActionPerformed
        int respo = JOptionPane.showOptionDialog(null, "{Reordenar os codigos consoante a ordem dos produtos?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"SIM", "NÃO"}, null);
        if (respo == 1 || respo == -1) {
            return;
        }
        ProdutoJpaController pc = new ProdutoJpaController(emf);
        List<Produto> lp = pc.getProdAsc();
        int i = 0;
        for (Produto p : lp) {
            p.setCodigo(String.valueOf(++i));
            try {
                pc.edit(p);
            } catch (Exception ex) {
                Logger.getLogger(CadastroProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(null, "Codigos foram ordenados com sucesso");
        loadListShow(new ProdutoJpaController(emf).getProdAsc(), "");

    }//GEN-LAST:event_butaoOrdActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //List<Entrada> le = new EntradaJpaController(emf).getEntExp();
        ProdutoExp f = null;
        try {
            f = new ProdutoExp();
        } catch (Exception ex) {
            Logger.getLogger(CadastroProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setContentPane(f.getContentPane());
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        dia.setBounds(f.getBounds());
        // dia.setSize(v.getWidth(), C);
        dia.setLocationRelativeTo(null);
        dia.setVisible(true);
        dia.setDefaultCloseOperation(0);
        painelprazo.setVisible(false);
        verPrazos();
        loadListShow(new ProdutoJpaController(emf).getProdAsc(), "");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      //  loadListShow(new ProdutoJpaController(emf).getProdAsc(), "");
            List<Produto> lp = new ProdutoJpaController(emf).findProdutoEntities();
            for(Produto p: lp){
                try {
                    Entrada ent = new Entrada();
                    ent.setIdfornecedor(new Fornecedor(1));
//                Produto oldprod = ((Produto) comboprod.getSelectedItem());
//                int idp = oldprod.getIdproduto();
                    ent.setEntradaPK(new EntradaPK(p.getIdproduto(), new Date()));
                    ent.setQtd(p.getQtdvenda());
                    ent.setQntinicial(ent.getQtd());
                    
                    ent.setQs(p.getQtdvenda());
                    //////          ent.setQv(Integer.parseInt(txtQntP.getText()));
                    ent.setQv(p.getQtdvenda());
                    ent.setQd(0);
                    
                    ent.setPreco(p.getPrecovenda());
                    ent.setSerie("1");
                    ent.setQa(1);
                    String precoi = ent.getQa() + "x" + ent.getPreco();
                    ent.setNa("Item");
                    
                    ent.setPb(null);
                    ent.setQb(null);
                
                    ent.setPc(null);
                    ent.setQc(null);
                    Imposto imp = new ImpostoJpaController(emf).findImposto(4);
                    ent.setIdimposto(imp);
                    precoi = precoi + "(IVA-" + imp.getPerc() + ")";
                    ent.setPrecoinicial(precoi);
                    ent.setProduto(p);
                    
                    
                    ent.setUtilizador(new Vendedor(1));
                    ent.setQstock(p.getQtdvenda());
                    ent.setQpac(0);
                   // ent.setQpac(p.getQitem());
                    // pc.edit(oldprod, prod, em);
                    new EntradaJpaController(emf).create(ent);
                    
                     Saida s = new Saida();
                s.setProduto(p);
                s.setUtilizador(new Vendedor(1));
                s.setDatae(ent.getEntradaPK().getData());
                s.setSaidaPK(new SaidaPK(p.getIdproduto(), new Date()));
                
                s.setQtd(p.getQtdvenda());
                    
                        
                        s.setObs(null);
                        Tiposaida ts = new TiposaidaJpaController(emf).findTiposaida(1);
                        s.setTiposaida(ts);
                    
                    //   System.out.println("qv qt:" + e.getQv() + "" + qt);
                    s.setQpac(p.getQtdvenda());
                    s.setQstock(0);
                    s.setQa(1);
                    s.setNa(ent.getNa());
                    s.setPreco(p.getPrecovenda());
                    new SaidaJpaController(emf).create(s);
                } catch (Exception ex) {
                    Logger.getLogger(CadastroProduto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btSalvarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarProdActionPerformed
        // TODO add your handling code here:
        FormProduto f = new FormProduto(new javax.swing.JFrame(), null, "Adicionar Produto", true);
        f.setVisible(true);
        loadListShow(new ProdutoJpaController(emf).getProdAsc(), "");
        
    }//GEN-LAST:event_btSalvarProdActionPerformed

    @SuppressWarnings("unchecked")

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableProduto;
    private keeptoo.KButton btSalvarProd;
    private javax.swing.JButton butaoOrd;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel4;
    private javax.swing.JLabel labelHistory;
    private javax.swing.JPanel painelprazo;
    private javax.swing.JTextField txtCampoPesq;
    // End of variables declaration//GEN-END:variables
}
