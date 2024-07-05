package travelplanner;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Travel_Planner extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDestination;
	private JTextField txtDepDate;
	private JTextField txtRetDate;
	private JTextField txtBudget;
	private JTable table;

	private Vector<String> columnNames = new Vector<>();
	private Vector<Vector<Object>> data = new Vector<>();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Travel_Planner frame = new Travel_Planner();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Travel_Planner() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 818, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Travel Planner");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(298, 10, 212, 72);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Planner", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(40, 104, 335, 341);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtDestination = new JTextField();
		txtDestination.setBounds(105, 46, 96, 19);
		panel.add(txtDestination);
		txtDestination.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Destination");
		lblNewLabel_1.setBounds(10, 49, 85, 13);
		panel.add(lblNewLabel_1);
		
		txtDepDate = new JTextField();
		txtDepDate.setColumns(10);
		txtDepDate.setBounds(105, 91, 96, 19);
		panel.add(txtDepDate);
		
		JLabel lblNewLabel_2 = new JLabel("Departure Date");
		lblNewLabel_2.setBounds(10, 94, 96, 13);
		panel.add(lblNewLabel_2);
		
		txtRetDate = new JTextField();
		txtRetDate.setColumns(10);
		txtRetDate.setBounds(105, 135, 96, 19);
		panel.add(txtRetDate);
		
		JLabel lblNewLabel_3 = new JLabel("Return Date");
		lblNewLabel_3.setBounds(10, 138, 85, 13);
		panel.add(lblNewLabel_3);
		
		txtBudget = new JTextField();
		txtBudget.setColumns(10);
		txtBudget.setBounds(105, 182, 96, 19);
		panel.add(txtBudget);
		
		JLabel lblNewLabel_4 = new JLabel("Budget (USD)");
		lblNewLabel_4.setBounds(10, 185, 85, 13);
		panel.add(lblNewLabel_4);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRecord();
			}
		});
		btnAdd.setBounds(21, 256, 85, 21);
		panel.add(btnAdd);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
				DefaultTableModel model = new DefaultTableModel(data, columnNames);
				int selectedIndex = table.getSelectedRow();	
       		 
				try {
   
					int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
					String destination = txtDestination.getText();
					String departureDate = txtDepDate.getText();
					String returnDate = txtRetDate.getText();
					String budget = txtBudget.getText();
				   			   			
				   	 Class.forName("com.mysql.cj.jdbc.Driver");
				         Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/travelplanner", "root", "");
				
				         PreparedStatement insert = con1
				        		 .prepareStatement("UPDATE traveltable SET Destination=?, `Departure Date`=?, `Return Date`=?, `Budget (USD)`=? WHERE ID=?");
				         insert.setString(1, destination);
				         insert.setString(2, departureDate);
				         insert.setString(3, returnDate);
				         insert.setString(4, budget);
				         insert.setInt(5, id);
				         
				         insert.executeUpdate();
				
				         table_update();
				
				         JOptionPane.showMessageDialog(null, "Record Edited");
				
				         txtDestination.setText("");
				         txtDepDate.setText("");
				         txtRetDate.setText("");
				         txtBudget.setText("");
				         txtDestination.requestFocus();
				
				     } catch (ClassNotFoundException | SQLException e1) {
				         e1.printStackTrace();
				     } 					
				
			}
		});
		btnEdit.setBounds(116, 256, 85, 21);
		panel.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
				DefaultTableModel model = new DefaultTableModel(data, columnNames);
            	int selectedIndex = table.getSelectedRow();	
            	
            	try {
               		
            		int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
           			
           			int dialogResult = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this record?","Warning",JOptionPane.YES_NO_OPTION);
           			
           			if(dialogResult == JOptionPane.YES_OPTION) {
           					
       				Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/travelplanner", "root", "");
                    PreparedStatement insert = con1
                   		 .prepareStatement("DELETE FROM traveltable WHERE ID=?");
                   
           			insert.setInt(1, id);	
                    insert.executeUpdate();

                    table_update();

                    JOptionPane.showMessageDialog(null, "Record Deleted");

                    txtDestination.setText("");
                    txtDepDate.setText("");
                    txtRetDate.setText("");
                    txtBudget.setText("");
                    txtDestination.requestFocus();
           				
           			}
       
                 } catch (ClassNotFoundException | SQLException e1) {
                     e1.printStackTrace();
                 } 	
							
			}
		});
		btnDelete.setBounds(217, 256, 85, 21);
		panel.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(385, 102, 409, 341);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					
       		 DefaultTableModel model = new DefaultTableModel(data, columnNames);
       		 int selectedIndex = table.getSelectedRow();
       		 
       		 txtDestination.setText(model.getValueAt(selectedIndex, 1).toString());
       		 txtDepDate.setText(model.getValueAt(selectedIndex, 2).toString());
       		 txtRetDate.setText(model.getValueAt(selectedIndex, 3).toString());
       		 txtBudget.setText(model.getValueAt(selectedIndex, 4).toString());
    		
			}
		});
		scrollPane.setViewportView(table);
		
		table_update();
	}	
		private void table_update() {
			
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver"); 
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/travelplanner", "root", "");
				PreparedStatement insert = con1.prepareStatement("Select * from traveltable");

				ResultSet rs = insert.executeQuery();
				
				ResultSetMetaData metadata = rs.getMetaData();
				int columnCount = metadata.getColumnCount();
				
				columnNames.clear();
				
				for (int i = 1; i <= columnCount; i++) {
					columnNames.add(metadata.getColumnName(i));
				}
				
				data.clear();
				while (rs.next()) {
					Vector<Object> row = new Vector<>();
					row.add(rs.getString("ID"));
					row.add(rs.getString("Destination"));
					row.add(rs.getString("Departure Date"));
					row.add(rs.getString("Return Date"));
					row.add(rs.getString("Budget (USD)"));
					
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
				String destination = txtDestination.getText();
				String departureDate = txtDepDate.getText();
				String returnDate = txtRetDate.getText();
				String budget = txtBudget.getText();
				
				Class.forName("com.mysql.cj.jdbc.Driver"); 
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/travelplanner", "root", "");
				PreparedStatement insert = con1.prepareStatement("INSERT INTO traveltable (Destination, `Departure Date`, `Return Date`, `Budget (USD)`) VALUES (?, ?, ?, ?)");

				insert.setString(1, destination);
				insert.setString(2, departureDate);
				insert.setString(3, returnDate);
				insert.setString(4, budget);
				insert.executeUpdate();
				
				table_update();
				
				JOptionPane.showMessageDialog(null,  "Record Added");
				
				txtDestination.setText("");
				txtDepDate.setText("");
				txtRetDate.setText("");
				txtBudget.setText("");
				txtDestination.requestFocus();
				
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
					}	
			}	
}