import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Processor {
	private InstructionSet instructionSet;
	private Memory memory;
	private InstructionDecoder intructionDecoder;
	private LinkedList<String> errorMessages;
	private Map<Register, String> registerValues;
	private Map<IndexRegister, String> indexRegisterValues;
	private FrontPanel frontPanel;
	private Boolean isEnd;
	private Boolean isStart;
	public Boolean insertRequired;
	private int[] conditionCode;
	

public Processor(FrontPanel _frontPanel)
{
	setInstructionSet(this.CreateInstructionsSet());	
	setIntructionDecoder(new InstructionDecoder(this.getInstructionSet()));
	setRegisterValues(new HashMap<Register,String>());
	setIndexRegisterValues(new HashMap<IndexRegister,String>());
	setMemory(new Memory(this));
	setFrontPanel(_frontPanel);
	LoadDefaultRegisterValues();
	this.isStart = false;
	this.isEnd = false;
	this.insertRequired = false;
	setConditionCode(new int[4]);
}

public InstructionSet getInstructionSet() {
	return instructionSet;
}

private void setInstructionSet(InstructionSet instructionSet) {
	this.instructionSet = instructionSet;
}

public Memory getMemory() {
	return memory;
}

private void setMemory(Memory memory) {
	this.memory = memory;
}

public InstructionDecoder getIntructionDecoder() {
	return intructionDecoder;
}

private void setIntructionDecoder(InstructionDecoder intructionDecoder) {
	this.intructionDecoder = intructionDecoder;
}

public LinkedList<String> getErrorMessages() {
	return errorMessages;
}

public void setErrorMessages(LinkedList<String> errorMessages) {
	this.errorMessages = errorMessages;
}

public Map<Register, String> getRegisterValues() {
	return registerValues;
}

private void setRegisterValues(Map<Register, String> registerValues) {
	this.registerValues = registerValues;
}

public Map<IndexRegister, String> getIndexRegisterValues() {
	return indexRegisterValues;
}

public void setIndexRegisterValues(Map<IndexRegister, String> indexRegisterValues) {
	this.indexRegisterValues = indexRegisterValues;
}

public FrontPanel getFrontPanel() {
	return frontPanel;
}

public void setFrontPanel(FrontPanel frontPanel) {
	this.frontPanel = frontPanel;
}

public int[] getConditionCode() {
	return conditionCode;
}

public void setConditionCode(int[] conditionCode) {
	this.conditionCode = conditionCode;
}

/*
private InstructionSet CreateInstructionsSet()
{
	InstructionSet tempt = new InstructionSet();
	temp.Add("MOVRA"); // 001
	temp.Add("MOV");	// 002
	temp.Add("ADD");	// 003
	temp.Add("AMR"); 	// Opcode 004 
	temp.Add("SMR"); 	// Opcode 005
	temp.Add("AIR"); 	// Opcode 006
	temp.Add("SIR"); 	// Opcode 007
	temp.Add("MOVB"); 	// 008
	temp.Add("END");	// 009
	return tempt;
	
	}
	*/

private InstructionSet CreateInstructionsSet()
{
	InstructionSet temp = new InstructionSet();
	temp.Add(1, "LDR");
	temp.Add(2, "STR");
	temp.Add(3, "LDA");
	temp.Add(4, "AMR");
	temp.Add(5, "SMR");
	temp.Add(6, "AIR");
	temp.Add(7, "SIR");
	temp.Add(10, "JZ");
	temp.Add(11, "JNE");
	temp.Add(12, "JCC");
	temp.Add(13, "JMA");
	temp.Add(14, "JSR");
	temp.Add(15, "RFS");
	temp.Add(16, "SOB");
	temp.Add(17, "JGE");
	temp.Add(20, "MLT");
	temp.Add(21, "DVD");
	temp.Add(22, "TRR");
	temp.Add(23, "AND");
	temp.Add(24, "ORR");
	temp.Add(25, "NOT");
	temp.Add(26, "STT");
	temp.Add(27, "END");
	temp.Add(31, "SRC");
	temp.Add(32, "RRC");
	temp.Add(41, "LDX");
	temp.Add(42, "STX");
	temp.Add(61, "IN");
	temp.Add(62, "OUT");
	temp.Add(63, "CHK");
	return temp;
	
	}

/*	
public Memory LoadProgramOneIntoMemory()
{
	
	Memory tempt = new Memory();
	
	temp.Insert(0b00001000000111);	// 	MOVRA	Reg 0	Address 1
	temp.Insert(0b00001010001000);	//	MovRA	Reg 1	Address 2
	temp.Insert(0b00010000000000);	//	Mov	Acc	Reg 0
	temp.Insert(0b00011010000000);	//	Add	Reg 1
	temp.Insert(0b01001000000000);
	temp.Insert(0b01000100000000);		
	temp.Insert(0b0000000000000010); // Integer value 2
	temp.Insert(0b0000000000000011); // Integer value 3
	setMemory(tempt);
	LoadProgramCounter();
	
	return tempt;	
	}
	

private void LoadProgramCounter() {
	String defaultPC = registerValues.get(Register.PC);
	registerValues.put(Register.IR, this.getMemory().Read(defaultPC));
}


public Memory LoadProgramTwoIntoMemory()
{	
	Memory tempt = new Memory();
	
	temp.Insert(0b00100000000011);	//	AMR	Reg0 Address1
	temp.Insert(0b01001000000000);	// 	END
	temp.Insert(0b00000000000010);	//	numeric value(2) in binary- 2
	setMemory(tempt);
	LoadProgramCounter();
	
	return tempt;
	
	}
	*/

public Boolean ValidBinaryString(String str)
{
	if(str.length()==16)
	{
		for(int i =0; i<str.length();i++)
		{
			if(str.charAt(i) == '0' || str.charAt(i) == '1' )
			{
				return true;
			}
		}
	}
	
	return false;
}


private void LoadDefaultRegisterValues()
{	
	String defaultV = "0000000000000000";
	String defaultPC = "0000000000000001";
	registerValues.put(Register.REGISTER_01,defaultV);
	registerValues.put(Register.REGISTER_02, defaultV);
	registerValues.put(Register.REGISTER_03,defaultV);
	registerValues.put(Register.REGISTER_04, defaultV);
	registerValues.put(Register.MAR,defaultV);
	registerValues.put(Register.MDR, defaultV);
	registerValues.put(Register.PC, defaultPC);
	registerValues.put(Register.ACC, defaultV);
	indexRegisterValues.put(IndexRegister.NoIndexing, defaultV);
	indexRegisterValues.put(IndexRegister.REGISTER_01, defaultV);
	indexRegisterValues.put(IndexRegister.REGISTER_02, defaultV);
	indexRegisterValues.put(IndexRegister.REGISTER_03, defaultV);	
}


public LinkedList<InstructionFormat> ReadInstructionsFromMemory()
{	
	LinkedList<InstructionFormat> instructions = new LinkedList<InstructionFormat>();
	InstructionFormat temp;
	String pc;
	boolean userInput = false;
	// ArthematicLogicalUnit ALU = new ArthematicLogicalUnit(this);
	LoadDefaultRegisterValues();
	
	while(!isEnd)
	{
		pc = this.registerValues.get(Register.PC);
		temp = this.getIntructionDecoder().ReadInstruction(this.getMemory().Read(pc));
		
		if(this.isStart)
		{
			userInput = this.ExecuteInstruction();
			if(userInput)
			{
				break;
			}
		}
		
		if(temp!=null && temp.InstructionName.equals("STT"))
		{
			frontPanel.consolePrinter.append("\n Program Start found");
			this.isStart = true;			
		}
		/*else if(!this.isStart)
		{
			this.IncreamentProgramCounter();
		}*/
		
		if(temp!=null && temp.InstructionName.equals("END"))
		{
			this.isEnd = true;
		}
		
		/*if(temp!=null&&isStart)
		{
			instructions.add(temp);
		}
		else
		{		
			ALU.Process(temp);
		}
		*/
		if(!userInput)
		this.IncreamentProgramCounter();
		frontPanel.LoadRegisterValues();
		
	}
	
	return instructions;
}

public Boolean ExecuteInstruction()
{
	String instructionToExecute = this.getMemory().Read(this.getRegisterValues().get(Register.PC));	
	int updatePC = (Integer.parseInt((this.getRegisterValues().get(Register.PC)),2)) + 1;
	String nextInstruction = String.format("%16s", Integer.toBinaryString(updatePC)).replace(' ', '0');
	registerValues.replace(Register.IR,this.getMemory().Read(nextInstruction));
	InstructionFormat format = this.getIntructionDecoder().ReadInstruction(instructionToExecute);
	String str = new String();
	str = "\n" + format.InstructionName;
	frontPanel.consolePrinter.append(str);
	ArthematicLogicalUnit ALU = new ArthematicLogicalUnit(this);
	return (ALU.Process(format));
}

public String IncreamentProgramCounter()
{
	int updatePC = (Integer.parseInt((this.getRegisterValues().get(Register.PC)),2)) + 1;
	String updatedBinaryPC = String.format("%16s", Integer.toBinaryString(updatePC)).replace(' ', '0');
	registerValues.replace(Register.PC, updatedBinaryPC);
	return updatedBinaryPC;
}

public void InsertNumberInMemory(int address, int number)
{
	this.getMemory().Insert(String.format("%16s", Integer.toBinaryString(number)).replace(' ', '0'),String.format("%16s", Integer.toBinaryString(number)).replace(' ', '0'));
}

public void Reset()
{
	this.LoadDefaultRegisterValues();
}
}


