import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

    //Actividad Unidad 3 - Incorporando UI y BD
 
public class ConexionTester extends JFrame implements ActionListener {

    
    private static final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";

    // Colores
    private static final Color VERDE = new Color(0, 128, 0);
    private static final Color ROJO = new Color(200, 0, 0);

    // Componentes de la interfaz
    private JTextField txtUrl;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnProbar;
    private JTextArea txtResultado;

   
    public ConexionTester() {
        super("Probador de conexión JDBC - MySQL");
        inicializarComponentes();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 380);
        setLocationRelativeTo(null); // centra la ventana en la pantalla
    }

    /** Crea los paneles, etiquetas, campos, botón y área de resultados. */
    private void inicializarComponentes() {
        // Panel principal con BorderLayout: formulario arriba, resultado al centro
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));

        txtUrl = new JTextField("jdbc:mysql://localhost:3306/inventario");
        txtUsuario = new JTextField("root");
        txtContrasena = new JPasswordField();

        panelFormulario.add(new JLabel("URL de conexión:"));
        panelFormulario.add(txtUrl);
        panelFormulario.add(new JLabel("Usuario:"));
        panelFormulario.add(txtUsuario);
        panelFormulario.add(new JLabel("Contraseña:"));
        panelFormulario.add(txtContrasena);

        // Botón que dispara la prueba.
        btnProbar = new JButton("Probar conexión");
        btnProbar.addActionListener(this);

        JPanel panelNorte = new JPanel(new BorderLayout(5, 5));
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(btnProbar, BorderLayout.SOUTH);

        // Área de resultados 
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);
        txtResultado.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultado de la prueba"));

        panelPrincipal.add(panelNorte, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        String url = txtUrl.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (url.isEmpty()) {
            mostrarMensaje("Debe escribir la URL de conexión.", ROJO);
            return;
        }

        probarConexion(url, usuario, contrasena);
    }

        // Lógica JDBC 
    private void probarConexion(String url, String usuario, String contrasena) {
        try {
            
            Class.forName(DRIVER_MYSQL);

            // try-with-resources: 
            try (Connection conexion = DriverManager.getConnection(url, usuario, contrasena)) {
                DatabaseMetaData info = conexion.getMetaData();
                mostrarMensaje("Conexión exitosa\n\n"
                        + "Motor:    " + info.getDatabaseProductName() + " "
                        + info.getDatabaseProductVersion() + "\n"
                        + "Base:     " + conexion.getCatalog() + "\n"
                        + "Usuario:  " + info.getUserName(), VERDE);
            }

        } catch (ClassNotFoundException ex) {
            mostrarMensaje("Fallo del driver: no se encontró " + DRIVER_MYSQL + "\n\n"
                    + "Verifique que mysql-connector-j.jar esté agregado al proyecto.\n"
                    + "Detalle técnico: " + ex.getMessage(), ROJO);

        } catch (SQLException ex) {
            mostrarMensaje("Fallo de conexión\n\n"
                    + "Detalle técnico: " + ex.getMessage() + "\n"
                    + "SQLState: " + ex.getSQLState() + " | Código: " + ex.getErrorCode(), ROJO);
        }
    }

    // Escribe un mensaje en el JTextArea con el color indicado.
    private void mostrarMensaje(String mensaje, Color color) {
        txtResultado.setForeground(color);
        txtResultado.setText(mensaje);
    }

    public static void main(String[] args) {
        // Swing debe crearse en el hilo de eventos (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> new ConexionTester().setVisible(true));
    }
}
