import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ListagemVIEW extends JFrame {
    private JTable tabelaItens;

    public ListagemVIEW() {
        setTitle("Listagem de Itens");
        setSize(400,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabelaItens = new JTable(new DefaultTableModel(
            new Object[]{"ID","Nome","Descrição"},0));
        JScrollPane scroll = new JScrollPane(tabelaItens);
        add(scroll);

        listarItens();
    }

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
                    rs.getString("descricao")
                });
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar itens: " + e.getMessage());
        }
    }
}