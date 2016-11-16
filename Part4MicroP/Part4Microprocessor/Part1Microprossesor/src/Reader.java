
public class Reader {
	
	boolean isBinary;	
	Processor processor;
	int sentenceCount;
	boolean isFirstBinary;
	
	public Reader(Processor _processor)
	{		
		isBinary = false;
		this.processor = _processor;
		this.sentenceCount = 0;
		this.isFirstBinary = true;		
	}
	
	public int ConvertAndStore(String line,  int addrCount){
		
		if(checkForBinary(line))
		{	System.out.println("Inserting Binary");	
		/*
			if(sentenceCount > 0 && this.isFirstBinary)
			{
				addrCount = 97;
				this.isFirstBinary = false;
			}
			*/
			processor.getMemory().InsertDirect(line, String.format("%20s", Integer.toBinaryString(addrCount++)).replace(' ', '0'));
		}
		else
		{			
			int numericValue;
			switch(sentenceCount)
			{
			case 0:
				addrCount = 33;
				break;
			case 1:
				addrCount = 49;
				break;
			case 2:
				addrCount = 65;
				break;
			case 3:
				addrCount = 81;
				break;
			case 4:
				addrCount = 97;
				break;
			case 5:
				addrCount = 113;
				break;
			
			}
			
			System.out.println("Inserting Characters");
			for(int i =0;i<line.length();i++)
			{				
				numericValue = Character.isWhitespace(line.charAt(i))? 32 : Character.getNumericValue(line.charAt(i));
				processor.getMemory().InsertDirect(String.format("%20s", Integer.toBinaryString(numericValue)).replace(' ', '0'), String.format("%20s", Integer.toBinaryString(addrCount++)).replace(' ', '0'));				
			}
			
			sentenceCount++;
		}
		
		return addrCount;
	}

	public Boolean checkForBinary(String line) {
		int characters = 0;
		char temp;
		for(int i =0;i<line.length();i++)
		{
			temp = line.charAt(i);			
			if(temp!='0' && temp!='1')
			{
				characters++;
			}
						
		}
		
		this.isBinary = characters>0?false:true;
		
		return this.isBinary;
	}

}
