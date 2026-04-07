import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CadastroVIEW extends JFrame {
    private JTextField txtNome;
    private JTextField txtDescricao;
    private JButton btnSalvar;

    public CadastroVIEW() {
        criarColunaStatus(); // 👈 adiciona automaticamente a coluna no banco

        setTitle("Cadastro de Itens");
        setSize(400,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20,20,80,25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(100,20,250,25);
        add(txtNome);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(20,60,80,25);
        add(lblDescricao);

        txtDescricao = new JTextField();
        txtDescricao.setBounds(100,60,250,25);
        add(txtDescricao);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(100,100,100,25);
        add(btnSalvar);

        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                salvarItem();
            }
        });
    }

    // ✅ CRIA A COLUNA STATUS AUTOMATICAMENTE
    private void criarColunaStatus() {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/leiloes", "root", "");
            Statement st = con.createStatement();

            st.executeUpdate(
                "ALTER TABLE itens ADD COLUMN status VARCHAR(50) DEFAULT 'Disponivel'"
            );

        } catch (Exception e) {
            // Se já existir, ignora o erro
            System.out.println("Coluna status já existe ou erro: " + e.getMessage());
        }
    }

    // ✅ AGORA SALVA COM STATUS
    private void salvarItem() {
        try {
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/leiloes", "root", "");
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO itens (nome, descricao, status) VALUES (?, ?, 'Disponivel')"
            );

            ps.setString(1, nome);
            ps.setString(2, descricao);

            int res = ps.executeUpdate();

            if(res > 0) {
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                txtNome.setText("");
                txtDescricao.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Falha no cadastro.");
            }

        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}