import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaVotacion extends JFrame {
    public SistemaVotacion() {
        setTitle("Sistema de Votación");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        // Menu Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(menuItemSalir);

        // Menu Administración
        JMenu menuAdministracion = new JMenu("Administración");
        JMenuItem menuItemMesas = new JMenuItem("Mesas");
        menuItemMesas.addActionListener(e -> mostrarDialogoMesas());
        menuAdministracion.add(menuItemMesas);
        JMenuItem menuItemCursos = new JMenuItem("Cursos");
        menuItemCursos.addActionListener(e -> mostrarVentanaCursos());
        menuAdministracion.add(menuItemCursos);
        JMenuItem menuItemEstudiantes = new JMenuItem("Estudiantes");
        menuItemEstudiantes.addActionListener(e -> mostrarDialogoAgregarEstudiante());
        menuAdministracion.add(menuItemEstudiantes);
        JMenuItem menuItemCandidatos = new JMenuItem("Candidatos");
        menuItemCandidatos.addActionListener(e -> mostrarDialogoAgregarCandidato());
        menuAdministracion.add(menuItemCandidatos);


        // Menu Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem menuItemPadronElectoral = new JMenuItem("Padrón electoral");
        menuItemPadronElectoral.addActionListener(e -> mostrarPadronElectoral());
        menuReportes.add(menuItemPadronElectoral);


        menuBar.add(menuArchivo);
        menuBar.add(menuAdministracion);
        menuBar.add(menuReportes);

        setJMenuBar(menuBar);
    }


    private void mostrarPadronElectoral() {
        JFrame ventanaPadron = new JFrame("Padrón Electoral");
        ventanaPadron.setSize(400, 600);
        ventanaPadron.setLocationRelativeTo(null);

        // Lista de candidatos para votar
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        for (Candidato candidato : candidatos) {
            modeloLista.addElement(candidato.nombre + " " + candidato.apellido + " - " + candidato.partido);
        }
        JList<String> listaCandidatos = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaCandidatos);
        ventanaPadron.add(scrollPane, BorderLayout.CENTER);

        // Botón para votar
        JButton botonVotar = new JButton("Votar");
        botonVotar.setBackground(new Color(100, 149, 237));
        botonVotar.setForeground(Color.WHITE);
        botonVotar.setFont(new Font("Arial", Font.BOLD, 16));
        botonVotar.setFocusPainted(false);
        botonVotar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        botonVotar.addActionListener(e -> {
            int indexCandidato = listaCandidatos.getSelectedIndex();
            if (indexCandidato != -1) {
                Estudiante estudianteSeleccionado = obtenerEstudianteSeleccionado();
                if (estudianteSeleccionado != null) {
                    Candidato candidatoSeleccionado = candidatos.get(indexCandidato);
                    Voto voto = new Voto(estudianteSeleccionado);
                    candidatoSeleccionado.agregarVoto(voto);
                    JOptionPane.showMessageDialog(ventanaPadron, "Voto registrado para " + candidatoSeleccionado.nombre + " " + candidatoSeleccionado.apellido);
                }
            }
        });
        ventanaPadron.add(botonVotar, BorderLayout.SOUTH);

        // Botón para mostrar resultados
        JButton botonResultados = new JButton("Mostrar Resultados");
        botonResultados.setBackground(new Color(60, 179, 113));
        botonResultados.setForeground(Color.WHITE); // Color del texto
        botonResultados.setFont(new Font("Arial", Font.BOLD, 16));
        botonResultados.setFocusPainted(false);
        botonResultados.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        botonResultados.addActionListener(e -> mostrarResultados());
        ventanaPadron.add(botonResultados, BorderLayout.NORTH);

        ventanaPadron.setVisible(true);
    }


    class Candidato {
        String nombre;
        String apellido;
        String partido;
        List<Voto> votos; // Lista para almacenar los votos que recibe cada candidato

        public Candidato(String nombre, String apellido, String partido) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.partido = partido;
            this.votos = new ArrayList<>();
        }

        // Método para agregar un voto al candidato
        public void agregarVoto(Voto voto) {
            votos.add(voto);
        }
    }


    class Voto {
        Estudiante estudiante;

        public Voto(Estudiante estudiante) {
            this.estudiante = estudiante;
        }

        public Estudiante getEstudiante() {
            return estudiante;
        }
    }


    class Estudiante {
        String nombre;
        String apellido;
        String curso;

        public Estudiante(String nombre, String apellido, String curso) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.curso = curso;
        }

        @Override
        public String toString() {
            return nombre + " " + apellido + " - " + curso;
        }
    }

    List<Estudiante> estudiantes = new ArrayList<>();


    List<Candidato> candidatos = new ArrayList<>();


    private void mostrarVentanaCursos() {
        JFrame ventanaCursos = new JFrame("Lista de Cursos");
        ventanaCursos.setSize(300, 300);
        ventanaCursos.setLocationRelativeTo(null);

        // Lista de cursos
        String[] cursos = {"- Primer Grado de EGB", "- Segundo Grado de EGB", "- Tercer Grado de EGB",
                "- Cuarto Grado de EGB", "- Quinto Grado de EGB", "- Sexto Grado de EGB",
                "- Séptimo Grado de EGB", "- Octavo Grado de EGB", "- Noveno Grado de EGB",
                "- Décimo Grado de EGB", "- Primero de Bachillerato", "- Segundo de Bachillerato", "- Tercero de Bachillerato "};
        JList<String> listaCursos = new JList<>(cursos);

        listaCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaCursos.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(listaCursos);
        ventanaCursos.add(scrollPane);

        ventanaCursos.setVisible(true);
    }


    private void mostrarDialogoAgregarCandidato() {
        JTextField campoNombre = new JTextField();
        JTextField campoApellido = new JTextField();
        JTextField campoPartido = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(campoApellido);
        panel.add(new JLabel("Partido:"));
        panel.add(campoPartido);
        int resultado = JOptionPane.showConfirmDialog(this, panel, "Agregar Candidato", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String nombre = campoNombre.getText();
            String apellido = campoApellido.getText();
            String partido = campoPartido.getText();
            candidatos.add(new Candidato(nombre, apellido, partido));
            JOptionPane.showMessageDialog(this, "Candidato agregado: " + nombre + " " + apellido + ", Partido: " + partido);
            System.out.println("Candidato añadido: " + nombre + " " + apellido + ", Partido: " + partido);

        }

    }


    private void mostrarResultados() {
        StringBuilder resultados = new StringBuilder();
        for (Candidato candidato : candidatos) {
            resultados.append(candidato.nombre).append(" ").append(candidato.apellido)
                    .append(" - Votos: ").append(candidato.votos.size()).append("\n");
            for (Voto voto : candidato.votos) {
                Estudiante estudiante = voto.estudiante;
                resultados.append("    Votante: ")
                        .append(estudiante.nombre).append(" ")
                        .append(estudiante.apellido).append(", Curso: ")
                        .append(estudiante.curso).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, resultados.toString(), "Resultados de la Elección", JOptionPane.INFORMATION_MESSAGE);
    }


    private void mostrarDialogoAgregarEstudiante() {
        JTextField campoNombre = new JTextField();
        JTextField campoApellido = new JTextField();
        JComboBox<String> comboCursos = new JComboBox<>(new String[]{"Cuarto Grado de EGB", "Quinto Grado de EGB",
                "Sexto Grado de EGB", "Séptimo Grado de EGB", "Octavo Grado de EGB",
                "Noveno Grado de EGB",
                "Décimo Grado de EGB", "Primero de Bachillerato",
                "Segundo de Bachillerato", "Tercero de Bachillerato "});
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(campoApellido);
        panel.add(new JLabel("Curso:"));
        panel.add(comboCursos);
        int resultado = JOptionPane.showConfirmDialog(null, panel, "Agregar Estudiante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String nombre = campoNombre.getText();
            String apellido = campoApellido.getText();
            String curso = (String) comboCursos.getSelectedItem();
            estudiantes.add(new Estudiante(nombre, apellido, curso));
            JOptionPane.showMessageDialog(this, "Estudiante agregado: " + nombre + " " + apellido + ", Curso: " + curso);
        }
    }

    private Estudiante obtenerEstudianteSeleccionado() {
        JComboBox<Estudiante> comboBoxEstudiantes = new JComboBox<>();
        for (Estudiante estudiante : estudiantes) {
            comboBoxEstudiantes.addItem(estudiante);
        }
        int resultado = JOptionPane.showConfirmDialog(null, comboBoxEstudiantes, "Seleccione Estudiante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            return (Estudiante) comboBoxEstudiantes.getSelectedItem();
        } else {
            return null;
        }
    }


    private void mostrarDialogoMesas() {
        String[] opcionesMesas = {"Mesa 1", "Mesa 2", "Mesa 3", "Mesa 4"};
        String mesaSeleccionada = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione la mesa:",
                "Mesas",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesMesas,
                opcionesMesas[0]
        );

        if (mesaSeleccionada != null) {
            JOptionPane.showMessageDialog(this, "Has seleccionado: " + mesaSeleccionada);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaVotacion ventanaPrincipal = new SistemaVotacion();
            ventanaPrincipal.setVisible(true);


        });
    }
}
