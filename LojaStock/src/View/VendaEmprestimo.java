package View;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import controller.ClienteJpaController;
import controller.DetalorgJpaController;
import controller.EmpresaJpaController;
import controller.EmprestimoJpaController;
import controller.EntradaJpaController;
import controller.ProdutoJpaController;
import controller.SeriefacturaJpaController;
import controller.VendaJpaController;
import controller.VendedorJpaController;
import controller.exceptions.NonexistentEntityException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
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
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 *
 * @author rjose
 */
public final class VendaEmprestimo extends javax.swing.JDialog {

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
    private final DefaultTableModel tbl_carinho = new DefaultTableModel(null, new String[]{"Codigo", "Nome do Produto", "Qtd", "Preço Unitario", "SubTotal", "Excluir"});

    private int valor = 0;
    private float soma = 0;
    private int last = 0;
    private int ctr = 0;
    private int client = 0;
    private double desconto = 0;

    public static final int RET_OK = 1;
    private static final Boolean bool = true;
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

    public VendaEmprestimo(int c) throws Exception {
        initComponents();
        this.setTitle("Nova Venda");
        this.setLocation(50, 0);
        
        setResizable(true);
        //setSize(, 300);  
        setLocationRelativeTo(null);
        this.client = c;
        this.getRootPane().setDefaultButton(btAdd);
        tfemp.setVisible(true);
        jcClient.setVisible(true);
        tfcli.setVisible(false);
        tfcp.setVisible(false);
        tfemp.setVisible(false);
        //jButton1.setVisible(false);
        combobox(Comboproduto);
        comboboxe(cbemp);
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

            // catch the enter key in the JTextField and perform an OK click on the JButton
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
            jcClient.setSelectedIndex(0);
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
        ButtonColumn bt = new ButtonColumn(this.jtable_carrinha, Anular, 5, ir);
        this.jtable_carrinha.setRowHeight(30);
//        btConfirmar.setVisible(false);

        AbstractAction desconts = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //  System.out.println("Here");
                txt_valor.requestFocusInWindow();//request that the button has focus

                try {

                    double valor_cliente = Double.parseDouble(txt_valor.getText());
                    if (valor_cliente >= desconto) {

                        double troco = Double.parseDouble(txt_valor.getText()) - desconto;
                        label_troco.setText("" + troco);

                    } else {
                        JOptionPane.showMessageDialog(txtDesc, "Erro no valor digitado \n"
                                + " Certifique se o valor do cliente é suficiente as compras", "Atencao", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception e) {

                    System.out.println("erro: " + e.getMessage());
                }
            }
        ;
        }; //so button can be pressed using F1 and ENTER
                
        txt_valor.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        txt_valor.getActionMap().put("Enter", desconts);
        //btAdd.addActionListener(aa);

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
                ve.setCaixa(Integer.parseInt(caxa.getText().substring(1)));
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
                Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
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

        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        labelHistory = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        caxa = new javax.swing.JLabel();
        jcClient = new javax.swing.JComboBox();
        Emprestimo = new javax.swing.JTabbedPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Comboproduto = new javax.swing.JComboBox();
        txtQtVendida = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        btCloseVenda = new keeptoo.KButton();
        label_troco = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_valor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btCancel = new keeptoo.KButton();
        jSeparator2 = new javax.swing.JSeparator();
        txtDesc = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jButton1 = new keeptoo.KButton();
        cbtquant = new javax.swing.JComboBox();
        cbtpreco = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btAdd = new com.alee.laf.button.WebButton();
        jButton2 = new com.alee.laf.button.WebButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtable_carrinha = new javax.swing.JTable();
        labelTotal1 = new javax.swing.JLabel();
        vendaLista = new java.awt.List();
        tfemp = new javax.swing.JTextField();
        tfcli = new javax.swing.JTextField();
        tfcp = new javax.swing.JTextField();
        labelDesconto = new javax.swing.JLabel();
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
        labelHistory.setText("AREA DE VENDA");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Caixa: ");

        caxa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        caxa.setText(" ");

        jcClient.setMaximumRowCount(3);
        jcClient.setToolTipText("Seleccione ou pesquise cliente");
        jcClient.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcClientItemStateChanged(evt);
            }
        });
        jcClient.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jcClientInputMethodTextChanged(evt);
            }
        });
        jcClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jcClientKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(451, 451, 451)
                .addComponent(labelHistory)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(caxa, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jcClient, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(labelHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(caxa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jcClient, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        Emprestimo.setMinimumSize(new java.awt.Dimension(100, 544));
        Emprestimo.setPreferredSize(new java.awt.Dimension(1281, 758));
        Emprestimo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EmprestimoMouseClicked(evt);
            }
        });

        jDesktopPane1.setBackground(new java.awt.Color(240, 240, 240));

        jPanel2.setBackground(new java.awt.Color(251, 250, 250));
        jPanel2.setMinimumSize(new java.awt.Dimension(1280, 600));
        jPanel2.setPreferredSize(new java.awt.Dimension(1280, 600));

        jPanel6.setBackground(new java.awt.Color(242, 238, 238));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setPreferredSize(new java.awt.Dimension(631, 450));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setText("PRODUTO:");

        Comboproduto.setToolTipText("Seleccione ou pesquise produto");
        Comboproduto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Comboproduto.setOpaque(false);
        Comboproduto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboprodutoItemStateChanged(evt);
            }
        });
        Comboproduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboprodutoActionPerformed(evt);
            }
        });

        txtQtVendida.setToolTipText("");
        txtQtVendida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtVendidaActionPerformed(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(225, 225, 225));

        btCloseVenda.setText("VENDA CASH");
        btCloseVenda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btCloseVenda.setkBackGroundColor(new java.awt.Color(51, 102, 255));
        btCloseVenda.setkEndColor(new java.awt.Color(0, 102, 102));
        btCloseVenda.setkHoverEndColor(new java.awt.Color(0, 102, 102));
        btCloseVenda.setkHoverForeGround(new java.awt.Color(0, 51, 51));
        btCloseVenda.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        btCloseVenda.setkSelectedColor(new java.awt.Color(153, 218, 218));
        btCloseVenda.setkStartColor(new java.awt.Color(204, 204, 255));
        btCloseVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseVendaActionPerformed(evt);
            }
        });

        label_troco.setBackground(new java.awt.Color(255, 255, 255));
        label_troco.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label_troco.setForeground(new java.awt.Color(204, 102, 0));
        label_troco.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label_troco.setText("0,00");
        label_troco.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("TROCO:");

        txt_valor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_valorMouseClicked(evt);
            }
        });
        txt_valor.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                txt_valorCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txt_valorInputMethodTextChanged(evt);
            }
        });
        txt_valor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_valorActionPerformed(evt);
            }
        });
        txt_valor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txt_valorPropertyChange(evt);
            }
        });
        txt_valor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_valorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_valorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_valorKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("PAGAMENTO:");

        btCancel.setText("CANCELAR");
        btCancel.setkEndColor(new java.awt.Color(0, 102, 102));
        btCancel.setkHoverEndColor(new java.awt.Color(0, 102, 102));
        btCancel.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btCancel.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        btCancel.setkSelectedColor(new java.awt.Color(0, 102, 102));
        btCancel.setkStartColor(new java.awt.Color(204, 204, 204));
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        txtDesc.setToolTipText("");
        txtDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescActionPerformed(evt);
            }
        });
        txtDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("DESCONTO:");

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jButton1.setText("VENDA A CRÉDITO");
        jButton1.setkBackGroundColor(new java.awt.Color(51, 51, 51));
        jButton1.setkEndColor(new java.awt.Color(0, 102, 102));
        jButton1.setkHoverEndColor(new java.awt.Color(204, 204, 204));
        jButton1.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        jButton1.setkHoverStartColor(new java.awt.Color(153, 153, 153));
        jButton1.setkPressedColor(new java.awt.Color(153, 153, 153));
        jButton1.setkSelectedColor(new java.awt.Color(204, 204, 204));
        jButton1.setkStartColor(new java.awt.Color(204, 204, 204));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_troco, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator3)
                        .addComponent(txtDesc)
                        .addComponent(txt_valor, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btCloseVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(145, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(txtDesc)
                        .addGap(2, 2, 2))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_troco)))
                .addGap(5, 5, 5)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCloseVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        cbtpreco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbtpreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtprecoActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 153));
        jLabel10.setText("LOTE/SERIE:");

        jLabel14.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 153));
        jLabel14.setText("QUANTIDADE:");

        btAdd.setText("ADD +");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        jButton2.setText("FACTURA PROFORMA");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Comboproduto, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(17, 17, 17)
                            .addComponent(cbtquant, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(17, 17, 17)
                            .addComponent(txtQtVendida, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cbtpreco, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Comboproduto, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(cbtquant, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbtpreco, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQtVendida, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(13, 13, 13))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 0, 153));
        jLabel9.setText("PRODUTOS ADICIONADOS #");

        jtable_carrinha.setModel(tbl_carinho);
        jtable_carrinha.setColumnSelectionAllowed(true);
        jScrollPane7.setViewportView(jtable_carrinha);
        jtable_carrinha.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        labelTotal1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelTotal1.setForeground(new java.awt.Color(255, 51, 0));

        vendaLista.setBackground(new java.awt.Color(240, 238, 238));
        vendaLista.setVisible(false);
        vendaLista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vendaListaMouseClicked(evt);
            }
        });
        vendaLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                none(evt);
            }
        });

        tfemp.setText("1");
        tfemp.setToolTipText("codigo empresa");
        tfemp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfempKeyReleased(evt);
            }
        });

        tfcli.setToolTipText("codigo cliente");
        tfcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfcliActionPerformed(evt);
            }
        });
        tfcli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfcliKeyReleased(evt);
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

        labelDesconto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelDesconto.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(1193, 1270, Short.MAX_VALUE)
                .addComponent(vendaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(tfcli, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tfcp, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tfemp, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1238, Short.MAX_VALUE)
                            .addComponent(jScrollPane7))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(vendaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfcli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfcp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jDesktopPane1.add(jPanel2);
        jPanel2.setBounds(0, 0, 1280, 580);
        jPanel2.getAccessibleContext().setAccessibleName("");

        Emprestimo.addTab("Registo de Venda", jDesktopPane1);

        labelSucessoEmp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelSucessoEmp.setForeground(new java.awt.Color(102, 0, 0));
        labelSucessoEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelSucessoEmpMouseEntered(evt);
            }
        });

        painelVenda1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
                .addContainerGap(323, Short.MAX_VALUE))
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

        lbsaldo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbsaldo.setForeground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout painelVenda1Layout = new javax.swing.GroupLayout(painelVenda1);
        painelVenda1.setLayout(painelVenda1Layout);
        painelVenda1Layout.setHorizontalGroup(
            painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVenda1Layout.createSequentialGroup()
                .addGap(464, 464, 464)
                .addComponent(lbsaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(painelVenda1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8))
                .addContainerGap())
        );
        painelVenda1Layout.setVerticalGroup(
            painelVenda1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVenda1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbsaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(labelSucessoEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(802, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                    .addContainerGap(22, Short.MAX_VALUE)
                    .addComponent(painelVenda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(21, Short.MAX_VALUE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addComponent(labelSucessoEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelVenda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(71, 71, 71)))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Emprestimo.addTab("Pendentes", jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Emprestimo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Emprestimo, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                .addGap(0, 0, 0))
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

    public void limparVenda() {
        vendaLista.removeAll();
        vendaLista.add(" Produto  " + "                                                                                                                 Preco   " + "     Qtd  " + "      Total  ");
        lpro.clear();
        lproe.clear();
        lprov.clear();
        txtQtVendida.setText("1");
        labelTotal1.setText("");
        //ControlaVenda.setText("");
        valor = 0;
        soma = 0;
        le.clear();
        ls.clear();
        if (support2 != null) {
            support2.uninstall();
            combobox(Comboproduto);
        }
        System.out.println("" + tbl_carinho.getRowCount());
        while (tbl_carinho.getRowCount() > 0) {
            tbl_carinho.removeRow(0);
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

    public void comboboxClient(JComboBox c) {
        listac = new ClienteJpaController(emf).getClientAsc();
        if (listac.isEmpty()) {
            return;
        }
        Cliente[] elements = listac.toArray(new Cliente[]{});
        support = AutoCompleteSupport.install(
                c, GlazedLists.eventListOf(elements));
    }

    public int getLimpaVenda() {
        limparVenda();
        return 1;
    }

    public void mostrarResultdo(String prod, float p, int qt, int v, int j, int t) {

        // desconto = Float.parseFloat(txt_desconto.getText());
//        if (desconto > 0 && desconto < p){
//            
//            p = p - desconto;
//        }
        float total = p * qt;
        String pr;

        if (prod.length() > 70) {
            pr = prod.substring(0, 68);
        } else {

            pr = prod;
            for (int i = 0; i < 100 - prod.length(); i++) {
                pr = pr + " ";

                pr = pr + p;
                pr = pr + "                             ";
                pr = pr + qt;
                pr = pr + "                             ";
                pr = pr + total;
                pr = pr + "                   ";

            }

            vendaLista.addItem(pr);
            vendaLista.setForeground(Color.BLUE);
            soma = soma + (p * qt);
            labelTotal1.setText("Total:  " + soma + ",00");

            String[] linha = new String[]{null, null, null, null, null, null, null};
            t = vendaLista.getVisibleIndex();
            tbl_carinho.addRow(linha);
            tbl_carinho.setValueAt("001662", inc_venda, 0);
            tbl_carinho.setValueAt(prod, inc_venda, 1);
            tbl_carinho.setValueAt(qt, inc_venda, 2);
            tbl_carinho.setValueAt(p, inc_venda, 3);
            tbl_carinho.setValueAt(total, inc_venda, 4);

            inc_venda++;
        }
    }

    static List<Produto> lpro = new ArrayList<>();
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
            int qs = 0;
            int rest = 0;

            for (int j = 0; j < lsp.size(); j++) {

                Entrada e = lsp.get(j);
                while ((qs + qitem) <= e.getQv() && (qs + qitem) <= (qtt * qitem)) {
                    System.out.println(qs + "");
                    qs = qs + qitem;
                    rest = rest + 1;
                }

                if (qs >= qitem) {

                    pro.setDataexpiracao(e.getEntradaPK().getData());
                    int qtipo = qs;
                    int x = qtipo;

                    int idtipo = pro.getIdproduto();

                    if (lpro.contains(pro)) {
                        int i = lpro.indexOf(pro);
                        x = lpro.get(i).getQtdvenda() + qtipo;
                        lpro.get(i).setQtdvenda(x);

                    } else {
                        lpro.add(new Produto(pro.getIdproduto(), pro.getNome(), qtipo, preco, pro.getMargem(), pro.getCusto(), pro.getIdimposto(), pro.getCodigo(), qs, pro.getDataexpiracao(), pro.getQtstock()));
                        System.out.println("222");
                    }

                    qtdvenda = pro.getQtdvenda();
                    lproe.add(new Produto(pro.getIdproduto(), pro.getNome(), rest, preco, new Integer(qtdvenda - x).floatValue(), pro.getCusto(), en.getIdimposto(), pro.getCodigo(), qs, pro.getDataexpiracao(), pro.getQtstock()));
                    mostrarResultdo(pro.getNome(), preco, rest, ++valor, lproe.indexOf(pro) + 1, j);
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

    private void tfcpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfcpKeyTyped

    }//GEN-LAST:event_tfcpKeyTyped

    private void tfcpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfcpKeyReleased
        Produto p = new ProdutoJpaController(emf).getProdCod(tfcp.getText());
        if (p != null) {
            Comboproduto.setSelectedItem(p);
            txtQtVendida.requestFocusInWindow();
            txtQtVendida.setSelectionStart(0);
            txtQtVendida.setSelectionStart(txtQtVendida.getColumns());
        }
    }//GEN-LAST:event_tfcpKeyReleased

    private void tfcpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcpActionPerformed

    private void jcClientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcClientKeyReleased

    }//GEN-LAST:event_jcClientKeyReleased

    private void jcClientInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jcClientInputMethodTextChanged

    }//GEN-LAST:event_jcClientInputMethodTextChanged

    private void jcClientItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcClientItemStateChanged
        if (jcClient.getSelectedItem() == null) {
            return;
        }
        Cliente c = (Cliente) jcClient.getSelectedItem();
        tfcli.setText(c.getCodigo());
    }//GEN-LAST:event_jcClientItemStateChanged

    private void tfcliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfcliKeyReleased
        Cliente c;
        if (tfemp.getText().trim().equals("")) {
            c = new ClienteJpaController(emf).getCliCod(tfcli.getText());
        } else {
            c = new ClienteJpaController(emf).getClienteCod(tfemp.getText(), tfcli.getText());
        }
        if (c != null) {
            jcClient.setSelectedItem(c);
        }
    }//GEN-LAST:event_tfcliKeyReleased

    private void tfcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcliActionPerformed

    private void tfempKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfempKeyReleased
        if (tfemp.getText().trim().equals("")) {
            listac = new ClienteJpaController(emf).getClientAsc();
        } else {
            listac = new ClienteJpaController(emf).getClienteEmp(tfemp.getText());
        }
        if (listac.isEmpty()) {
            return;
        }
        support.uninstall();
        Cliente[] elements = listac.toArray(new Cliente[]{});
        support = AutoCompleteSupport.install(
                jcClient, GlazedLists.eventListOf(elements));
        tfcli.setText("");
    }//GEN-LAST:event_tfempKeyReleased

    private void none(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_none
        // TODO add your handling code here:
    }//GEN-LAST:event_none

    private void vendaListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_vendaListaMouseClicked
        int ind = vendaLista.getSelectedIndex();
        boolean bool = false;
        if (ind == 0) {
            return;
        }
        int respo = JOptionPane.showOptionDialog(null, "Alterar ?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"SIM", "NÃO"}, null);
        if (respo == 1 || respo == -1) {
            return;
        } else {
            Produto p = lproe.get(ind - 1);
            System.out.println(p.getNome() + " " + p.getPrecovenda());
            Produto po = new ProdutoJpaController(emf).findProduto(p.getIdproduto());
            if (po != null) {
                Comboproduto.setSelectedItem(po);
            }

            int ilpro = lpro.indexOf(p);
            int qt = lpro.get(ilpro).getQtdvenda();
            qt = qt - p.getQitem();
            if (qt == 0) {
                lpro.remove(ilpro);
            } else {
                lpro.get(ilpro).setQtdvenda(qt);
            }

            Iterator<Produto> items = new ArrayList(lpro).listIterator();
            Produto pp;
            items = new ArrayList(lprov).listIterator();
            while (items.hasNext()) {
                pp = items.next();
                if (pp.getNome().equals(p.getNome())) {
                    if (pp.getQtdvenda() + p.getQitem() == po.getQtdvenda()) {
                        lprov.remove(pp);
                        bool = true;
                    } else {
                        int ii = lprov.indexOf(pp);
                        lprov.get(ii).setQtdvenda(pp.getQtdvenda() + p.getQitem());
                    }
                }
            }
            Iterator<Entrada> items2 = new ArrayList(le).listIterator();
            Entrada ee;
            Date de = p.getDataexpiracao();
            while (items2.hasNext()) {
                ee = items2.next();
                if (ee.getEntradaPK().getData().equals(de)) {
                    if (bool) {
                        int ii = le.indexOf(ee);
                        le.remove(ee);
                        ls.remove(ii);
                    } else {
                        int ii = le.indexOf(ee);
                        le.get(ii).setQv(ee.getQv() + p.getQitem());
                        ls.get(ii).setQv(ls.get(ii).getQv() - p.getQitem());
                    }
                }
            }
            removeL(ind);
            soma = soma - (p.getPrecovenda() * p.getQtdvenda());
            labelTotal1.setText("Total:   " + soma);
            lproe.remove(ind - 1);
            txtQtVendida.requestFocusInWindow();
            txtQtVendida.setSelectionStart(0);
            txtQtVendida.setSelectionStart(txtQtVendida.getColumns());
            //            for (Produto e : lproe) {
            //                System.out.println("lproe:" + e.getNome() + " " + e.getQtdvenda() + " " + e.getQitem());
            //            }
            //            for (Produto e : lprov) {
            //                System.out.println("lprov:" + e.getNome() + " " + e.getQtdvenda() + " " + e.getQitem());
            //            }
            //            for (Produto e : lpro) {
            //                System.out.println("lpro:" + e.getNome() + " " + e.getQtdvenda() + " " + e.getQitem());
            //            }
            //            for (Entrada e : le) {
            //                System.out.println("le:" + e.getQv());
            //            }//
            p = (Produto) Comboproduto.getSelectedItem();
            List<Entrada> le = new EntradaJpaController(emf).getEntrada(p);//problema nao tras entradas expiradas
            comboboxe(cbtquant, le);
        }
    }//GEN-LAST:event_vendaListaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        if (jtable_carrinha.getRowCount() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione os produtos para venda", "Atencao", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int tve = ((Cliente) jcClient.getSelectedItem()).getIdempresa().getTipovend();
        if (jcClient.getSelectedItem().equals("") || (tve != 1 && tve != 2)) {
            JOptionPane.showMessageDialog(this, "Selecione um Cliente de Venda a Credito", "Atencao", JOptionPane.WARNING_MESSAGE);
            jcClient.grabFocus();
            return;
        }

        new SwingWorker() {
            
            
            @Override
            protected Object doInBackground() throws Exception {
                //JOptionPane.showMessageDialog(null, "Seleccione os produtos para venda", "Atencao", JOptionPane.WARNING_MESSAGE);
            
                Date d = new Date();
                //                String s = new SeriefacturaJpaController(emf).findSeriefacturaEntities().get(0).getSeriefactura();
                //                String ss = (Integer.parseInt(s) + 1) + "";
                String n;
                Cliente c = (Cliente) jcClient.getSelectedItem();
                int desc = c.getIdempresa().getDesconto();
                //                if (soma < 50) {
                //                    desc = 0;
                //                }
                EmpData f = new EmpData(new javax.swing.JFrame(), lpro, ls, lproe, d, c, client, desc, c.getNome(), impvenda, true);
                f.setVisible(true);
                limparVenda();
                return null;
            }

            @Override
            protected void done() {
            }
        }.execute();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbtprecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtprecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbtprecoActionPerformed

    private void cbtquantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtquantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbtquantActionPerformed

    private void cbtquantItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbtquantItemStateChanged
        if (cbtquant.getSelectedItem() == null) {
            return;
        }
        Entrada e = (Entrada) cbtquant.getSelectedItem();
        comboboxpreco(e);
    }//GEN-LAST:event_cbtquantItemStateChanged

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        // TODO add your handling code here:

        limparVenda();
    }//GEN-LAST:event_btCancelActionPerformed

    private void txt_valorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_valorKeyTyped
        // TODO add your handling code here:


    }//GEN-LAST:event_txt_valorKeyTyped

    private void txt_valorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_valorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_valorActionPerformed

    private void btCloseVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseVendaActionPerformed
        {
            if (vendaLista.getItemCount() == 1) {
                JOptionPane.showMessageDialog(this, "Seleccione os produtos para venda", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int tve = ((Cliente) jcClient.getSelectedItem()).getIdempresa().getTipovend();
            if (jcClient.getSelectedIndex() == -1 || (tve != 0 && tve != 2)) {
                JOptionPane.showMessageDialog(this, "Selecione um Cliente de Venda a Dinheiro", "Atencao", JOptionPane.WARNING_MESSAGE);
                jcClient.grabFocus();
                return;
            }
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService printService = null;
            //System.out.println(printServices.length + "");
            for (PrintService ps : printServices) {
                if (ps.getName().equals(impvenda)) {
                    printService = ps;
                    break;
                }
            }
            if (printService == null) {
                JOptionPane.showMessageDialog(this, "A impressora " + impvenda + "nao foi encontrada\n"
                        + "por favor configure um nome valido", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Date d = new Date();
            //  String n;
            Cliente c = (Cliente) jcClient.getSelectedItem();
//        int desc = c.getIdempresa().getDesconto();
            int desc = 0;
            if (txtDesc.getText().trim().equals("") || Integer.parseInt(txtDesc.getText()) == 0) {
                desc = new Float(desconto).intValue();
            } else {
                desc = new Double(Math.ceil((Integer.parseInt(txtDesc.getText()) * 100) / soma)).intValue();
            }

        //        if (soma < 50) {
            //            desc = 0;
            //        }
            float tde = 0;
            float tvp = 0;
            float tiv = 0;
            EntityManager em = emf.createEntityManager();
            List<Entrada> lsord = new ArrayList<Entrada>();
            lsord.addAll(ls);
            int n = lsord.size();
            for (int p = 0; p < n - 1; p++) {
                for (int i = 0; i < n - p - 1; i++) {
                    if (lsord.get(i).getEntradaPK().getData().after(lsord.get(i + 1).getEntradaPK().getData())) {
                        Entrada aux = lsord.get(i);
                        lsord.set(i, lsord.get(i + 1));
                        lsord.set(i + 1, aux);
                    }
                }
            }
        //        for (int p = 0; p <  lsord.size(); p++){  para ver se ordena
            //			System.out.println(lsord.get(p).getEntradaPK().getData()+"  "+ls.get(p).getEntradaPK().getData());
            //		}

            String ss = null;
            try {
                EntradaJpaController ec = new EntradaJpaController(emf);
                em.getTransaction().begin();
                for (Entrada p : lsord) {
                    //                System.out.println("antes edit qv: " + p.getQv());
                    int q = ec.editVenda(p, em);
                //                System.out.println("antes edit qv: " + p.getQv());
                    //                System.out.println("q:" + q);
                    if (q > 0) {
                        String seri = "--";
                        if (p.getSerie() != null) {
                            seri = p.getSerie();
                        }
                        JOptionPane.showMessageDialog(this, "A quantidade de " + p.getProduto().getNome() + " do Lote/Serie " + seri + " ja nao esta disponivel", "Atencao", JOptionPane.WARNING_MESSAGE);
                        // em.getTransaction().rollback();
                        em.clear();
                        em.getTransaction().commit();
                        return;
                    }
                }
                for (Produto p : lpro) {
                    new ProdutoJpaController(emf).editVenda(p, em);
                }

                // ss = new SeriefacturaJpaController(emf).addSerie(em);
                ss = new SeriefacturaJpaController(emf).addSerie(em, 1);
                for (Produto p : lproe) {
                    float valo = p.getPrecovenda() * p.getQtdvenda();
                    float de = new Double(Math.ceil((new Integer(desc).floatValue() / 100) * valo)).floatValue();
                    float imp = new Double(Math.ceil((p.getIdimposto().getPerc().floatValue() / 100) * valo)).floatValue();
                    float vp = valo - de;
                    tde = tde + de;
                    tvp = tvp + vp;
                    tiv = tiv + imp;
                    //    System.out.println("vp: "+vp+"tvp: "+tvp);
                    Venda v = new Venda();
                    v.setDatavenda(d);
                    v.setDesconto(desc);
                    v.setIdvendedor(new Vendedor(client));
                    v.setSeriefactura(ss);
                    v.setIdproduto(p);
                    v.setQtd(p.getQtdvenda());
                    v.setIdcliente(c);
                    v.setValor(vp);
                    v.setIva(p.getIdimposto().getPerc());
                    v.setTdesc(de);
                    v.setTiva(imp);
                    v.setDatae(p.getDataexpiracao());
                    v.setQc(p.getQitem());
                    v.setPrec(p.getPrecovenda());
                    v.setQstock(p.getQtstock());
                    v.setQpac(p.getMargem().intValue());
                    v.setEstado(0);
                    v.setCaixa(Integer.parseInt(caxa.getText().substring(1)));
                    new VendaJpaController(emf).create(v, em);
                }

                em.getTransaction().commit();
            } catch (NonexistentEntityException ex) {
                //  em.getTransaction().rollback();
                em.clear();
                em.getTransaction().commit();
                Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
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
        //        try {
            //
            //        } catch (Exception ex) {
            //            Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
            //        }
            try {
                parametros.clear();

                parametros.put("cli", c.getNome());
                /*if (painel1.isVisible()) {
                 parametros.put("cli", txnom.getText());
                 parametros.put("nuit", txnu.getText());
                 }*/
                parametros.put("data", d);
                parametros.put("org", dog.getOrg());
                parametros.put("end", dog.getEnde());
                // parametros.put("dir", dog.getDir());
                parametros.put("nif", dog.getNif());
                parametros.put("cid", dog.getCid());
                parametros.put("tel", dog.getTel());
                parametros.put("tde", tde + "(" + desc + "%)");
                parametros.put("tiv", tiv);
                parametros.put("tvp", tvp);
                parametros.put("fatura", ss);
                parametros.put("ven", new VendedorJpaController(emf).findVendedor(client).getNomecompleto());

                String imge = new File("saslogo.png").getAbsolutePath();
                parametros.put("img", imge);
                
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(lproe);
                String path = new File("relatorios/fatura.jasper").getAbsolutePath();
            //        try {
                // jpPrint = JasperFillManager.fillReport(path, parametros, ds);//new metodos.ControllerAcess().conetion());
                JasperPrint jasperPrint = null;
                
                try {
                    jasperPrint = JasperFillManager.fillReport(path, parametros, ds);
                } catch (JRException ex) {
                    Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
                }
            // JasperPrintManager.printPage(jasperPrint, 0, false);
                //                    JasperPrintManager.printPage(jasperPrint, 0, false);
                //  PrinterJob job = PrinterJob.getPrinterJob();

                PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
                //    printRequestAttributeSet.add(new Copies(2));
                
                JRExporter jrExporter = new JRPrintServiceExporter();
                jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrExporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
                jrExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
                jrExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,
                        printService.getAttributes());
                jrExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                jrExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            //            try {
                //                jrExporter.exportReport();
                //                jrExporter.exportReport();
                //            } catch (JRException ex) {
                //                Logger.getLogger(VendaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
                //                JOptionPane.showMessageDialog(this, "Erro na Impressao \n"
                //                        + "por favor configure a Impressora", "Atencao", JOptionPane.WARNING_MESSAGE);
                //            }

                FormVenda f = new FormVenda(new javax.swing.JFrame(), jrExporter, true);
                f.setVisible(true);

            } finally {
                limparVenda();
            }
            //limparVenda();
        }
    }//GEN-LAST:event_btCloseVendaActionPerformed

    private void txtQtVendidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtVendidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtVendidaActionPerformed

    private void ComboprodutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboprodutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboprodutoActionPerformed

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


    private void txt_valorPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txt_valorPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_valorPropertyChange

    private void txt_valorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_valorKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_valorKeyReleased

    private void txt_valorInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txt_valorInputMethodTextChanged
        // TODO add your handling code here:


    }//GEN-LAST:event_txt_valorInputMethodTextChanged

    private void txt_valorCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txt_valorCaretPositionChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_valorCaretPositionChanged

    private void txt_valorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_valorKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_valorKeyPressed

    private void txt_valorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_valorMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_txt_valorMouseClicked

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        // TODO add your handling code here:

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
    }//GEN-LAST:event_btAddActionPerformed

    private void txtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescActionPerformed

    private void txtDescKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescKeyReleased

        if (!txtDesc.getText().trim().isEmpty() && Integer.parseInt(txtDesc.getText()) > soma) {
            txtDesc.setText("");
        }

        float des = 0;
        if (txtDesc.getText().trim().equals("") || Integer.parseInt(txtDesc.getText()) == 0) {
            des = new Double(Math.ceil((desconto / 100) * soma)).floatValue();

        } else {
            des = Float.parseFloat(txtDesc.getText());
        }

        labelDesconto.setText("Desc.#  " + des);
        labelTotal1.setText("Total:  " + (soma - des));
        desconto = soma - des ;
        
        //soma -= des; // desacumular o valor da soma actual.
    }//GEN-LAST:event_txtDescKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        if (vendaLista.getItemCount() == 1) {
            JOptionPane.showMessageDialog(this, "Seleccione os produtos para venda", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new SwingWorker() {
            @Override

            protected Object doInBackground() throws Exception {
                Date d = new Date();
                Proforma f = new Proforma(new javax.swing.JFrame(), lprov, lproe, dog, impprof, client, true);
                f.setVisible(true);
                limparVenda();
                return null;
            }

            @Override
            protected void done() {
            }
        }.execute();

    }//GEN-LAST:event_jButton2ActionPerformed
    private static int act = 0;
    private static int ind = 0;

    public void removeL(int i) {
        vendaLista.remove(i);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox Comboproduto;
    private javax.swing.JTabbedPane Emprestimo;
    private com.alee.laf.button.WebButton btAdd;
    private keeptoo.KButton btCancel;
    private keeptoo.KButton btCloseVenda;
    private javax.swing.JLabel caxa;
    private javax.swing.JComboBox cbemp;
    private javax.swing.JComboBox cbtpreco;
    private javax.swing.JComboBox cbtquant;
    private javax.swing.JButton jBpesquisarProdEmprest;
    private keeptoo.KButton jButton1;
    private com.alee.laf.button.WebButton jButton2;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JComboBox jcClient;
    private com.toedter.calendar.JDateChooser jdFim1;
    private com.toedter.calendar.JDateChooser jdInicio1;
    private javax.swing.JTable jtable_carrinha;
    private javax.swing.JLabel labelDesconto;
    private javax.swing.JLabel labelHistory;
    private javax.swing.JLabel labelSucessoEmp;
    private javax.swing.JLabel labelTotal1;
    private javax.swing.JLabel label_troco;
    private javax.swing.JLabel lbsaldo;
    private javax.swing.JPanel painelVenda1;
    private javax.swing.JTable tabelaFactura;
    private javax.swing.JTextField tfcli;
    private javax.swing.JTextField tfcp;
    private javax.swing.JTextField tfemp;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtQtVendida;
    private javax.swing.JTextField txt_valor;
    private java.awt.List vendaLista;
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
