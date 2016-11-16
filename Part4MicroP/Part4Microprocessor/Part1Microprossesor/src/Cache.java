import java.util.LinkedList;

public class Cache {
	private int cSize;
	private int bitsize;
	private int blckSize;
	private int[][] cachedata;
	private LinkedList<String> writeBuffer;
	private Processor processor;

	public Cache(int csize, int bsize, int blsize, Processor processor) {
		cSize = csize; // 16
		bitsize = bsize; // 100
		blckSize = blsize; // 4
		cachedata = new int[csize][bitsize];
		writeBuffer = new LinkedList<String>();
		this.processor = processor;
	}

	public String Read(int address, Processor p) {

		String word = "";
		int block = 0;
		boolean onCache = false;
		
		try {
			Thread.currentThread().sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// calculate offset
		int offset = Integer.valueOf(address % blckSize);
		int tag = address - offset;
		System.out.println("Read Cache offset:" + offset + " tag:" + tag + " address:" + address);
		p.getFrontPanel().consolePrinter.append("\n Read address:" + address);
		String OffSet = "";
		OffSet = offset <= 1 ? "0" + String.format("%s", new Object[] { offset }) : Long.toBinaryString(offset);
		
		for (int i = 0; i < cSize; i++) {
			if (GetBlockTag(i) == tag) {
				this.cachedata[i][5] = Integer.parseInt(OffSet.substring(0, 1));
				this.cachedata[i][6] = Integer.parseInt(OffSet.substring(1, 2));
				// read hit
				// MainConsole.textField_1.append("Read Hit!\n");
				block = i;
				onCache = true;
				break;
			}
		}

		if (onCache == false) {
			// read miss
			this.processor.getFrontPanel().consolePrinter.append("\nRead Miss!");

			block = tag % 16;
			if (this.cachedata[block][1] == 1) {
				this.writeBuffer.add(TakeDataBitsFromtheBlock(block, 0, 99));
				Writeback(block);
			}

			// fetch the data from memory into cache
			Fetch(tag);
			this.cachedata[block][5] = Integer.parseInt(OffSet.substring(0, 1));
			this.cachedata[block][6] = Integer.parseInt(OffSet.substring(1, 2));
		}

		// return the word
		word = GetDataInBlock(block);
		return word;
	}

	public String Fetch(int tag) {
		String data = "";
		String msg = "";
		String Tag = "";
		int block = tag % 16;

		SetDataBitsIntheBlock("0000000", block, 0, 6);
		Tag = Long.toBinaryString(tag);
		Tag = String.format(new StringBuilder().append("%0").append(13 - Tag.length()).append("d").toString(),
				new Object[] { Integer.valueOf(0) }) + Tag;
		SetDataBitsIntheBlock(Tag, block, 7, 19);

		int indexStart = 20;
		int indexEnd = 39;

		for (int i = 1; i <= 4; i++) {
			data = this.processor.getMemory().ReadDirect(Long.toBinaryString(tag + i - 1));
			SetDataBitsIntheBlock(data, block, i * indexStart, indexEnd + (i - 1) * indexStart);
			msg = msg + String.format("%s", new Object[] { Integer.valueOf(tag + i - 1) }) + "-";
		}
		return msg;
	}

	public void Write(String data, int address) {
		int tag = 0;
		String add;
		boolean onCache = false;
		int offset = address % 4; // 0,1,2,3
		tag = address - offset; // 0-16		
		String OffSet = "";
		OffSet = offset <= 1 ? "0" + String.format("%s", new Object[] { offset }) : Long.toBinaryString(offset);		
		for (int i = 0; i <= 15; i++) {
			if (GetBlockTag(i) == tag) {
				this.cachedata[i][1] = 1;
				this.cachedata[i][5] = Integer.parseInt(OffSet.substring(0, 1));
				this.cachedata[i][6] = Integer.parseInt(OffSet.substring(1, 2));
				SetDataInBlock(data, i);
				processor.getFrontPanel().consolePrinter.append("\nWrite Hit!");
				onCache = true;
				break;
			}
		}

		if (!onCache) {
			processor.getFrontPanel().consolePrinter.append("\nWrite Miss! ");
			add = Long.toBinaryString(address);
			add = String.format(new StringBuilder().append("%0").append(20 - add.length()).append("d").toString(),
					new Object[] { Integer.valueOf(0) }) + add;
			this.processor.getMemory().InsertDirect(data, add);

			Fetch(tag);
		}
	}

	void Writeback(int block) {
		int strtIndex;
		int endIndex;
		// writing tag at back
		int tag = Integer.parseInt(TakeDataBitsFromtheBlock(block, 7, 19), 2);
		// writing data at back
		strtIndex = 20;
		endIndex = 39;
		String data = "";
		String address = "";

		for (int i = 1; i <= 4; i++) {
			data = TakeDataBitsFromtheBlock(block, i * strtIndex, endIndex + (i - 1) * strtIndex);
			address = Long.toBinaryString(tag + i - 1);
			address = String.format(
					new StringBuilder().append("%0").append(20 - address.length()).append("d").toString(),
					new Object[] { Integer.valueOf(0) }) + address;
			this.processor.getMemory().InsertDirect(data, address);
		}
	}

	private int GetBlockTag(int block) {
		String BlockTag = "";
		int blckTag = 0;
		int strtIndex = 7;
		int endIndex = 19;
		for (int i = strtIndex; i < endIndex; i++)
			BlockTag = BlockTag + cachedata[block][i];
		blckTag = Integer.parseInt(BlockTag, 2);
		return blckTag;
	}

	private String GetDataInBlock(int block) {
		String data = "";
		int strtIndex = 0;
		int endIndex = 0;
		int offset = 0;
		// calculate the offset
		offset = 1 * this.cachedata[block][6] + 2 * this.cachedata[block][5];
		switch (offset) {

		case 0:
			strtIndex = 20;
			endIndex = 39;
			break;

		case 1:
			strtIndex = 40;
			endIndex = 59;
			break;

		case 2:
			strtIndex = 60;
			endIndex = 79;
			break;

		case 3:
			strtIndex = 80;
			endIndex = 99;
			break;
		}

		for (int i = strtIndex; i <= endIndex; i++)
			data = data + cachedata[block][i];

		return data;
	}

	private void SetDataInBlock(String data, int block) {
		int strtIndex = 0;
		int endIndex = 0;
		int offset = 0;
		// calcuate offset
		offset = 1 * this.cachedata[block][6] + 2 * this.cachedata[block][5];
		switch (offset) {

		case 0:
			strtIndex = 20;
			endIndex = 39;
			break;

		case 1:
			strtIndex = 40;
			endIndex = 59;
			break;

		case 2:
			strtIndex = 60;
			endIndex = 79;
			break;

		case 3:
			strtIndex = 80;
			endIndex = 99;
			break;
		}

		int j = 0;
		for (int i = strtIndex; i < endIndex; i++) {
			cachedata[block][i] = Integer.parseInt(data.substring(j, j + 1));
			j++;
		}

	}

	private void SetDataBitsIntheBlock(String mData, int block, int indexStart, int indexEnd) {
		int j = 0;
		for (int i = indexStart; i <= indexEnd; i++) {
			this.cachedata[block][i] = Integer.parseInt(mData.substring(j, j + 1));
			j++;
		}
	}

	private String TakeDataBitsFromtheBlock(int block, int indexStart, int indexEnd) {
		String sData = "";

		for (int i = indexStart; i <= indexEnd; i++) {
			sData = sData + this.cachedata[block][i];
		}
		return sData;
	}

}
