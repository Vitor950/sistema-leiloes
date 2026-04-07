import javax.swing.*;
import java.util.ArrayList;

public class VendasVIEW extends JFrame {

    public VendasVIEW() {
        setTitle("Produtos Vendidos");
        setSize(300,300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ProdutosDAO dao = new ProdutosDAO();
        ArrayList<String> lista = dao.listarProdutosVendidos();

        DefaultListModel<String> model = new DefaultListModel<>();
        for (String item : lista) {
            model.addElement(item);
        }

        JList<String> jlist = new JList<>(model);
        add(new JScrollPane(jlist));
    }
}