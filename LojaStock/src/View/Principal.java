package View;

import ca.odell.glazedlists.swing.AutoCompleteSupport;
import controller.ClienteJpaController;
import controller.EmpresaJpaController;
import controller.EmprestimoJpaController;
import controller.EntradaJpaController;
import controller.FornecedorJpaController;
import controller.FproformaJpaController;
import controller.ProdutoJpaController;
import controller.VendaJpaController;
import controller.VendedorJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import metodos.Main;
import modelo.Cliente;
import modelo.Detalorg;
import modelo.Empresa;
import modelo.Emprestimo;
import modelo.Entrada;
import modelo.EntradaPK;
import modelo.Familia;
import modelo.Produto;
import modelo.Venda;
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
public final class Principal extends javax.swing.JFrame {

    Calendar calendar = Calendar.getInstance();
    Date currentDate = new Date(calendar.getTime().getTime());
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    ClienteJpaController clients = new ClienteJpaController(emf);
    VendaJpaController venda = new VendaJpaController(emf);
    VendedorJpaController vendador = new VendedorJpaController(emf);
    FornecedorJpaController fornecedor = new FornecedorJpaController(emf);
    FproformaJpaController factura = new FproformaJpaController(emf);

    List<Produto> listaTipo = new ArrayList<>();
    List<Cliente> listac = new ArrayList<>();
    List<Venda> listaX = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();
    List<Emprestimo> emprestimos = new ArrayList<>();
    List<Empresa> empresa = new ArrayList<>();
    List<Produto> tipoProd = new ArrayList<>();

    Detalorg dog = new Detalorg();
    String cbarra = "";
    private final DefaultTableModel vendaTmodel = new DefaultTableModel(null, new String[]{"Codigo", "Cliente", "Produto", "Qtd", "Preco", "Data Emprest", "Data Pagamento", "Total a Pagar"});
    private final DefaultTableModel prevenda = new DefaultTableModel(null, new String[]{"Produto", "Categoria", "Qt-Prev", "preco", "Total"});

    private final DefaultTableModel t_data_view = new DefaultTableModel(null, new String[]{"Nome do Cliente", "Produto", "QTD", "Preco Uni", "SubTotal", "Data de Emprestimo", "Vendedor", "Estado", "Pagar", "Anular"});
    DefaultTableModel dTableModel = new DefaultTableModel(null, new String[]{"CODIGO", "PRODUTO", "QTD. ARMAZEM", "QTD. LOJA", "PRECO VENDA", "MARGEM LUCRO", "LUCRO"});

    private int valor = 0;
    private float soma = 0;
    private int last = 0;
    private int ctr = 0;
    private int client = 0;
    public static final int RET_OK = 1;
    private static Boolean bool = true;
    AutoCompleteSupport support2 = null;
    AutoCompleteSupport support = null;
    private int inc_venda = 0;
    float vp = 0;
    Date n;
    String impvenda = "";
    String impprof = "";
    int impv = 0;
    private static boolean nrcaixa = false;
   

    JasperPrint jpPrint;
    JasperViewer jv;
    Map parametros = new HashMap<>();

    private final DefaultTableModel fmodel = new DefaultTableModel(null, new String[]{"id", "Nome do Cliente", "Empresa", "Valor", "Data Fornecimento", "Data de Pagamento", "Vendedor", "Ver", "Pagar", "Anular"});
    private final DefaultTableModel tbl_carinho = new DefaultTableModel(null, new String[]{"Codigo", "Nome do Produto", "Qtd", "Preço Unitario", "SubTotal", "Excluir"});
    private int t;

    public Principal(int i, String s, int t) {
        this.Anular = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                Emprestimo v = (Emprestimo) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
                int resp = JOptionPane.showOptionDialog(null, "{Pretende anular este Pendente?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, new String[]{"SIM", "NÃO"}, null);
                if (resp == 1 || resp == -1) {
                    return;
                }
                EntityManager em = emf.createEntityManager();
                List<Emprestimo> le = new EmprestimoJpaController(emf).getEmprestimo(v.getDataemprestimo());
                em.getTransaction().begin();/////////////////////////////
                try {
                    for (Emprestimo ep : le) {

                        Entrada ent = new EntradaJpaController(emf).findEntradaS(new EntradaPK(ep.getIdproduto().getIdproduto(), ep.getDatae()), em);
                        Produto p = new ProdutoJpaController(emf).findProdutoS(ep.getIdproduto().getIdproduto(), em);
//                  Entrada ent = new EntradaJpaController(emf).getEntrada(ep.getDatae());

                        ent.setQv(ent.getQv() + ep.getQc());
                        p.setQtdvenda(p.getQtdvenda() + ep.getQtprod());

                        new EntradaJpaController(emf).edit(ent, ent, em);
                        new ProdutoJpaController(emf).edit(p, p, em);
                        new EmprestimoJpaController(emf).destroy(ep.getIdemp(), em);

                    }
                    ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                    JOptionPane.showMessageDialog(null, "Anulado com sucesso");
                    em.getTransaction().commit();
                } catch (Exception ex) {
                    //   em.getTransaction().rollback();
                    em.clear();
                    em.getTransaction().commit();
                    Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (em != null) {
                        em.close();
                    }
                }
            }
        };

        Dimension centro = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((centro.width) / 3, (centro.height) / 3);
        //setIconImage(new ImageIcon(getClass().getResource("/Imagens/img_es1.jpg")).getImage());

        setResizable(true);
        // setSize(400, 300);  
        setLocationRelativeTo(null);
        setVisible(true);
        initComponents();

        this.setExtendedState(MAXIMIZED_BOTH);
        lbUser.setText(s);
        lblCliente.setText(Integer.toString(clients.getClienteCount()));
        lblTotVenda.setText(Integer.toString(venda.getVendaCount()));
        lblUser.setText(Integer.toString(vendador.getVendedorCount()));
        lblForne.setText(Integer.toString(fornecedor.getFornecedorCount()));
        lblTotVenda.setText("" + total_venda() + "0");
        txtCampoPesq.setVisible(false);

        //comboboxe(cbemp);
        emprestimos = new EmprestimoJpaController(emf).findEmprestimoEntities();
        //loadListShow(empresa, txtCampoPesq.getText());
        //this.tbl_view.setRowHeight(30);
        Icon ip = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_pago.png"));
        Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_cancel.png"));

        loadListShowprod(new ProdutoJpaController(emf).getProdAsc(), 10);
        verPrazos();

//        ButtonColumn bp = new ButtonColumn(this.tbl_view, pagar, 8, ip);
//        ButtonColumn bd = new ButtonColumn(this.tbl_view, Anular, 9, ir);
        this.t = t;
      
        
        if (t == 0) {
            lbUProf.setText("Vendedor");
//              btn_venda.setVisible(false);
//        btn_cliente.setVisible(false);
//        btn_produto.setVisible(false);
//        btn_fornecedor.setVisible(false);
            
        btn_user.setVisible(false);
        btn_report.setVisible(false);
        btn_conf.setVisible(false);
//           
        }
        if (t == 1) {
            lbUProf.setText("Gestor de Produtos e clientes");
              btn_venda.setVisible(false);
//        btn_cliente.setVisible(false);
//        btn_produto.setVisible(true);
        btn_fornecedor.setVisible(false);
        btn_user.setVisible(false);
        btn_report.setVisible(false);
        btn_conf.setVisible(false);
//          
        }
        if (t == 2) {
            lbUProf.setText("Super utilizador");
        }
        this.client = i;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        loja_clinicaPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("LojaPU").createEntityManager();
        emprestimoQuery = java.beans.Beans.isDesignTime() ? null : loja_clinicaPUEntityManager.createQuery("SELECT e FROM Emprestimo e");
        emprestimoList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : emprestimoQuery.getResultList();
        vendasPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("LojaPU").createEntityManager();
        emprestimoQuery1 = java.beans.Beans.isDesignTime() ? null : vendasPUEntityManager.createQuery("SELECT e FROM Emprestimo e");
        emprestimoList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : emprestimoQuery1.getResultList();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        caxa1 = new javax.swing.JLabel();
        btn_user = new keeptoo.KButton();
        btn_venda = new keeptoo.KButton();
        btn_cliente = new keeptoo.KButton();
        btn_fornecedor = new keeptoo.KButton();
        btn_conf = new keeptoo.KButton();
        btn_produto = new keeptoo.KButton();
        lbUProf1 = new javax.swing.JLabel();
        btn_report = new keeptoo.KButton();
        lbUser = new javax.swing.JLabel();
        lbUProf = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        LabelBackground = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        kGradientPanel5 = new keeptoo.KGradientPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotVenda = new javax.swing.JLabel();
        lblVenda = new javax.swing.JLabel();
        kGradientPanel15 = new keeptoo.KGradientPanel();
        jLabel5 = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        lblCliente1 = new javax.swing.JLabel();
        kGradientPanel17 = new keeptoo.KGradientPanel();
        jLabel6 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        kGradientPanel18 = new keeptoo.KGradientPanel();
        jLabel7 = new javax.swing.JLabel();
        lblForne = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TableProduto = new javax.swing.JTable();
        txtCampoPesq = new javax.swing.JTextField();
        jButton5 = new keeptoo.KButton();
        webButton1 = new com.alee.laf.button.WebButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        jMenuItem1.setText("jMenuItem1");

        jMenu2.setText("jMenu2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        kGradientPanel2.setkEndColor(new java.awt.Color(0, 102, 102));
        kGradientPanel2.setkStartColor(new java.awt.Color(255, 255, 255));

        caxa1.setForeground(new java.awt.Color(255, 0, 0));
        caxa1.setText(" ");

        btn_user.setText("UTILIZADORES");
        btn_user.setkEndColor(new java.awt.Color(204, 204, 204));
        btn_user.setkHoverEndColor(new java.awt.Color(0, 204, 51));
        btn_user.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btn_user.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        btn_user.setkSelectedColor(new java.awt.Color(0, 0, 0));
        btn_user.setkStartColor(new java.awt.Color(204, 51, 0));
        btn_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_userActionPerformed(evt);
            }
        });

        btn_venda.setText(" VENDAS");
        btn_venda.setkBackGroundColor(new java.awt.Color(204, 204, 255));
        btn_venda.setkEndColor(new java.awt.Color(204, 204, 204));
        btn_venda.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btn_venda.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        btn_venda.setkPressedColor(new java.awt.Color(0, 102, 102));
        btn_venda.setkSelectedColor(new java.awt.Color(102, 102, 102));
        btn_venda.setkStartColor(new java.awt.Color(204, 51, 0));
        btn_venda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vendaActionPerformed(evt);
            }
        });

        btn_cliente.setText("CLIENTES");
        btn_cliente.setkEndColor(new java.awt.Color(204, 204, 204));
        btn_cliente.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btn_cliente.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        btn_cliente.setkSelectedColor(new java.awt.Color(0, 102, 102));
        btn_cliente.setkStartColor(new java.awt.Color(204, 102, 0));
        btn_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clienteActionPerformed(evt);
            }
        });

        btn_fornecedor.setText("FORNCEDORES");
        btn_fornecedor.setkEndColor(new java.awt.Color(204, 204, 204));
        btn_fornecedor.setkHoverEndColor(new java.awt.Color(0, 153, 102));
        btn_fornecedor.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btn_fornecedor.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        btn_fornecedor.setkSelectedColor(new java.awt.Color(51, 0, 0));
        btn_fornecedor.setkStartColor(new java.awt.Color(204, 51, 0));
        btn_fornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fornecedorActionPerformed(evt);
            }
        });

        btn_conf.setText("CONFIGURAÇÕES");
        btn_conf.setkEndColor(new java.awt.Color(204, 204, 204));
        btn_conf.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btn_conf.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        btn_conf.setkSelectedColor(new java.awt.Color(102, 88, 102));
        btn_conf.setkStartColor(new java.awt.Color(204, 51, 0));
        btn_conf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confActionPerformed(evt);
            }
        });

        btn_produto.setText("PRODUTOS");
        btn_produto.setkEndColor(new java.awt.Color(204, 204, 204));
        btn_produto.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btn_produto.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        btn_produto.setkSelectedColor(new java.awt.Color(255, 51, 0));
        btn_produto.setkStartColor(new java.awt.Color(204, 51, 0));
        btn_produto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_produtoActionPerformed(evt);
            }
        });

        lbUProf1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbUProf1.setForeground(new java.awt.Color(153, 0, 51));

        btn_report.setText("RELATÓRIOS");
        btn_report.setkEndColor(new java.awt.Color(204, 204, 204));
        btn_report.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btn_report.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        btn_report.setkSelectedColor(new java.awt.Color(0, 153, 255));
        btn_report.setkStartColor(new java.awt.Color(153, 51, 0));
        btn_report.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reportActionPerformed(evt);
            }
        });

        lbUser.setForeground(new java.awt.Color(245, 243, 243));

        lbUProf.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        lbUProf.setForeground(new java.awt.Color(245, 243, 243));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Profile.png"))); // NOI18N

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbUProf1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(499, 499, 499))
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addComponent(btn_venda, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_produto, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_user, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_report, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_conf, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbUProf, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caxa1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(230, 230, 230))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbUProf1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(caxa1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbUProf, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                .addComponent(btn_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_venda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_produto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_fornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_conf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_report, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        LabelBackground.setkEndColor(new java.awt.Color(255, 255, 255));
        LabelBackground.setkStartColor(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Matura MT Script Capitals", 2, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("eVendas, Farmacia Ukumi");

        kGradientPanel5.setkStartColor(new java.awt.Color(51, 153, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TOTAL DE VENDAS");

        lblTotVenda.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotVenda.setForeground(new java.awt.Color(255, 255, 255));
        lblTotVenda.setText("0");

        lblVenda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblVenda.setForeground(new java.awt.Color(204, 204, 255));
        lblVenda.setText("0");

        javax.swing.GroupLayout kGradientPanel5Layout = new javax.swing.GroupLayout(kGradientPanel5);
        kGradientPanel5.setLayout(kGradientPanel5Layout);
        kGradientPanel5Layout.setHorizontalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(kGradientPanel5Layout.createSequentialGroup()
                        .addComponent(lblVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)))
                .addContainerGap())
        );
        kGradientPanel5Layout.setVerticalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel5Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel5Layout.createSequentialGroup()
                        .addComponent(lblTotVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        kGradientPanel15.setkEndColor(new java.awt.Color(0, 153, 102));
        kGradientPanel15.setkStartColor(new java.awt.Color(51, 153, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("TOTAL CREDITOS");

        lblCliente.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblCliente.setText("0");

        lblCliente1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblCliente1.setForeground(new java.awt.Color(204, 204, 255));
        lblCliente1.setText("0");

        javax.swing.GroupLayout kGradientPanel15Layout = new javax.swing.GroupLayout(kGradientPanel15);
        kGradientPanel15.setLayout(kGradientPanel15Layout);
        kGradientPanel15Layout.setHorizontalGroup(
            kGradientPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(kGradientPanel15Layout.createSequentialGroup()
                        .addComponent(lblCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        kGradientPanel15Layout.setVerticalGroup(
            kGradientPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel15Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(kGradientPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        kGradientPanel17.setkStartColor(new java.awt.Color(0, 153, 153));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TOTAL UTILIZADORES");

        lblUser.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setText("0");

        javax.swing.GroupLayout kGradientPanel17Layout = new javax.swing.GroupLayout(kGradientPanel17);
        kGradientPanel17.setLayout(kGradientPanel17Layout);
        kGradientPanel17Layout.setHorizontalGroup(
            kGradientPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(98, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        kGradientPanel17Layout.setVerticalGroup(
            kGradientPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel17Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        kGradientPanel18.setkEndColor(new java.awt.Color(204, 102, 0));
        kGradientPanel18.setkStartColor(new java.awt.Color(51, 153, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("TOTAL FORNECEDORES  ");

        lblForne.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblForne.setForeground(new java.awt.Color(255, 255, 255));
        lblForne.setText("0");

        javax.swing.GroupLayout kGradientPanel18Layout = new javax.swing.GroupLayout(kGradientPanel18);
        kGradientPanel18.setLayout(kGradientPanel18Layout);
        kGradientPanel18Layout.setHorizontalGroup(
            kGradientPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(78, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblForne, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        kGradientPanel18Layout.setVerticalGroup(
            kGradientPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel18Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblForne, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel7.getAccessibleContext().setAccessibleParent(jLabel7);

        jLabel2.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("PRODUTOS /ORDEM DE COMPRA");

        TableProduto.setModel(dTableModel);
        TableProduto.setColumnSelectionAllowed(true);
        jScrollPane7.setViewportView(TableProduto);
        TableProduto.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

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

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/docpdf.png"))); // NOI18N
        jButton5.setText("EXPORTAR PDF");
        jButton5.setkBackGroundColor(new java.awt.Color(102, 102, 102));
        jButton5.setkEndColor(new java.awt.Color(153, 153, 153));
        jButton5.setkHoverEndColor(new java.awt.Color(102, 102, 102));
        jButton5.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        jButton5.setkHoverStartColor(new java.awt.Color(51, 0, 0));
        jButton5.setkPressedColor(new java.awt.Color(102, 102, 102));
        jButton5.setkSelectedColor(new java.awt.Color(0, 153, 153));
        jButton5.setkStartColor(new java.awt.Color(51, 0, 0));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        webButton1.setText("Actualizar");
        webButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LabelBackgroundLayout = new javax.swing.GroupLayout(LabelBackground);
        LabelBackground.setLayout(LabelBackgroundLayout);
        LabelBackgroundLayout.setHorizontalGroup(
            LabelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LabelBackgroundLayout.createSequentialGroup()
                .addGroup(LabelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LabelBackgroundLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(LabelBackgroundLayout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(LabelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 1339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LabelBackgroundLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCampoPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(webButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147))
        );
        LabelBackgroundLayout.setVerticalGroup(
            LabelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LabelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addGroup(LabelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kGradientPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(kGradientPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(kGradientPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(kGradientPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addGroup(LabelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCampoPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(webButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(45, 126, 237));
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 1397, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1397, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public Principal() {
        initComponents();
        this.setLocation(40, 225);
        new Main();
    }

    public void loadListShowprod(List<Produto> lista, int nome) {

        while (dTableModel.getRowCount() > 0) {
            dTableModel.removeRow(0);
        }
        if (nome > 0) {
            lista = new ProdutoJpaController(emf).getProdQtd(nome);//new Consultas().formarProduto("%" + nome + "%");
        }
        String[] linha = new String[]{null, null, null, null, null, null, null, null, null, null};
        for (int i = 0; i < lista.size(); i++) {
            Produto p = lista.get(i);
            dTableModel.addRow(linha);

            dTableModel.setValueAt(p.getCodigo(), i, 0);
            dTableModel.setValueAt(p.getNome(), i, 1);
            dTableModel.setValueAt(p.getQtstock(), i, 2);
            dTableModel.setValueAt(p.getQtdvenda(), i, 3);
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
            dTableModel.setValueAt(prec, i, 4);
            dTableModel.setValueAt(p.getMargem(), i, 5);
            dTableModel.setValueAt(p.getLucro(), i, 6);
        }

    }

    public void verPrazos() {
        Long i = new EntradaJpaController(emf).getEntrFora();
        if (i > 0) {
            //painelprazo.setVisible(true);
        }
    }

    public void loadListShow(List<Empresa> lista, String nome) {

        while (t_data_view.getRowCount() > 0) {
            t_data_view.removeRow(0);
        }

        if (!nome.isEmpty()) {
            lista = (List<Empresa>) new EmpresaJpaController(emf);
        }

        String[] linha = new String[]{null, null, null, null, null, null, null, null};
        for (int i = 0; i < emprestimos.size(); i++) {
            t_data_view.addRow(linha);
           //System.out.println("" + emprestimos.get(i).getIdproduto().getNome());

            //Fproforma f = lista.get(i);t_data_view.setValueAt(emprestimos.get(i).getIdcliente().getNome(), i, 0);
            t_data_view.setValueAt(emprestimos.get(i).getIdproduto().getNome(), i, 0);
            t_data_view.setValueAt(emprestimos.get(i).getQc(), i, 1);
            t_data_view.setValueAt(emprestimos.get(i).getPrec(), i, 2);
            t_data_view.setValueAt(emprestimos.get(i).getTotalpagar(), i, 3);
            t_data_view.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(emprestimos.get(i).getDataemprestimo()), i, 4);
                //tbl_view.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy").format(emprestimos.get(i).getDataemprestimo(), i, 5);

            //tbl_view.setValueAt(emprestimos.get(i).getDataemprestimo(), i, 5);
            t_data_view.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy").format(emprestimos.get(i).getDatapagamento()), i, 5);

            t_data_view.setValueAt(emprestimos.get(i).getIdvendedor().getNomecompleto(), i, 6);
            t_data_view.setValueAt(emprestimos.get(i).getIva(), i, 7);

        }
    }

    public void comboboxe(JComboBox jComboNomeCliet) {
        List<Empresa> e = new ArrayList<>();
        e = new EmpresaJpaController(emf).findEmpresaEntities();
        jComboNomeCliet.removeAllItems();
        jComboNomeCliet.addItem("Todas Empresas");
        for (Empresa emp : e) {
            jComboNomeCliet.addItem(emp);
        }
    }

    public double total_venda() {
        List<Venda> emps = new VendaJpaController(emf).findVendaEntities();
        double soma = 0;
        for (Venda emp : emps) {
            soma += emp.getValor();
        }
        return soma;
    }

    Action Anular = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Emprestimo v = (Emprestimo) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            int resp = JOptionPane.showOptionDialog(null, "{Pretende anular este Pendente?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (resp == 1 || resp == -1) {
                return;
            }
            EntityManager em = emf.createEntityManager();
            List<Emprestimo> le = new EmprestimoJpaController(emf).getEmprestimo(v.getDataemprestimo());
            em.getTransaction().begin();/////////////////////////////
            try {
                for (Emprestimo ep : le) {

                    Entrada ent = new EntradaJpaController(emf).findEntradaS(new EntradaPK(ep.getIdproduto().getIdproduto(), ep.getDatae()), em);
                    Produto p = new ProdutoJpaController(emf).findProdutoS(ep.getIdproduto().getIdproduto(), em);
//                  Entrada ent = new EntradaJpaController(emf).getEntrada(ep.getDatae());

                    ent.setQv(ent.getQv() + ep.getQc());
                    p.setQtdvenda(p.getQtdvenda() + ep.getQtprod());

                    new EntradaJpaController(emf).edit(ent, ent, em);
                    new ProdutoJpaController(emf).edit(p, p, em);
                    new EmprestimoJpaController(emf).destroy(ep.getIdemp(), em);

                }
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                JOptionPane.showMessageDialog(null, "Anulado com sucesso");
                em.getTransaction().commit();
            } catch (Exception ex) {
                //   em.getTransaction().rollback();
                em.clear();
                em.getTransaction().commit();
                Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        }
    };

    Action pagar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Emprestimo v = (Emprestimo) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            int resp = JOptionPane.showOptionDialog(null, "{Considera um pendente pago?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, new String[]{"SIM", "NÃO"}, null);
            if (resp == 1 || resp == -1) {
                return;
            }
//            String s = new SeriefacturaJpaController(emf).findSeriefacturaEntities().get(0).getSeriefactura();
//            String ss = (Integer.parseInt(s) + 1) + "";
            List<Emprestimo> le = new EmprestimoJpaController(emf).getEmprestimo(v.getDataemprestimo());
            Date pag = new Date();
            for (Emprestimo p : le) {
                Venda ve = new Venda();
                ve.setDatavenda(p.getDataemprestimo());
                ve.setDatapag(pag);
                ve.setDesconto(p.getDesconto());
                ve.setIdvendedor(new Vendedor(client));
                ve.setIdproduto(p.getIdproduto());
                ve.setQtd(p.getQtprod());
                ve.setIdcliente(p.getIdcliente());
                ve.setValor(p.getTotalpagar());
                ve.setDatae(p.getDatae());
                ve.setQc(p.getQc());
                ve.setTiva(p.getTiva());
                ve.setTdesc(p.getTdesc());
                ve.setPrec(p.getPrec());
                ve.setIva(p.getIva());
                ve.setQstock(p.getQstock());
                ve.setQpac(p.getQpac());
                ve.setCaixa(1);
                ve.setEstado(0);
                new VendaJpaController(emf).create(ve);
                try {
                    new EmprestimoJpaController(emf).destroy(p.getIdemp());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            try {
//                new SeriefacturaJpaController(emf).edit(new Seriefactura(1, ss));
//            } catch (Exception ex) {
//                Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
//            }
            ((DefaultTableModel) table.getModel()).removeRow(modelRow);
            JOptionPane.showMessageDialog(null, "Venda efectuda com sucesso");
//           
        }
    };

    private void btn_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_userActionPerformed
        try {
            CadastroUtilizador v = new CadastroUtilizador();

            JDialog dia = new JDialog();
            dia.setModal(true);
            dia.setContentPane(v.getContentPane());
            dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
            dia.setBounds(v.getBounds());
            dia.setSize(v.getWidth(), LabelBackground.getHeight() - 45);
            dia.setLocationRelativeTo(LabelBackground);
            dia.setLocation(60, 225);
            dia.setVisible(true);

        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_userActionPerformed

    private void btn_fornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fornecedorActionPerformed
        CadastroFornec c = new CadastroFornec();
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        // dia.setUndecorated(true);
        dia.setContentPane(c.getContentPane());
        dia.setBounds(c.getBounds());
        dia.setLocationRelativeTo(LabelBackground);
        dia.setVisible(true);
    }//GEN-LAST:event_btn_fornecedorActionPerformed

    private void btn_produtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_produtoActionPerformed
        CadastroProduto c = new CadastroProduto(client);
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        // dia.setUndecorated(true);
        dia.setContentPane(c.getContentPane());
        // dia.setBounds(c.getBounds());
        dia.setSize(c.getWidth(), LabelBackground.getHeight() - 45);
        dia.setLocationRelativeTo(LabelBackground);
        dia.setVisible(true);
    }//GEN-LAST:event_btn_produtoActionPerformed

    private void btn_reportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reportActionPerformed
        ConsultasInternas c = null;
        try {
            c = new ConsultasInternas(client);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        dia.setContentPane(c.getContentPane());
        // dia.setBounds(c.getBounds());
        dia.setSize(c.getWidth(), LabelBackground.getHeight() - 45);
        dia.setLocationRelativeTo(LabelBackground);
        dia.setVisible(true);
    }//GEN-LAST:event_btn_reportActionPerformed

    private void btn_vendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vendaActionPerformed
        try {

            VendaEmprestimo v = new VendaEmprestimo(client);
            JDialog dia = new JDialog();
            dia.setModal(true);
            dia.setContentPane(v.getContentPane());
            dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
            // dia.setBounds(v.getBounds());
            dia.setSize(v.getWidth(), LabelBackground.getHeight() - 45);
            dia.setLocationRelativeTo(LabelBackground);
            dia.setVisible(true);
            dia.setDefaultCloseOperation(0);
            //            LabelBackground.add(dia).setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_vendaActionPerformed

    private void btn_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clienteActionPerformed
        try {

            CadastroClient v = new CadastroClient();
            JDialog dia = new JDialog();
            dia.setModal(true);
            dia.setContentPane(v.getContentPane());
            dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
            //dia.setBounds(v.getBounds());
            dia.setSize(v.getWidth(), LabelBackground.getHeight() - 45);
            dia.setLocationRelativeTo(LabelBackground);
            dia.setVisible(true);
            dia.setDefaultCloseOperation(0);

        } catch (Exception e) {
        }
    }//GEN-LAST:event_btn_clienteActionPerformed

    private void btn_confActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confActionPerformed
        Configuracao c = new Configuracao();
        JDialog dia = new JDialog();
        dia.setModal(true);
        dia.setIconImage(new ImageIcon(getClass().getResource("/Imagens/sas.png")).getImage());
        // dia.setUndecorated(true);
        dia.setContentPane(c.getContentPane());
        dia.setBounds(c.getBounds());
        dia.setLocationRelativeTo(LabelBackground);
        dia.setVisible(true);
    }//GEN-LAST:event_btn_confActionPerformed

    private void txtCampoPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoPesqKeyReleased

        if (!txtCampoPesq.getText().trim().equals("")) {

            int valor = Integer.parseInt(txtCampoPesq.getText());

            System.out.println("" + valor);
            if (valor >= 0) {
                loadListShowprod(tipoProd, valor);
                loadListShowprod(new ProdutoJpaController(emf).getProdAsc(), valor);
            }
        }
    }//GEN-LAST:event_txtCampoPesqKeyReleased

    private void cbempItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbempItemStateChanged
        try {
            emprestimos.clear();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbempItemStateChanged

    private void txtCampoPesqMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCampoPesqMouseExited

    }//GEN-LAST:event_txtCampoPesqMouseExited

    private void txtCampoPesqMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCampoPesqMouseReleased

    }//GEN-LAST:event_txtCampoPesqMouseReleased

    private void txtCampoPesqKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCampoPesqKeyPressed

    }//GEN-LAST:event_txtCampoPesqKeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String pl = new File("saslogo.jpg").getAbsolutePath();
        parametros.clear();
        List<Produto> ListProd = new ArrayList<>();
        parametros.put("img", pl);
        if (!txtCampoPesq.getText().isEmpty()) {
            ListProd = new ProdutoJpaController(emf).getProdNomeLike(txtCampoPesq.getText());//new Consultas().formarProduto("%" + nome + "%");
        } else {
            ListProd = new ProdutoJpaController(emf).getProdAscUpdate();
        }
        for (int i = 0; i < ListProd.size(); i++) {
            Produto produt = ListProd.get(i);
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
    }//GEN-LAST:event_jButton5ActionPerformed

    private void webButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webButton1ActionPerformed
        // TODO add your handling code here:
//        Main frame = new Main();
//        //frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);

        loadListShowprod(new ProdutoJpaController(emf).getProdAscUpdate(), valor);
        
        

    }//GEN-LAST:event_webButton1ActionPerformed

    public void addVendaFrame() {
        try {
            VendaEmprestimo v = new VendaEmprestimo(client);
            LabelBackground.add(v).setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("infoss.net");
                String line;
                Process p = null;
                try {
                    p = Runtime.getRuntime().exec("tasklist");
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println(line.intern());
                        if (line.contains("java")) {
                            while ((line = input.readLine()) != null) {
                                if (line.contains("java")) {
                                    //  System.exit(0);
                                }
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                //new Principal().setVisible(true);
                login dialog = new login(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KGradientPanel LabelBackground;
    private javax.swing.JTable TableProduto;
    private keeptoo.KButton btn_cliente;
    private keeptoo.KButton btn_conf;
    private keeptoo.KButton btn_fornecedor;
    private keeptoo.KButton btn_produto;
    private keeptoo.KButton btn_report;
    private keeptoo.KButton btn_user;
    private keeptoo.KButton btn_venda;
    private javax.swing.JLabel caxa1;
    private java.util.List<modelo.Emprestimo> emprestimoList;
    private java.util.List<modelo.Emprestimo> emprestimoList1;
    private javax.persistence.Query emprestimoQuery;
    private javax.persistence.Query emprestimoQuery1;
    private keeptoo.KButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane7;
    private keeptoo.KGradientPanel kGradientPanel15;
    private keeptoo.KGradientPanel kGradientPanel17;
    private keeptoo.KGradientPanel kGradientPanel18;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel5;
    private javax.swing.JLabel lbUProf;
    private javax.swing.JLabel lbUProf1;
    private javax.swing.JLabel lbUser;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblCliente1;
    private javax.swing.JLabel lblForne;
    private javax.swing.JLabel lblTotVenda;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblVenda;
    public javax.persistence.EntityManager loja_clinicaPUEntityManager;
    private javax.swing.JTextField txtCampoPesq;
    public javax.persistence.EntityManager vendasPUEntityManager;
    private com.alee.laf.button.WebButton webButton1;
    // End of variables declaration//GEN-END:variables

}
