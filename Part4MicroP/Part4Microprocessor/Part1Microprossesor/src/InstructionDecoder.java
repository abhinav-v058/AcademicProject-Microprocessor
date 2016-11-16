
public class InstructionDecoder {
	
	private InstructionSet instructionSet;
	
	public InstructionDecoder(InstructionSet _instructionSet)
	{
		instructionSet = _instructionSet;
	}
	
	public InstructionFormat ReadInstruction(String binaryInstruction)
	{
		InstructionFormat format= new InstructionFormat();
		String opcode = binaryInstruction.substring(4, 10);
		Instruction instruction = instructionSet.GetInstruction(Integer.parseInt(opcode,2));
		
		if(instruction !=null)
		{
			format.InstructionName = instruction.InstructionName;
			switch(format.InstructionName)
			{
				case "MOVRA":
				case "AMR":
				case "SMR":
					format.Registers.add(this.GetRegister(binaryInstruction.substring(10,12)));
					format.Addresses.add(binaryInstruction.substring(15,19));
					break;
				case "MOV":					
				case "ADD":					
				case "MOVB":
				case "IN":
					format.Registers.add(this.GetRegister(binaryInstruction.substring(10,12)));
					break;
				case "AIR":
				case "SIR":
					format.Registers.add(this.GetRegister(binaryInstruction.substring(10,12)));
					format.Immediates.add(binaryInstruction.substring(15,19));
					break;
				case "LDR":
				case "STR":
				case "LDA":
					format.IndexRegisters.add(this.GetIndexRegister(binaryInstruction.substring(12,14)));
					format.Registers.add(this.GetRegister(binaryInstruction.substring(10,12)));
					format.IndirectAddressing = (binaryInstruction.charAt(14) == '1')?true:false;
					format.Addresses.add(binaryInstruction.substring(15,19));
					break;
				case "JCC":
					format.IndexRegisters.add(this.GetIndexRegister(binaryInstruction.substring(12,14)));
					format.ConditionCodeBit = Integer.parseInt(binaryInstruction.substring(14,15),2);
					format.IndirectAddressing = (binaryInstruction.charAt(15) == '1')?true:false;
					format.Addresses.add(binaryInstruction.substring(15,19));					
					break;
				case "TRR":
					format.Registers.add(this.GetRegister(binaryInstruction.substring(10,12)));
					format.Registers.add(this.GetRegister(binaryInstruction.substring(12,14)));
				break;
			}
			
			return format;
		}
		
		return null; 
	}
	
	private Register GetRegister(String  reg)
	{
		switch(reg)
		{
		case "00":
			return Register.REGISTER_01;
		case "01":
			return Register.REGISTER_02;
		case "10":
			return Register.REGISTER_03;
		case "11":
			return Register.REGISTER_04;
		/*case "100":
			return Register.MAR;
		case "101":
			return Register.MDR;
		case "110":
			return Register.TempReg;
		case "111":
			return Register.ACC;
			*/
		default:
			return null;
		}
	}
	
	private IndexRegister GetIndexRegister(String  reg)
	{
		switch(reg)
		{
		case "00":
			return IndexRegister.NoIndexing;
		case "01":
			return IndexRegister.REGISTER_01;
		case "10":
			return IndexRegister.REGISTER_02;
		case "11":
			return IndexRegister.REGISTER_03;		
		default:
			return null;
		}
	}

}
