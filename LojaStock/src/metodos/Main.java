/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import controller.VendaJpaController;
import javax.persistence.EntityManagerFactory;
import modelo.Venda;

/**
 *
 * @author acer
 */
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.util.List;

public class Main {

    JTable table;

    public static void main(String args[]) {

        EntityManagerFactory emf = new metodos.ControllerAcess().getEntityManagerFactory();
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date(calendar.getTime().getTime());

        Date d = new Date();
        Calendar cal = new GregorianCalendar();

        cal.set(Calendar.DAY_OF_MONTH, -50);
        Date d1 = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 90);
        Date d2 = cal.getTime();
        

        List<Venda> vendas = new VendaJpaController(emf).getMaisVendidos(d1, d2);
        
        System.out.println("Dati: " + d1 + "\nDf: " + d2);
        System.out.println("Vendas"+ vendas.toString());

        for (Venda venda : vendas) {
            System.out.println("" + venda.getIdproduto().getNome()+" | "+venda.getQtd());
        }

        // vs = new VendaJpaController(emf).findVendaEntities();
//   
//        Object[] columnNames = new String[]{"CODIGO", "PRODUTO", "QTD. ARMAZEM", "QTD. LOJA", "PRECOS", "STATUS"};
//       //List<Produto> listaTipo = new ProdutoJpaController(emf).findProdutoEntities();
//        
//        Object[][] data = new Object[][]{{"Buy", "IBM", new Double(1000), new Double(80.5), Boolean.TRUE,"Fora do Prazo"},
//        {"Buy", "IBM", new Double(1000), new Double(80.5), Boolean.TRUE,"Dentro do Prazo" }
//        ,{"Buy", "IBM", new Double(1000), new Double(80.5), Boolean.TRUE, "Fora do Prazo"},
//        {"Buy", "IBM", new Double(1000), new Double(80.5), Boolean.TRUE, "Fora do Prazo"}};
////        
////        for (int i = 0; i < listaTipo.size(); i++) {
////            Produto get = listaTipo.get(i);
////            for (int j = 0; j < data.length; j++) {
////                
////               data[i][j] = get.getCusto();
////               data[i][j] = get.getCusto();
////               data[i][j] = get.getCusto();
////               data[i][j] = get.getDatacriacao();
////               data[i][j] = get.getCusto();
////               data[i][j] = get.getMargem();
////               data[i][j] = get.getCusto();
////            }
////            
////        }
//
//
//        DefaultTableModel model = new DefaultTableModel(data, columnNames);
//        table = new JTable(model) {
//            
//            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//                Component c = super.prepareRenderer(renderer, row, column);
//                JComponent jc = (JComponent) c;
//
//                //  Alternate row color
//                if (!isRowSelected(row)) {
//                    c.setBackground(row % 2 == 0 ? getBackground() : Color.PINK);
//                }
//
//                return c;
//            }
//        };
//
//        table.setPreferredScrollableViewportSize(new Dimension(700, 500));
//        table.changeSelection(0, 0, false, false);
//        //table.setSize(new Dimension(1000, 500));
//        JScrollPane scrollPane = new JScrollPane(table);
//        getContentPane().add(scrollPane);
//    }
    }
}
