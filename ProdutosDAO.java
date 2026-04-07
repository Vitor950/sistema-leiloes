import java.sql.*;
import java.util.ArrayList;

public class ProdutosDAO {

    // ✅ Vender produto
    public void venderProduto(int id) {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/leiloes", "root", "");
            PreparedStatement ps = con.prepareStatement(
                "UPDATE itens SET status='Vendido' WHERE id=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro ao vender produto: " + e.getMessage());
        }
    }

    // ✅ Listar produtos vendidos
    public ArrayList<String> listarProdutosVendidos() {
        ArrayList<String> lista = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/leiloes", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT nome FROM itens WHERE status='Vendido'"
            );
            while (rs.next()) {
                lista.add(rs.getString("nome"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Erro ao listar vendidos: " + e.getMessage());
        }
        return lista;
    }
}