import java.util.LinkedList;

public class InstructionFormat {
	public String InstructionName;
	public LinkedList<Register> Registers;
	public LinkedList<String> Addresses;
	public LinkedList<String> Immediates;
	public LinkedList<IndexRegister> IndexRegisters;
	public Boolean Accumulator;
	public Boolean IndirectAddressing;
	public int ConditionCodeBit;
	
	
	public InstructionFormat()
	{
		Registers = new LinkedList<Register>();
		IndexRegisters = new LinkedList<IndexRegister>();
		Addresses = new LinkedList<String>();
		Immediates = new LinkedList<String>();
	}
}
