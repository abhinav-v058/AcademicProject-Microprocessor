import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLayeredPane;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import java.awt.ScrollPane;

public class FrontPanel {

	private JFrame frame;
	private JTextField IR;
	private JTextField PC;
	private JTextField MAR;
	private JTextField MDR;
	private JTextField Reg1;
	private JTextField Reg2;
	private JTextField Reg3;
	private JTextField Reg4;
	private RegisterGUI register1;
	private RegisterGUI register2;
	private RegisterGUI register3;
	private RegisterGUI register4;
	private RegisterGUI mAR;
	private RegisterGUI mDR;
	private RegisterGUI aCC;
	private RegisterGUI pC;
	private RegisterGUI iR;
	private RegisterGUI indexReg1;
	private RegisterGUI indexReg2;
	private RegisterGUI indexReg3;	
	private Processor processor;
	public JTextArea consolePrinter;
	private JTextField ACC;
	private JTextField LoadRegisterValue;
	private JComboBox SelectedLoadRegister;
	private JButton btnLoadProgram;
	private JButton btnLoadProgram_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton LoadRegisterButton;
	private JButton btnEnterNumber;
	public JTextField MachineMsg;
	private Boolean buttonEnabled;
	private int counter;
	private int remaining;
	private static int numbersToBeEntered = 20;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JTextField iReg1;
	private JTextField iReg2;
	private JTextField iReg3;
	private JScrollPane scrollPane;
	public Boolean instructionFromProcessor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrontPanel window = new FrontPanel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrontPanel() {
		initialize();		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 2769, 1371);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
		JLayeredPane layeredPane = new JLayeredPane();
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);
		
		btnNewButton = new JButton("IPL");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.buttonEnabled = true;
		btnNewButton.setEnabled(buttonEnabled);		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				LoadRegisterValues();
			}
		});
		btnNewButton.setBounds(2230, 936, 205, 87);
		layeredPane.add(btnNewButton);
		
		consolePrinter = new JTextArea();
		consolePrinter.setEditable(false);
		consolePrinter.setFont(new Font("Monospaced", Font.PLAIN, 25));
		consolePrinter.setLineWrap(true);
		consolePrinter.setBounds(2126, 87, 596, 627);
		layeredPane.add(consolePrinter);
		
		processor = new Processor(this);		
		
		btnNewButton_1 = new JButton("Single Step");
		btnNewButton_1.setEnabled(buttonEnabled);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			processor.ExecuteInstruction();
			LoadRegisterValues();
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnNewButton_1.setBounds(2517, 1085, 205, 93);
		layeredPane.add(btnNewButton_1);
		
		JLabel consolePrinterText = new JLabel("Console Printer");
		consolePrinterText.setFont(new Font("Tahoma", Font.PLAIN, 30));
		consolePrinterText.setBounds(2230, 45, 218, 37);
		layeredPane.add(consolePrinterText);
		
		IR = new JTextField();
		IR.setEditable(false);
		IR.setFont(new Font("Tahoma", Font.PLAIN, 20));
		IR.setBounds(1515, 102, 230, 87);		
		layeredPane.add(IR);
		IR.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Instruction Register");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(1519, 50, 360, 26);
		layeredPane.add(lblNewLabel_1);
		
		PC = new JTextField();
		PC.setEditable(false);
		PC.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PC.setBounds(866, 102, 230, 87);
		layeredPane.add(PC);
		PC.setColumns(10);
		
		JLabel lblProgramCounter = new JLabel("Program Counter");
		lblProgramCounter.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblProgramCounter.setBounds(864, 45, 298, 37);
		layeredPane.add(lblProgramCounter);
		
		MAR = new JTextField();
		MAR.setEditable(false);
		MAR.setFont(new Font("Tahoma", Font.PLAIN, 20));
		MAR.setColumns(10);
		MAR.setBounds(866, 236, 230, 87);
		layeredPane.add(MAR);
		
		JLabel lblMemoryAddressRegister = new JLabel("Memory Address Register");
		lblMemoryAddressRegister.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblMemoryAddressRegister.setBounds(866, 200, 405, 37);
		layeredPane.add(lblMemoryAddressRegister);
		
		MDR = new JTextField();
		MDR.setEditable(false);
		MDR.setFont(new Font("Tahoma", Font.PLAIN, 20));
		MDR.setColumns(10);
		MDR.setBounds(866, 402, 230, 87);
		layeredPane.add(MDR);
		
		JLabel lblMemoryDataRegister = new JLabel("Memory Data Register");
		lblMemoryDataRegister.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblMemoryDataRegister.setBounds(866, 365, 405, 37);
		layeredPane.add(lblMemoryDataRegister);
		
		Reg1 = new JTextField();
		Reg1.setEditable(false);
		Reg1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Reg1.setColumns(10);
		Reg1.setBounds(83, 92, 230, 87);
		layeredPane.add(Reg1);
		
		JLabel lblR = new JLabel("R0");
		lblR.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblR.setBounds(83, 45, 298, 37);
		layeredPane.add(lblR);
		
		Reg2 = new JTextField();
		Reg2.setEditable(false);
		Reg2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Reg2.setColumns(10);
		Reg2.setBounds(83, 236, 230, 87);
		layeredPane.add(Reg2);
		
		JLabel lblR_1 = new JLabel("R1");
		lblR_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblR_1.setBounds(83, 200, 405, 37);
		layeredPane.add(lblR_1);
		
		Reg3 = new JTextField();
		Reg3.setEditable(false);
		Reg3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Reg3.setColumns(10);
		Reg3.setBounds(83, 402, 230, 87);
		layeredPane.add(Reg3);
		
		JLabel lblR_2 = new JLabel("R2");
		lblR_2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblR_2.setBounds(83, 365, 405, 37);
		layeredPane.add(lblR_2);
		
		Reg4 = new JTextField();
		Reg4.setEditable(false);
		Reg4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Reg4.setColumns(10);
		Reg4.setBounds(83, 577, 230, 87);
		layeredPane.add(Reg4);
		
		JLabel lblR_3 = new JLabel("R3");
		lblR_3.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblR_3.setBounds(83, 538, 405, 37);
		layeredPane.add(lblR_3);
		
		ACC = new JTextField();
		ACC.setEditable(false);
		ACC.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ACC.setColumns(10);
		ACC.setBounds(866, 577, 230, 87);
		layeredPane.add(ACC);
		
		JLabel lblAccumulator = new JLabel("Accumulator");
		lblAccumulator.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblAccumulator.setBounds(866, 542, 405, 37);
		layeredPane.add(lblAccumulator);
		
		LoadRegisterValue = new JTextField();
		LoadRegisterValue.setFont(new Font("Tahoma", Font.PLAIN, 30));
		LoadRegisterValue.setColumns(10);
		LoadRegisterValue.setBounds(531, 920, 1025, 87);
		layeredPane.add(LoadRegisterValue);
		
		JLabel lblLoadRegister = new JLabel("Load Register");
		lblLoadRegister.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblLoadRegister.setBounds(83, 754, 405, 37);
		layeredPane.add(lblLoadRegister);
		
		LoadRegisterButton = new JButton("Load Register");
		LoadRegisterButton.setEnabled(buttonEnabled);
		LoadRegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String registerValue= LoadRegisterValue.getText();
				if(processor.ValidBinaryString(registerValue))
				{
					String selectedReg = (String) SelectedLoadRegister.getSelectedItem();
					switch(selectedReg)
					{
					case "Register 0":
						processor.getRegisterValues().replace(Register.REGISTER_01, registerValue);
						break;
					case "Register 1":
						processor.getRegisterValues().replace(Register.REGISTER_02, registerValue);
						break;
					case "Register 2":
						processor.getRegisterValues().replace(Register.REGISTER_03, registerValue);
						break;
					case "Register 3":
						processor.getRegisterValues().replace(Register.REGISTER_04, registerValue);
						break;
					}
				}
				
				LoadRegisterValues();
			}
		});
		LoadRegisterButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
		LoadRegisterButton.setBounds(83, 917, 330, 93);
		layeredPane.add(LoadRegisterButton);
		
		SelectedLoadRegister = new JComboBox();
		SelectedLoadRegister.setFont(new Font("Tahoma", Font.PLAIN, 30));
		SelectedLoadRegister.setEditable(true);
		SelectedLoadRegister.setBounds(83, 807, 330, 76);
		SelectedLoadRegister.addItem(new String("Register 0"));
		SelectedLoadRegister.addItem(new String("Register 1"));
		SelectedLoadRegister.addItem(new String("Register 2"));
		SelectedLoadRegister.addItem(new String("Register 3"));
		
		layeredPane.add(SelectedLoadRegister);
		
		btnLoadProgram = new JButton("Execute Prog.");
		btnLoadProgram.setEnabled(true);
		btnLoadProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
				
				consolePrinter.append("\nProgram Executing");
				// processor.LoadProgramTwoIntoMemory();
				LoadRegisterValues();
				processor.ReadInstructionsFromMemory();
			}
		});
		btnLoadProgram.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnLoadProgram.setBounds(2230, 1085, 205, 93);
		layeredPane.add(btnLoadProgram);
		
		btnLoadProgram_1 = new JButton("Enter Numbers");
		btnLoadProgram_1.setEnabled(buttonEnabled);
		btnLoadProgram_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text;
				text = "Enter 20 numbers into the machine input textbox in decimal system";
							
				consolePrinter.setText(text);
				SetButtonEnabled(false);
				counter = 1;
				remaining = numbersToBeEntered;
				MachineMsg.setText(String.format("Enter %d numbers; Enter a number below and press enter",remaining)); 
				// processor.LoadProgramOneIntoMemory();
				LoadRegisterValues();
			}
		});
		btnLoadProgram_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnLoadProgram_1.setBounds(2517, 931, 205, 93);
		layeredPane.add(btnLoadProgram_1);
		
		MachineMsg = new JTextField();
		MachineMsg.setFont(new Font("Tahoma", Font.PLAIN, 30));
		MachineMsg.setEditable(false);
		MachineMsg.setColumns(10);
		MachineMsg.setBounds(531, 796, 1025, 87);
		layeredPane.add(MachineMsg);
		
		JLabel lblMessage = new JLabel("Machine Message");
		lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblMessage.setBounds(528, 748, 405, 37);
		layeredPane.add(lblMessage);
		
		JLabel lblEnterValueInto = new JLabel("Enter Value into machine");
		lblEnterValueInto.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblEnterValueInto.setBounds(531, 881, 405, 37);
		layeredPane.add(lblEnterValueInto);
		
		btnEnterNumber = new JButton("Enter");
		btnEnterNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				if(instructionFromProcessor)
				{
					String temp = LoadRegisterValue.getText();
					boolean isBreak = false;
					if(temp.length()<16)
					{
						for(int i = 0; i<temp.length();i++)
						{
							if(Character.isWhitespace(temp.charAt(i)))
							{								
								consolePrinter.append("\n Invalid Input. Enter only a word. Program Terminated.");
								System.out.println("\n Invalid Input. Enter only a word. Program Terminated.");
								SetButtonEnabled(true);
								processor.Reset();
								isBreak = true;
								break;
							}							
						}
						
						if(!isBreak)
						{
							String instructionToExecute = processor.getMemory().Read(processor.getRegisterValues().get(Register.PC));
							InstructionFormat instruction = processor.getIntructionDecoder().ReadInstruction(instructionToExecute);
							Register reg = instruction.Registers.getFirst();
							int i = Character.getNumericValue(temp.charAt(0));
							processor.getRegisterValues().replace(reg,String.format("%20s", Integer.toBinaryString(i)).replace(' ', '0'));
							processor.IncreamentProgramCounter();
							SetButtonEnabled(true);
							processor.ReadInstructionsFromMemory();							
						}
						
					}
				}
				else
				{
					if(remaining>0)
					{
						String temp = LoadRegisterValue.getText();
						int number = Integer.parseInt(temp);
						processor.InsertNumberInMemory(83+counter, number);				
						counter++;
						remaining = remaining-counter;
						MachineMsg.setText(String.format("Enter %d numbers; Enter a number below and press enter",remaining+1));
						LoadRegisterValue.setText("");				
					}
					else
					{
						if(counter == numbersToBeEntered)
						{
							MachineMsg.setText("Enter number to be searched and press enter:");
							LoadRegisterValue.setText("");
							counter ++;
						}
						else if(counter==numbersToBeEntered+1)
						{
							String temp = LoadRegisterValue.getText();
							int number = Integer.parseInt(temp);
							processor.InsertNumberInMemory(83+counter, number);
							counter++;
							LoadRegisterValue.setText("");
							MachineMsg.setText("Numbers were entered into the memory, now Execute Program 1");
							SetButtonEnabled(true);
							btnLoadProgram.setEnabled(true);
						}
					}
				}
			}
		});
		btnEnterNumber.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnEnterNumber.setEnabled(true);
		btnEnterNumber.setBounds(531, 1049, 305, 93);
		layeredPane.add(btnEnterNumber);
		
		 register1 = new RegisterGUI();
		register1.AddToLayeredPane(318, 118, layeredPane);
		
		 register2 = new RegisterGUI();
		register2.AddToLayeredPane(318, 280, layeredPane);
		
		 register3 = new RegisterGUI();
		register3.AddToLayeredPane(318, 433, layeredPane);
		
		 register4 = new RegisterGUI();
		register4.AddToLayeredPane(318, 583, layeredPane);
		
		 pC = new RegisterGUI();
		pC.AddToLayeredPane(1107, 132, layeredPane);
		
		 mAR = new RegisterGUI();
		mAR.AddToLayeredPane(1107, 260, layeredPane);
		
		 mDR = new RegisterGUI();
		mDR.AddToLayeredPane(1107, 428, layeredPane);
		
		 aCC = new RegisterGUI();
		aCC.AddToLayeredPane(1107, 600, layeredPane);
		
		iR = new RegisterGUI();
		iR.AddToLayeredPane(1762, 128, layeredPane);
		
		indexReg1 = new RegisterGUI();
		indexReg1.AddToLayeredPane(1762, 254, layeredPane);
		
		indexReg2 = new RegisterGUI();
		indexReg2.AddToLayeredPane(1762, 420, layeredPane);
		
		indexReg3 = new RegisterGUI();
		indexReg3.AddToLayeredPane(1762, 606, layeredPane);
		
		
		iReg1 = new JTextField();
		iReg1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		iReg1.setEditable(false);
		iReg1.setColumns(10);
		iReg1.setBounds(1515, 236, 230, 87);
		layeredPane.add(iReg1);
		
		JLabel lblIndexRegister = new JLabel("Index Register 1");
		lblIndexRegister.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblIndexRegister.setBounds(1515, 200, 405, 37);
		layeredPane.add(lblIndexRegister);
		
		JLabel lblIndexRegister_1 = new JLabel("Index Register 2");
		lblIndexRegister_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblIndexRegister_1.setBounds(1515, 366, 405, 37);
		layeredPane.add(lblIndexRegister_1);
		
		iReg2 = new JTextField();
		iReg2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		iReg2.setEditable(false);
		iReg2.setColumns(10);
		iReg2.setBounds(1515, 402, 230, 87);
		layeredPane.add(iReg2);
		
		JLabel lblIndexRegister_2 = new JLabel("Index Register 3");
		lblIndexRegister_2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblIndexRegister_2.setBounds(1515, 541, 405, 37);
		layeredPane.add(lblIndexRegister_2);
		
		iReg3 = new JTextField();
		iReg3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		iReg3.setEditable(false);
		iReg3.setColumns(10);
		iReg3.setBounds(1515, 577, 230, 87);
		layeredPane.add(iReg3);	
		
		JButton loadFile = new JButton("Load File");
		loadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        fileChooser.setAcceptAllFileFilterUsed(false);
		 
		        int rVal = fileChooser.showOpenDialog(null);
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		        	readFile(fileChooser.getSelectedFile().toString());
		        }
			}
		});
		loadFile.setFont(new Font("Tahoma", Font.PLAIN, 25));
		loadFile.setEnabled(true);
		loadFile.setBounds(2517, 807, 205, 93);
		layeredPane.add(loadFile);		
		
		scrollPane = new JScrollPane(consolePrinter);
		scrollPane.setBounds(2126, 79, 607, 656);
		layeredPane.add(scrollPane);
		
		
		/*
		checkBox = new JCheckBox("");
		checkBox.setBounds(443, 118, 179, 35);
		layeredPane.add(checkBox);
		
		JCheckBox checkBox = new JCheckBox("");
		JCheckBox checkBox2 = new JCheckBox("");
		checkBox.setBounds(83, 184, 35, 35);
		checkBox2.setBounds(234, 189, 35, 35);
		layeredPane.add(checkBox);
		layeredPane.add(checkBox2);
		*/
			}
	
	private void readFile(String fName) {
		FileInputStream fs = null;
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null;
		BufferedReader dumpReader = null;
		int addrCount = 1, linecount =0;
		try {
		fs = new FileInputStream(fName);
		baos = new ByteArrayOutputStream();
		dos = new DataOutputStream(baos);
		Reader reader = new Reader(this.processor);
		String data;
		dumpReader = new BufferedReader(new FileReader(fName));
		System.out.println("Reading!!");
		while ((data = dumpReader.readLine()) != null) {
		System.out.println(data);
		if(data.length()>16)
		{
			System.out.println("Invalid File- Too many characters");
			processor.getFrontPanel().consolePrinter.setText("Invalid File- Too many characters");
			fs.close();
			baos.close();
			dos.close();
			dumpReader.close();
			throw new Exception();
		}
		
		// processor.getMemory().InsertDirect(data, String.format("%20s", Integer.toBinaryString(addrCount++)).replace(' ', '0'));
		processor.getFrontPanel().consolePrinter.append(data + "\n");
		addrCount = reader.ConvertAndStore(data, addrCount);
		linecount++;
		}
		System.out.println("Read ends here");
		fs.close();
		baos.close();
		dos.close();
		dumpReader.close();

		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	
	public void SetButtonEnabled(Boolean isEnable)
	{		
		btnLoadProgram.setEnabled(isEnable);
		btnLoadProgram_1.setEnabled(isEnable);
		btnNewButton.setEnabled(isEnable);
		btnNewButton_1.setEnabled(isEnable);
		LoadRegisterButton.setEnabled(isEnable);
		btnEnterNumber.setEnabled(!isEnable);		
	}
	
	public void LoadRegisterValues()
	{
		// System.out.println("Msg: Update all registers");
		// consolePrinter.append("\nMsg: Update all registers \n");
		MAR.setText(processor.getRegisterValues().get(Register.MAR));
		mAR.setText(processor.getRegisterValues().get(Register.MAR));
		MDR.setText(processor.getRegisterValues().get(Register.MDR));
		mDR.setText(processor.getRegisterValues().get(Register.MDR));
		PC.setText(processor.getRegisterValues().get(Register.PC));
		pC.setText(processor.getRegisterValues().get(Register.PC));
		Reg1.setText(processor.getRegisterValues().get(Register.REGISTER_01));
		register1.setText(processor.getRegisterValues().get(Register.REGISTER_01));
		Reg2.setText(processor.getRegisterValues().get(Register.REGISTER_02));
		register2.setText(processor.getRegisterValues().get(Register.REGISTER_02));
		Reg3.setText(processor.getRegisterValues().get(Register.REGISTER_03));
		register3.setText(processor.getRegisterValues().get(Register.REGISTER_03));
		Reg4.setText(processor.getRegisterValues().get(Register.REGISTER_04));
		register4.setText(processor.getRegisterValues().get(Register.REGISTER_04));
		ACC.setText(processor.getRegisterValues().get(Register.ACC));
		aCC.setText(processor.getRegisterValues().get(Register.ACC));		
		iReg1.setText(processor.getIndexRegisterValues().get(IndexRegister.REGISTER_01));
		indexReg1.setText(processor.getIndexRegisterValues().get(IndexRegister.REGISTER_01));
		iReg2.setText(processor.getIndexRegisterValues().get(IndexRegister.REGISTER_02));
		indexReg2.setText(processor.getIndexRegisterValues().get(IndexRegister.REGISTER_02));
		iReg3.setText(processor.getIndexRegisterValues().get(IndexRegister.REGISTER_03));
		indexReg3.setText(processor.getIndexRegisterValues().get(IndexRegister.REGISTER_03));
	}
}

