import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class ListagemVIEW extends JFrame {
    private JTable tabelaItens;
    private JButton btnVender;
    private JButton btnConsultarVendas;

    public ListagemVIEW() {
        setTitle("Listagem de Itens");
        setSize(500,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        tabelaItens = new JTable(new DefaultTableModel(
            new Object[]{"ID","Nome","Descrição","Status"},0));
        JScrollPane scroll = new JScrollPane(tabelaItens);
        scroll.setBounds(20,20,450,250);
        add(scroll);

        // Botão Vender
        btnVender = new JButton("Vender");
        btnVender.setBounds(20,300,100,25);
        add(btnVender);

        // Botão Consultar Vendas
        btnConsultarVendas = new JButton("Consultar Vendas");
        btnConsultarVendas.setBounds(150,300,150,25);
        add(btnConsultarVendas);

        listarItens();

        // Ação do botão vender
        btnVender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                venderItemSelecionado();
            }
        });

        // Ação do botão consultar vendas
        btnConsultarVendas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VendasVIEW().setVisible(true);
            }
        });
    }

    // ✅ Lista todos os itens
    public void listarItens() {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/leiloes","root","");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM itens");

            DefaultTableModel model = (DefaultTableModel) tabelaItens.getModel();
            model.setRowCount(0);

            while(rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("status")
                });
            }
            con.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar itens: " + e.getMessage());
        }
    }

    // ✅ Vender item selecionado
    private void venderItemSelecionado() {
        int linha = tabelaItens.getSelectedRow();
        if(linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para vender.");
            return;
        }

        int id = (int) tabelaItens.getValueAt(linha, 0);
        ProdutosDAO dao = new ProdutosDAO();
        dao.venderProduto(id);
        JOptionPane.showMessageDialog(this, "Produto vendido com sucesso!");
        listarItens(); // atualiza a lista
    }

    public static void main(String[] args) {
        new ListagemVIEW().setVisible(true);
    }
}