/*
 * Libraries and packages used:
 */

package verifierPackage;
import verifierPackage.Paillier_Verifier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SwingApp { 
	
	private JFrame frame;
	private JPanel contentPane;
	
	private JPanel topPanel; 
	private JTextArea instructions; 
	
	private JLabel transactionDetailsMargin;
	private JPanel transactionDetailsPanel;
	
	private JPanel transactionDetailsInnerTopMargin;
	private JPanel transactionDetailsInnerBottomMargin;
	
	private JPanel accountHolderPanel;
	private JLabel accountHolderLabel;
	private JLabel accountHolderName;
	
	private JPanel qualifyingBalancePanel;
	private JLabel qualifyingBalanceLabel;
	private JLabel qualifyingBalanceValue;
	
	private JPanel encryptedBalancePanel;
	private JLabel encryptedBalanceLabel;
	private JLabel encryptedBalanceValue;
	
	private JLabel keyMargin;
	private JPanel keyPanel;
	
	private JPanel keyPanelInnerTopMargin;
	private JPanel keyPanelInnerBottomMargin;
	
	private JPanel enterKeyPanel;
	private JLabel enterKeyLabel;
	private static JTextField enterKeyField;
	
	private JLabel resultMargin;
	private JPanel resultPanel;
	
	private JPanel resultPanelInnerTopMargin;
	private JPanel resultPanelInnerBottomMargin;
	
	private JPanel decryptedBalancePanel;
	private JLabel decryptedBalanceLabel;
	private static JLabel decryptedBalanceValue; 
	
	private JPanel decryptedResultPanel;
	private JLabel decryptedResultLabel;
	private static JLabel decryptedResultValue;  
	
	private JPanel bottomPanel;
	
	private JButton decryptButton; 
	
	private static String encryptedBalance;
	
	protected static String verificationId;
	  
	public SwingApp(String appName, String clientName, String qualifyingBalance, String cipherTextResultMask, String id) {
		encryptedBalance = cipherTextResultMask;
		verificationId = id;
		frame = new JFrame(appName);
		contentPane = new JPanel();
		
		topPanel = new JPanel();  
		instructions = new JTextArea();
		
		transactionDetailsMargin = new JLabel();
		transactionDetailsPanel = new JPanel();
		
		transactionDetailsInnerTopMargin = new JPanel();
		transactionDetailsInnerBottomMargin = new JPanel();
		
		accountHolderPanel = new JPanel();
		accountHolderLabel = new JLabel("Account Holder: ");
		accountHolderName = new JLabel(clientName);
		
		qualifyingBalancePanel = new JPanel();
		qualifyingBalanceLabel = new JLabel("Qualifying Balance: ");
		qualifyingBalanceValue = new JLabel("$"+qualifyingBalance); 
		
		encryptedBalancePanel = new JPanel();
		encryptedBalanceLabel = new JLabel("Encrypted Balance: ");
		encryptedBalanceValue = new JLabel(cipherTextResultMask.substring(0, 10)+"...");
		
		keyMargin = new JLabel();
		keyPanel = new JPanel();
		
		keyPanelInnerTopMargin = new JPanel();
		keyPanelInnerBottomMargin = new JPanel();
		 
		enterKeyPanel = new JPanel();
		enterKeyLabel = new JLabel("Decryption Key: ");
		enterKeyField = new JTextField();
		
		resultMargin = new JLabel();
		resultPanel = new JPanel();
		
		resultPanelInnerTopMargin = new JPanel();
		resultPanelInnerBottomMargin = new JPanel();
		
		decryptedBalancePanel = new JPanel();
		decryptedBalanceLabel = new JLabel("Decrypted Value: ");
		decryptedBalanceValue = new JLabel();
		
		decryptedResultPanel = new JPanel();
		decryptedResultLabel = new JLabel("Result: ");
		decryptedResultValue = new JLabel();
		
		bottomPanel = new JPanel();
		decryptButton = new JButton("DECRYPT");

	}    
	
	public void initializeEverifyApp() {
		
		 /*
		  * Set frame and content pane
		  */
		
		 this.frame.setSize(500,600);
		 this.frame.setContentPane(contentPane);
		 this.contentPane.setLayout(new BoxLayout(this.contentPane, BoxLayout.Y_AXIS));
		 this.contentPane.setMaximumSize(new Dimension(500,600));
		 
		 /*
		  * Top panel
		  */
		 
		 instructions.setText("Please enter the key and click decrypt.  "
					+ "The encrypted balance minus the qualifying amount will be decrypted and revealed.  The value shown does not reflect the actual balance. "
					+ "Any positive value represents an account balance with at least the qualifying amount.  ");
		 
		 this.instructions.setBackground(this.topPanel.getBackground());
		 this.instructions.setLineWrap(true);
		 this.instructions.setWrapStyleWord(true);
		 this.instructions.setFont(new Font("Tahoma", Font.BOLD, 12));
		 this.instructions.setBorder(new EmptyBorder(10,10,10,10));
		 this.instructions.setEditable(false);
		 
		 this.topPanel.setLayout(new BorderLayout());
		 this.topPanel.add(this.instructions, BorderLayout.CENTER);
			
		 this.topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		 this.topPanel.setMaximumSize(new Dimension(500,80));
		 this.topPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		 
		 /*
		  * Transaction details panel top margin
		  */
		 
		 this.transactionDetailsMargin.setMaximumSize(new Dimension(500,20));
		 
		 /*
		  * Transaction details panel
		  */
		 
		 this.transactionDetailsPanel.setLayout(new BoxLayout(this.transactionDetailsPanel, BoxLayout.Y_AXIS));
		 this.transactionDetailsPanel.setBorder(new EmptyBorder(50,0,0,0));
		 this.transactionDetailsPanel.setBorder(BorderFactory.createTitledBorder("Transaction Details"));
		 this.transactionDetailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
		 this.transactionDetailsPanel.setMaximumSize(new Dimension(500,120));
		 
		 /*
		  * Transaction details top margin
		  */
		 
		 this.transactionDetailsInnerTopMargin.setMaximumSize(new Dimension(500,40));
		 this.transactionDetailsPanel.add(this.transactionDetailsInnerTopMargin);
		 
		 /*
		  * Account holder panel -- transaction details sub panel
		  */
		 
		 this.accountHolderPanel.setLayout(new BoxLayout(this.accountHolderPanel, BoxLayout.X_AXIS));
		 this.accountHolderPanel.setMaximumSize(new Dimension(500,40));
		 
		 this.accountHolderLabel.setMaximumSize(new Dimension(150,25));
		 this.accountHolderLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
		 
		 this.accountHolderName.setMaximumSize(new Dimension(120,25));
		 
		 this.accountHolderPanel.add(this.accountHolderLabel);
		 this.accountHolderPanel.add(this.accountHolderName);
		 
		 this.transactionDetailsPanel.add(this.accountHolderPanel);
		 
		 /*
		  * Qualifying balance panel -- transaction details sub panel
		  */
		 
		 this.qualifyingBalancePanel.setLayout(new BoxLayout(this.qualifyingBalancePanel, BoxLayout.X_AXIS));
		 this.qualifyingBalancePanel.setMaximumSize(new Dimension(500,40));
		 
		 this.qualifyingBalanceLabel.setMaximumSize(new Dimension(150,25));
		 this.qualifyingBalanceLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
		 
		 this.qualifyingBalanceValue.setMaximumSize(new Dimension(120,25));
		 
		 this.qualifyingBalancePanel.add(this.qualifyingBalanceLabel);
		 this.qualifyingBalancePanel.add(this.qualifyingBalanceValue);
		 
		 this.transactionDetailsPanel.add(this.qualifyingBalancePanel);
		 
		 /*
		  * Encrypted balance panel -- transaction details sub panel
		  */
		 
		 this.encryptedBalancePanel.setLayout(new BoxLayout(this.encryptedBalancePanel, BoxLayout.X_AXIS));
		 this.encryptedBalancePanel.setMaximumSize(new Dimension(500,40));
		 
		 this.encryptedBalanceLabel.setMaximumSize(new Dimension(150,25));
		 this.encryptedBalanceLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
		 
		 this.encryptedBalanceValue.setMaximumSize(new Dimension(120,25));
		 
		 this.encryptedBalancePanel.add(this.encryptedBalanceLabel);
		 this.encryptedBalancePanel.add(this.encryptedBalanceValue);
		 
		 this.transactionDetailsPanel.add(this.encryptedBalancePanel);  
		 
		 /*
		  * Transaction details bottom margin
		  */
		 
		 this.transactionDetailsInnerBottomMargin.setMaximumSize(new Dimension(500,40));
		 this.transactionDetailsPanel.add(this.transactionDetailsInnerBottomMargin);
		 
		 this.keyMargin.setMaximumSize(new Dimension(500,20));
		 
		 /*
		  * Key panel
		  */
		 
		 this.keyPanel.setLayout(new BoxLayout(this.keyPanel, BoxLayout.Y_AXIS));
		 this.keyPanel.setBorder(new EmptyBorder(50,0,0,0));
		 this.keyPanel.setBorder(BorderFactory.createTitledBorder("Key Information"));
		 this.keyPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
		 this.keyPanel.setMaximumSize(new Dimension(500,90));
		 
		 /*
		  * Key panel inner top margin
		  */
		 
		 this.keyPanelInnerTopMargin.setMaximumSize(new Dimension(500,10));
		 this.keyPanel.add(this.keyPanelInnerTopMargin);
		 
		 this.enterKeyPanel.setLayout(new BoxLayout(this.enterKeyPanel, BoxLayout.X_AXIS));
		 this.enterKeyPanel.setMaximumSize(new Dimension(500,40));
		  
		 this.enterKeyLabel.setMaximumSize(new Dimension(150,25));
		 this.enterKeyLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
		 
		 this.enterKeyField.setMaximumSize(new Dimension(120,25));
		 
		 this.enterKeyPanel.add(this.enterKeyLabel);
		 this.enterKeyPanel.add(this.enterKeyField);
		 
		 this.keyPanel.add(this.enterKeyPanel);
		 
		 this.resultMargin.setMaximumSize(new Dimension(500,20));
		 
		 /*
		  * Decrypted result panel
		  */
		 
		 this.resultPanel.setLayout(new BoxLayout(this.resultPanel, BoxLayout.Y_AXIS));
		 this.resultPanel.setBorder(new EmptyBorder(50,0,0,0));
		 this.resultPanel.setBorder(BorderFactory.createTitledBorder("Transaction Result"));
		 this.resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
		 this.resultPanel.setMaximumSize(new Dimension(500,100));
		 
		 /*
		  * Decrypted result inner top margin
		  */
		 
		 this.resultPanelInnerTopMargin.setMaximumSize(new Dimension(500,50));
		 this.resultPanel.add(this.resultPanelInnerTopMargin);
		 
		 /*
		  * Decrypted balance panel - Result sub panel
		  */
		 
		 this.decryptedBalancePanel.setLayout(new BoxLayout(this.decryptedBalancePanel, BoxLayout.X_AXIS));
		 this.decryptedBalancePanel.setMaximumSize(new Dimension(500,50));
		  
		 this.decryptedBalanceLabel.setMaximumSize(new Dimension(150,25));
		 this.decryptedBalanceLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
		 
		 this.decryptedBalanceValue.setMaximumSize(new Dimension(120,25));
		 
		 this.decryptedBalancePanel.add(this.decryptedBalanceLabel);
		 this.decryptedBalancePanel.add(this.decryptedBalanceValue);
		  
		 this.resultPanel.add(this.decryptedBalancePanel);
		 
		 /*
		  * Decrypted value panel - Result sub panel
		  */
		 
		 this.decryptedResultPanel.setLayout(new BoxLayout(this.decryptedResultPanel, BoxLayout.X_AXIS));
		 this.decryptedResultPanel.setMaximumSize(new Dimension(500,50));
		  
		 this.decryptedResultLabel.setMaximumSize(new Dimension(150,25));
		 this.decryptedResultLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
		 
		 this.decryptedResultValue.setMaximumSize(new Dimension(120,25));
		 
		 this.decryptedResultPanel.add(this.decryptedResultLabel);
		 this.decryptedResultPanel.add(this.decryptedResultValue);
		  
		 this.resultPanel.add(this.decryptedResultPanel);
		 
		 /*
		  * Result panel bottom margin
		  */
		 
		 this.resultPanelInnerBottomMargin.setMaximumSize(new Dimension(500,50));
		 this.resultPanel.add(this.resultPanelInnerBottomMargin);
		 
		 /*
		  * Bottom panel
		  */
		 
		 this.bottomPanel.setLayout(new BoxLayout(this.bottomPanel, BoxLayout.X_AXIS));
		 this.bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
		 this.bottomPanel.setMaximumSize(new Dimension(500,100));
		 
		 this.decryptButton.setMaximumSize(new Dimension(100,25));
			
		 this.bottomPanel.add(Box.createRigidArea(new Dimension(210,0)));
		 this.bottomPanel.add(this.decryptButton);
		 
		 this.decryptButton.addActionListener(new ActionListener(){
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
			
		 this.contentPane.add(this.topPanel);
		 this.contentPane.add(this.transactionDetailsMargin);
		 this.contentPane.add(this.transactionDetailsPanel);
		 this.contentPane.add(this.keyMargin);
		 this.contentPane.add(this.keyPanel);
		 this.contentPane.add(this.resultMargin);
		 this.contentPane.add(this.resultPanel);
		 this.contentPane.add(this.bottomPanel);
			
		 this.frame.setVisible(true);
	}    
	
	public void sendParameters() throws NumberFormatException, IOException {
		 
		String[] key = enterKeyField.getText().split("\\+");
		
		if(key.length == 3){
			String[] parameters = {key[0], key[1], key[2], encryptedBalance};
			Paillier_Verifier.main(parameters);
		} else {
			JOptionPane.showMessageDialog(frame, "Key invalid.  Please enter a valid key.");
		}
	}  
	  
	public static void sendResult(String decryptedValue, String finalResult){
		decryptedBalanceValue.setText(decryptedValue);
		decryptedResultValue.setText(finalResult);
		enterKeyField.setText("");
	}  
	 
	public static void main(String[] args) {
		
		SwingApp encryptionApp = new SwingApp("eVERIFY", args[0], args[1], args[2], args[4]);
		encryptionApp.initializeEverifyApp();
		
	} 
 
}
