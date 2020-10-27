/*
 *Menú principal de la base de datos, involucra creación de registros y consultas 
 *
 */
package GraphicalInterface;
/*
 *Este paquete contiene solamente la interfaz gráfica de la aplicación
 *Solamente desarrolla interfaces en éste paquete.
*/
import DatabaseClass.Conection;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.Random;
import javax.swing.JFileChooser;

/**
 *
 * @author Shadowkiller
 */
public class PrincipalWindows extends javax.swing.JFrame {

    Conection con = new Conection();//Creacion del conector a la Base de Datos
    Connection cn = con.getConnection();//recepción de la conexión de la base de datos
    PreparedStatement ps;//preparar consultas, eliminaciones, etc
    ResultSet rs;//objeto de resultado de las consultas, etc
    public PrincipalWindows(){
        initComponents();
    }
    boolean validateDate(String a, String b)
    {
        boolean state = true;
        String x = "#";
        try{
            ps = cn.prepareStatement("SELECT * FROM cita WHERE FechaConsulta = ? and numConsultorio = ?");
            ps.setString(1, a);
            ps.setString(2, b);
            rs = ps.executeQuery();
            if(rs.next())
                x = rs.getString("FechaConsulta");
            
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(x);
        if(a.equals(x))
            state = false;
        return state;
    }
    void generate(String x[], String y[])
    {
        for(int i = 0; i < 10; i++)
        {
            switch(i)
            {
                case 0:
                    x[0] = "Paracetamol";
                    y[0] = "Influenza";
                    break;
                case 1:
                    x[1] = "Ibupofreno";
                    y[1] = "Gastrointeritis";
                    break;
                case 2:
                    x[2] = "Trimebutina";
                    y[2] = "Neumonia";
                    break;
                case 3:
                    x[3] = "Metamizol sodico";
                    y[3] = "Resfriado comun";
                    break;
                case 4:
                    x[4] = "Betametazona";
                    y[4] = "Dengue";
                    break;
                case 5:
                    x[5] = "amoxicilina";
                    y[5] = "Conjuntivitis";
                    break;
                case 6:
                    x[6] = "Salbutamol";
                    y[6] = "Neumonia";
                    break;
                case 7:
                    x[7] = "Fuceramida";
                    y[7] = "Asma";
                    break;
                case 8:
                    x[8] = "Lanzoprazol";
                    y[8] = "Gastritis";
                    break;
                 case 9:
                     x[9] = "Aspirina";
                     y[9] = "Colitis Nerviosa";
                    break;
            }
        }
    }
    void mostrarPacientes()
    {
        DefaultTableModel m = new DefaultTableModel();
        m.addColumn("Nombre");
        m.addColumn("Apellido1");
        m.addColumn("Apellido2");
        m.addColumn("Edad");
        m.addColumn("NSS");
        m.addColumn("Calle");
        m.addColumn("Numero");
        m.addColumn("Cp");
        TablePacient.setModel(m);
        String sql = "SELECT * FROM paciente";
        String data[] = new String[8]; 
        Statement st;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);//consulta de citas
        while(rs.next())
        {
            data[0] = rs.getString(1);
            data[1] = rs.getString(2);
            data[2] = rs.getString(3);
            data[3] = rs.getString(4);
            data[4] = rs.getString(5);
            data[5] = rs.getString(6);
            data[6] = rs.getString(7);
            data[7] = rs.getString(8);
            m.addRow(data);
        }
        TablePacient.setModel(m);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    void mostrarConsultas()
    {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID_Cita");
        modelo.addColumn("Fecha/Hora consulta");
        modelo.addColumn("ID_Paciente");
        modelo.addColumn("numConsultorio");
        TablaCitas.setModel(modelo);
        String sql = "SELECT * FROM cita";
        String data[] = new String[4]; 
        Statement st;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);//consulta de citas
        while(rs.next())
        {
            data[0] = rs.getString(1);
            data[1] = rs.getString(2);
            data[2] = rs.getString(3);
            data[3] = rs.getString(4);
            modelo.addRow(data);
        }
        TablaCitas.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //método para insertar los datos, de aquí obtenemos el consultorio para generar la consulta médica
    void insertData(String y[], String x, String constl[])
    {
        System.out.println(Number.getValue().toString());
        try{
            ps = cn.prepareStatement("SELECT * FROM consultorio");
            rs = ps.executeQuery();
            int i = 0;
            while(rs.next())
            {
                y[i] = rs.getString("ID_Consultorio");
                System.out.println(y[i]);
                i++;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ps = cn.prepareStatement("SELECT * FROM paciente WHERE NSS = ?");
            ps.setString(1, Number.getValue().toString());
            
            rs = ps.executeQuery();
            if(rs.next())
            {
                x = rs.getString("Cp");
                System.out.println(x);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(x != "#" && y.length > 0)
        {
            String numC = " ";
            long cp = Long.parseLong(x);
            System.out.println(y[9]);
            if(cp >=44100 && cp<=44277)
                numC = y[0];
            else if(cp >=44277 && cp<=44454)
                numC = y[1];
            else if(cp >=44277 && cp<=44454)
                numC = y[2];
            else if(cp >=444254 && cp<=44631)
                numC = y[3];
            else if(cp >=44631 && cp<=44808)
                numC = y[4];
            else if(cp >=44808 && cp<=44985)
                numC = y[5];
            else if(cp >=44985 && cp<=45162)
                numC = y[6];
            else if(cp >=45162 && cp<=45339)
                numC = y[7];
            else if(cp >=45339 && cp<=45516)
                numC = y[8];
            else if(cp >=45516 && cp<=45696)
                numC = y[9];
            constl[0] = numC;
            String tx= ((JTextField)FechaConsulta.getDateEditor().getUiComponent()).getText();
            System.out.println(tx);
            boolean state = validateDate(tx, numC);
            if(state == true)
            {
             try {
                ps = cn.prepareStatement("INSERT INTO cita(FechaConsulta, ID_Paciente, numConsultorio) VALUES(?, ?, ?)");
                ps.setString(1, tx);//insertar Fecha
                ps.setString(2, Number.getValue().toString());
                ps.setString(3, numC);
                ps.execute();
                JOptionPane.showMessageDialog(this, "Cita Registrada Correctamente");
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);   
            }
            }
            else{
                JOptionPane.showMessageDialog(this, "Fecha ya registrada");
                constl[0] = "#";
            }
        }
    }
    //método para la obtención de los datos para generar la consulta
    void generatePDF(String numP, String numC)
    {
        Random r = new Random();
        String medicaments[] = new String[10];
        String sickness[] = new String[10];
        generate(medicaments, sickness);//generación de los medicamentos
        String name = " ", ap1 = " ", ap2 = " ", age = " ";//datos del paciente a generar la consulta
        String nm = " ", ap = " ", ap0 = " ";//Datos del médico a capturar
        rs = null;
        ps = null;
        try {
            ps = cn.prepareStatement("SELECT * FROM paciente WHERE NSS = ?");
            ps.setString(1, numP);
            rs = ps.executeQuery();
            if(rs.next())
            {
                name = rs.getString(1);
                ap1 = rs.getString(2);
                ap2 = rs.getString(3);
                age = rs.getString(4);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
        String idMedic = "#";//id del médico a capturar
        try {
            ps = cn.prepareStatement("SELECT * FROM consultorio WHERE ID_Consultorio = ?");
            ps.setString(1, numC);
            rs = ps.executeQuery();
            if(rs.next())
            {
                idMedic = rs.getString(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(idMedic != "#")
        {
            try {
                ps = cn.prepareStatement("SELECT * FROM medico WHERE DNI = ?");
                ps.setString(1, idMedic);
                rs = ps.executeQuery();
                if(rs.next())
                {
                    nm = rs.getString(1);
                    ap = rs.getString(2);
                    ap0 = rs.getString(3);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
            }
            int enf = r.nextInt(9);
            int med = r.nextInt(9);
            System.out.println("Atenido por el medico");
            System.out.println("Nombre completo: "+nm+ " "+ap+ " "+ap0);
            System.out.println("Datos del paciente");
            System.out.println("Nombre completo: "+name+ " "+ap1+ " "+ap2);
            System.out.println("Edad: "+age);
            System.out.println("Afeccion: "+sickness[enf]);
            System.out.println("Medicamento recetado: "+medicaments[med]);
            JFileChooser dlg= new JFileChooser();
            int option = dlg.showSaveDialog(this);
            String ruta = "#";
            if(option == JFileChooser.APPROVE_OPTION)
            {
                File f =  dlg.getSelectedFile();
                ruta = f.toString();
            }
            if(ruta != "#")
            {
                String contenido = "Hospital la salud es lo primero\nReceta medica\n";
                contenido+="Atendido por el medico\nNombre Completo: "+nm+ " "+ap+ " "+ap0;
                contenido+="\nCedula profesional: "+idMedic;
                contenido+="\nDatos del paciente: "+name+ " "+ap1+ " "+ap2;
                contenido+="\nEdad: "+age;
                contenido+="\nAfeccion: "+sickness[enf];
                contenido+="\nMedicamento recetado: "+medicaments[med];
                try {
                    FileOutputStream archivo = new FileOutputStream(ruta+".pdf");
                    Document doc = new Document();
                    try {
                        PdfWriter.getInstance(doc, archivo);
                        doc.open();
                        doc.add(new Paragraph(contenido));
                        
                        doc.close();
                    } catch (DocumentException ex) {
                        Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    JOptionPane.showMessageDialog(this, "Receta guardata exitosamente");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
    void mostrarConsultorio(int numC)
    {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID_Cita");
        modelo.addColumn("Fecha/Hora consulta");
        modelo.addColumn("ID_Paciente");
        modelo.addColumn("numConsultorio");
        TablaCitas.setModel(modelo);        
        String data[] = new String[4]; 
        try {
            String p = String.valueOf(numC);
            ps = cn.prepareStatement("SELECT * FROM cita WHERE numConsultorio = ?");
            ps.setString(1, p);
            rs = ps.executeQuery();//consulta de consultorios
        while(rs.next())
        {
            data[0] = rs.getString(1);
            data[1] = rs.getString(2);
            data[2] = rs.getString(3);
            data[3] = rs.getString(4);
            modelo.addRow(data);
        }
        TablaCitas.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void mostrarConsultorios()
    {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID_Consultorio");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Medico");
        TableCons.setModel(modelo);
        String sql = "SELECT * FROM consultorio";
        String data[] = new String[3]; 
        Statement st;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);//consulta de consultorios
        while(rs.next())
        {
            data[0] = rs.getString(1);
            data[1] = rs.getString(2);
            data[2] = rs.getString(3);
            modelo.addRow(data);
        }
        TableCons.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void mostrarMedicos()
    {
        DefaultTableModel m = new DefaultTableModel();
        m.addColumn("Nombre");
        m.addColumn("Apellido1");
        m.addColumn("Apellido2");
        m.addColumn("DNI");
        m.addColumn("Calle");
        m.addColumn("Numero");
        m.addColumn("Cp");
        TableMedics.setModel(m);
        String sql = "SELECT * FROM medico";
        String data[] = new String[7]; 
        Statement st;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);//consulta de medicos
        while(rs.next())
        {
            data[0] = rs.getString(1);
            data[1] = rs.getString(2);
            data[2] = rs.getString(3);
            data[3] = rs.getString(4);
            data[4] = rs.getString(5);
            data[5] = rs.getString(6);
            data[6] = rs.getString(7);
            m.addRow(data);
        }
        TableMedics.setModel(m);
        } catch (SQLException ex) {
            Logger.getLogger(PrincipalWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        FechaConsulta = new com.toedter.calendar.JDateChooser();
        Number = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaCitas = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        NumC = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablePacient = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableCons = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableMedics = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GraphicalInterface/imagenInicio.jpg"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Bienvenido a la Base de Datos de HDB");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addGap(0, 48, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Principal", jPanel2);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        jPanel8.setBackground(new java.awt.Color(0, 102, 102));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Registro Cita"));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Numero de Seguro Social");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Fecha");

        jButton1.setText("Registrar Cita");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        FechaConsulta.setDateFormatString("yyyy-MM-dd HH:mm:ss");

        Number.setModel(new javax.swing.SpinnerNumberModel(0L, null, null, 1L));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Number, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FechaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(FechaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(193, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Registrar Cita", jPanel3);

        TablaCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(TablaCitas);

        jButton2.setText("Mostrar Citas");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Numero de consultorio a mostrar citas: ");

        NumC.setModel(new javax.swing.SpinnerNumberModel());
        NumC.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NumC)
                .addGap(82, 82, 82)
                .addComponent(jButton2)
                .addGap(84, 84, 84))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel5)
                    .addComponent(NumC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Mostrar Citas", jPanel4);

        TablePacient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(TablePacient);

        jButton3.setText("Mostrar Pacientes");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Mostrar Pacientes", jPanel5);

        TableCons.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(TableCons);

        jButton4.setText("Mostrar Consultorios");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Mostrar Consultorios", jPanel6);

        TableMedics.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(TableMedics);

        jButton5.setText("Mostrar Médicos");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mostrar Médicos", jPanel7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu1.setText("Opciones");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
//método que agrega las citas a la base de datos, ellas son encargadas de generar la instancia para su respectivo uso
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String x = "#";
        String y[] = new String[10];
        String constl[] = new String[1];
        insertData(y, x, constl);//bloque de ejecución de la inserción
        Integer n = y.length;
        System.out.println(constl[0]);
        if(n > 0 && constl[0] != "#")
        {
            generatePDF(Number.getValue().toString(), constl[0]);
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int numC = Integer.parseInt(NumC.getValue().toString());
        if(numC != 0)
            mostrarConsultorio(numC);
        else
            mostrarConsultas();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        con.disconection();//desconexion de la BD
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    //mostrar pacientes
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        mostrarPacientes();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        mostrarConsultorios();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       mostrarMedicos();
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalWindows.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalWindows.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalWindows.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalWindows.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalWindows().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser FechaConsulta;
    private javax.swing.JSpinner NumC;
    private javax.swing.JSpinner Number;
    private javax.swing.JTable TablaCitas;
    private javax.swing.JTable TableCons;
    private javax.swing.JTable TableMedics;
    private javax.swing.JTable TablePacient;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
