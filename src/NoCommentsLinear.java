import java.util.Arrays;

public class NoCommentsLinear {

	int m_nTableSize = 13;
	DataObject[] m_ObjectArray;
	int itemCount = 0;
	
	public NoCommentsLinear() {
		m_ObjectArray = new DataObject[m_nTableSize];
	}
	
	public NoCommentsLinear( int nTableSize ) {
		m_nTableSize = nTableSize;
		m_ObjectArray = new DataObject[m_nTableSize];
	}
	
	public void Expand() {
		itemCount = 0;
		DataObject[] old = Arrays.copyOf(m_ObjectArray, m_ObjectArray.length);
		m_nTableSize = (int) Utility.NextPrime(m_nTableSize * 2);
		m_ObjectArray = new DataObject[m_nTableSize];
		
		for (DataObject x : old) {
			if (x != null && x.GetKey().length() > 0) {
				this.put(x);
			}
		}
	}
	
	public void put( DataObject objData ) {
		long hash = Utility.HashFromString(objData.GetKey());
		int index = (int) (hash %= m_nTableSize);
		
		while (m_ObjectArray[index] != null) {
			if (objData.GetKey() == m_ObjectArray[index].GetKey()) {
				m_ObjectArray[index] = objData;
				return;
			}
			index++;
			index %= m_nTableSize;
		}
		
		m_ObjectArray[index] = objData;
		itemCount++;
		
		if (itemCount > (m_nTableSize / 2)) {
			Expand();
		}
	}

	public DataObject get( String strKey ) {
		long hash = Utility.HashFromString(strKey);
		int index = (int) (hash %= m_nTableSize);
		
		while (m_ObjectArray[index] != null) {
			if (m_ObjectArray[index].GetKey() == strKey) {
				return m_ObjectArray[index];
			}
			index++;
			index %= m_nTableSize;
		}
		
		return null;
	}
}
