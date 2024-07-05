package hiketracker;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Hike_Tracker extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtName;
    private JTextField txtState;
    private JTextField txtMiles;
    private JTextField txtDateComp;
    private JTextField txtDifficulty;
    private JTable table;
    
    private Vector<String> columnNames = new Vector<>();
    private Vector<Vector<Object>> data = new Vector<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Hike_Tracker frame = new Hike_Tracker();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Hike_Tracker() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 731, 509);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Hike Tracker");
        lblNewLabel.setBounds(285, 10, 153, 29);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        contentPane.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255),
                new Color(160, 160, 160)), "Hike Input", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_1.setBounds(10, 67, 229, 314);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Name");
        lblNewLabel_1.setBounds(93, 21, 45, 13);
        panel_1.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("State");
        lblNewLabel_2.setBounds(93, 63, 45, 13);
        panel_1.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Miles");
        lblNewLabel_3.setBounds(93, 108, 45, 13);
        panel_1.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Date Completed");
        lblNewLabel_4.setBounds(75, 157, 151, 13);
        panel_1.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Difficulty (1 easy to 5 hard)");
        lblNewLabel_5.setBounds(54, 209, 163, 13);
        panel_1.add(lblNewLabel_5);

        txtName = new JTextField();
        txtName.setBounds(65, 34, 96, 19);
        panel_1.add(txtName);
        txtName.setColumns(10);

        txtState = new JTextField();
        txtState.setBounds(65, 79, 96, 19);
        panel_1.add(txtState);
        txtState.setColumns(10);

        txtMiles = new JTextField();
        txtMiles.setBounds(65, 127, 96, 19);
        panel_1.add(txtMiles);
        txtMiles.setColumns(10);
        
        txtDateComp = new JTextField();
        txtDateComp.setBounds(65, 180, 96, 19);
        panel_1.add(txtDateComp);
        txtDateComp.setColumns(10);
        
        txtDifficulty = new JTextField();
        txtDifficulty.setBounds(65, 227, 96, 19);
        panel_1.add(txtDifficulty);
        txtDifficulty.setColumns(10);

        JButton btnNewButton = new JButton("Add");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRecord();
            }
        });
        btnNewButton.setBounds(10, 283, 62, 21);
        panel_1.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Edit");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle edit action if needed
            	
            	DefaultTableModel model = new DefaultTableModel(data, columnNames);
            	int selectedIndex = table.getSelectedRow();	
            
       		 
       		try {
       		
   			String name = model.getValueAt(selectedIndex, 0).toString();
   	        String state = txtState.getText();
   	        String miles = txtMiles.getText();
   	        String date = txtDateComp.getText();
   	        String difficulty = txtDifficulty.getText();
       			
       			
       	     Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/hike%20tracker", "root", "");
             PreparedStatement insert = con1
            		 .prepareStatement("UPDATE records SET Name=?, State=?, Miles=?, Date=?, Difficulty=? WHERE Name=?");
             insert.setString(1, name);
             insert.setString(2, state);
             insert.setString(3, miles);
             insert.setString(4, date);
             insert.setString(5, difficulty);
             insert.setString(6, name);
           
             
             
 
             insert.executeUpdate();

             table_update();

             JOptionPane.showMessageDialog(null, "Record Edited");

             txtName.setText("");
             txtState.setText("");
             txtMiles.setText("");
             txtDateComp.setText("");
             txtDifficulty.setText("");
             txtName.requestFocus();

         } catch (ClassNotFoundException | SQLException e1) {
             e1.printStackTrace();
         } 	
            	
            }
        });
        btnNewButton_1.setBounds(82, 283, 56, 21);
        panel_1.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Delete");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle delete action if needed
            	
            	DefaultTableModel model = new DefaultTableModel(data, columnNames);
            	int selectedIndex = table.getSelectedRow();	
            	
            	try {
               		
           			String name = model.getValueAt(selectedIndex, 0).toString();   		
           			
           			
           			int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this record?","Warning",JOptionPane.YES_NO_OPTION);
           			
           			if(dialogResult == JOptionPane.YES_OPTION) {
           				     				
       				Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/hike%20tracker", "root", "");
                    PreparedStatement insert = con1
                   		 .prepareStatement("DELETE FROM records WHERE Name=?");
                   
                    insert.setString(1, name);
           				
                    insert.executeUpdate();

                    table_update();

                    JOptionPane.showMessageDialog(null, "Record Deleted");

                    txtName.setText("");
                    txtState.setText("");
                    txtMiles.setText("");
                    txtDateComp.setText("");
                    txtDifficulty.setText("");
                    txtName.requestFocus();	
           				
           			}
       
                 } catch (ClassNotFoundException | SQLException e1) {
                     e1.printStackTrace();
                 } 	
	
            }
        });
        btnNewButton_2.setBounds(148, 283, 69, 21);
        panel_1.add(btnNewButton_2);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(316, 89, 353, 286);
        contentPane.add(scrollPane);
        
                table = new JTable();
                scrollPane.setViewportView(table);
                table.addMouseListener(new MouseAdapter() {
                	@Override
                	public void mouseClicked(MouseEvent e) {                		
                		
                		 DefaultTableModel model = new DefaultTableModel(data, columnNames);
                		 int selectedIndex = table.getSelectedRow();
                		 
                		 txtName.setText(model.getValueAt(selectedIndex, 0).toString());
                		 txtState.setText(model.getValueAt(selectedIndex, 1).toString());
                		 txtMiles.setText(model.getValueAt(selectedIndex, 2).toString());
                		 txtDateComp.setText(model.getValueAt(selectedIndex, 3).toString());
                		 txtDifficulty.setText(model.getValueAt(selectedIndex, 4).toString());              		
                		
                	}
                });

                table_update(); // Call table_update initially to populate the table
    }

    private void table_update() {
    	
        try {
        	
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/hike%20tracker", "root", "");
            PreparedStatement insert = con1.prepareStatement("select * from records");
            ResultSet rs = insert.executeQuery();

            // Get column names
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            columnNames.clear();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metadata.getColumnName(i));
            }

            // Get data
            data.clear();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("Name"));
                row.add(rs.getString("State"));
                row.add(rs.getString("Miles"));
                row.add(rs.getString("Date"));
                row.add(rs.getString("Difficulty"));

                data.add(row);
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            table.setModel(model);

        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
    }

    private void addRecord() {
        
        try {
        	
        	String name = txtName.getText();
            String state = txtState.getText();
            String miles = txtMiles.getText();
            String date = txtDateComp.getText();
            String difficulty = txtDifficulty.getText();
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/hike%20tracker", "root", "");
            PreparedStatement insert = con1
                    .prepareStatement("insert into records(Name,State,Miles,Date,Difficulty)values(?,?,?,?,?)");
            insert.setString(1, name);
            insert.setString(2, state);
            insert.setString(3, miles);
            insert.setString(4, date);
            insert.setString(5, difficulty);
            insert.executeUpdate();

            table_update();

            JOptionPane.showMessageDialog(null, "Record Added");

            txtName.setText("");
            txtState.setText("");
            txtMiles.setText("");
            txtDateComp.setText("");
            txtDifficulty.setText("");
            txtName.requestFocus();

        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
    }
}