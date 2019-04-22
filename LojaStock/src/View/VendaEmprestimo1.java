package View;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import controller.ClienteJpaController;
import controller.DetalorgJpaController;
import controller.EmpresaJpaController;
import controller.EmprestimoJpaController;
import controller.EntradaJpaController;
import controller.ProdutoJpaController;
import controller.VendaJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import metodos.ButtonColumn;
import metodos.ValidarInteiro;
import modelo.Cliente;
import modelo.Detalorg;
import modelo.Empresa;
import modelo.Emprestimo;
import modelo.Entrada;
import modelo.EntradaPK;
import modelo.Produto;
import modelo.Venda;
import modelo.Vendedor;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 *
 * @author rjose
 */
public final class VendaEmprestimo1 extends javax.swing.JDialog {
    
    Calendar calendar = Calendar.getInstance();
    Date currentDate = new Date(calendar.getTime().getTime());
    EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
    List<Produto> listaTipo = new ArrayList<>();
    List<Cliente> listac = new ArrayList<>();
    List<Venda> listaX = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();
    List<Emprestimo> emprestimos = new ArrayList<>();
    List<Emprestimo> listafatura = new ArrayList<>();
    Detalorg dog = new Detalorg();
    String cbarra = "";
    private final DefaultTableModel vendaTmodel = new DefaultTableModel(null, new String[]{"Codigo", "Cliente", "Produto", "Qtd", "Preco", "Data Emprest", "Data Pagamento", "Total a Pagar"});
    private final DefaultTableModel prevenda = new DefaultTableModel(null, new String[]{"Produto", "Categoria", "Qt-Prev", "preco", "Total"});
    private final DefaultTableModel fmodel = new DefaultTableModel(null, new String[]{"id", "Nome do Cliente", "Empresa", "Valor", "Data Fornecimento", "Data de Pagamento", "Vendedor", "Ver", "Pagar", "Anular"});
    private int valor = 0;
    private float soma = 0;
    private int last = 0;
    private int ctr = 0;
    private int client = 0;
    public static final int RET_OK = 1;
    private static Boolean bool = true;
    AutoCompleteSupport support2 = null;
    AutoCompleteSupport support = null;
    float vp = 0;
    Date n;
    String impvenda = "";
    String impprof = "";
    int impv = 0;
    private static boolean nrcaixa = false;
    
    JasperPrint jpPrint;
    JasperViewer jv;
    Map parametros = new HashMap<>();
    
    public VendaEmprestimo1(int c) throws Exception {
        initComponents();
        this.setTitle("Nova Venda");
        this.setLocation(30, 0);
        this.client = c;
    
        this.getRootPane().setDefaultButton(btAdd);
        combobox(Comboproduto);
        comboboxe(cbemp);
        JComboBox jcClient = null;
        comboboxClient(jcClient);
        //  File arquivo = new File("Farmacia/META-INF/config.properties");
        String cpath = new File("config.properties").getAbsolutePath();
        File arquivo = new File(cpath);
        String url = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(arquivo, "rw");
            url = raf.readLine();
            if (url == null || url.equals("")) {
                JOptionPane.showMessageDialog(this, "Por favor introduza o nr de caixa para este computador\n"
                        + "O numero de caixa deve ser diferente em cada computador de venda", "Atencao", JOptionPane.WARNING_MESSAGE);
                nrcaixa = false;
            } else {
                nrcaixa = true;
            }
            caxa.setText("#" + url);
            String imp1 = raf.readLine();
            String imp2 = raf.readLine();
            url = raf.readLine();
            // System.out.println(url);
            if (url != null && url.equals("0")) {
                impvenda = imp1;
            } else if (url != null) {
                impvenda = imp2;
            } else {
                impvenda = "";
            }
            impprof = imp2;
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLconfig.class.getName()).log(Level.SEVERE, null, ex);
        }
//        Cliente cl = new ClienteJpaController(emf).getClienteLikeN("Cliente");
//        if (cl != null) {
//            jcClient.setSelectedItem(cl);
//        }
        //vendaLista.add("Espaco de Pre-venda", 200);
        
        vendaLista.setForeground(Color.black);
        dog = new DetalorgJpaController(emf).findDetalorgEntities().get(0);
        txtQtVendida.setDocument(new ValidarInteiro());
        txtQtVendida.setText("1");
        tfcp.setDocument(new ValidarInteiro());
        //painel1.setVisible(false);
        Emprestimo.setSelectedIndex(0);
        if (!listaTipo.isEmpty()) {
            Comboproduto.setSelectedIndex(0);
        }
        if (!listac.isEmpty()) {
            //jcClient.setSelectedIndex(0);
        }
        //jcClient.setSelectedItem("");
        Date d = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        n = cal.getTime();
        jdInicio1.setDate(n);
        listafatura = new EmprestimoJpaController(emf).getEmprestPeriodo(n, null, cbemp.getSelectedItem());
        mostraEmprest();
        this.tabelaFactura.getColumnModel().getColumn(0).setMinWidth(0);//setPreferredWidth(10);
        this.tabelaFactura.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tabelaFactura.getColumnModel().getColumn(0).setPreferredWidth(0);
//        this.tabelaFactura.getColumnModel().getColumn(1).setMaxWidth(20);
//        this.tabelaFactura.getColumnModel().getColumn(4).setMaxWidth(70);
//        this.tabelaFactura.getColumnModel().getColumn(5).setMaxWidth(50);//setPreferredWidth(3);
//        this.tabelaFactura.getColumnModel().getColumn(6).setMaxWidth(70);//setPreferredWidth(3);
        this.tabelaFactura.setRowHeight(30);
        Icon iv = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_ver.png"));
        Icon ip = new javax.swing.ImageIcon(getClass().getResource("/Imagens/ico_pago.png"));
        Icon ir = new javax.swing.ImageIcon(getClass().getResource("/Imagens/bt_cancel.png"));
        ButtonColumn bv = new ButtonColumn(this.tabelaFactura, ver, 7, iv);
        ButtonColumn bp = new ButtonColumn(this.tabelaFactura, pagar, 8, ip);
        ButtonColumn bd = new ButtonColumn(this.tabelaFactura, Anular, 9, ir);
//        btConfirmar.setVisible(false);

        AbstractAction aa = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //  System.out.println("Here");
                btAdd.requestFocusInWindow();//request that the button has focus
                try {
                    int ip = showListaCompra(0);
                    txtQtVendida.setText("1");//
                    Produto p = (Produto) Comboproduto.getSelectedItem();
                    List<Entrada> le = new EntradaJpaController(emf).getEntrada(p);//problema nao tras entradas expiradas
                    comboboxe(cbtquant, le);
                    cbtquant.setSelectedIndex(ip);
                } catch (Exception e) {
                    System.out.println("erro: " + e.getMessage());
                }
                //txtcbarra.setText("");
                cbarra = "";
            }
        };

        //so button can be pressed using F1 and ENTER
        btAdd.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        btAdd.getActionMap().put("Enter", aa);
        //btAdd.addActionListener(aa);
    }
    
    public void mostraEmprest() throws Exception {
        while (fmodel.getRowCount() > 0) {
            fmodel.removeRow(0);
        }
        Date d = null;
        float saldo = 0;
        int j = -1;
        int k = 0;
        vp = 0;
        String[] linha = new String[]{null, null, null, null, null, null, null};
        for (int i = 0; i < listafatura.size(); i++) {
            if (d == null || d.equals(listafatura.get(i).getDataemprestimo()) == false) {
                if (j != -1) {
                    fmodel.setValueAt(saldo, j, 3);
                    vp = vp + saldo;
                    saldo = 0;
                }
                fmodel.addRow(linha);
                fmodel.setValueAt(listafatura.get(i), k, 0);
                fmodel.setValueAt(listafatura.get(i).getIdcliente(), k, 1);
                fmodel.setValueAt(listafatura.get(i).getIdcliente().getIdempresa(), k, 2);
                //   fmodel.setValueAt(listafatura.get(i).getTotalpagar(), j, 3);
                fmodel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(listafatura.get(i).getDataemprestimo()), k, 4);
                fmodel.setValueAt(new java.text.SimpleDateFormat("dd/MM/yyyy").format(listafatura.get(i).getDatapagamento()), k, 5);
                fmodel.setValueAt(listafatura.get(i).getIdvendedor().getNomecompleto(), k, 6);
                saldo = saldo + listafatura.get(i).getTotalpagar();
                d = listafatura.get(i).getDataemprestimo();
                j = k;
                k = k + 1;
            } else {
                saldo = saldo + listafatura.get(i).getTotalpagar();
            }
        }
        if (j != -1) {
            fmodel.setValueAt(saldo, j, 3);
        }
        vp = vp + saldo;
        lbsaldo.setText("Valor Total: " + vp);
    }
    
    Action ver = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Emprestimo v = (Emprestimo) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            List<Emprestimo> le = new EmprestimoJpaController(emf).getEmprestimo(v.getDataemprestimo());
            FormEmprestimo f = new FormEmprestimo(new javax.swing.JFrame(), le, true);
            f.setVisible(true);
        }
    };
    
    
    
    Action pagar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = (JTable) e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());
            Emprestimo v = (Emprestimo) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0);
            int resp = JOptionPane.showOptionDialog(null, "Considera um pendente pago?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
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
                ve.setCaixa(Integer.parseInt(caxa.getText().substring(1)));
                ve.setEstado(0);
                new VendaJpaController(emf).create(ve);
                try {
                    new EmprestimoJpaController(emf).destroy(p.getIdemp());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(VendaEmprestimo1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            try {
//                new SeriefacturaJpaController(emf).edit(new Seriefactura(1, ss));
//            } catch (Exception ex) {
//                Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
//            }
            ((DefaultTableModel) table.getModel()).removeRow(modelRow);
            JOptionPane.showMessageDialog(null, "Venda efectuda com sucesso");
//            try {
//  //              new SeriefacturaJpaController(emf).edit(new Seriefactura(1, ss));
//                parametros.clear();
//                parametros.put("nome", v.getIdcliente().getNome());//txtClient.getText());
//                //   parametros.put("nuit", v.getNuitc());
//                String imge = new File("saslogo.jpg").getAbsolutePath();
//                parametros.put("img", imge);
////                parametros.put("fatura", ss);
//                parametros.put("data", v.getDatapagamento());
//                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(le);
//                String path = new File("relatorios/recibov2.jasper").getAbsolutePath();
//                jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
//                jv = new JasperViewer(jpPrint, false); //O false eh para nao fechar a aplicacao
//                JDialog viewer = new JDialog(new javax.swing.JFrame(), "Factura", true);
//                viewer.setSize(1024, 768);
//                viewer.setLocationRelativeTo(null);
//                viewer.getContentPane().add(jv.getContentPane());
//                viewer.setVisible(true);
//                limparVenda();
//            } catch (Exception ex) {
//                System.out.println("Houve problemas na impressao: " + ex.getLocalizedMessage());
//            }
        }
    };
    
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
                Logger.getLogger(VendaEmprestimo1.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        }
    };

//    public void analisarBotao() {
//        if (labelTotal.getText() != null) {
//            btTroco.setVisible(true);
//            txtNota.setForeground(Color.lightGray);
//        }
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelHistory = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        caxa = new javax.swing.JLabel();
        Emprestimo = new javax.swing.JTabbedPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel2 = new javax.swing.JPanel();
        vendaLista = new javax.swing.JPanel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        btCancel3 = new keeptoo.KButton();
        jButton8 = new keeptoo.KButton();
        btCloseVenda3 = new keeptoo.KButton();
        jButton7 = new keeptoo.KButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btAdd = new keeptoo.KButton();
        cbtpreco = new javax.swing.JComboBox();
        cbtquant = new javax.swing.JComboBox();
        Comboproduto = new javax.swing.JComboBox();
        tfcp = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtQtVendida = new javax.swing.JTextField();
        txtcbarra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        label_troco = new javax.swing.JLabel();
        txt_valor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tfcp1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        labelSucessoEmp = new javax.swing.JLabel();
        painelVenda1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jBpesquisarProdEmprest = new javax.swing.JButton();
        jdFim1 = new com.toedter.calendar.JDateChooser();
        jdInicio1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbemp = new javax.swing.JComboBox();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelaFactura = new javax.swing.JTable();
        lbsaldo = new javax.swing.JLabel();

        setDefaultCloseOperation(getLimpaVenda());
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        labelHistory.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelHistory.setText("Venda");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Caixa ");

        caxa.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(557, 557, 557)
                .addComponent(labelHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caxa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(caxa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        Emprestimo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EmprestimoMouseClicked(evt);
            }
        });

        jDesktopPane1.setBackground(new java.awt.Color(240, 240, 240));

        vendaLista.setPreferredSize(new java.awt.Dimension(631, 450));

        kGradientPanel1.setkEndColor(new java.awt.Color(255, 255, 255));
        kGradientPanel1.setkStartColor(new java.awt.Color(204, 204, 255));

        btCancel3.setText("Cancelar");
        btCancel3.setkEndColor(new java.awt.Color(0, 102, 102));
        btCancel3.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btCancel3.setkHoverStartColor(new java.awt.Color(153, 153, 153));

        jButton8.setText("Venda a Credito");
        jButton8.setkEndColor(new java.awt.Color(0, 102, 102));
        jButton8.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        jButton8.setkHoverStartColor(new java.awt.Color(153, 153, 153));

        btCloseVenda3.setText("Finalizar Venda");
        btCloseVenda3.setkEndColor(new java.awt.Color(0, 102, 102));
        btCloseVenda3.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btCloseVenda3.setkHoverStartColor(new java.awt.Color(153, 153, 153));

        jButton7.setText("Proforma");
        jButton7.setkEndColor(new java.awt.Color(0, 102, 102));
        jButton7.setkHoverColor(new java.awt.Color(102, 102, 102));
        jButton7.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        jButton7.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        jButton7.setkIndicatorColor(new java.awt.Color(153, 153, 153));
        jButton7.setkPressedColor(new java.awt.Color(0, 0, 0));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable1);

        btAdd.setText(">>");
        btAdd.setkBackGroundColor(new java.awt.Color(255, 51, 0));
        btAdd.setkEndColor(new java.awt.Color(255, 51, 0));
        btAdd.setkHoverEndColor(new java.awt.Color(255, 51, 0));
        btAdd.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btAdd.setkHoverStartColor(new java.awt.Color(255, 51, 0));
        btAdd.setkSelectedColor(new java.awt.Color(0, 102, 102));
        btAdd.setkStartColor(new java.awt.Color(0, 153, 102));
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        cbtpreco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbtpreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtprecoActionPerformed(evt);
            }
        });

        cbtquant.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbtquant.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbtquantItemStateChanged(evt);
            }
        });
        cbtquant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtquantActionPerformed(evt);
            }
        });

        Comboproduto.setToolTipText("Seleccione ou pesquise produto");
        Comboproduto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Comboproduto.setOpaque(false);
        Comboproduto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboprodutoItemStateChanged(evt);
            }
        });

        tfcp.setToolTipText("codigo");
        tfcp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcpActionPerformed(evt);
            }
        });
        tfcp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfcpKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfcpKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setText("Produto:*");

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 102, 0));
        jLabel10.setText("Lot/Serie:");

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 102, 0));
        jLabel7.setText("Quantidade: *");

        txtQtVendida.setToolTipText("");
        txtQtVendida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtVendidaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setText("Codigo de Barra:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 153));
        jLabel3.setText("PAGAMENTOS");

        label_troco.setBackground(new java.awt.Color(255, 204, 204));
        label_troco.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        label_troco.setForeground(new java.awt.Color(255, 51, 0));
        label_troco.setText("0,00");
        label_troco.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txt_valor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_valorActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Montante:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Troco Cliente:");

        tfcp1.setToolTipText("codigo");
        tfcp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcp1ActionPerformed(evt);
            }
        });
        tfcp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfcp1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfcp1KeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 0, 153));
        jLabel4.setText("Produtos Adicionados");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, kGradientPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(41, 41, 41))
                                                .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGap(28, 28, 28)))
                                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(tfcp, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(tfcp1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(21, 21, 21)
                                                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(cbtquant, 0, 301, Short.MAX_VALUE)
                                                        .addComponent(Comboproduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addComponent(txtcbarra)))
                                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(28, 28, 28)
                                            .addComponent(txtQtVendida, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(20, 20, 20)
                                            .addComponent(cbtpreco, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(61, 61, 61)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btCancel3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                        .addGap(0, 18, Short.MAX_VALUE)
                                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(18, 18, 18)
                                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label_troco, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btCloseVenda3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(23, 23, 23))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addComponent(txtcbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tfcp, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Comboproduto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbtquant, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfcp1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbtpreco)
                                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtQtVendida, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75)
                        .addComponent(btCloseVenda3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btCancel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_troco, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout vendaListaLayout = new javax.swing.GroupLayout(vendaLista);
        vendaLista.setLayout(vendaListaLayout);
        vendaListaLayout.setHorizontalGroup(
            vendaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vendaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        vendaListaLayout.setVerticalGroup(
            vendaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vendaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(vendaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 1293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vendaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDesktopPane1.add(jPanel2);
        jPanel2.setBounds(-20, 0, 1340, 440);

        Emprestimo.addTab("Registo de Venda", jDesktopPane1);

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelSucessoEmp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelSucessoEmp.setForeground(new java.awt.Color(102, 0, 0));
        labelSucessoEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelSucessoEmpMouseEntered(evt);
            }
        });

        painelVenda1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBpesquisarProdEmprest.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBpesquisarProdEmprest.setText("Pesquisar Emprestimo");
        jBpesquisarProdEmprest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBpesquisarProdEmprestActionPerformed(evt);
            }
        });

        jdFim1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdFim1KeyReleased(evt);
            }
        });

        jdInicio1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jdInicio1KeyReleased(evt);
            }
        });

        jLabel5.setText("Apartir de:");

        jLabel6.setText("a");

        cbemp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbemp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbempItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jdFim1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBpesquisarProdEmprest, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(cbemp, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(399, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBpesquisarProdEmprest)
                        .addComponent(cbemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jdFim1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabelaFactura.setModel(fmodel);
        tabelaFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaFacturaMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tabelaFactura);

        lbsaldo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbsaldo.setForeground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout painelVenda1Layout = new javax.swing.GroupLayout(painelVenda1);
        painelVenda1.setLayout(painelVenda1Layout);
        painelVenda1Layout.setHorizontalGroup(
            painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelVenda1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbsaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(499, 499, 499))
            .addGroup(painelVenda1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelVenda1Layout.setVerticalGroup(
            painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVenda1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbsaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(labelSucessoEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(809, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(painelVenda1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addComponent(labelSucessoEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelVenda1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(22, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Emprestimo.addTab("Pendentes", jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Emprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, 1312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Emprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void selecionarNovaVenda() {
        Emprestimo.setSelectedIndex(0);
        
    }
    
    public void analisarTab() throws Exception {
        if (Emprestimo.getSelectedIndex() == 0) {
            support2.uninstall();
            combobox(Comboproduto);
            if (!listaTipo.isEmpty()) {
                Comboproduto.setSelectedIndex(0);
            }
        } else {
            limparVenda();
            listafatura.clear();
            listafatura = new EmprestimoJpaController(emf).getEmprestPeriodo(n, null, cbemp.getSelectedItem());
            mostraEmprest();
            // this.setTitle("Emprestimo de Prouto");
        }
        //  Principal p = new Principal();

    }
    
    public void setIndexAba(int i) {
        Emprestimo.setSelectedIndex(i);
    }
    
    public void selecionarPromocao() {
        Emprestimo.setSelectedIndex(1);
    }

    private void EmprestimoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EmprestimoMouseClicked
        try {
            analisarTab();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_EmprestimoMouseClicked
    
      public void comboboxClient(JComboBox c) {
        listac = new ClienteJpaController(emf).getClientAsc();
        if (listac.isEmpty()) {
            return;
        }
        Cliente[] elements = listac.toArray(new Cliente[]{});
        //support = AutoCompleteSupport.install(
               // c, GlazedLists.eventListOf(elements));
    }
    public void limparVenda() {
        vendaLista.removeAll();
        //vendaLista.add(" Produto  " + "                                                                                                                 Preco   " + "     Qtd  " + "      Total  ");
        lpro.clear();
        lproe.clear();
        lprov.clear();
        txtQtVendida.setText("1");
        //labelTotal.setText("");
        //ControlaVenda.setText("");
        valor = 0;
        soma = 0;
        le.clear();
        ls.clear();
        if (support2 != null) {
            support2.uninstall();
            combobox(Comboproduto);
        }
    }
    
    public void combobox(JComboBox c) {
        listaTipo = new ProdutoJpaController(emf).getProdAsc();
        if (listaTipo.isEmpty()) {
            return;
        }
        Produto[] elements = listaTipo.toArray(new Produto[]{});
        support2 = AutoCompleteSupport.install(
                c, GlazedLists.eventListOf(elements));
        
    }
    
  
    
    public int getLimpaVenda() {
        limparVenda();
        return 1;
    }//    public void cabecalho(int i) {
//
//        vendaLista.add("Solicitacao :" + i);
//        vendaLista.add("_____________________________________________________________________________________");
//    }

    public void mostrarResultdo(String prod, float p, int qt, int v, int j) {
        //  cabecalho(v);
        float total = p * qt;
        String pr;
        if (prod.length() > 70) {
            pr = prod.substring(0, 68);
        } else {
            pr = prod;
            for (int i = 0; i < 100 - prod.length(); i++) {
                pr = pr + " ";
            }
            pr = pr + p;
            pr = pr + "             ";
            pr = pr + qt;
            pr = pr + "             ";
            pr = pr + total;
            pr = pr + "             ";
        }
       // vendaLista.addItem(pr);
        vendaLista.setForeground(Color.BLUE);
        soma = soma + (p * qt);
        //labelTotal.setText("Total:   " + soma);
    }
    
    static List<Produto> lpro = new ArrayList<Produto>();
    static List<Produto> lproe = new ArrayList<Produto>();
    static List<Produto> lprov = new ArrayList<Produto>();
    static List<Venda> lv = new ArrayList<Venda>();
    static List<Entrada> le = new ArrayList<Entrada>();
    static List<Entrada> ls = new ArrayList<Entrada>();
    
    public int showListaCompra(int c) throws Exception {
        if (nrcaixa == false) {
            JOptionPane.showMessageDialog(this, "Por favor introduza o nr de caixa para este computador\n"
                    + "O numero de caixa deve ser diferente em cada computador de venda", "Atencao", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        int icb = 0;
        try {
            //lprov tem um unico produto com a quantidade que devera permanecer na base de dados depois da venda
            //lpro tem um unico produto com qunatidade total seleccionada para veificar se esta disponivel
            //lproe tem toda seleccao feita conforme aparecera no recibo, e sera usado para modificar a seleccao
            //lsp tem a lista de entradas que permanecerao na base de dados depois da venda
            //le tem a entrada que devera permanecer na base de dados depois da venda
            //ls tem a entrada com a quantidade total seleccionada
            int qitem = 0;
            int id = ((Produto) Comboproduto.getSelectedItem()).getIdproduto();
            if (txtQtVendida.getText().trim().equals("") || txtQtVendida.getText().equals("0")) {
                JOptionPane.showMessageDialog(this, "Insira a quantidade para venda", "Atencao", JOptionPane.WARNING_MESSAGE);
                txtQtVendida.requestFocusInWindow();//grabFocus();
                return 0;
            }
            System.out.println(le);
            List<Entrada> lsp = new ArrayList<>();
            int qtt = Integer.valueOf(txtQtVendida.getText());
            Produto pro = new ProdutoJpaController(emf).findProduto(id);
            Entrada en = (Entrada) cbtquant.getSelectedItem();
            System.out.println("en: " + en);
            icb = cbtquant.getSelectedIndex();
            int qtdvenda = en.getQv();//pro.getQtdvenda(); pro.getPrecovenda();
            float preco = 0;
            int ip = cbtpreco.getSelectedIndex();
            if (ip == 0) {
                preco = en.getPreco();
                qitem = en.getQa();
            } else if (ip == 1) {
                preco = en.getPb();
                qitem = en.getQb();
            } else if (ip == 2) {
                preco = en.getPc();
                qitem = en.getQc();
            }
            //System.out.println("preco: "+pro.getNome()+" "+preco);
            String produto = pro.getNome();
            if (pro.getQtdvenda() == 0) {
                JOptionPane.showMessageDialog(this, "Nao ha " + produto + " na Loja", "Atencao", JOptionPane.WARNING_MESSAGE);
                txtQtVendida.grabFocus();
                return 0;
            }
            if (qtt * qitem > pro.getQtdvenda()) {
                JOptionPane.showMessageDialog(this, "A Qtd. de " + produto + " disponivel na Loja e de  " + qtdvenda, "Atencao", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            if (qtt * qitem > qtdvenda) {
                JOptionPane.showMessageDialog(this, "Para esta Serie/Lote, de " + produto + " a Qtd.  disponivel na Loja e de  " + qtdvenda, "Atencao", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            Date d = new Date();
            if (en.getDataexpiracao() != null && en.getDataexpiracao().before(d)) {
                if (qtt > 0) {
                    JOptionPane.showMessageDialog(this, produto + "\n nesta Serie/Lote estao foram do prazo", "Atencao", JOptionPane.WARNING_MESSAGE);
                }
                return 0;
            }
            
            if (lpro.contains(pro)) {
                int l = lprov.indexOf(pro);
                if (qtt * qitem > lprov.get(l).getQtdvenda()) {
                    JOptionPane.showMessageDialog(this, "Excedeu a Qtd. disponivel\n"
                            + "na Loja para este Produto", "Atencao", JOptionPane.WARNING_MESSAGE);
                    return 0;
                }
            }
            
            if (lpro.contains(pro) && le.contains(en)) {
                int l = lprov.indexOf(pro);
                if (qtt * qitem > lprov.get(l).getQtdvenda()) {
                    JOptionPane.showMessageDialog(this, "Excedeu a Qtd. disponivel\n"
                            + "na Loja para este Produto", "Atencao", JOptionPane.WARNING_MESSAGE);
                    return 0;
                }
                l = le.indexOf(en);
                System.out.println("passo: " + l);
                if (qtt * qitem > le.get(l).getQv()) {
                    JOptionPane.showMessageDialog(this, "Excedeu a Qtd. disponivel\n"
                            + "na Loja para esta Serie/Lote", "Atencao", JOptionPane.WARNING_MESSAGE);
                    return 0;
                }
                Iterator<Entrada> items2 = new ArrayList(le).listIterator();
                Entrada ee;
                //Date de = pro.getDataexpiracao();
                while (items2.hasNext()) {
                    ee = items2.next();
//                    if (ee.getProduto().getNome().equalsIgnoreCase(pro.getNome())) {
//                        lsp.add(ee);
                    if (ee.getEntradaPK().equals(en.getEntradaPK())) {
                        lsp.add(ee);
                    }
                }
                if (lsp.isEmpty()) {
                    lsp.add(en);
                    le.addAll(lsp);
                    Entrada entra = new Entrada(en.getEntradaPK());
                    entra.setQv(0);
                    entra.setProduto(pro);
                    ls.add(entra);
                }
            } else {
                lsp.add(en);//lsp = new EntradaJpaController(emf).getEntrav(pro);//trazer saidas do produto x com estado x
                if (!lsp.isEmpty()) {
                    le.addAll(lsp);
                    Entrada entra = new Entrada(en.getEntradaPK());
                    entra.setQv(0);
                    entra.setProduto(pro);
                    ls.add(entra);
                }
            }
            
            int qv = 0;
            //////   

            //int qitem =  en.getQa();//int qitem = pro.getQitem();
            int qs = 0;
            int rest = 0;
            System.out.println("qitem: " + qitem);
            //       if (qitem > 1) {
            for (int j = 0; j < lsp.size(); j++) {
                // if(qtt)
                Entrada e = lsp.get(j);
//            if (ip == 0) {
//                preco = e.getPreco();
//                qitem = e.getQa();
//            } else if (ip == 1) {
//                preco = e.getPb();  //se for para seleccionar automaticamente diferentes entradas
//                qitem = e.getQb();
//            } else if (ip == 2) {
//                preco = e.getPc();
//                qitem = e.getQc();
//            }
                while ((qs + qitem) <= e.getQv() && (qs + qitem) <= (qtt * qitem)) {
                    System.out.println(qs + "");
                    qs = qs + qitem;
                    rest = rest + 1;
                }
                System.out.println("soma qs: " + qs);
                if (qs >= qitem) {
                    //pro.setPrecovenda(e.getPreco());
                    //pro.setQitem(qs);
                    pro.setDataexpiracao(e.getEntradaPK().getData());
                    int qtipo = qs;
                    int x = qtipo;
                    
                    int idtipo = pro.getIdproduto();
                    
                    if (lpro.contains(pro)) {
                        int i = lpro.indexOf(pro);
                        x = lpro.get(i).getQtdvenda() + qtipo;
                        lpro.get(i).setQtdvenda(x);
                        System.out.println(qtipo);
                        System.out.println("111");
                    } else {
//                pro.setQtStock(qtipo);
//                lpro.add(pro);
                        lpro.add(new Produto(pro.getIdproduto(), pro.getNome(), qtipo, preco, pro.getMargem(), pro.getCusto(), pro.getIdimposto(), pro.getCodigo(), qs, pro.getDataexpiracao(), pro.getQtstock()));
                        System.out.println("222");
                    }
                    // pro.setQtStock(qtipo);
                    qtdvenda = pro.getQtdvenda();
                    lproe.add(new Produto(pro.getIdproduto(), pro.getNome(), rest, preco, new Integer(qtdvenda - x).floatValue(), pro.getCusto(), en.getIdimposto(), pro.getCodigo(), qs, pro.getDataexpiracao(), pro.getQtstock()));
                    mostrarResultdo(pro.getNome(), preco, rest, ++valor, lproe.indexOf(pro) + 1);
                    pro.setQtdvenda(qtdvenda - x);
                    if (lprov.contains(pro)) {
                        lprov.remove(pro);
                    }
                    lprov.add(pro);
                    //  lprov.add(new Produto(pro.getIdproduto(), pro.getNome(), qtipo, pro.getPrecovenda(), pro.getMargem(), pro.getCusto(), pro.getIdimposto()));
                    System.out.println("qv: " + e.getQv());
                    System.out.println(qs);
                    lsp.get(j).setQv(e.getQv() - qs);
                    Iterator<Entrada> items2 = new ArrayList(ls).listIterator();
                    Entrada ee;
                    while (items2.hasNext()) {
                        ee = items2.next();
                        if (ee.getEntradaPK().equals(en.getEntradaPK())) {
                            ee.setQv(ee.getQv() + qs);
                        }
                    }
//                    ls.get(j).setQv(ls.get(j).getQv() + qs);
                }
                qtt = qtt - (qs / qitem);
                qs = 0;
                rest = 0;
            }

            // le.addAll(lsp);
//            for (Entrada e : lsp) {
//                System.out.println("lsp:" + e.getQv());
//            }
//            for (Entrada e : le) {
//                System.out.println("le:" + e.getQv());
//            }
//            if (qtt > 0) {
//                JOptionPane.showMessageDialog(this, qtt + " " + produto + "\n estao foram do prazo", "Atencao", JOptionPane.WARNING_MESSAGE);
//            }
        } catch (NumberFormatException | HeadlessException e) {
            changeText();
        }
        return icb;//(new Consultas().getlastIndex(7) - valor);

    }
    
    public void changeText() {
        //ControlaVenda.setForeground(red);
        //ControlaVenda.setBackground(new Color(240, 200, 220));
        //ControlaVenda.setFont(new Font("arial", 12, 18));
    }

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus

    }//GEN-LAST:event_formWindowLostFocus

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost

    }//GEN-LAST:event_formFocusLost

    private void tabelaFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaFacturaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaFacturaMouseClicked

    private void jdInicio1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdInicio1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jdInicio1KeyReleased

    private void jdFim1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdFim1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jdFim1KeyReleased

    private void jBpesquisarProdEmprestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBpesquisarProdEmprestActionPerformed
        try {
            listafatura.clear();
            listafatura = new EmprestimoJpaController(emf).getEmprestPeriodo(jdInicio1.getDate(), jdFim1.getDate(), cbemp.getSelectedItem());
            mostraEmprest();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jBpesquisarProdEmprestActionPerformed

    private void labelSucessoEmpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelSucessoEmpMouseEntered
        labelSucessoEmp.setText("");
    }//GEN-LAST:event_labelSucessoEmpMouseEntered

    private void cbempItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbempItemStateChanged
        try {
            listafatura.clear();
            listafatura = new EmprestimoJpaController(emf).getEmprestPeriodo(jdInicio1.getDate(), jdFim1.getDate(), cbemp.getSelectedItem());
            mostraEmprest();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbempItemStateChanged

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        try {
            int ip = showListaCompra(0);
            txtQtVendida.setText("1");//
            Produto p = (Produto) Comboproduto.getSelectedItem();
            List<Entrada> le = new EntradaJpaController(emf).getEntrada(p);//problema nao tras entradas expiradas
            comboboxe(cbtquant, le);
            cbtquant.setSelectedIndex(ip);
        } catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
        }
        txtcbarra.setText("");
        cbarra = "";
    }//GEN-LAST:event_btAddActionPerformed

    private void cbtprecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtprecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbtprecoActionPerformed

    private void cbtquantItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbtquantItemStateChanged
        if (cbtquant.getSelectedItem() == null) {
            return;
        }
        Entrada e = (Entrada) cbtquant.getSelectedItem();
        comboboxpreco(e);
    }//GEN-LAST:event_cbtquantItemStateChanged

    private void cbtquantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtquantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbtquantActionPerformed

    private void ComboprodutoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboprodutoItemStateChanged
        if (Comboproduto.getSelectedItem() == null) {
            return;
        }
        Produto p = (Produto) Comboproduto.getSelectedItem();
        tfcp.setText(p.getCodigo());
        txtQtVendida.setText("1");
        List<Entrada> le = new EntradaJpaController(emf).getEntrada(p);//problema nao tras entradas expiradas
        comboboxe(cbtquant, le);
    }//GEN-LAST:event_ComboprodutoItemStateChanged

    private void tfcpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcpActionPerformed

    private void tfcpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfcpKeyReleased
        Produto p = new ProdutoJpaController(emf).getProdCod(tfcp.getText());
        if (p != null) {
            Comboproduto.setSelectedItem(p);
            txtQtVendida.requestFocusInWindow();
            txtQtVendida.setSelectionStart(0);
            txtQtVendida.setSelectionStart(txtQtVendida.getColumns());
        }
    }//GEN-LAST:event_tfcpKeyReleased

    private void tfcpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfcpKeyTyped

    }//GEN-LAST:event_tfcpKeyTyped

    private void txtQtVendidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtVendidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtVendidaActionPerformed

    private void txt_valorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_valorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_valorActionPerformed

    private void tfcp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcp1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcp1ActionPerformed

    private void tfcp1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfcp1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcp1KeyReleased

    private void tfcp1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfcp1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcp1KeyTyped
    private static int act = 0;
    private static int ind = 0;    
    public void removeL(int i) {
        vendaLista.remove(i);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox Comboproduto;
    private javax.swing.JTabbedPane Emprestimo;
    private keeptoo.KButton btAdd;
    private keeptoo.KButton btCancel3;
    private keeptoo.KButton btCloseVenda3;
    private javax.swing.JLabel caxa;
    private javax.swing.JComboBox cbemp;
    private javax.swing.JComboBox cbtpreco;
    private javax.swing.JComboBox cbtquant;
    private javax.swing.JButton jBpesquisarProdEmprest;
    private keeptoo.KButton jButton7;
    private keeptoo.KButton jButton8;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable1;
    private com.toedter.calendar.JDateChooser jdFim1;
    private com.toedter.calendar.JDateChooser jdInicio1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel labelHistory;
    private javax.swing.JLabel labelSucessoEmp;
    private javax.swing.JLabel label_troco;
    private javax.swing.JLabel lbsaldo;
    private javax.swing.JPanel painelVenda1;
    private javax.swing.JTable tabelaFactura;
    private javax.swing.JTextField tfcp;
    private javax.swing.JTextField tfcp1;
    private javax.swing.JTextField txtQtVendida;
    private javax.swing.JTextField txt_valor;
    private javax.swing.JTextField txtcbarra;
    private javax.swing.JPanel vendaLista;
    // End of variables declaration//GEN-END:variables

//    public void preenceCliente(JComboBox jComboNomeCliet) {
//        List<Cliente> clietes = new ArrayList<>();
//        clietes = new ClienteJpaController(emf).findClienteEntities();
//        jComboNomeCliet.removeAllItems();
//        for (Cliente cliete : clietes) {
//            jComboNomeCliet.addItem(cliete);
//        }
//    }
    public void comboboxe(JComboBox jComboNomeCliet) {
        List<Empresa> e = new ArrayList<>();
        e = new EmpresaJpaController(emf).findEmpresaEntities();
        jComboNomeCliet.removeAllItems();
        jComboNomeCliet.addItem("Todas Empresas");
        for (Empresa emp : e) {
            jComboNomeCliet.addItem(emp);
        }
    }
    
    public void comboboxe(JComboBox jComboNomeCliet, List<Entrada> le) {
        jComboNomeCliet.removeAllItems();
        cbtpreco.removeAllItems();
        for (Entrada e : le) {
            e.setQitem(e.getQv());
            jComboNomeCliet.addItem(e);
        }
    }
    
    public void comboboxpreco(Entrada e) {
        cbtpreco.removeAllItems();
        String prec = "";
        if (e.getNa() != null) {
            prec = e.getNa() + " - ";
        }
        prec = prec + e.getQa() + "x" + e.getPreco();
        cbtpreco.addItem(prec);
        if (e.getPb() != null) {
            prec = "";
            if (e.getNb() != null) {
                prec = e.getNb() + " - ";
            }
            prec = prec + e.getPb() + "x" + e.getPb();
            cbtpreco.addItem(prec);
        }
        if (e.getPc() != null) {
            prec = "";
            if (e.getNc() != null) {
                prec = e.getNc() + " - ";
            }
            prec = prec + e.getPc() + "x" + e.getPc();
            cbtpreco.addItem(e);
        }
        
    }
    
}
