import java.util.LinkedList;

public class ArthematicLogicalUnit {
	
	Processor processor;
	Accumulator accumulator;
	Memory memory;
	
	public ArthematicLogicalUnit(Processor _processor)
	{
		processor = _processor;
		memory = _processor.getMemory();
		accumulator = new Accumulator();
	}

	public Boolean Process(InstructionFormat instruction)
	{
		Register reg;
		 String temp;
		 String acc;
		 String effectiveAddress;
		 IndexRegister index;
		 Boolean insertRequired = false;
		 
		switch(instruction.InstructionName)
		{
		case "MOVRA":
			 reg = instruction.Registers.getFirst();
			 String add1 = instruction.Addresses.getFirst();
			 processor.getRegisterValues().replace(Register.MAR, String.format("%16s", add1).replace(' ', '0'));
			 temp = processor.getMemory().Read(add1);
			 processor.getRegisterValues().replace(reg,temp);
			 processor.getRegisterValues().replace(Register.MDR,temp);
			 break;
		case "MOV":
			reg = instruction.Registers.getFirst();
			processor.getRegisterValues().replace(Register.ACC,processor.getRegisterValues().get(reg));
			break;
		case "ADD":
			acc = processor.getRegisterValues().get(Register.ACC);
			reg = instruction.Registers.getFirst();
			temp = processor.getRegisterValues().get(reg);
			int sum = Integer.parseInt(acc,2) + Integer.parseInt(temp,2);
			acc = String.format("%16s", Integer.toBinaryString(sum)).replace(' ', '0');
			processor.getRegisterValues().replace(Register.ACC,acc);
			break;
		case "SUB":
			acc = processor.getRegisterValues().get(Register.ACC);
			reg = instruction.Registers.getFirst();
			temp = processor.getRegisterValues().get(reg);
			int difference = Integer.parseInt(acc,2) - Integer.parseInt(temp,2);
			acc = String.format("%16s", Integer.toBinaryString(difference)).replace(' ', '0');
			processor.getRegisterValues().replace(Register.ACC,acc);
			break;
		case "MOVB":
			acc = processor.getRegisterValues().get(Register.ACC);
			processor.getRegisterValues().replace(instruction.Registers.getFirst(),acc);
			break;
		case "AMR":
			LinkedList<InstructionFormat> AMRInstructions = new LinkedList<InstructionFormat>();
			LinkedList<Register> moveToMDR = new LinkedList<Register>();
			moveToMDR.add(Register.MDR);
			AMRInstructions.add(moveAddressValueToRegister(instruction.Addresses, moveToMDR));			
			AMRInstructions.add(MoveRegToAccumulator(instruction));			
			AMRInstructions.add(add(moveToMDR));			
			AMRInstructions.add(moveBackToReg(instruction));
			this.ExecuteComplexInstructions(AMRInstructions);
			break;
		case "SMR":
			LinkedList<InstructionFormat> SMRInstructions = new LinkedList<InstructionFormat>();			
			SMRInstructions.add(moveAddressValueToRegister(instruction.Addresses,instruction.Registers));			
			SMRInstructions.add(MoveRegToAccumulator(instruction));			
			SMRInstructions.add(subtract(instruction));			
			SMRInstructions.add(moveBackToReg(instruction));
			this.ExecuteComplexInstructions(SMRInstructions);
			break;
		case "AIR":
			LinkedList<InstructionFormat> AIRInstructions = new LinkedList<InstructionFormat>();	
			AIRInstructions.add(MoveRegToAccumulator(instruction));
			AIRInstructions.add(moveAddressValueToRegister(instruction.Addresses,instruction.Registers));
			AIRInstructions.add(add(instruction.Registers));
			AIRInstructions.add(moveBackToReg(instruction));
			this.ExecuteComplexInstructions(AIRInstructions);
			break;
		case "SIR":
			LinkedList<InstructionFormat> SIRInstructions = new LinkedList<InstructionFormat>();	
			SIRInstructions.add(MoveRegToAccumulator(instruction));
			SIRInstructions.add(moveAddressValueToRegister(instruction.Addresses,instruction.Registers));
			SIRInstructions.add(subtract(instruction));
			SIRInstructions.add(moveBackToReg(instruction));
			this.ExecuteComplexInstructions(SIRInstructions);
			break;
		case "END":
			processor.Reset();
			break;
		case "LDR":
			effectiveAddress = this.GetEffectiveAddress(instruction);
			processor.getRegisterValues().replace(Register.MAR, effectiveAddress);
			reg = instruction.Registers.getFirst();
			temp = processor.getMemory().Read(effectiveAddress);
			processor.getRegisterValues().replace(Register.MDR, temp);
			processor.getRegisterValues().replace(reg, temp);
			break;
		case "STR":
			temp = processor.getRegisterValues().get(instruction.Registers.getFirst());
			effectiveAddress = this.GetEffectiveAddress(instruction);
			memory.Insert(effectiveAddress, temp);
			break;
		case "LDA":
			effectiveAddress = this.GetEffectiveAddress(instruction);
			temp = memory.Read(effectiveAddress);
			processor.getRegisterValues().replace(Register.MAR, effectiveAddress);
			processor.getRegisterValues().replace(Register.MDR, temp);
			processor.getRegisterValues().replace(Register.ACC, temp);
			break;
		case "LDX":
			effectiveAddress = this.GetEffectiveAddress(instruction);
			temp = memory.Read(effectiveAddress);
			processor.getRegisterValues().replace(Register.MAR, effectiveAddress);
			processor.getRegisterValues().replace(Register.MDR, temp);
			index = instruction.IndexRegisters.getFirst();		
			processor.getIndexRegisterValues().replace(index, temp);
			break;
		case "STX":
			effectiveAddress = this.GetEffectiveAddress(instruction);
			index  = instruction.IndexRegisters.getFirst();
			temp = processor.getIndexRegisterValues().get(index);
			memory.Insert(effectiveAddress, temp);
			break;
		case "JZ":
			effectiveAddress = this.GetEffectiveAddress(instruction);
			reg = instruction.Registers.getFirst();
			temp = processor.getRegisterValues().get(reg);
			if(Integer.parseInt(temp,2) == 0)
			{
				if(!instruction.IndirectAddressing)
				{
					processor.getRegisterValues().replace(Register.PC, effectiveAddress);
				}
				else
				{
					processor.getRegisterValues().replace(Register.PC, memory.Read(effectiveAddress));
				}
			}
			
			break;
		case "JNE":
			effectiveAddress = this.GetEffectiveAddress(instruction);
			reg = instruction.Registers.getFirst();
			temp = processor.getRegisterValues().get(reg);
			if(Integer.parseInt(temp,2) != 0)
			{
				if(!instruction.IndirectAddressing)
				{
					processor.getRegisterValues().replace(Register.PC, effectiveAddress);
				}
				else
				{
					processor.getRegisterValues().replace(Register.PC, memory.Read(effectiveAddress));
				}
			}
			
			break;
		case "JCC":
			effectiveAddress = this.GetEffectiveAddress(instruction);
			temp = processor.getRegisterValues().get(Register.CC);
			if(temp.charAt(instruction.ConditionCodeBit) == '1')
			{
				if(!instruction.IndirectAddressing)
				{
					processor.getRegisterValues().replace(Register.PC, effectiveAddress);
				}
				else
				{
					processor.getRegisterValues().replace(Register.PC, memory.Read(effectiveAddress));
				}
			}
			
			break;
		case "JMA":
			effectiveAddress = this.GetEffectiveAddress(instruction);
			if(!instruction.IndirectAddressing)
			{
				processor.getRegisterValues().replace(Register.PC, effectiveAddress);
			}
			else
			{
				processor.getRegisterValues().replace(Register.PC, memory.Read(effectiveAddress));
			}
			break;
			
		case "HLT":
			processor.getRegisterValues().replace(Register.PC, "0");
			break;
		
		case "MLT":
			reg = instruction.Registers.getFirst();
			temp = processor.getRegisterValues().get(reg);			
			accumulator.operand1 = Integer.parseInt(temp,2);
			Register reg2 = instruction.Registers.getLast();
			accumulator.operand2 =  Integer.parseInt(processor.getRegisterValues().get(reg2));
			accumulator.Multiply();
			processor.getRegisterValues().replace(Register.ACC, String.format("%16s", Integer.toBinaryString(accumulator.result)).replace(' ', '0'));
			break;
			
		case "TRR":
			String value1 = processor.getRegisterValues().get(instruction.Registers.get(0));
			String value2 = processor.getRegisterValues().get(instruction.Registers.get(1));
			int[] tempCC = new int[4];
			tempCC = processor.getConditionCode();
			if(value1.equals(value2))
			{				
				tempCC[3] = 1;				
			}
			else
			{
				tempCC[3] = 0;				
			}
			
			processor.setConditionCode(tempCC);
			break;
		
		case "IN":
			FrontPanel frontPanel = processor.getFrontPanel();
			System.out.println("\nUser Input Required");
			frontPanel.SetButtonEnabled(false);
			frontPanel.instructionFromProcessor = true;
			frontPanel.MachineMsg.setText("Insert Value into Machine:");
			insertRequired = true;
			break;
		case "OUT":
			FrontPanel frontPanelw = processor.getFrontPanel();
			int sentenceNo = getSentenceNo(instruction);
			frontPanelw.consolePrinter.setText("Found word in Sentence"+ sentenceNo);
			break;
		}
		
		return insertRequired;
	}	

	private InstructionFormat add(LinkedList<Register> registers) {
		InstructionFormat add = new InstructionFormat();
		add.InstructionName ="ADD";
		add.Registers = registers;
		return add;
	}
	
	private InstructionFormat subtract(InstructionFormat instruction) {
		InstructionFormat add = new InstructionFormat();
		add.InstructionName ="SUB";
		add.Registers = instruction.Registers;
		return add;
	}

	private InstructionFormat moveBackToReg(InstructionFormat instruction) {
		InstructionFormat moveBackToReg = new InstructionFormat();
		moveBackToReg.InstructionName ="MOVB";
		moveBackToReg.Registers = instruction.Registers;
		return moveBackToReg;
	}

	private InstructionFormat MoveRegToAccumulator(InstructionFormat instruction) {
		InstructionFormat moveRegToAccumulator = new InstructionFormat();
		moveRegToAccumulator.InstructionName ="MOV";
		moveRegToAccumulator.Registers = instruction.Registers;
		return moveRegToAccumulator;
	}

	private InstructionFormat moveAddressValueToRegister(LinkedList<String> addresses, LinkedList<Register> registers) {
		InstructionFormat moveAddressToReg = new InstructionFormat();
		moveAddressToReg.InstructionName = "MOVRA";
		moveAddressToReg.Addresses = addresses;
		moveAddressToReg.Registers = registers;
		return moveAddressToReg;
	}
	
	public void ExecuteComplexInstructions(LinkedList<InstructionFormat> complexInstructions)
	{
		for(int i = 0;i < complexInstructions.size();i++)
		{
			this.Process(complexInstructions.get(i));
		}
	}
	
	private int getSentenceNo(InstructionFormat instruction)
	{
		switch(instruction.Registers.getFirst())
		{
		case REGISTER_01:
			return 0;
			
		case REGISTER_02:
			return 1;
			
		case REGISTER_03:
			return 2;
			
		case REGISTER_04:
			return 3;
		default:
			return 0;
			
		}
	}

	public String GetEffectiveAddress(InstructionFormat instruction)
	{
		if(instruction.IndirectAddressing)
		{
			if(instruction.IndexRegisters.equals(IndexRegister.NoIndexing))
			{
				return this.processor.getMemory().Read(instruction.Addresses.getFirst());
			}
			else
			{
				String cXj = this.processor.getMemory().Read(this.processor.getIndexRegisterValues().get(instruction.IndexRegisters.getFirst()));
				this.accumulator.operand1 = Integer.parseInt(cXj,2);
				this.accumulator.operand2 = Integer.parseInt(instruction.Addresses.getFirst(),2);
				this.accumulator.Add();
				String address = String.format("%16s", Integer.toBinaryString(this.accumulator.result)).replace(' ', '0');
				this.accumulator.clear();
				return this.processor.getMemory().Read(address);
			}
		}
		else
		{
			if(instruction.IndexRegisters.equals(IndexRegister.NoIndexing))
			{
				return instruction.Addresses.getFirst();
			}
			else
			{
				String cXj = this.processor.getMemory().Read(this.processor.getIndexRegisterValues().get(instruction.IndexRegisters.getFirst()));
				this.accumulator.operand1 = Integer.parseInt(cXj,2);
				this.accumulator.operand2 = Integer.parseInt(instruction.Addresses.getFirst(),2);
				this.accumulator.Add();
				return String.format("%16s", Integer.toBinaryString(this.accumulator.result)).replace(' ', '0');
			}
		}		
	}
}
