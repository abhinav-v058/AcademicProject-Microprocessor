import java.util.LinkedList;
import java.util.ListIterator;

public class InstructionSet {
	
	private LinkedList<Instruction> instructions;	
	
	public InstructionSet()
	{
		instructions = new LinkedList<Instruction>();		
	}
	
	public Boolean Add(String instruction)
	{
		try
		{
			if(!instructions.isEmpty())
			{
				if(instructions.getLast().Opcode>64)
					throw new Exception();
				instructions.add(new Instruction((instructions.getLast().Opcode+1),instruction));
			}
			else
			{
				instructions.add(new Instruction(1,instruction));
			}
		}
		catch(Exception ex)
		{
			return false;
		}
		
		return true;
	}
	
	public Boolean Add(int opcode, String instruction)
	{
		try
	
		{
			if(opcode<64)
			instructions.add(new Instruction(opcode,instruction));
			else
				throw new Exception();
		}
	
		catch(Exception ex)
	
		{		
			return false;	
		}	
	
		return true;		
	}

	public Instruction GetInstruction(int opcode)
	{		
		for(int i =0; i<instructions.size();i++)
		{
            if(instructions.get(i).Opcode == opcode)
            {
            	return instructions.get(i);
            }  
			
		}
        
        return null;
	}
}
