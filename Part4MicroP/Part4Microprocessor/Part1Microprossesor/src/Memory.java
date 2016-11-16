
public class Memory {
	
	public int[][] bank0;
	  public int[][] bank1;
	  public int[][] bank2;
	  public int[][] bank3;
	  public int[][] bank4;
	  public int[][] bank5;
	  public int[][] bank6;
	  public int[][] bank7;
	  private Cache L1Cache;
	  private Processor processor;

	  public Memory(Processor processor)
	  {
	    this.bank0 = new int[16][20]; // 0 - 16
	    this.bank1 = new int[16][20]; // 17 - 34
	    this.bank2 = new int[16][20]; // 35 - 48
	    this.bank3 = new int[16][20]; // 49 - 64
	    this.bank4 = new int[16][20]; // 65 - 80
	    this.bank5 = new int[16][20]; // 81 - 96
	    this.bank6 = new int[16][20]; // 97 - 112
	    this.bank7 = new int[16][20]; // 113 - 128
	    this.processor = processor;

	    this.L1Cache = new Cache(16,100,4, this.processor);
	  }

		public String Read(String addr) {

			String s = "";

			s = this.L1Cache.Read(Integer.parseInt(addr, 2), processor);
			
			return s;
		}
		
		public String ReadDirect(String addr)
		  {
		    String DATA = ""; String BankNumber = "";
		    int OffSet = 0;
		    try {
				Thread.currentThread().sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    addr = addr.length() != 11 ? String.format(new StringBuilder().append("%0").append(11 - addr.length()).append("d").toString(), new Object[] { Integer.valueOf(0) }) + addr : addr;

		    BankNumber = addr.substring(addr.length() - 3, addr.length());
		    OffSet = Integer.parseInt(addr.substring(0, addr.length() - 3), 2);

		    switch (BankNumber) {
		    case "000":
		      DATA = ReadDataFromBank(this.bank0, OffSet);
		      break;
		    case "001":
		      DATA = ReadDataFromBank(this.bank1, OffSet);
		      break;
		    case "010":
		      DATA = ReadDataFromBank(this.bank2, OffSet);
		      break;
		    case "011":
		      DATA = ReadDataFromBank(this.bank3, OffSet);
		      break;
		    case "100":
		      DATA = ReadDataFromBank(this.bank4, OffSet);
		      break;
		    case "101":
		      DATA = ReadDataFromBank(this.bank5, OffSet);
		      break;
		    case "110":
		      DATA = ReadDataFromBank(this.bank6, OffSet);
		      break;
		    case "111":
		      DATA = ReadDataFromBank(this.bank7, OffSet);
		    }

		    return DATA;
		  }

		  public String ReadDataFromBank(int[][] Bank, int OffSet)
		  {
		    String s = "";

		    for (int i = 0; i < 20; i++) {
		      s = s + Bank[OffSet][i];
		    }

		    return s;
		  }
		

		public void Insert(String strValue, String strAddr) 
		{

			 strValue = strValue.length() != 20 ? String.format(new StringBuilder().append("%0").append(20 - strValue.length()).append("d").toString(), new Object[] { Integer.valueOf(0) }) + strValue : strValue;

			    this.L1Cache.Write(strValue, Integer.parseInt(strAddr, 2));
		}
		
		public void InsertDirect(String DATA, String addr)
		  {		    
			int tLength = DATA.length();
			if(tLength != 20)
			{
				DATA = String.format(new StringBuilder().append("%0").append(20 - DATA.length()).append("d").toString(), new Object[] { Integer.valueOf(0) }) + DATA; 
			}

		    String BankNumber = "";

		    BankNumber = addr.substring(addr.length() - 3, addr.length());
		    int OffSet = Integer.parseInt(addr.substring(0, addr.length() - 3), 2);

		    switch (BankNumber) {
		    case "000":
		      InsertDataToBank(this.bank0, OffSet, DATA);
		      break;
		    case "001":
		      InsertDataToBank(this.bank1, OffSet, DATA);
		      break;
		    case "010":
		      InsertDataToBank(this.bank2, OffSet, DATA);
		      break;
		    case "011":
		      InsertDataToBank(this.bank3, OffSet, DATA);
		      break;
		    case "100":
		      InsertDataToBank(this.bank4, OffSet, DATA);
		      break;
		    case "101":
		      InsertDataToBank(this.bank5, OffSet, DATA);
		      break;
		    case "110":
		      InsertDataToBank(this.bank6, OffSet, DATA);
		      break;
		    case "111":
		      InsertDataToBank(this.bank7, OffSet, DATA);
		    }

		    processor.getFrontPanel().consolePrinter.append("Memory(" + Integer.parseInt(addr, 2) +") : " + DATA + "\n");
		  }

		  public void InsertDataToBank(int[][] Bank, int OffSet, String DATA)
		  {			  
		    for (int i = 0; i < 20; i++)
		      Bank[OffSet][i] = Integer.parseInt(Character.toString(DATA.charAt(i)));
		  }
}
