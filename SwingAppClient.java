/*
 * Libraries and packages used:
 */

package clientPackage;
import clientPackage.PaillierClient;
import clientPackage.Email;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SwingAppClient { 
	
	private static JFrame frame;
	private static JLayeredPane pane;
	private static JPanel bottomPane;
	private JPanel topPanel; 
	private JPanel verifierPanel;
	private JPanel thresholdPanel;
	private JPanel banksPanel; 
	private JLabel verifierLabel;
	private JLabel thresholdLabel;
	private JLabel banksLabel;
	private JTextField thresholdField;
	private JTextField banksField;
	private DefaultListModel bankList;
	private String[] verifiers;
	private JComboBox<String> verifierList;
	private JButton submit;
	private int banksAdded; 
	private JPanel leftBankPanel;
	private JPanel innerLeftBankPanel;
	private JPanel buttonLeftBankPanel;
	private JButton addBank;
	private JPanel rightBankPanel;
	private JList newBankList;
	private JTextArea instructions; 
	private JLabel topMargin;
	private JLabel verifierMargin;
	private JLabel thresholdMargin;
	private JLabel banksMargin;
	private JPanel bottomPanel;
	private JButton submitButton;
	private JLabel processingLabel;
	private ImageIcon processingIcon;
	private static String userId;
	protected static String userName;
	  
	public SwingAppClient(String appName, String id, String name) {
		frame = new JFrame(appName);
		pane = new JLayeredPane();
		bottomPane = new JPanel();
		topPanel = new JPanel(); 
		verifierPanel = new JPanel();
		thresholdPanel = new JPanel();
		banksPanel = new JPanel();
		verifierLabel = new JLabel("Lender");
		thresholdLabel = new JLabel("Amount");
		banksLabel = new JLabel("Banks");
		thresholdField = new JTextField(15);
		banksField = new JTextField(15);
		bankList = new DefaultListModel();
		submit = new JButton("Submit");
		banksAdded = 1;  
		leftBankPanel = new JPanel();
		innerLeftBankPanel = new JPanel();
		buttonLeftBankPanel = new JPanel();
		addBank = new JButton("ADD");
		rightBankPanel = new JPanel();
		newBankList = new JList(bankList);
		instructions = new JTextArea();
		topMargin = new JLabel();
		verifierMargin = new JLabel();
		thresholdMargin = new JLabel();
		banksMargin = new JLabel();
		bottomPanel = new JPanel();
		submitButton = new JButton("SUBMIT");
		processingLabel = new JLabel("Processing");
		processingIcon = new ImageIcon(this.getClass().getResource("ajax-loader (1).gif"));
		processingLabel.setIcon(processingIcon);
		userId = id;
		userName = name;
	}  
	 
	public void initializeEverifyApp() {
		
		 /*
		  * Set frame, pane, bottomPane and topPane
		  */
		
		 frame.setSize(500,600);
		 frame.setLayout(new BorderLayout());
		 frame.add(pane, BorderLayout.CENTER);
		 
		 pane.setBounds(0, 0, 500, 600);
		 
		 bottomPane.setBounds(0, 0, 500, 600);
		 bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.Y_AXIS));
		 
		 pane.add(bottomPane, new Integer(0), 0); 
		 
		 /*
		  * Set processingLabel
		  */
		
		 processingLabel.setMaximumSize(new Dimension(200,150));
		 processingLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		 processingLabel.setForeground(Color.WHITE);	
		 processingLabel.setBorder(new EmptyBorder(250,0,0,0));
		 
		 /*
		  * Top panel top margin
		  */
		 
		 topMargin.setMaximumSize(new Dimension(500,15));
		 
		 /*
		  * Top panel
		  */
		 
		 instructions.setText("Please enter all information and click submit.  "
					+ "Your encryption keys will be generated on your local machine and only shared with the verifier and your financial "
					+ "institutions.  You "
					+ "can enter up to 5 banks for a single transaction.");
		 
		 instructions.setBackground(topPanel.getBackground());
		 instructions.setLineWrap(true);
		 instructions.setWrapStyleWord(true);
		 instructions.setFont(new Font("Tahoma", Font.BOLD, 12));
		 instructions.setBorder(new EmptyBorder(10,10,10,10));
		 instructions.setEditable(false);
		 
		 topPanel.setLayout(new BorderLayout());
		 topPanel.add(instructions, BorderLayout.CENTER);
			
		 topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		 topPanel.setMaximumSize(new Dimension(500,80));
		 topPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		 
		 /*
		  * Verifier panel top margin
		  */
		 
		 verifierMargin.setMaximumSize(new Dimension(500,20));
		 
		 /*
		  * Verifier panel
		  */
		 
		 verifiers = new String[] {" -----Select Lender-----", "Bank of America", "Capital One", "Chase Bank", "Citibank", "Wells Fargo"};
		 verifierList = new JComboBox<String>(verifiers);
		 
		 verifierLabel.setMaximumSize(new Dimension(80,25));
		 verifierLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
		 
		 verifierList.setMaximumSize(new Dimension(150,25));
		 
		 verifierPanel.add(verifierLabel);
		 verifierPanel.add(verifierList);
		 
		 verifierPanel.setLayout(new BoxLayout(verifierPanel, BoxLayout.X_AXIS));
		 verifierPanel.setBorder(new EmptyBorder(50,0,0,0));
		 verifierPanel.setBorder(BorderFactory.createTitledBorder("1.  Verification Information"));
		 verifierPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
		 verifierPanel.setMaximumSize(new Dimension(500,100));
		 
		 /*
		  * Threshold panel top margin
		  */
		 
		 thresholdMargin.setMaximumSize(new Dimension(500,10));
		 
		 /*
		  * Threshold panel
		  */
		 
		 thresholdPanel.setLayout(new BoxLayout(thresholdPanel, BoxLayout.X_AXIS)); 
		 thresholdPanel.setBorder(BorderFactory.createTitledBorder("2.  Qualifying Balance"));
		 
		 thresholdLabel.setMaximumSize(new Dimension(80,25));
		 thresholdLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
		 
		 thresholdField.setMaximumSize(new Dimension(150,25));
		 
		 thresholdPanel.add(thresholdLabel);
		 thresholdPanel.add(thresholdField);
		 thresholdPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
		 thresholdPanel.setMaximumSize(new Dimension(500,100));
		 
		 /*
		  * Bank panel top margin
		  */
		 
		 banksMargin.setMaximumSize(new Dimension(500,10));
		 
		 /*
		  * Bank panel
		  */
		
		 banksPanel.setLayout(new BoxLayout(banksPanel, BoxLayout.X_AXIS)); 
		 banksPanel.setBorder(BorderFactory.createTitledBorder("3.  Financial Institutions"));
		 
		 /*
		  * Left side of bank panel
		  */
			
		 leftBankPanel.setLayout(new BoxLayout(leftBankPanel, BoxLayout.Y_AXIS));
		 leftBankPanel.setMaximumSize(new Dimension(250,100));
			
		 innerLeftBankPanel.setLayout(new BoxLayout(innerLeftBankPanel, BoxLayout.X_AXIS));
		 innerLeftBankPanel.setMaximumSize(new Dimension(250,50));
		 
		 buttonLeftBankPanel.setLayout(new BoxLayout(buttonLeftBankPanel, BoxLayout.X_AXIS));
		 buttonLeftBankPanel.setMaximumSize(new Dimension(250,50));
		 buttonLeftBankPanel.setBorder(new EmptyBorder(0,0,25,0));
		 
		 banksLabel.setMaximumSize(new Dimension(80,25));
		 banksLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
		 
		 banksField.setMaximumSize(new Dimension(150,25));
		 
		 innerLeftBankPanel.add(banksLabel);
		 innerLeftBankPanel.add(banksField);
		
		 addBank.setMaximumSize(new Dimension(58,25));
		 addBank.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 if(banksAdded < 6){
					bankList.addElement(banksAdded++ + ". " + banksField.getText());
					banksField.setText(null);
				 } else {
					banksField.setText(null);
				 }
			 }  
		 }); 
	 	
		 buttonLeftBankPanel.add(Box.createRigidArea(new Dimension(170,0)));
		 buttonLeftBankPanel.add(addBank);
		
		 leftBankPanel.add(innerLeftBankPanel);
		 leftBankPanel.add(buttonLeftBankPanel);	
		 
		 /*
		  * Right side of bank panel
		  */
		
		 rightBankPanel.setLayout(new BoxLayout(rightBankPanel, BoxLayout.Y_AXIS));
		 rightBankPanel.setMaximumSize(new Dimension(250,100));
		 
		 newBankList.setBackground(rightBankPanel.getBackground());
		 rightBankPanel.add(newBankList);
		 
		 banksPanel.add(leftBankPanel);
		 banksPanel.add(rightBankPanel);
		 
		 banksPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
		 banksPanel.setMaximumSize(new Dimension(500,150));
		 
		 /*
		  * Bottom panel
		  */
		 
		 bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		 bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
		 bottomPanel.setMaximumSize(new Dimension(500,100));
		 
		 submitButton.setMaximumSize(new Dimension(80,25));
			
		 bottomPanel.add(Box.createRigidArea(new Dimension(210,0)));
		 bottomPanel.add(submitButton);
		 
		 submitButton.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e){
				try {
					sendParameters();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 }
		 });
		     
		 /*
		  * Panels added
		  */
			
		 bottomPane.add(topMargin);
		 bottomPane.add(topPanel);
		 bottomPane.add(verifierMargin);
		 bottomPane.add(verifierPanel);
		 bottomPane.add(thresholdMargin);
		 bottomPane.add(thresholdPanel);
		 bottomPane.add(banksMargin);
		 bottomPane.add(banksPanel);
		 bottomPane.add(bottomPanel);
		 
		 frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		 frame.setVisible(true);
	}    
	
	public void sendParameters() throws NumberFormatException, IOException, AddressException, MessagingException {
		String[] parameters = {thresholdField.getText(), Integer.toString(bankList.getSize()), 
				verifierList.getSelectedItem().toString(), userId};
	
		PaillierClient.main(parameters); 
	}    
	 
	public static void closeApp() {
		JOptionPane.showMessageDialog(frame, "Your verification has been submitted!  \n"
				+ "    Your total financial balance is:\n\n                     $" + 
				NumberFormat.getNumberInstance(Locale.US).format(PaillierClient.plainTextSum) + 
				"\n\n    Thank you for using eVERIFY.\n\n");
		frame.dispose();
	} 
	
	public static void main(String[] args) {
		
		SwingAppClient encryptionApp = new SwingAppClient("eVERIFY", args[0], args[1]);
		encryptionApp.initializeEverifyApp();
		
	}
  
}
